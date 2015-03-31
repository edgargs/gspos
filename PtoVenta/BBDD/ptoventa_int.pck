CREATE OR REPLACE PACKAGE PTOVENTA.Ptoventa_Int AS

  TYPE FarmaCursor IS REF CURSOR;

  g_cNumIntVentas PBL_NUMERA.COD_NUMERA%TYPE := '018';
  g_cNumIntPed PBL_NUMERA.COD_NUMERA%TYPE := '022';
  g_cNumIntMov2 PBL_NUMERA.COD_NUMERA%TYPE := '021';
  g_cNumGuiaProv PBL_NUMERA.COD_NUMERA%TYPE := '027';
  g_cNumProdQuiebre INTEGER :=450;
  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
  v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
  g_cNumDias INTEGER :=5;

  v_gUltimoDoc VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE := NULL;

  g_cCodMatriz PBL_LOCAL.COD_LOCAL%TYPE := '009';
  --Descripcion: Calcula los totales de ventas de Boletas en un dia.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  PROCEDURE INT_RESUMEN_VENTAS_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2);

  --Descripcion: Obtiene las series asignadas al local.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_SERIES_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2) RETURN FarmaCursor;

  --Descripcion: Obtiene la numeracion de boletas emitidas en un dia para una serie en un local.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_NUM_DOC_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2) RETURN FarmaCursor;

  --Descripcion: Obtiene la numeracion de boletas emitidas en un dia para una serie en un local.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_NUM_DOC_REF(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR) RETURN VARCHAR2;

  --Descripcion: Obtiene los distintos productos vendidos por boletas.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  --04/12/2007  dubilluz  modificacion
  FUNCTION INT_GET_PROD_VENDIDOS_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene los totales por producto.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_TOTALES_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2, cTipVenta_in IN CHAR, cCodProd_in IN CHAR,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene los detalles de fraccion del producto.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_DET_FRAC_PROD(cCodGrupoCia_in IN CHAR,cCodProd_in IN CHAR,cPordFrac_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene la cantidad de presentacion.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
   FUNCTION INT_GET_CANT_PRESENT(cCodGrupoCia_in IN CHAR,cCodProd_in IN CHAR,cPordFrac_in IN CHAR,nValFrac_in IN NUMBER) RETURN NUMBER;
  --FACTURAS--
  --Descripcion: Calcula los totales de ventas de Facturas en un dia.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  PROCEDURE INT_RESUMEN_VENTAS_FACTURAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2);

  --Descripcion: Obtiene las facturas emitidas.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_FACTURAS_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2,cNumSerie_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle de una factura.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  --04/12/2007  DUBILLUZ  Modificacion
  FUNCTION INT_GET_DET_FACT(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cSecComp_in IN CHAR) RETURN FarmaCursor;
  --NOTA DEVOLUCION--
  --Descripcion: Calcula los totales de ventas de Facturas en un dia.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  PROCEDURE INT_RESUMEN_VENTAS_DEVOLUCION(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2);

  --Descripcion: Obtiene las facturas emitidas.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_DEVOLUCION_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2,cNumSerie_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el secuencial.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_SEC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR) RETURN NUMBER;

  --Descripcion: Obtiene el correlativo.
  --Fecha       Usuario		Comentario
  --31/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_CORR(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,nNumSecDoc_in IN NUMBER) RETURN CHAR;

  --Descripcion: Graba el redondeo de una serie de boletas en un local.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  PROCEDURE SET_REDONDEO_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR);

  --Descripcion: Graba el redondeo de una factura.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  PROCEDURE SET_REDONDEO_FACTURA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cSecComp_in IN CHAR);

  /*EJECUTA RESUMEN DE LOCAL*/
  --Descripcion: Genera los datos por dia.
  --Fecha       Usuario		Comentario
  --17/03/2006  ERIOS    	Creación
  PROCEDURE INT_GET_RESUMEN_DIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2);

  --Descripcion: Procesa el resumen diario.
  --Fecha       Usuario		Comentario
  --30/03/2006  ERIOS    	Creación
  PROCEDURE INT_EJECT_RESUMEN_DIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2);

  --Descripcion: Setea el numero de documento, en las boletas procesadas.
  --Fecha       Usuario		Comentario
  --04/04/2006  ERIOS    	Creación
  PROCEDURE SET_MUN_SEC_PROCESO_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2, nNumSecDoc_in IN NUMBER, cNumCompI_in IN CHAR,cNumCompF_in IN CHAR);

  --Descripcion: Setea el numero de documento, en las facturas procesadas.
  --Fecha       Usuario		Comentario
  --04/04/2006  ERIOS    	Creación
  PROCEDURE SET_MUN_SEC_PROCESO_FACTURAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cSecComp_in IN CHAR, nNumSecDoc_in IN NUMBER);

  --Descripcion: Setea el numero de documento, en los comprobantes anulados.
  --Fecha       Usuario		Comentario
  --04/04/2006  ERIOS    	Creación
  PROCEDURE SET_MUN_SEC_PROCESO_DEVOL(cSecComp_in IN CHAR, nNumSecDoc_in IN NUMBER);

  --INTERFAZ INVENTARIO

  --Descripcion: Obtiene el pedido de reposicion a ser enviado.
  --Fecha       Usuario		Comentario
  --05/04/2006  ERIOS    	CreaciÃ³n
  --03/09/2007  ERIOS  Se movio al paquete PTOVENT_INT_PED_REP
  /*PROCEDURE INT_RESUMEN_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  vFecProceso_in IN VARCHAR2, cIndUrgencia_in IN CHAR DEFAULT 'N',
  cIndPedAprov_in IN CHAR DEFAULT 'N');*/

  --Descripcion: Obtiene el la confirmacion de recepcion de productos.
  --Fecha       Usuario		Comentario
  --07/04/2006  ERIOS    	CreaciÃ³n
  PROCEDURE INT_RESUMEN_REC_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --INTERFAZ MOVIMIENTOS

  --Descripcion: Obtiene los pedidos de transferencias a matriz.
  --Fecha       Usuario		Comentario
  --07/04/2006  ERIOS    	Creación
  PROCEDURE INT_RESUMEN_MOV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 0);

  --NOTA DE CREDITO
  --Descripcion: Obtiene el resumen de las notas de credito.
  --Fecha       Usuario		Comentario
  --21/04/2006  ERIOS    	Creación
  --06/07/2006  ERIOS    	Modificación: Agregado la varriable v_vNumDocAnul_A
  PROCEDURE INT_RESUMEN_NOTA_CREDITO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cTipDocOrigen_in IN CHAR, vFecProceso_in IN VARCHAR2);

  --Descripcion: Obtiene el detalle de la nota de credito.
  --Fecha       Usuario		Comentario
  --21/04/2006  ERIOS    	Creación
  FUNCTION INT_GET_DET_NC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cTipDoc_in IN CHAR,cTipDocOrigen_in IN CHAR, vFecProceso_in IN VARCHAR2) RETURN FarmaCursor;

  --Descripcion: Obtiene el redondeo de la nota de credito.
  --Fecha       Usuario		Comentario
  --26/04/2006  ERIOS    	Creación
  --06/07/2006  ERIOS    	Modificación: vNumDocAnul_in
  PROCEDURE SET_REDONDEO_NC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cSecComp_in IN CHAR,vNumDocAnul_in IN CHAR);

  --INTERFAZ MOVIMIENTOS 2

  --Descripcion: Obtiene los ajustes de kardex.
  --Fecha       Usuario		Comentario
  --27/04/2006  ERIOS    	Creación
  PROCEDURE INT_RESUMEN_MOV2(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2);

  --Descripcion: Genera archivo de Interface Rep Ped.
  --Fecha       Usuario		Comentario
  --28/04/2006  ERIOS     	Creación
  --03/09/2007  ERIOS  Se movio al paquete PTOVENT_INT_PED_REP
  /*PROCEDURE INT_GET_RESUMEN_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);*/

  --Descripcion: Genera archivo de Interface Conf Rep Ped.
  --Fecha       Usuario		Comentario
  --28/04/2006  ERIOS    	Creación
  PROCEDURE INT_GET_RESUMEN_REC_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Genera archivo de Interface Devoluciones.
  --Fecha       Usuario		Comentario
  --28/04/2006  ERIOS    	Creación
  PROCEDURE INT_GET_RESUMEN_MOV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Genera archivo de Interface Ajustes.
  --Fecha       Usuario		Comentario
  --28/04/2006  ERIOS    	Creación
  PROCEDURE INT_GET_RESUMEN_MOV2(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Obtiene el redondeo de una devolucion.
  --Fecha       Usuario		Comentario
  --11/05/2006  ERIOS    	Creación
  PROCEDURE SET_REDONDEO_DEVOLUCION(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cSecComp_in IN CHAR);

  --Descripcion: Procesa la interface de ventas para un rango de fechas
  --Fecha       Usuario		Comentario
  --12/05/2006  RCASTRO    	Creación
  PROCEDURE INT_EJECT_RESUMEN_DIA_RANGO (codigocompania_in IN CHAR, cCodLocal_in IN CHAR, fechainicio_in IN CHAR, fechafin_in IN CHAR);

  --Descripcion: Genera los archivos de la interface de ventas para un rango de fechas
  --Fecha       Usuario		Comentario
  --12/05/2006  RCASTRO    	Creación
  PROCEDURE INT_GET_RESUMEN_DIA_RANGO (codigocompania_in IN CHAR, cCodLocal_in IN CHAR, fechainicio_in IN CHAR, fechafin_in IN CHAR);

  --Descripcion: Valida si el monto de ventas de la Interface, es igual al reporte de ventas.
  --Fecha       Usuario		Comentario
  --17/05/2006  ERIOS    	Creación
  PROCEDURE VALIDA_MONTO_VENTAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2);

  --Descripcion: Verifica si existe registros de comprobantes para un dia especificado
  --Fecha       Usuario		Comentario
  --01/06/2006  ERIOS    	Creación
  FUNCTION INT_VERIFICA_COMP_DIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,vFecha_in IN VARCHAR2) RETURN NUMBER;

  --Descripcion: Envia correo de informacion de estado de generacion de interface.
  --Fecha       Usuario		Comentario
  --10/07/2006  ERIOS    	Creación
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2);

  --Descripcion: Procedimiento para revertir interfaz de ventas.
  --Fecha       Usuario		Comentario
  --14/09/2006  ERIOS    	Creación
  PROCEDURE INT_REVERTIR_VENTAS(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                vFecha_in IN VARCHAR2,
                                vIdUsu_in IN VARCHAR2);

  --Descripcion: Genera la clasificación de los productos (material).
  --Fecha       Usuario		Comentario
  --16/11/2006  ERIOS    	Creación
  --04/12/2007  DUBILLUZ  MODIFICACION
  PROCEDURE INT_RESUMEN_CLASMAT(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Obtiene el archivo de clasificacion material.
  --Fecha       Usuario		Comentario
  --16/11/2006  ERIOS    	Creación
  PROCEDURE INT_GET_RESUMEN_CLASMAT(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Genera las guías para las ventas de productos sin control de stock.
  --Fecha       Usuario		Comentario
  --05/12/2006  ERIOS    	Creación
  PROCEDURE INT_GENERA_GUIA_PROV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  nDiasAtras_in IN INTEGER DEFAULT 1);

  --Descripcion: Reemplaza los caracteres no validos.
  --Fecha       Usuario		Comentario
  --12/07/2007  ERIOS    	Creacion
  FUNCTION REEMPLAZAR_CARACTERES(vCadena_in IN VARCHAR2)
  RETURN VARCHAR2;

  --Descripcion: Obtiene los ajustes de kardex del tipo CC.
  --Fecha       Usuario		Comentario
  --11/09/2007  ERIOS    	Creacion
  PROCEDURE INT_RESUMEN_CC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2);

  --Descripcion: Obtiene los ajustes de kardex del tipo RD.
  --Fecha       Usuario		Comentario
  --11/10/2007  ERIOS    	Creacion
  PROCEDURE INT_RESUMEN_RD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2);

  --Descripcion: Genera archivo de Interface Ajustes - RD.
  --Fecha       Usuario		Comentario
  --11/10/2006  ERIOS    	Creacion
  PROCEDURE INT_GET_RESUMEN_RD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Se verifica y corrige el orden del documento.
  --Fecha       Usuario		Comentario
  --30/01/2008  ERIOS    	Creacion
  PROCEDURE VALIDA_ORDEN_DOCUMENTO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  vFecProceso_in IN VARCHAR2);

  --Descripcion:
  --Fecha       Usuario		Comentario
  --11/03/2009  JOLIVA    	Creacion
  FUNCTION INT_GET_IND_FRAC_NVO_CC(cCodGrupoCia_in IN CHAR, cCodProdOrigenCC_in IN CHAR, cCodProdDestinoCC_in IN CHAR) RETURN CHAR;


  --
  --21/04/2009 JCORTEZ  Creacion
   PROCEDURE INT_RES_VTS_TICKET_BOLETAS(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cTipDoc_in      IN CHAR,
                                        vFecProceso_in  IN VARCHAR2);

  --
  --21/04/2009 JCORTEZ  Creacion
   FUNCTION INT_GET_NUM_DOC_TICKET_BOLETAS(cCodGrupoCia_in IN CHAR,
                                            cCodLocal_in    IN CHAR,
                                            cTipDoc_in      IN CHAR,
                                            cNumSerie_in    IN CHAR,
                                            vFecProceso_in  IN VARCHAR2)
   RETURN FarmaCursor;

    --
  --21/04/2009 JCORTEZ  Creacion
  FUNCTION INT_GET_NUM_DOC_REF1(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cTipDoc_in IN CHAR,
                                cNumSerie_in IN CHAR,
                                vFecProceso_in IN VARCHAR2,
                                cNumCompI_in IN CHAR,
                                cNumCompF_in IN CHAR)
  RETURN VARCHAR2;

   --
  --21/04/2009 JCORTEZ  Creacion
  PROCEDURE SET_REDONDEO_TICKET_BOLETAS(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      nNumSecDoc_in IN NUMBER,
                                      cTipDoc_in IN CHAR,
                                      cNumSerie_in IN CHAR,
                                      vFecProceso_in IN VARCHAR2,
                                      cNumDocRef_in IN CHAR,
                                      cNumCompI_in IN CHAR,
                                      cNumCompF_in IN CHAR);

    --
  --21/04/2009 JCORTEZ  Creacion
   PROCEDURE INT_RES_VTS_TICKET_DEVOLUCION(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in IN CHAR,
                                          cTipDoc_in IN CHAR,
                                          vFecProceso_in IN VARCHAR2);

    --
  --21/04/2009 JCORTEZ  Creacion
   PROCEDURE INT_RES_NOTA_CRED_TICKET_BOL(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   cTipDoc_in IN CHAR,
                                   cTipDocOrigen_in IN CHAR,
                                   vFecProceso_in IN VARCHAR2);
END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_INT" AS

  PROCEDURE INT_RESUMEN_VENTAS_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2)
  AS
    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;
    v_vNumDocRef INT_VENTA.NUM_DOC_REF%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;

    curSeries FarmaCursor;
    v_cSerie VTA_SERIE_LOCAL.NUM_SERIE_LOCAL%TYPE;

    curProd FarmaCursor;
    v_cCodProd INT_VENTA.COD_PROD%TYPE;
    v_cTipoVenta INT_VENTA.TIP_VENTA%TYPE;
    --v_cUnidPresent INT_VENTA.UNID_MED%TYPE;
    v_vIndLab INT_VENTA.TIP_LABORATORIO%TYPE;

    curTotales FarmaCursor;
    v_cValPrecio INT_VENTA.PRECIO%TYPE;
    v_cIndFrac INT_VENTA.IND_VTA_FRACC%TYPE;
    v_cCantVend INT_VENTA.CANTIDAD%TYPE;
    v_cValTotal INT_VENTA.MONTO_TOTAL%TYPE;
    v_cValIgv INT_VENTA.MONTO_IGV%TYPE;

    curDetFrac FarmaCursor;
    v_cUnidVenta INT_VENTA.UNID_MED%TYPE;
    v_cFactConv INT_VENTA.FACT_CONVERSION%TYPE;

    --j INTEGER:=0;

    curDocRef FarmaCursor;
    v_cNumCompI VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
    v_cNumCompF VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
  BEGIN

    --OBTENER SERIES ASIGNADAS AL LOCAL
    curSeries:=INT_GET_SERIES_LOCAL(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,vFecProceso_in);
    LOOP
      FETCH curSeries INTO v_cSerie;
    EXIT WHEN curSeries%NOTFOUND;
      --DBMS_OUTPUT.PUT_LINE(v_cSerie);
      --CAMBIAR POR CURSOR
      curDocRef:=INT_GET_NUM_DOC_BOLETAS(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,v_cSerie ,vFecProceso_in);

      --OBTIENE LOS PRODUCTOS PARA EL RANGO DE BOLETAS VENDIDAS
      --DBMS_OUTPUT.PUT_LINE(curDocRef%ROWCOUNT);
      LOOP
        FETCH curDocRef INTO v_cNumCompI, v_cNumCompF;
      EXIT WHEN curDocRef%NOTFOUND;
        --DBMS_OUTPUT.PUT_LINE(v_cSecCompI||','|| v_cSecCompF);
        curProd:=INT_GET_PROD_VENDIDOS_BOLETAS(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,v_cSerie ,vFecProceso_in,v_cNumCompI, v_cNumCompF);
        --curProd:=INT_GET_PROD_VENDIDOS_BOLETAS(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,v_cSerie ,vFecProceso_in);
        v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);
        v_vNumDocRef:=INT_GET_NUM_DOC_REF(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,v_cSerie ,vFecProceso_in,v_cNumCompI, v_cNumCompF);
        LOOP
          FETCH curProd INTO v_cCodProd,v_cTipoVenta,v_vIndLab;
        EXIT WHEN curProd%NOTFOUND;
          --DBMS_OUTPUT.PUT_LINE(v_cCodProd||' '||v_cTipoVenta||' '||v_vIndLab||' '||j);
          --OBTIENE LOS TOTALES DE VENTA POR PRODUCTO
          curTotales:=INT_GET_TOTALES_PROD(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in, v_cSerie,vFecProceso_in, v_cTipoVenta, v_cCodProd,v_cNumCompI, v_cNumCompF);
          FETCH curTotales INTO v_cValPrecio,v_cIndFrac,v_cCantVend,v_cValTotal,v_cValIgv;
          CLOSE curTotales;
          --OBTIENE EL DETALLE DE FRACC DEL PRODUCTO
          curDetFrac:=INT_GET_DET_FRAC_PROD(cCodGrupoCia_in,v_cCodProd,v_cIndFrac);
          FETCH curDetFrac INTO v_cUnidVenta,v_cFactConv;
          CLOSE curDetFrac;
          --OBTENER CORRELATIVO
          v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc);

          --DBMS_OUTPUT.PUT_LINE(v_cCorr||','||v_vNumDocRef||','||v_cCodProd||','||v_cCantVend||','||v_cValTotal||','||v_cValIgv);
            INSERT INTO INT_VENTA(COD_GRUPO_CIA,COD_LOCAL,NUM_SEC_DOC,CORRELATIVO,
                                  FEC_PROCESO,
                                  RUC_CLI,
                                  RAZ_SOCIAL,
                                  CLASE_DOC,
                                  NUM_DOC_REF,
                                  TIP_VENTA,
                                  TIP_LABORATORIO,
                                  COD_PROD,
                                  CANTIDAD,
                                  UNID_MED,
                                  IND_VTA_FRACC,
                                  FACT_CONVERSION,
                                  PRECIO,
                                  MONTO_IGV,
                                  MONTO_TOTAL,
                                  NUM_CANTIDAD,
                                  NUM_FACT_CONVERSION,
                                  NUM_MONTO_TOTAL,
                                  TIP_POSICION  )
            VALUES(cCodGrupoCia_in, cCodLocal_in,v_nNumSecDoc,v_cCorr,
                              TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
                              '',
                              '',--RAZ_SOCIAL
                              '1',
                              v_vNumDocRef,
                              v_cTipoVenta,
                              v_vIndLab,
                              v_cCodProd,
                              '0'||TRIM(v_cCantVend),
                              v_cUnidVenta,
                              v_cIndFrac,
                              DECODE(v_cFactConv,NULL,'     ','0'||TRIM(v_cFactConv)),
                              '0'||TRIM(v_cValPrecio),
                              '0'||TRIM(v_cValIgv),
                              '0'||TRIM(v_cValTotal),
                              TO_NUMBER(TRIM(v_cCantVend),'00000000000.000'),
                              DECODE(v_cFactConv,NULL,0,TO_NUMBER(v_cFactConv)),
                              TO_NUMBER(TRIM(v_cValTotal),'000000000.00'),
                              '1');

        END LOOP;
        --SET_REDONDEO_BOLETAS
        SET_REDONDEO_BOLETAS(cCodGrupoCia_in, cCodLocal_in,v_nNumSecDoc, cTipDoc_in , v_cSerie ,vFecProceso_in ,v_vNumDocRef ,v_cNumCompI, v_cNumCompF);
        --SET PROCESO EN DOCUMENTOS
        SET_MUN_SEC_PROCESO_BOLETAS(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,v_cSerie ,vFecProceso_in, v_nNumSecDoc,v_cNumCompI, v_cNumCompF);

      END LOOP;
    END LOOP;
    v_gUltimoDoc := NULL;
  END;

  /****************************************************************************/
  FUNCTION INT_GET_SERIES_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2)
  RETURN FarmaCursor
  IS
    curSeries FarmaCursor;
  BEGIN
    OPEN curSeries FOR
    SELECT NUM_SERIE_LOCAL
    FROM VTA_SERIE_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TIP_COMP = cTipDoc_in
          AND EST_SERIE_LOCAL = 'A'
          AND EXISTS             (SELECT 1
                                  FROM VTA_COMP_PAGO
                                  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                        AND COD_LOCAL = cCodLocal_in
                                        AND TIP_COMP_PAGO = cTipDoc_in
                                        AND SUBSTR(NUM_COMP_PAGO,1,3) = NUM_SERIE_LOCAL
                                        AND (FEC_CREA_COMP_PAGO BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS') OR
                                             FEC_ANUL_COMP_PAGO BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS') )
                                            );
 --MODIFICACION JLUNA 20081715 OPTIMIZA LA CONSULTA
 /*   SELECT NUM_SERIE_LOCAL
    FROM VTA_SERIE_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TIP_COMP = cTipDoc_in
          AND EST_SERIE_LOCAL = 'A'
          AND NUM_SERIE_LOCAL IN (SELECT DISTINCT SUBSTR(NUM_COMP_PAGO,1,3)
                                  FROM VTA_COMP_PAGO
                                  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                        AND COD_LOCAL = cCodLocal_in
                                        AND TIP_COMP_PAGO = cTipDoc_in
                                        AND (FEC_CREA_COMP_PAGO BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS') OR
                                             FEC_ANUL_COMP_PAGO BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS') )
                                            );*/

    RETURN curSeries;
  END;
  /****************************************************************************/
  FUNCTION INT_GET_NUM_DOC_REF(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vBoletas VARCHAR2(25);
    v_vBoletaInicial VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
    v_vBoletaFinal VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
  BEGIN
    IF v_gUltimoDoc IS NULL THEN
      --GET ULTIMO DOCUMENTO EMITIDO
      /*SELECT LPAD(NVL((MAX(NUM_COMP_PAGO)+1),cNumSerie_in||'0000001'),10,'0')
      INTO v_gUltimoDoc
      FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND B.TIP_COMP_PAGO = cTipDoc_in
            AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
            AND B.EST_PED_VTA = 'C'
            AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy'))
            AND NUM_SEC_DOC_SAP IS NOT NULL
            AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
            AND C.COD_LOCAL = B.COD_LOCAL
            AND C.NUM_PED_VTA = B.NUM_PED_VTA;*/
      --GET ULTIMO DOCUMENTO PROCESADO
      SELECT cNumSerie_in||LPAD(NVL(TRIM(SUBSTR(MAX(NUM_DOC_REF),018)+1),'0000001'),7,'0')
        INTO v_gUltimoDoc
      FROM INT_VENTA
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND CLASE_DOC = 1;
    END IF;
    v_vBoletaInicial := v_gUltimoDoc;
    --DBMS_OUTPUT.PUT_LINE(v_vBoletaInicial);
    --DBMS_OUTPUT.PUT_LINE(cNumCompI_in||'>'||cNumCompF_in);
    --OBTIENE ULTIMO DOCUMENTO EMITIDO EN EL RANGO
    v_vBoletaFinal := TRIM(cNumCompF_in);
    /*SELECT TRIM(MAX(NUM_COMP_PAGO))
    INTO v_vBoletaFinal
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND B.TIP_COMP_PAGO = cTipDoc_in
          AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
          AND B.EST_PED_VTA = 'C'
          AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
          AND C.NUM_SEC_DOC_SAP IS NULL
          AND C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
          AND B.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND C.COD_LOCAL = B.COD_LOCAL
          AND C.NUM_PED_VTA = B.NUM_PED_VTA;*/
    --DBMS_OUTPUT.PUT_LINE(v_vBoletas);
    --DBMS_OUTPUT.PUT_LINE(v_vBoletaFinal||'<'||v_vBoletaInicial);
    IF v_vBoletaFinal < v_vBoletaInicial THEN
      v_vBoletaFinal := v_vBoletaInicial;
      --DBMS_OUTPUT.PUT_LINE(v_vBoletaFinal);
    END IF;
    v_gUltimoDoc := LPAD((v_vBoletaFinal+1),10,'0');

    v_vBoletas := '03-00'||cNumSerie_in||'-'||SUBSTR(v_vBoletaInicial,4)||'-'||SUBSTR(v_vBoletaFinal,4);
    RETURN v_vBoletas;
  END;
  /****************************************************************************/
  FUNCTION INT_GET_NUM_DOC_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2)
  RETURN FarmaCursor
  IS
    CURSOR curBoletas IS
    SELECT C.SEC_COMP_PAGO,C.CANT_ITEM, C.NUM_COMP_PAGO
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND B.TIP_COMP_PAGO = cTipDoc_in
          AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
          AND B.EST_PED_VTA = 'C'
          AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
          AND C.NUM_SEC_DOC_SAP IS NULL
          AND B.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND C.COD_LOCAL = B.COD_LOCAL
          AND C.NUM_PED_VTA = B.NUM_PED_VTA
      ORDER BY 3;
    v_rCurBoletas curBoletas%ROWTYPE;

    CURSOR curProductos(cNumComp_in IN CHAR) IS
    SELECT D.COD_PROD,DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5') AS TIP_VENTA,C.NUM_COMP_PAGO
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B,VTA_PEDIDO_VTA_DET D
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND B.TIP_COMP_PAGO = cTipDoc_in
          AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
          AND B.EST_PED_VTA = 'C'
          AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
          AND C.NUM_COMP_PAGO = cNumComp_in
          AND C.NUM_SEC_DOC_SAP IS NULL
          AND B.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND C.COD_LOCAL = B.COD_LOCAL
          AND C.NUM_PED_VTA = B.NUM_PED_VTA
          AND B.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND B.COD_LOCAL = D.COD_LOCAL
          AND B.NUM_PED_VTA = D.NUM_PED_VTA
          AND C.SEC_COMP_PAGO = D.SEC_COMP_PAGO
      ORDER BY 1;
    v_rCurProductos curProductos%ROWTYPE;

    v_nIndBoleta INTEGER:=1;
    i INTEGER;
    v_nTotalProd INTEGER;
    v_cant_rangos INTEGER;

    curRangos FarmaCursor;
  BEGIN
    --INICIO
    DELETE FROM TMP_RANGO_BOLETAS;
    DELETE FROM TMP_PROD_INT_VENTA;
    --CUERPO
    OPEN curBoletas;
    LOOP
      IF v_nIndBoleta = 1 THEN
        FETCH curBoletas INTO v_rCurBoletas;
      END IF;
    EXIT WHEN curBoletas%NOTFOUND;
      --DBMS_OUTPUT.PUT_LINE(v_rCurBoletas.SEC_COMP_PAGO);
      i := 0;
      FOR v_rCurProductos IN curProductos(v_rCurBoletas.NUM_COMP_PAGO)
      LOOP
        BEGIN
         -- DBMS_OUTPUT.PUT_LINE(v_rCurProductos.COD_PROD||','||v_rCurProductos.NUM_COMP_PAGO);
          i:=i+1;
          INSERT INTO TMP_PROD_INT_VENTA(COD_PROD,
                                          TIP_VENTA,
                                          NUM_COMP_PAGO,
                                          NUM_COMP_PAGO_2)
          VALUES(v_rCurProductos.COD_PROD,v_rCurProductos.TIP_VENTA,v_rCurProductos.NUM_COMP_PAGO,
          v_rCurProductos.NUM_COMP_PAGO);
          --DBMS_OUTPUT.PUT_LINE(v_rCurProductos.COD_PROD||','||v_rCurProductos.SEC_COMP_PAGO);
        EXCEPTION
          WHEN OTHERS THEN
         --  DBMS_OUTPUT.PUT_LINE('DUPLICADO->'||v_rCurProductos.COD_PROD||','||v_rCurProductos.NUM_COMP_PAGO);
           IF i = v_rCurBoletas.CANT_ITEM THEN
            UPDATE TMP_PROD_INT_VENTA
            SET NUM_COMP_PAGO = v_rCurProductos.NUM_COMP_PAGO
            WHERE COD_PROD = v_rCurProductos.COD_PROD
                  AND TIP_VENTA = v_rCurProductos.TIP_VENTA;
           END IF;
        END;

        SELECT COUNT(*)
          INTO v_nTotalProd
        FROM TMP_PROD_INT_VENTA;
        IF v_nTotalProd = (g_cNumProdQuiebre-1) THEN --(-1) SE DEBE AL AJUSTE QUE SE APLICA AL COMPROBANTE.
          IF i<> v_rCurBoletas.CANT_ITEM THEN
            --DBMS_OUTPUT.PUT_LINE('BORRAR->'||v_rCurBoletas.SEC_COMP_PAGO);
            DELETE FROM TMP_PROD_INT_VENTA
            WHERE NUM_COMP_PAGO = v_rCurBoletas.NUM_COMP_PAGO
                  OR NUM_COMP_PAGO_2 = v_rCurBoletas.NUM_COMP_PAGO ;
            v_nIndBoleta := 0;
          END IF;
          INSERT INTO TMP_RANGO_BOLETAS
          SELECT MIN(NUM_COMP_PAGO_2),MAX(NUM_COMP_PAGO)
          FROM TMP_PROD_INT_VENTA;
          --VERIFICAR
          /*INSERT INTO T_PROD_INT_VENTA(COD_PROD,TIP_VENTA,NUM_COMP_PAGO,NUM_COMP_PAGO_2,FECHA)
          SELECT COD_PROD,TIP_VENTA,NUM_COMP_PAGO,NUM_COMP_PAGO_2,SYSDATE
          FROM TMP_PROD_INT_VENTA;*/
          --FIN VERIFICAR
          DELETE FROM TMP_PROD_INT_VENTA;
          --DBMS_OUTPUT.PUT_LINE('-------');
          EXIT;
        END IF;
        --DBMS_OUTPUT.PUT_LINE('CONTINUA');
        v_nIndBoleta := 1;
      END LOOP;
    END LOOP;

    -- AL TERMINAR DE RECORRER TODOS LOS PRODUCTOS, GRABO LO QUE QUEDA PENDIENTE
    SELECT COUNT(*) INTO v_cant_rangos
    FROM TMP_PROD_INT_VENTA;

    IF (v_cant_rangos > 0) THEN
       INSERT INTO TMP_RANGO_BOLETAS
       SELECT MIN(NUM_COMP_PAGO_2),MAX(NUM_COMP_PAGO)
       FROM TMP_PROD_INT_VENTA;
       DELETE FROM TMP_PROD_INT_VENTA;
    END IF;

    CLOSE curBoletas;
    --FIN
    OPEN curRangos FOR
    SELECT NUM_COMP_PAGO_I,NUM_COMP_PAGO_F
    FROM TMP_RANGO_BOLETAS;

    RETURN curRangos;
  END;

  /****************************************************************************/
  --04/12/2007  DUBILLUZ  MIFARMA
  FUNCTION INT_GET_PROD_VENDIDOS_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR)
  RETURN FarmaCursor
  IS
    curProd FarmaCursor;
  BEGIN
    OPEN curProd FOR
    SELECT DISTINCT D.COD_PROD,
                    DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5'),
                    DECODE(P.IND_PROD_PROPIO/*L.IND_LAB_PROPIO*/,'S','1','N','2') IND
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB B,
         LGT_PROD P,
         VTA_COMP_PAGO C
    WHERE   D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND D.COD_LOCAL     = cCodLocal_in
        AND D.COD_GRUPO_CIA = B.COD_GRUPO_CIA
        AND D.COD_LOCAL     = B.COD_LOCAL
        AND D.NUM_PED_VTA   = B.NUM_PED_VTA
        AND P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
        AND P.COD_PROD      = D.COD_PROD
        AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
        AND C.COD_LOCAL   = B.COD_LOCAL
        AND B.TIP_COMP_PAGO = cTipDoc_in
        AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
        AND C.SEC_COMP_PAGO = D.SEC_COMP_PAGO
        AND B.EST_PED_VTA = 'C'
        AND   (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
        AND   C.NUM_SEC_DOC_SAP IS NULL
        AND   C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
        AND   B.FEC_PED_VTA   BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
    ORDER BY D.COD_PROD;
    --V2
/*    SELECT DISTINCT D.COD_PROD,
                    DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5'),
                    DECODE(P.IND_PROD_PROPIO,'S','1','N','2') IND
    FROM VTA_PEDIDO_VTA_DET D,
         VTA_PEDIDO_VTA_CAB B,
         LGT_PROD P
    WHERE   D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND D.COD_LOCAL     = cCodLocal_in
        AND D.COD_GRUPO_CIA = B.COD_GRUPO_CIA
        AND D.COD_LOCAL     = B.COD_LOCAL
        AND D.NUM_PED_VTA   = B.NUM_PED_VTA
        AND P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
        AND P.COD_PROD      = D.COD_PROD
        AND EXISTS             (SELECT 1
                                FROM VTA_COMP_PAGO C,
                                     VTA_PEDIDO_VTA_CAB B1
                                WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                AND   C.COD_LOCAL     = cCodLocal_in
                                AND   B1.TIP_COMP_PAGO = cTipDoc_in
                                AND   SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
                                AND   C.SEC_COMP_PAGO = D.SEC_COMP_PAGO
                                AND   B1.EST_PED_VTA = 'C'
                                AND   (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
                                AND   C.NUM_SEC_DOC_SAP IS NULL
                                AND   C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
                                AND   B1.FEC_PED_VTA   BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
                                AND   C.COD_GRUPO_CIA = B1.COD_GRUPO_CIA
                                AND   C.COD_LOCAL     = B1.COD_LOCAL
                                AND   C.NUM_PED_VTA   = B1.NUM_PED_VTA)
    ORDER BY D.COD_PROD;*/
    /*SELECT DISTINCT D.COD_PROD,
                    DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5'),
                    (SELECT DECODE(P.IND_PROD_PROPIO,'S','1','N','2')
                      FROM LGT_PROD P--, LGT_LAB L
                      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND P.COD_PROD = D.COD_PROD
                            ) AS IND
    FROM VTA_PEDIDO_VTA_DET D,VTA_PEDIDO_VTA_CAB B
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND D.COD_LOCAL = cCodLocal_in
        AND D.COD_GRUPO_CIA = B.COD_GRUPO_CIA
        AND D.COD_LOCAL = B.COD_LOCAL
        AND D.NUM_PED_VTA = B.NUM_PED_VTA
        AND D.SEC_COMP_PAGO IN (SELECT SEC_COMP_PAGO
                                FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
                                WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                      AND C.COD_LOCAL = cCodLocal_in
                                      AND B.TIP_COMP_PAGO = cTipDoc_in
                                      AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
                                      AND B.EST_PED_VTA = 'C'
                                      AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
                                      AND C.NUM_SEC_DOC_SAP IS NULL
                                      AND C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
                                      AND B.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
                                      AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                                      AND C.COD_LOCAL = B.COD_LOCAL
                                      AND C.NUM_PED_VTA = B.NUM_PED_VTA)
    ORDER BY D.COD_PROD;
    */
    RETURN curProd;
  END;
  /****************************************************************************/
  FUNCTION INT_GET_TOTALES_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2, cTipVenta_in IN CHAR, cCodProd_in IN CHAR,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR)
  RETURN FarmaCursor
  IS
    curTotales FarmaCursor;
    v_cProdFrac CHAR(1);
  BEGIN
    --OBTENER INDICADOR DE PRODUCTO FRACCIONADO
    SELECT DECODE(COUNT(D.VAL_FRAC),0,' ','X') INTO v_cProdFrac
    FROM VTA_PEDIDO_VTA_DET D
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND D.COD_LOCAL = cCodLocal_in
        AND D.COD_PROD = TRIM(cCodProd_in)
        AND D.VAL_FRAC > 1
        AND D.SEC_COMP_PAGO IN (SELECT c.SEC_COMP_PAGO
                                FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
                                WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                      AND C.COD_LOCAL = cCodLocal_in
                                      AND B.TIP_COMP_PAGO = cTipDoc_in
                                      AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
                                      AND B.EST_PED_VTA = 'C'
                                      AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
                                      AND C.NUM_SEC_DOC_SAP IS NULL
                                      AND B.TIP_PED_VTA = DECODE(TRIM(cTipVenta_in),'3','01','4','02','5','03')
                                      AND C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
                                      AND B.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
                                      AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                                      AND C.COD_LOCAL = B.COD_LOCAL
                                      AND C.NUM_PED_VTA = B.NUM_PED_VTA);
    --
    OPEN curTotales FOR
    SELECT NULL,
          v_cProdFrac,
          TO_CHAR(
                  SUM((D.CANT_ATENDIDA*INT_GET_CANT_PRESENT(cCodGrupoCia_in,cCodProd_in,v_cProdFrac,D.VAL_FRAC))/D.VAL_FRAC)
                  ,'00000000000.000'),
          TO_CHAR(ROUND(SUM(D.VAL_PREC_TOTAL),2),'000000000.00'),
          TO_CHAR(ROUND(SUM(D.VAL_PREC_TOTAL-D.VAL_PREC_TOTAL/((100+D.VAL_IGV)/100)),2),'000000000.00')

    FROM VTA_PEDIDO_VTA_DET D
    WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
        AND D.COD_LOCAL = cCodLocal_in
        AND D.COD_PROD = TRIM(cCodProd_in)
        AND D.SEC_COMP_PAGO IN (SELECT c.SEC_COMP_PAGO
                                FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
                                WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                      AND C.COD_LOCAL = cCodLocal_in
                                      AND B.TIP_COMP_PAGO = cTipDoc_in
                                      AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
                                      AND B.EST_PED_VTA = 'C'
                                      AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
                                      AND C.NUM_SEC_DOC_SAP IS NULL
                                      AND C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
                                      AND B.TIP_PED_VTA = DECODE(TRIM(cTipVenta_in),'3','01','4','02','5','03')
                                      AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
                                      AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                                      AND C.COD_LOCAL = B.COD_LOCAL
                                      AND C.NUM_PED_VTA = B.NUM_PED_VTA)
    GROUP BY D.COD_PROD;

    RETURN curTotales;
  END;
  /****************************************************************************/
  FUNCTION INT_GET_DET_FRAC_PROD(cCodGrupoCia_in IN CHAR,cCodProd_in IN CHAR,cPordFrac_in IN CHAR)
  RETURN FarmaCursor
  IS
    curDetFrac FarmaCursor;
  BEGIN
    OPEN curDetFrac FOR
    SELECT 'UN',
            DECODE(cPordFrac_in,'X',TO_CHAR(VAL_MAX_FRAC,'0000'),'1')
    FROM LGT_PROD
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_PROD = TRIM(cCodProd_in);
    RETURN curDetFrac;
    --CLOSE curDetFrac;
  END;
  /****************************************************************************/
  FUNCTION INT_GET_CANT_PRESENT(cCodGrupoCia_in IN CHAR,cCodProd_in IN CHAR,
                                cPordFrac_in IN CHAR,nValFrac_in IN NUMBER)
  RETURN NUMBER
  IS
    v_nCant LGT_PROD.VAL_MAX_FRAC%TYPE;
  BEGIN
    --DBMS_OUTPUT.PUT_LINE(cPordFrac_in);
    IF cPordFrac_in='X' THEN
      SELECT VAL_MAX_FRAC INTO v_nCant
      FROM LGT_PROD
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_PROD = TRIM(cCodProd_in);

      IF (MOD(v_nCant,nValFrac_in) <> 0) THEN
        RAISE_APPLICATION_ERROR(-20050,'ERROR EN DETERMINAR LA FRACCION MAX. MAX_FRAC='||v_nCant||' VAL_FRAC='||nValFrac_in || ' COD_PROD=' || cCodProd_in);
      END IF;
    ELSE
      v_nCant:=1;
    END IF;
    RETURN v_nCant;
    --CLOSE curDetFrac;
  END;
  /****************************************************************************/
                                  --FACTURAS--
  /****************************************************************************/
  PROCEDURE INT_RESUMEN_VENTAS_FACTURAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2)
  AS
    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;

    curSeries FarmaCursor;
    v_cSerie VTA_SERIE_LOCAL.NUM_SERIE_LOCAL%TYPE;

    curFac FarmaCursor;
    v_cSecComp VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
    v_cRucCli INT_VENTA.RUC_CLI%TYPE;
    v_cRazSocCli INT_VENTA.RAZ_SOCIAL%TYPE;
    v_vNumDocRef INT_VENTA.NUM_DOC_REF%TYPE;
    v_cTipoVenta INT_VENTA.TIP_VENTA%TYPE;

    curDet FarmaCursor;
    v_vIndLab INT_VENTA.TIP_LABORATORIO%TYPE;
    v_cCodProd INT_VENTA.COD_PROD%TYPE;
    v_cCantVend INT_VENTA.CANTIDAD%TYPE;
    v_cIndFrac INT_VENTA.IND_VTA_FRACC%TYPE;
    v_cValPrecio INT_VENTA.PRECIO%TYPE;
    v_cValIgv INT_VENTA.MONTO_IGV%TYPE;
    v_cValTotal INT_VENTA.MONTO_TOTAL%TYPE;

    curDetFrac FarmaCursor;
    v_cUnidVenta INT_VENTA.UNID_MED%TYPE;
    v_cFactConv INT_VENTA.FACT_CONVERSION%TYPE;
  BEGIN

    --OBTENER SERIES ASIGNADAS AL LOCAL
    curSeries:=INT_GET_SERIES_LOCAL(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,vFecProceso_in);
    LOOP
      FETCH curSeries INTO v_cSerie;
    EXIT WHEN curSeries%NOTFOUND;
      curFac:=INT_GET_FACTURAS_LOCAL(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in, vFecProceso_in,v_cSerie);
      LOOP
        FETCH curfac INTO v_cSecComp,v_cRucCli,v_cRazSocCli,v_vNumDocRef,v_cTipoVenta;
      EXIT WHEN curFac%NOTFOUND;
        --DBMS_OUTPUT.PUT_LINE(v_vNumDocRef||'|'||v_cTipoVenta);
        --OBTENER NUM_SEC_DOC
        v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);

        curDet:=INT_GET_DET_FACT(cCodGrupoCia_in, cCodLocal_in,v_cSecComp);
        LOOP
          FETCH curDet INTO v_vIndLab,v_cCodProd,v_cCantVend,v_cIndFrac,v_cValPrecio,v_cValIgv,v_cValTotal;
        EXIT WHEN curDet%NOTFOUND;
          --IMPRIME EL DETALLE
          --OBTIENE EL DETALLE DE FRACC DEL PRODUCTO
          curDetFrac:=INT_GET_DET_FRAC_PROD(cCodGrupoCia_in,v_cCodProd,v_cIndFrac);
          FETCH curDetFrac INTO v_cUnidVenta,v_cFactConv;
          CLOSE curDetFrac;
            --OBTENER CORRELATIVO
            v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc);
            --DBMS_OUTPUT.PUT_LINE(v_nNumSecDoc||'|'||v_cCorr||'|'||v_vNumDocRef||'|'||v_cTipoVenta);
            INSERT INTO INT_VENTA(COD_GRUPO_CIA,
                                COD_LOCAL,
                                NUM_SEC_DOC,
                                CORRELATIVO,
                                FEC_PROCESO,
                                RUC_CLI,
                                RAZ_SOCIAL,
                                CLASE_DOC,
                                NUM_DOC_REF,
                                TIP_VENTA,
                                TIP_LABORATORIO,
                                COD_PROD,
                                CANTIDAD,
                                UNID_MED,
                                IND_VTA_FRACC,
                                FACT_CONVERSION,
                                PRECIO,
                                MONTO_IGV,
                                MONTO_TOTAL,
                                NUM_CANTIDAD,
                                NUM_FACT_CONVERSION,
                                NUM_MONTO_TOTAL,
                                TIP_POSICION  )
          VALUES(cCodGrupoCia_in, cCodLocal_in,v_nNumSecDoc,v_cCorr,
                            TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
                            v_cRucCli,
                            REEMPLAZAR_CARACTERES(v_cRazSocCli),
                            '2',
                            v_vNumDocRef,
                            v_cTipoVenta,
                            v_vIndLab,
                            v_cCodProd,
                            '0'||TRIM(v_cCantVend),
                            v_cUnidVenta,
                            v_cIndFrac,
                            DECODE(v_cFactConv,NULL,'     ','0'||TRIM(v_cFactConv)),
                            '0'||TRIM(v_cValPrecio),
                            '0'||TRIM(v_cValIgv),
                            '0'||TRIM(v_cValTotal),
                            TO_NUMBER(TRIM(v_cCantVend),'00000000000.000'),
                            DECODE(v_cFactConv,NULL,0,TO_NUMBER(v_cFactConv)),
                            TO_NUMBER(TRIM(v_cValTotal),'000000000.00'),
                            '1'
                            );
        END LOOP;
        --DBMS_OUTPUT.PUT_LINE(v_vNumDocRef||'---------');
        SET_REDONDEO_FACTURA(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc, cTipDoc_in, v_cSerie,vFecProceso_in,v_vNumDocRef,v_cSecComp);
        --SET PROCESO EN DOCUMENTOS
        SET_MUN_SEC_PROCESO_FACTURAS(cCodGrupoCia_in, cCodLocal_in,v_cSecComp, v_nNumSecDoc);
        --ACTUALIZAR NUMERACION
        --Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntVentas,'ADMIN');
      END LOOP;
    END LOOP;
  END;
  /****************************************************************************/
  FUNCTION INT_GET_FACTURAS_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2,cNumSerie_in IN CHAR)
  RETURN FarmaCursor
  IS
    curFac FarmaCursor;
  BEGIN
    OPEN curFac FOR
    SELECT C.SEC_COMP_PAGO,
          B.RUC_CLI_PED_VTA,
          SUBSTR(B.NOM_CLI_PED_VTA,1,40),--MODIFICADO 08/09/2006 ERIOS: SE CAMBIO EL VALOR A 34, DEBIDO A QUE NO PERMITIA EL INTO A CHAR(35)
          '01-00'||cNumSerie_in||'-'||SUBSTR(C.NUM_COMP_PAGO,4),
          DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5')
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND B.TIP_COMP_PAGO = cTipDoc_in
          AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
          AND B.EST_PED_VTA = 'C'
          --AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
          AND C.NUM_SEC_DOC_SAP IS NULL
          AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND C.COD_LOCAL = B.COD_LOCAL
          AND C.NUM_PED_VTA = B.NUM_PED_VTA
    ORDER BY 4;
    RETURN curFac;
  END;
  /****************************************************************************/
  FUNCTION INT_GET_DET_FACT(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cSecComp_in IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
    SELECT (SELECT DECODE(P.IND_PROD_PROPIO/*L.IND_LAB_PROPIO*/,'S','1','N','2')
                      FROM LGT_PROD P--, LGT_LAB L
                      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND P.COD_PROD = D.COD_PROD
                            /*AND P.COD_LAB = L.COD_LAB*/) AS IND,
          D.COD_PROD,
          TO_CHAR(
                  SUM((D.CANT_ATENDIDA*INT_GET_CANT_PRESENT(cCodGrupoCia_in,D.COD_PROD,DECODE(D.VAL_FRAC,1,' ','X'),D.VAL_FRAC))/D.VAL_FRAC)
                  ,'00000000000.000'),
          DECODE(MAX(D.VAL_FRAC),1,' ','X'),
          TO_CHAR(ROUND(SUM(D.VAL_PREC_VTA),2),'00000000.00'),
          TO_CHAR(ROUND(SUM(D.VAL_PREC_TOTAL-D.VAL_PREC_TOTAL/((100+D.VAL_IGV)/100)),2),'000000000.00'),
          TO_CHAR(ROUND(SUM(D.VAL_PREC_TOTAL),2),'000000000.00')
    FROM VTA_PEDIDO_VTA_DET D
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND SEC_COMP_PAGO = cSecComp_in
    GROUP BY D.COD_PROD;

    /*SELECT (SELECT DECODE(L.IND_LAB_PROPIO,'S','1','N','2')
                      FROM LGT_PROD P, LGT_LAB L
                      WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND P.COD_PROD = D.COD_PROD
                            AND P.COD_LAB = L.COD_LAB) AS IND,
          D.COD_PROD,
          TO_CHAR(
                  ((D.CANT_ATENDIDA*INT_GET_CANT_PRESENT(cCodGrupoCia_in,D.COD_PROD,DECODE(D.VAL_FRAC,1,' ','X'),D.VAL_FRAC))/D.VAL_FRAC)
                  ,'00000000000.000'),
          DECODE(D.VAL_FRAC,1,' ','X'),
          TO_CHAR(ROUND(D.VAL_PREC_VTA,2),'00000000.00'),
          TO_CHAR(ROUND(D.VAL_PREC_TOTAL-D.VAL_PREC_TOTAL/((100+D.VAL_IGV)/100),2),'000000000.00'),
          TO_CHAR(ROUND(D.VAL_PREC_TOTAL,2),'000000000.00')
    FROM VTA_PEDIDO_VTA_DET D
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND SEC_COMP_PAGO = cSecComp_in;*/
    RETURN curDet;
  END;
  /****************************************************************************/
  --DEVOLUCION--
  PROCEDURE INT_RESUMEN_VENTAS_DEVOLUCION(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2)
  AS
    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;

    curSeries FarmaCursor;
    v_cSerie VTA_SERIE_LOCAL.NUM_SERIE_LOCAL%TYPE;

    curFac FarmaCursor;
    v_cSecComp VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
    v_cRucCli INT_VENTA.RUC_CLI%TYPE;
    v_cRazSocCli INT_VENTA.RAZ_SOCIAL%TYPE;
    v_vNumDocRef INT_VENTA.NUM_DOC_REF%TYPE;
    v_cTipoVenta INT_VENTA.TIP_VENTA%TYPE;
  v_cNumComprobanteAnulado INT_VENTA.NUM_DOC_ANULADO%TYPE;

    curDet FarmaCursor;
    v_vIndLab INT_VENTA.TIP_LABORATORIO%TYPE;
    v_cCodProd INT_VENTA.COD_PROD%TYPE;
    v_cCantVend INT_VENTA.CANTIDAD%TYPE;
    v_cIndFrac INT_VENTA.IND_VTA_FRACC%TYPE;
    v_cValPrecio INT_VENTA.PRECIO%TYPE;
    v_cValIgv INT_VENTA.MONTO_IGV%TYPE;
    v_cValTotal INT_VENTA.MONTO_TOTAL%TYPE;

    curDetFrac FarmaCursor;
    v_cUnidVenta INT_VENTA.UNID_MED%TYPE;
    v_cFactConv INT_VENTA.FACT_CONVERSION%TYPE;
  BEGIN
    --OBTENER SERIES ASIGNADAS AL LOCAL
    curSeries:=INT_GET_SERIES_LOCAL(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,vFecProceso_in);
    LOOP
      FETCH curSeries INTO v_cSerie;
    EXIT WHEN curSeries%NOTFOUND;
      --DBMS_OUTPUT.PUT_LINE(v_cSerie);
      curFac:=INT_GET_DEVOLUCION_LOCAL(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in, vFecProceso_in,v_cSerie);
      LOOP
        FETCH curfac INTO v_cSecComp,v_cRucCli,v_cRazSocCli,v_vNumDocRef,v_cTipoVenta, v_cNumComprobanteAnulado;
      EXIT WHEN curFac%NOTFOUND;
        --DBMS_OUTPUT.PUT_LINE(v_cSecComp||'|'||v_cRucCli||'|'||v_cRazSocCli||'|'||v_vNumDocRef||'|'||v_cTipoVenta);
        --OBTENER NUM_SEC_DOC
        v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);

        --detalle por producto
        curDet:=INT_GET_DET_FACT(cCodGrupoCia_in, cCodLocal_in,v_cSecComp);
        LOOP
          FETCH curDet INTO v_vIndLab,v_cCodProd,v_cCantVend,v_cIndFrac,v_cValPrecio,v_cValIgv,v_cValTotal;
        EXIT WHEN curDet%NOTFOUND;
          --IMPRIME EL DETALLE
          --OBTIENE EL DETALLE DE FRACC DEL PRODUCTO
          curDetFrac:=INT_GET_DET_FRAC_PROD(cCodGrupoCia_in,v_cCodProd,v_cIndFrac);
          FETCH curDetFrac INTO v_cUnidVenta,v_cFactConv;
          CLOSE curDetFrac;
            --OBTENER CORRELATIVO
            v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc);

          INSERT INTO INT_VENTA(COD_GRUPO_CIA,
                                COD_LOCAL,
                                NUM_SEC_DOC,
                                CORRELATIVO,
                                FEC_PROCESO,
                                RUC_CLI,
                                RAZ_SOCIAL,
                                CLASE_DOC,
                                NUM_DOC_REF,
                                TIP_VENTA,
                                TIP_LABORATORIO,
                                COD_PROD,
                                CANTIDAD,
                                UNID_MED,
                                IND_VTA_FRACC,
                                FACT_CONVERSION,
                                PRECIO,
                                MONTO_IGV,
                                MONTO_TOTAL,
                                NUM_CANTIDAD,
                                NUM_FACT_CONVERSION,
                                NUM_MONTO_TOTAL,
                                TIP_POSICION,
                NUM_DOC_ANULADO)
          VALUES(cCodGrupoCia_in, cCodLocal_in,v_nNumSecDoc,v_cCorr,
                            TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
                            v_cRucCli,
                            REEMPLAZAR_CARACTERES(v_cRazSocCli),
                            DECODE(cTipDoc_in,'01',3,'02',4),
                            v_vNumDocRef,
                            v_cTipoVenta,
                            v_vIndLab,
                            v_cCodProd,
                            '0'||TRIM(v_cCantVend),
                            v_cUnidVenta,
                            v_cIndFrac,
                            DECODE(v_cFactConv,NULL,'     ','0'||TRIM(v_cFactConv)),
                            '0'||TRIM(v_cValPrecio),
                            '0'||TRIM(v_cValIgv),
                            '0'||TRIM(v_cValTotal),
                            TO_NUMBER(v_cCantVend,'00000000000.000'),
                            DECODE(v_cFactConv,NULL,0,TO_NUMBER(v_cFactConv)),
                            TO_NUMBER(TRIM(v_cValTotal),'000000000.00'),
                            '1',
              v_cNumComprobanteAnulado
                            );

        END LOOP;
        SET_REDONDEO_DEVOLUCION(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc, cTipDoc_in, v_cSerie,vFecProceso_in,v_vNumDocRef,v_cSecComp);
        --SET PROCESO EN DOCUMENTOS
        SET_MUN_SEC_PROCESO_DEVOL(v_cSecComp, v_nNumSecDoc);
        --ACTUALIZAR NUMERACION
        --Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntVentas,'ADMIN');
      END LOOP;
    END LOOP;
  END;
  /****************************************************************************/
  FUNCTION INT_GET_DEVOLUCION_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2,cNumSerie_in IN CHAR)
  RETURN FarmaCursor
  IS
    curFac FarmaCursor;
  BEGIN
    IF cTipDoc_in='01' THEN
      OPEN curFac FOR
      SELECT C.SEC_COMP_PAGO,
            '',--B.RUC_CLI_PED_VTA,
            '',--SUBSTR(B.NOM_CLI_PED_VTA,0,35),
            --'03-00'||cNumSerie_in||'-'||SUBSTR(C.NUM_COMP_PAGO,4),
            (SELECT DISTINCT NUM_DOC_REF FROM INT_VENTA
              WHERE NUM_SEC_DOC = C.NUM_SEC_DOC_SAP
                    AND COD_LOCAL = C.COD_LOCAL),
            DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5'),
            '03-00'||cNumSerie_in||'-'||SUBSTR(C.NUM_COMP_PAGO,4) -- Número de documento anulado
      FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND B.TIP_COMP_PAGO = cTipDoc_in
            AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
            AND C.IND_COMP_ANUL='S'
            AND C.NUM_SEC_DOC_SAP IS NOT NULL
            AND C.NUM_SEC_DOC_SAP_ANUL IS NULL
            AND TO_CHAR(C.FEC_ANUL_COMP_PAGO,'dd/MM/yyyy') = vFecProceso_in
            AND B.FEC_PED_VTA < TO_DATE(vFecProceso_in,'dd/MM/yyyy')
            AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
            AND C.COD_LOCAL = B.COD_LOCAL
            AND C.NUM_PED_VTA = B.NUM_PED_VTA;
    ELSIF cTipDoc_in='02' THEN
      OPEN curFac FOR
      SELECT C.SEC_COMP_PAGO,
            B.RUC_CLI_PED_VTA,
            SUBSTR(B.NOM_CLI_PED_VTA,1,40),
            '01-00'||cNumSerie_in||'-'||SUBSTR(C.NUM_COMP_PAGO,4),
            DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5'),
            '01-00'||cNumSerie_in||'-'||SUBSTR(C.NUM_COMP_PAGO,4) -- Número de documento anulado
      FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND B.TIP_COMP_PAGO = cTipDoc_in
            AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
            AND C.IND_COMP_ANUL='S'
            AND C.NUM_SEC_DOC_SAP IS NOT NULL
            AND C.NUM_SEC_DOC_SAP_ANUL IS NULL
            AND TO_CHAR(C.FEC_ANUL_COMP_PAGO,'dd/MM/yyyy') = vFecProceso_in
            AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
            AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
            AND C.COD_LOCAL = B.COD_LOCAL
            AND C.NUM_PED_VTA = B.NUM_PED_VTA
      UNION--COMPROBANTES ANUALDOS DE DIAS ANTERIORES
      SELECT C.SEC_COMP_PAGO,
            B.RUC_CLI_PED_VTA,
            SUBSTR(B.NOM_CLI_PED_VTA,1,40),
            '01-00'||cNumSerie_in||'-'||SUBSTR(C.NUM_COMP_PAGO,4),
            DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5'),
            '01-00'||cNumSerie_in||'-'||SUBSTR(C.NUM_COMP_PAGO,4) -- Número de documento anulado
      FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND B.TIP_COMP_PAGO = cTipDoc_in
            AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
            AND C.IND_COMP_ANUL='S'
            AND C.NUM_SEC_DOC_SAP IS NOT NULL
            AND C.NUM_SEC_DOC_SAP_ANUL IS NULL
            AND TO_CHAR(C.FEC_ANUL_COMP_PAGO,'dd/MM/yyyy') = vFecProceso_in
            AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
            AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
            AND C.COD_LOCAL = B.COD_LOCAL
            AND C.NUM_PEDIDO_ANUL = B.NUM_PED_VTA;

    ELSIF cTipDoc_in='05' THEN  --JCORTEZ Comprobante Ticket
     OPEN curFac FOR
      SELECT C.SEC_COMP_PAGO,
            '',--B.RUC_CLI_PED_VTA,
            '',--SUBSTR(B.NOM_CLI_PED_VTA,0,35),
            --'03-00'||cNumSerie_in||'-'||SUBSTR(C.NUM_COMP_PAGO,4),
            (SELECT DISTINCT NUM_DOC_REF FROM INT_VENTA
              WHERE NUM_SEC_DOC = C.NUM_SEC_DOC_SAP
                    AND COD_LOCAL = C.COD_LOCAL),
            DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5'),
            --JCORTEZ 15.07.09 POR SOLICITUD DE QUIMICA FORMATO 12-00
            '12-00'||cNumSerie_in||'-'||SUBSTR(C.NUM_COMP_PAGO,4) -- Número de documento anulado
      FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND B.TIP_COMP_PAGO = cTipDoc_in
            AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
            AND C.IND_COMP_ANUL='S'
            AND C.NUM_SEC_DOC_SAP IS NOT NULL
            AND C.NUM_SEC_DOC_SAP_ANUL IS NULL
            AND TO_CHAR(C.FEC_ANUL_COMP_PAGO,'dd/MM/yyyy') = vFecProceso_in
            AND B.FEC_PED_VTA < TO_DATE(vFecProceso_in,'dd/MM/yyyy')
            AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
            AND C.COD_LOCAL = B.COD_LOCAL
            AND C.NUM_PED_VTA = B.NUM_PED_VTA;

    END IF;
    RETURN curFac;
  END;
  /****************************************************************************/
  FUNCTION INT_GET_SEC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN NUMBER
  IS
    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;
  BEGIN
    v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntVentas);
    --ACTUALIZAR NUMERACION
      Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntVentas,'ADMIN');
    RETURN v_nNumSecDoc;
  END;
  /****************************************************************************/
  FUNCTION INT_GET_CORR(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,nNumSecDoc_in IN NUMBER)
  RETURN CHAR
  IS
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;
  BEGIN
    SELECT COUNT(*)+1 INTO v_cCorr
    FROM INT_VENTA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_SEC_DOC = nNumSecDoc_in;
    RETURN Farma_Utility.COMPLETAR_CON_SIMBOLO(v_cCorr,6,'0','I');
  END;
  /****************************************************************************/
  PROCEDURE SET_REDONDEO_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR)
  AS
    v_nCantRedondeo INT_VENTA.NUM_MONTO_TOTAL%TYPE;
    v_nMontoTotal INT_VENTA .TOT_DOCUMENTO%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;
  BEGIN
    --OBTENER NUM_SEC_DOC
    --v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);
    --OBTENER CORRELATIVO
    v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, nNumSecDoc_in);

    --GET MONTO TOTAL
    SELECT ROUND(SUM(C.VAL_NETO_COMP_PAGO),2)
    INTO v_nMontoTotal
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND B.TIP_COMP_PAGO = cTipDoc_in
          AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
          AND B.EST_PED_VTA = 'C'
          AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
          AND C.NUM_SEC_DOC_SAP IS NULL
          AND C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
          AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND C.COD_LOCAL = B.COD_LOCAL
          AND C.NUM_PED_VTA = B.NUM_PED_VTA;

    --GET_REDONDEO
    SELECT SUM(VAL_REDONDEO_PED_VTA)
    INTO v_nCantRedondeo
          FROM VTA_PEDIDO_VTA_CAB
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND NUM_PED_VTA IN (SELECT DISTINCT B.NUM_PED_VTA
                                    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
                                    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                          AND C.COD_LOCAL = cCodLocal_in
                                          AND B.TIP_COMP_PAGO = cTipDoc_in
                                          AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
                                          AND B.EST_PED_VTA = 'C'
                                          --AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
                                          AND C.NUM_SEC_DOC_SAP IS NULL
                                          AND C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
                                          AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
                                          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                                          AND C.COD_LOCAL = B.COD_LOCAL
                                          AND C.NUM_PED_VTA = B.NUM_PED_VTA
                                      UNION
                                      SELECT DISTINCT C.NUM_PEDIDO_ANUL
                                      FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
                                      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND C.COD_LOCAL = cCodLocal_in
                                            AND B.TIP_COMP_PAGO = cTipDoc_in
                                            AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
                                            AND B.EST_PED_VTA = 'C'
                                            --AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
                                            AND C.NUM_SEC_DOC_SAP IS NULL
                                            AND C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
                                            AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
                                            AND C.NUM_PEDIDO_ANUL IS NOT NULL
                                            AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                                            AND C.COD_LOCAL = B.COD_LOCAL
                                            AND C.NUM_PEDIDO_ANUL = B.NUM_PED_VTA
                                    );
    IF v_nCantRedondeo <> 0 THEN
      INSERT INTO INT_VENTA(COD_GRUPO_CIA,
                            COD_LOCAL,
                            NUM_SEC_DOC,
                            CORRELATIVO,
                            FEC_PROCESO,
                            RUC_CLI,
                            RAZ_SOCIAL,
                            CLASE_DOC,
                            NUM_DOC_REF,
                            TIP_VENTA,
                            TIP_LABORATORIO,
                            COD_PROD,
                            CANTIDAD,
                            UNID_MED,
                            IND_VTA_FRACC,
                            FACT_CONVERSION,
                            PRECIO,
                            MONTO_IGV,
                            MONTO_TOTAL,
                            NUM_CANTIDAD,
                            NUM_FACT_CONVERSION,
                            NUM_MONTO_TOTAL,
                            TIP_POSICION  )--23
      VALUES(cCodGrupoCia_in, cCodLocal_in, nNumSecDoc_in,v_cCorr,
              TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
              '',
              '',--RAZ_SOCIAL
              '1',
              cNumDocRef_in,
              '',
              '',
              '',
              '000000000000.000',
              'UN',
              '',
              '',
              '000000000.00',
              '0000000000.00',
              '0'||TRIM(v_nCantRedondeo),
              0,
              0,
              v_nCantRedondeo,
              '2');
      END IF;
    --ACTUALIZAR MONTOS
    UPDATE INT_VENTA
    SET TOT_DOCUMENTO  = v_nMontoTotal,
        RED_DOCUMENTO  = v_nCantRedondeo
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND  NUM_SEC_DOC = nNumSecDoc_in;

  END;
  /****************************************************************************/
  PROCEDURE SET_REDONDEO_FACTURA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cSecComp_in IN CHAR)
  AS
    v_nCantRedondeo INT_VENTA.NUM_MONTO_TOTAL%TYPE;
    v_nMontoTotal INT_VENTA.TOT_DOCUMENTO%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;
  BEGIN
    --OBTENER NUM_SEC_DOC
    --v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);
    --OBTENER CORRELATIVO
    v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, nNumSecDoc_in);

    --SELECT TO_CHAR(ABS(ROUND(C.VAL_REDONDEO_COMP_PAGO,2)),'000000000.00')
    SELECT ROUND(SUM(C.VAL_REDONDEO_COMP_PAGO),2),ROUND(SUM(C.VAL_NETO_COMP_PAGO),2)
    INTO v_nCantRedondeo,v_nMontoTotal
    FROM VTA_COMP_PAGO C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.SEC_COMP_PAGO = cSecComp_in;
    --DBMS_OUTPUT.PUT_LINE(nNumSecDoc_in||'|'||v_cCorr||'|'||cNumDocRef_in||'|'||'RED');

    IF v_nCantRedondeo <> 0 THEN
      INSERT INTO INT_VENTA(COD_GRUPO_CIA,
                            COD_LOCAL,
                            NUM_SEC_DOC,
                            CORRELATIVO,
                            FEC_PROCESO,
                            RUC_CLI,
                            RAZ_SOCIAL,
                            CLASE_DOC,
                            NUM_DOC_REF,
                            TIP_VENTA,
                            TIP_LABORATORIO,
                            COD_PROD,
                            CANTIDAD,
                            UNID_MED,
                            IND_VTA_FRACC,
                            FACT_CONVERSION,
                            PRECIO,
                            MONTO_IGV,
                            MONTO_TOTAL,
                            NUM_CANTIDAD,
                            NUM_FACT_CONVERSION,
                            NUM_MONTO_TOTAL,
                            TIP_POSICION  )--23
      VALUES(cCodGrupoCia_in, cCodLocal_in, nNumSecDoc_in,v_cCorr,
              TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
              '',
              '',--RAZ_SOCIAL
              '2',
              cNumDocRef_in,
              '',
              '',
              '',
              '000000000000.000',
              'UN',
              '',
              '',
              '000000000.00',
              '0000000000.00',
              '0'||TRIM(v_nCantRedondeo),
              0,
              0,
              v_nCantRedondeo,
              '2');
              --TRIM(v_nCantRedondeo));
    END IF;
    --ACTUALIZAR MONTOS
    UPDATE INT_VENTA
    SET TOT_DOCUMENTO  = v_nMontoTotal,
        RED_DOCUMENTO  = v_nCantRedondeo
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND  NUM_SEC_DOC = nNumSecDoc_in;
  END;

  --SET REDONDEO DEVOLUCION
  /****************************************************************************/
  PROCEDURE SET_REDONDEO_DEVOLUCION(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cSecComp_in IN CHAR)
  AS
    v_nCantRedondeo INT_VENTA.NUM_MONTO_TOTAL%TYPE;
    v_nMontoTotal INT_VENTA.TOT_DOCUMENTO%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;
  BEGIN
    --OBTENER NUM_SEC_DOC
    --v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);
    --OBTENER CORRELATIVO
    v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, nNumSecDoc_in);

    --SELECT TO_CHAR(ABS(ROUND(C.VAL_REDONDEO_COMP_PAGO,2)),'000000000.00')
    SELECT ROUND(SUM(C.VAL_REDONDEO_COMP_PAGO),2),ROUND(SUM(C.VAL_NETO_COMP_PAGO),2)
    INTO v_nCantRedondeo,v_nMontoTotal
    FROM VTA_COMP_PAGO C
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.SEC_COMP_PAGO = cSecComp_in;
    --DBMS_OUTPUT.PUT_LINE(nNumSecDoc_in||'|'||v_cCorr||'|'||cNumDocRef_in||'|'||'RED');

    IF v_nCantRedondeo <> 0 THEN
      INSERT INTO INT_VENTA(COD_GRUPO_CIA,
                            COD_LOCAL,
                            NUM_SEC_DOC,
                            CORRELATIVO,
                            FEC_PROCESO,
                            RUC_CLI,
                            RAZ_SOCIAL,
                            CLASE_DOC,
                            NUM_DOC_REF,
                            TIP_VENTA,
                            TIP_LABORATORIO,
                            COD_PROD,
                            CANTIDAD,
                            UNID_MED,
                            IND_VTA_FRACC,
                            FACT_CONVERSION,
                            PRECIO,
                            MONTO_IGV,
                            MONTO_TOTAL,
                            NUM_CANTIDAD,
                            NUM_FACT_CONVERSION,
                            NUM_MONTO_TOTAL,
                            TIP_POSICION  )
      VALUES(cCodGrupoCia_in, cCodLocal_in, nNumSecDoc_in,v_cCorr,
              TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
              '',
              '',--RAZ_SOCIAL
              DECODE(cTipDoc_in,'01',3,'02',4,'05',11),
              cNumDocRef_in,
              '',
              '',
              '',
              '000000000000.000',
              'UN',
              '',
              '',
              '000000000.00',
              '0000000000.00',
              '0'||TRIM(v_nCantRedondeo),
              0,
              0,
              v_nCantRedondeo,
              '2');
              --TRIM(v_nCantRedondeo));
    END IF;
    --ACTUALIZAR MONTOS
    UPDATE INT_VENTA
    SET TOT_DOCUMENTO  = v_nMontoTotal,
        RED_DOCUMENTO  = v_nCantRedondeo
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND  NUM_SEC_DOC = nNumSecDoc_in;
  END;

  --NOTA DE CREDITO
  PROCEDURE INT_RESUMEN_NOTA_CREDITO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cTipDocOrigen_in IN CHAR,vFecProceso_in IN VARCHAR2)
  AS
    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;


    v_cSecComp VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
    v_cRucCli INT_VENTA.RUC_CLI%TYPE;
    v_cRazSocCli INT_VENTA.RAZ_SOCIAL%TYPE;
    v_vNumDocRef INT_VENTA.NUM_DOC_REF%TYPE;
    v_cTipoVenta INT_VENTA.TIP_VENTA%TYPE;
    v_cSecCompNC VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
    v_vNumDocAnul INT_VENTA.NUM_DOC_ANULADO %TYPE;

    curDet FarmaCursor;
    v_vIndLab INT_VENTA.TIP_LABORATORIO%TYPE;
    v_cCodProd INT_VENTA.COD_PROD%TYPE;
    v_cCantVend INT_VENTA.CANTIDAD%TYPE;
    v_cIndFrac INT_VENTA.IND_VTA_FRACC%TYPE;
    v_cValPrecio INT_VENTA.PRECIO%TYPE;
    v_cValIgv INT_VENTA.MONTO_IGV%TYPE;
    v_cValTotal INT_VENTA.MONTO_TOTAL%TYPE;

    curDetFrac FarmaCursor;
    v_cUnidVenta INT_VENTA.UNID_MED%TYPE;
    v_cFactConv INT_VENTA.FACT_CONVERSION%TYPE;

    v_vNumDocRefAux INT_VENTA.NUM_DOC_REF%TYPE:='';

    v_nNumSecDoc_A INT_VENTA.NUM_SEC_DOC%TYPE := NULL;
    v_cSecComp_A VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE := NULL;
    v_vNumDocRef_A INT_VENTA.NUM_DOC_REF%TYPE := NULL;
    v_vNumDocAnul_A INT_VENTA.NUM_DOC_ANULADO%TYPE := NULL;
  BEGIN

        --DBMS_OUTPUT.PUT_LINE(v_vNumDocRef||'|'||v_cTipoVenta);
        curDet:=INT_GET_DET_NC(cCodGrupoCia_in, cCodLocal_in,cTipDoc_in,cTipDocOrigen_in, vFecProceso_in);
        LOOP
          FETCH curDet INTO v_cSecComp,v_cRucCli,v_cRazSocCli,v_vNumDocRef,v_cTipoVenta,v_vIndLab,v_cCodProd,v_cCantVend,v_cIndFrac,v_cValPrecio,v_cValIgv,v_cValTotal,v_cSecCompNC,v_vNumDocAnul;
        EXIT WHEN curDet%NOTFOUND;
          --OBTIENE EL DETALLE DE FRACC DEL PRODUCTO
          curDetFrac:=INT_GET_DET_FRAC_PROD(cCodGrupoCia_in,v_cCodProd,v_cIndFrac);
          FETCH curDetFrac INTO v_cUnidVenta,v_cFactConv;
          CLOSE curDetFrac;

          --OBTENER NUM_SEC_DOC
          --IF v_vNumDocRefAux <> v_vNumDocRef THEN
          IF v_vNumDocRefAux <> v_cSecCompNC THEN
            v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);
            --v_vNumDocRefAux := v_vNumDocRef;
            v_vNumDocRefAux := v_cSecCompNC;
            IF v_nNumSecDoc_A IS NOT NULL THEN
              SET_REDONDEO_NC(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc_A, cTipDocOrigen_in,vFecProceso_in,v_vNumDocRef_A,v_cSecComp_A,v_vNumDocAnul_A);
            END IF;
          END IF;

          --OBTENER CORRELATIVO
            v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc);
            --DBMS_OUTPUT.PUT_LINE(v_nNumSecDoc||'|'||v_cSecCompNC ||'|'||v_cCorr||'|'||v_vNumDocRef||'|'||v_cCantVend);
            INSERT INTO INT_VENTA(COD_GRUPO_CIA,
                                COD_LOCAL,
                                NUM_SEC_DOC,
                                CORRELATIVO,
                                FEC_PROCESO,
                                RUC_CLI,
                                RAZ_SOCIAL,
                                CLASE_DOC,
                                NUM_DOC_REF,
                                TIP_VENTA,
                                TIP_LABORATORIO,
                                COD_PROD,
                                CANTIDAD,
                                UNID_MED,
                                IND_VTA_FRACC,
                                FACT_CONVERSION,
                                PRECIO,
                                MONTO_IGV,
                                MONTO_TOTAL,
                                NUM_CANTIDAD,
                                NUM_FACT_CONVERSION,
                                NUM_MONTO_TOTAL,
                                TIP_POSICION,
                                NUM_DOC_ANULADO)
          VALUES(cCodGrupoCia_in, cCodLocal_in,v_nNumSecDoc,v_cCorr,
                            TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
                            v_cRucCli,
                            REEMPLAZAR_CARACTERES(v_cRazSocCli),
                            DECODE(cTipDocOrigen_in,'01','6','02','5',NULL),
                            v_vNumDocRef,
                            v_cTipoVenta,
                            v_vIndLab,
                            v_cCodProd,
                            '0'||TRIM(v_cCantVend),
                            v_cUnidVenta,
                            v_cIndFrac,
                            DECODE(v_cFactConv,NULL,'     ','0'||TRIM(v_cFactConv)),
                            '0'||TRIM(v_cValPrecio),
                            '0'||TRIM(v_cValIgv),
                            '0'||TRIM(v_cValTotal),
                            TO_NUMBER(TRIM(v_cCantVend),'00000000000.000'),
                            DECODE(v_cFactConv,NULL,0,TO_NUMBER(v_cFactConv)),
                            TO_NUMBER(TRIM(v_cValTotal),'000000000.00'),
                            '1',
                            --TRIM(v_vNumDocRef)
                            TRIM(v_vNumDocAnul)
                            );

          --SET PROCESO EN DOCUMENTOS
          SET_MUN_SEC_PROCESO_FACTURAS(cCodGrupoCia_in, cCodLocal_in,v_cSecComp, v_nNumSecDoc);

          --GUARDA LOS VALORES PARA EL REDONDEO
          v_nNumSecDoc_A := v_nNumSecDoc;
          v_vNumDocRef_A := v_vNumDocRef;
          v_cSecComp_A := v_cSecCompNC;
          v_vNumDocAnul_A := v_vNumDocAnul;
        END LOOP;
        --PROCESA EL REDONDEO DEL ULTIMO COMPROBANTE
        SET_REDONDEO_NC(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc_A, cTipDocOrigen_in,vFecProceso_in,v_vNumDocRef_A,v_cSecComp_A,v_vNumDocAnul_A);

  END;
  /****************************************************************************/
  FUNCTION INT_GET_DET_NC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cTipDoc_in IN CHAR, cTipDocOrigen_in IN CHAR, vFecProceso_in IN VARCHAR2)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
    SELECT C.SEC_COMP_PAGO,
          B.RUC_CLI_PED_VTA,
          SUBSTR(B.NOM_CLI_PED_VTA,1,40),
          MAX(DECODE(A.TIP_COMP_PAGO,'01',(SELECT DISTINCT NUM_DOC_REF FROM INT_VENTA
                                        WHERE COD_LOCAL = C.COD_LOCAL
                                              AND NUM_SEC_DOC = (SELECT NUM_SEC_DOC_SAP
                                                                  FROM VTA_COMP_PAGO
                                                                  WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                                        AND COD_LOCAL = D.COD_LOCAL
                                                                        AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN)
                                              ),
                                  '02','01-00'||SUBSTR((SELECT NUM_COMP_PAGO
                                                FROM VTA_COMP_PAGO
                                                WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                      AND COD_LOCAL = D.COD_LOCAL
                                                      AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN),1,3)||'-'||SUBSTR((SELECT NUM_COMP_PAGO
                                                                                                                  FROM VTA_COMP_PAGO
                                                                                                                  WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                                                                                        AND COD_LOCAL = D.COD_LOCAL
                                                                                                                        AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN),4)
                                        ,'05',(SELECT DISTINCT NUM_DOC_REF FROM INT_VENTA
                                        WHERE COD_LOCAL = C.COD_LOCAL
                                              AND NUM_SEC_DOC = (SELECT NUM_SEC_DOC_SAP
                                                                  FROM VTA_COMP_PAGO
                                                                  WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                                        AND COD_LOCAL = D.COD_LOCAL
                                                                        AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN)
                                              ),NULL)),
          DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5'),
          --04/12/2007  DUBILLUZ  MODIFICACION
          /*MAX((SELECT DECODE(L.IND_LAB_PROPIO,'S','1','N','2')
                      FROM LGT_PROD P, LGT_LAB L
                      WHERE P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                            AND P.COD_PROD = D.COD_PROD
                            AND P.COD_LAB = L.COD_LAB)) AS IND,
          */
          MAX((SELECT DECODE(P.IND_PROD_PROPIO ,'S','1','N','2')
                      FROM LGT_PROD P
                      WHERE P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                            AND P.COD_PROD = D.COD_PROD
                            )) AS IND,
          D.COD_PROD,
          TO_CHAR(
                  SUM(ABS((D.CANT_ATENDIDA*PTOVENTA_INT.INT_GET_CANT_PRESENT(D.COD_GRUPO_CIA,D.COD_PROD,DECODE(D.VAL_FRAC,1,' ','X'),D.VAL_FRAC))/D.VAL_FRAC))
                  ,'00000000000.000'),
          DECODE(MAX(D.VAL_FRAC),1,' ','X'),
          TO_CHAR(SUM(ABS(ROUND(D.VAL_PREC_VTA,2))),'00000000.00'),
          TO_CHAR(SUM(ABS(ROUND(D.VAL_PREC_TOTAL-D.VAL_PREC_TOTAL/((100+D.VAL_IGV)/100),2))),'000000000.00'),
          TO_CHAR(SUM(ABS(ROUND(D.VAL_PREC_TOTAL,2))),'000000000.00'),
          D.SEC_COMP_PAGO_ORIGEN,
          --JCORTEZ 15.07.09 por solicitud de quimica de cambia para ticket formato 12-00
          MAX(DECODE(A.TIP_COMP_PAGO,'01','03-00','02','01-00','05','12-00',NULL)||SUBSTR((SELECT NUM_COMP_PAGO
                            FROM VTA_COMP_PAGO
                            WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                  AND COD_LOCAL = D.COD_LOCAL
                                  AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN),1,3)||'-'||SUBSTR((SELECT NUM_COMP_PAGO
                                                                                                  FROM VTA_COMP_PAGO
                                                                                                  WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                                                                        AND COD_LOCAL = D.COD_LOCAL
                                                                                                        AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN),4))
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B, VTA_PEDIDO_VTA_DET D, VTA_PEDIDO_VTA_CAB A
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in--'001'
          AND C.COD_LOCAL = cCodLocal_in--'007'
          AND B.TIP_COMP_PAGO = cTipDoc_in--'04'
          AND A.TIP_COMP_PAGO = cTipDocOrigen_in--'02'
--          AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
          AND B.FEC_PED_VTA between to_date(vFecProceso_in,'dd/mm/yyyy') and to_date(vFecProceso_in||' 23:59:59','dd/mm/yyyy hh24:mi:ss')
          AND B.EST_PED_VTA = 'C'
          AND C.NUM_SEC_DOC_SAP IS NULL
          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND C.COD_LOCAL = B.COD_LOCAL
          AND C.NUM_PED_VTA = B.NUM_PED_VTA
          AND C.COD_GRUPO_CIA  = D.COD_GRUPO_CIA
          AND C.COD_LOCAL  = D.COD_LOCAL
          AND C.NUM_PED_VTA  = D.NUM_PED_VTA
          AND C.SEC_COMP_PAGO  = D.SEC_COMP_PAGO
          AND B.COD_GRUPO_CIA = A.COD_GRUPO_CIA
          AND B.COD_LOCAL = A.COD_LOCAL
          AND B.NUM_PED_VTA_ORIGEN = A.NUM_PED_VTA
    GROUP BY C.SEC_COMP_PAGO,B.RUC_CLI_PED_VTA,B.NOM_CLI_PED_VTA,B.TIP_PED_VTA,D.COD_PROD,D.SEC_COMP_PAGO_ORIGEN
    ORDER BY 13,4;
    /*SELECT C.SEC_COMP_PAGO,
          B.RUC_CLI_PED_VTA,
          SUBSTR(B.NOM_CLI_PED_VTA,1,40),
          /*DECODE(A.TIP_COMP_PAGO,'01','03-00','02','01-00',NULL)||SUBSTR((SELECT NUM_COMP_PAGO
                            FROM VTA_COMP_PAGO
                            WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                  AND COD_LOCAL = D.COD_LOCAL
                                  AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN),1,3)||'-'||SUBSTR((SELECT NUM_COMP_PAGO
                                                                                                  FROM VTA_COMP_PAGO
                                                                                                  WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                                                                        AND COD_LOCAL = D.COD_LOCAL
                                                                                                        AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN),4),
          */
    /*      DECODE(A.TIP_COMP_PAGO,'01',(SELECT DISTINCT NUM_DOC_REF FROM INT_VENTA
                                        WHERE COD_LOCAL = C.COD_LOCAL
                                              AND NUM_SEC_DOC = (SELECT NUM_SEC_DOC_SAP
                                                                  FROM VTA_COMP_PAGO
                                                                  WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                                        AND COD_LOCAL = D.COD_LOCAL
                                                                        AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN)
                                              ),
                                  '02','01-00'||SUBSTR((SELECT NUM_COMP_PAGO
                                                FROM VTA_COMP_PAGO
                                                WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                      AND COD_LOCAL = D.COD_LOCAL
                                                      AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN),1,3)||'-'||SUBSTR((SELECT NUM_COMP_PAGO
                                                                                                                  FROM VTA_COMP_PAGO
                                                                                                                  WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                                                                                        AND COD_LOCAL = D.COD_LOCAL
                                                                                                                        AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN),4)
                                        ,NULL),
          DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5'),
          (SELECT DECODE(L.IND_LAB_PROPIO,'S','1','N','2')
                      FROM LGT_PROD P, LGT_LAB L
                      WHERE P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                            AND P.COD_PROD = D.COD_PROD
                            AND P.COD_LAB = L.COD_LAB) AS IND,
          D.COD_PROD,
          TO_CHAR(
                  (ABS((D.CANT_ATENDIDA*PTOVENTA_INT.INT_GET_CANT_PRESENT(D.COD_GRUPO_CIA,D.COD_PROD,DECODE(D.VAL_FRAC,1,' ','X'),D.VAL_FRAC))/D.VAL_FRAC))
                  ,'00000000000.000'),
          --TO_CHAR(ABS(D.CANT_ATENDIDA/D.VAL_FRAC),'00000000000.000'),
          DECODE(D.VAL_FRAC,1,' ','X'),
          TO_CHAR(ABS(ROUND(D.VAL_PREC_VTA,2)),'00000000.00'),
          TO_CHAR(ABS(ROUND(D.VAL_PREC_TOTAL-D.VAL_PREC_TOTAL/((100+D.VAL_IGV)/100),2)),'000000000.00'),
          TO_CHAR(ABS(ROUND(D.VAL_PREC_TOTAL,2)),'000000000.00'),
          D.SEC_COMP_PAGO_ORIGEN,
          DECODE(A.TIP_COMP_PAGO,'01','03-00','02','01-00',NULL)||SUBSTR((SELECT NUM_COMP_PAGO
                            FROM VTA_COMP_PAGO
                            WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                  AND COD_LOCAL = D.COD_LOCAL
                                  AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN),1,3)||'-'||SUBSTR((SELECT NUM_COMP_PAGO
                                                                                                  FROM VTA_COMP_PAGO
                                                                                                  WHERE COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                                                                                        AND COD_LOCAL = D.COD_LOCAL
                                                                                                        AND SEC_COMP_PAGO = D.SEC_COMP_PAGO_ORIGEN),4)
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B, VTA_PEDIDO_VTA_DET D, VTA_PEDIDO_VTA_CAB A
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in--'001'
          AND C.COD_LOCAL = cCodLocal_in--'002'
          AND B.TIP_COMP_PAGO = cTipDoc_in--'04'
          AND A.TIP_COMP_PAGO = cTipDocOrigen_in--'02'
          AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
          AND B.EST_PED_VTA = 'C'
          AND C.NUM_SEC_DOC_SAP IS NULL
          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND C.COD_LOCAL = B.COD_LOCAL
          AND C.NUM_PED_VTA = B.NUM_PED_VTA
          AND C.COD_GRUPO_CIA  = D.COD_GRUPO_CIA
          AND C.COD_LOCAL  = D.COD_LOCAL
          AND C.NUM_PED_VTA  = D.NUM_PED_VTA
          AND C.SEC_COMP_PAGO  = D.SEC_COMP_PAGO
          AND B.COD_GRUPO_CIA = A.COD_GRUPO_CIA
          AND B.COD_LOCAL = A.COD_LOCAL
          AND B.NUM_PED_VTA_ORIGEN = A.NUM_PED_VTA
    ORDER BY 4;--ORDER BY NUM_DOC*/
    RETURN curDet;
  END;
  /****************************************************************************/
  PROCEDURE SET_REDONDEO_NC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cSecComp_in IN CHAR,vNumDocAnul_in IN CHAR)
  AS
    v_nCantRedondeo INT_VENTA.NUM_MONTO_TOTAL%TYPE;
    v_nMontoTotal INT_VENTA.TOT_DOCUMENTO%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;
    v_nCantResto INTEGER;
  BEGIN
    --OBTENER NUM_SEC_DOC
    --v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);
    --OBTENER CORRELATIVO
    v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, nNumSecDoc_in);
    --DBMS_OUTPUT.PUT_LINE(cSecComp_in||'|'||'RED');
    SELECT SUM(CANT_ATENDIDA)- SUM(CANT_USADA_NC)
      INTO v_nCantResto
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_DET D
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.SEC_COMP_PAGO = cSecComp_in
          --AND C.TIP_COMP_PAGO = cTipDoc_in
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
          AND C.SEC_COMP_PAGO = D.SEC_COMP_PAGO;

    IF v_nCantResto = 0 THEN
      SELECT ROUND(C.VAL_REDONDEO_COMP_PAGO,2)
      INTO v_nCantRedondeo
      FROM VTA_COMP_PAGO C
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
            AND C.COD_LOCAL = cCodLocal_in
            AND C.SEC_COMP_PAGO = cSecComp_in;
      --DBMS_OUTPUT.PUT_LINE(v_nCantRedondeo||'|'||'RED');
      --ACTUALIZAR MONTOS
      UPDATE INT_VENTA
      SET RED_DOCUMENTO  = v_nCantRedondeo
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND  NUM_SEC_DOC = nNumSecDoc_in;
      IF v_nCantRedondeo <> 0 THEN
        --DBMS_OUTPUT.PUT_LINE('INSERTA REDONDEO NC');
        INSERT INTO INT_VENTA(COD_GRUPO_CIA,
                              COD_LOCAL,
                              NUM_SEC_DOC,
                              CORRELATIVO,
                              FEC_PROCESO,
                              RUC_CLI,
                              RAZ_SOCIAL,
                              CLASE_DOC,
                              NUM_DOC_REF,
                              TIP_VENTA,
                              TIP_LABORATORIO,
                              COD_PROD,
                              CANTIDAD,
                              UNID_MED,
                              IND_VTA_FRACC,
                              FACT_CONVERSION,
                              PRECIO,
                              MONTO_IGV,
                              MONTO_TOTAL,
                              NUM_CANTIDAD,
                              NUM_FACT_CONVERSION,
                              NUM_MONTO_TOTAL,
                              TIP_POSICION,
                              NUM_DOC_ANULADO)
        VALUES(cCodGrupoCia_in, cCodLocal_in, nNumSecDoc_in,v_cCorr,
                TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
                '',
                '',--RAZ_SOCIAL
                DECODE(cTipDoc_in,'01','6','02','5','05','10',NULL),
                cNumDocRef_in,
                '',
                '',
                '',
                '000000000000.000',
                'UN',
                '',
                '',
                '000000000.00',
                '0000000000.00',
                '0'||TRIM(v_nCantRedondeo),
                0,
                0,
                v_nCantRedondeo,
                '2',
                TRIM(vNumDocAnul_in)
                );
                --TRIM(v_nCantRedondeo));
      END IF;
    END IF;
    SELECT SUM(NUM_MONTO_TOTAL)
    INTO v_nMontoTotal
    FROM INT_VENTA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND  NUM_SEC_DOC = nNumSecDoc_in
            AND TIP_VENTA IS NOT NULL;
    --ACTUALIZAR MONTOS
      UPDATE INT_VENTA
      SET TOT_DOCUMENTO  = v_nMontoTotal,
          RED_DOCUMENTO  = NVL(v_nCantRedondeo,0)
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND  NUM_SEC_DOC = nNumSecDoc_in;
  END;

  /*EJECUTA RESUMEN DE LOCAL*/

  PROCEDURE INT_GET_RESUMEN_DIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2)
  AS
    CURSOR curResumen IS
    SELECT MANDANTE||
            COD_LOCAL||
            Farma_Utility.COMPLETAR_CON_SIMBOLO(NUM_SEC_DOC,10,'0','I')||
            CORRELATIVO||
            TO_CHAR(FEC_PROCESO,'yyyyMMdd')||
            LPAD(NVL(RUC_CLI,' '),16,' ')||
            RPAD(NVL(RAZ_SOCIAL,' '),40,' ')||
            RPAD(NVL(CLASE_DOC,' '),2,' ')||
            NUM_DOC_REF||
            LPAD(NVL(NUM_DOC_ANULADO,' '),16,' ')||
            NVL(TIP_VENTA,' ')||
            NVL(TIP_LABORATORIO,' ')||
            NVL(TIP_POSICION,' ')||
            LPAD(NVL(COD_PROD,' '),18,' ')||
            TO_CHAR(NUM_CANTIDAD,'99999999999990.000')||--CANTIDAD||
            UNID_MED||
            NVL(IND_VTA_FRACC,' ')||
            DECODE(FACT_CONVERSION,NULL,'     ',TO_CHAR(NUM_FACT_CONVERSION,'9999'))||
            DECODE(NUM_MONTO_TOTAL,0,TO_CHAR(NUM_MONTO_TOTAL,'99,999,990.00'),
            DECODE(NUM_MONTO_TOTAL+ABS(NUM_MONTO_TOTAL),
                    0,TO_CHAR(NUM_MONTO_TOTAL,'99,999,990.00S'),
                      TO_CHAR(NUM_MONTO_TOTAL,'99,999,990.00'))
                      )||
            MONEDA||
            TO_CHAR(TOT_DOCUMENTO,'9999,999,999.00')||
            DECODE(RED_DOCUMENTO,0,TO_CHAR(RED_DOCUMENTO,'9999,999,990.00'),
            DECODE(RED_DOCUMENTO+ABS(RED_DOCUMENTO),
                    0,TO_CHAR(RED_DOCUMENTO,'9999,999,990.00S'),
                      TO_CHAR(RED_DOCUMENTO,'9999,999,990.00'))
                      )
            AS RESUMEN
    FROM INT_VENTA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TO_CHAR(FEC_PROCESO,'dd/MM/yyyy' ) = vFecProceso_in
    ORDER BY NUM_SEC_DOC,CORRELATIVO;
    v_rCurResumen curResumen%ROWTYPE;

    v_vNombreArchivo VARCHAR2(100);
    v_nCant INTEGER;
  BEGIN
    SELECT COUNT(*) INTO v_nCant
    FROM INT_VENTA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND TO_CHAR(FEC_PROCESO,'dd/MM/yyyy' ) = vFecProceso_in;
    IF v_nCant > 0 THEN
     /*UPDATE int_venta
     SET RAZ_SOCIAL = REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(RAZ_SOCIAL,'Ñ','N'),'ñ','n'),'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ü','U'),'ü','u');

     UPDATE INT_VENTA SET RAZ_SOCIAL = REPLACE(REPLACE(RAZ_SOCIAL,'À','A'),'à','a');
     UPDATE INT_VENTA SET RAZ_SOCIAL = REPLACE(REPLACE(RAZ_SOCIAL,'È','E'),'è','e');
     UPDATE INT_VENTA SET RAZ_SOCIAL = REPLACE(REPLACE(RAZ_SOCIAL,'Ì','I'),'ì','i');
     UPDATE INT_VENTA SET RAZ_SOCIAL = REPLACE(REPLACE(RAZ_SOCIAL,'Ò','O'),'ò','o');
     UPDATE INT_VENTA SET RAZ_SOCIAL = REPLACE(REPLACE(RAZ_SOCIAL,'Ù','U'),'ù','u');

     UPDATE INT_VENTA SET RAZ_SOCIAL = REPLACE(REPLACE(RAZ_SOCIAL,'Â','A'),'â','a');
     UPDATE INT_VENTA SET RAZ_SOCIAL = REPLACE(REPLACE(RAZ_SOCIAL,'Ê','E'),'ê','e');
     UPDATE INT_VENTA SET RAZ_SOCIAL = REPLACE(REPLACE(RAZ_SOCIAL,'Î','I'),'î','i');
     UPDATE INT_VENTA SET RAZ_SOCIAL = REPLACE(REPLACE(RAZ_SOCIAL,'Ô','O'),'ô','o');
     UPDATE INT_VENTA SET RAZ_SOCIAL = REPLACE(REPLACE(RAZ_SOCIAL,'Û','U'),'û','u');

     UPDATE INT_VENTA SET RAZ_SOCIAL = REPLACE(RAZ_SOCIAL,'°',' ');

     COMMIT;*/

      v_vNombreArchivo := 'VTAMIFARMA'||cCodLocal_in||TO_CHAR(TO_DATE(vFecProceso_in,'dd/MM/yyyy'),'yyyyMMdd')||'.TXT';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));
      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'INICIO');
      FOR v_rCurResumen IN curResumen
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumen.RESUMEN);
      END LOOP;
      --UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'FIN');
      UTL_FILE.PUT(ARCHIVO_TEXTO,'FIN');
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
    END IF;

     --MAIL DE EXITO DE INTERFACE VENTAS
    INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'INTERFACE VENTAS EXITOSO: ',
                                        'EXITO',
                                        'EL PROCESO SE EJECUTO CORRECTAMENTE PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR><I>ARCHIVO: </I>'||v_vNombreArchivo||'<B>');
  EXCEPTION
    WHEN OTHERS THEN
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR ARCHIVO: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR EL ARCHIVO DE VENTAS PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');
  END;
  /****************************************************************************/
  PROCEDURE INT_EJECT_RESUMEN_DIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2)
  AS
    CTIPDOC_IN CHAR(2);
    CTIPDOCORIGEN_IN CHAR(2);
    v_eControlDia EXCEPTION;
    v_eControlFecha EXCEPTION;
    v_eControlFechaComp EXCEPTION;

    v_nCant INTEGER;

    CURSOR curFecha IS
    SELECT DISTINCT FEC_PROCESO
    FROM INT_VENTA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
    ORDER BY 1 DESC;
    v_dFecha DATE;

    --11/09/2007 ERIOS Se verifica que el primer dia tenga ventas efectivas.
    CURSOR curFechaComp IS
    SELECT TRUNC(FEC_PED_VTA)--,SUM(VAL_NETO_PED_VTA)
    FROM pbl_local t1, vta_pedido_vta_cab t2
    WHERE t1.cod_grupo_cia = cCodGrupoCia_in
              AND t1.cod_local = cCodLocal_in
              AND t1.cod_grupo_cia = t2.cod_grupo_cia
              AND t1.cod_local = t2.cod_local
              --AND FEC_PED_VTA BETWEEN TO_DATE('26/08/2007','dd/MM/yyyy') AND TO_DATE('26/08/2007','dd/MM/yyyy')+0.99999
              AND EST_PED_VTA = 'C'
    GROUP BY TRUNC(FEC_PED_VTA)
    HAVING SUM(VAL_NETO_PED_VTA) > 0
    ORDER BY 1 ASC;
    /*SELECT DISTINCT TRUNC(FEC_CREA_COMP_PAGO)
    FROM VTA_COMP_PAGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
    ORDER BY 1 ASC;*/
    v_dFechaComp DATE;

    v_numdias     NUMBER;
    v_dia         NUMBER;
  BEGIN

    IF TO_CHAR(SYSDATE,'dd/MM/yyyy') = vFecProceso_in THEN --VALIDA SI SE EJECUTA EL MISMO DIA
      RAISE v_eControlDia;
    ELSIF TO_DATE(vFecProceso_in,'dd/MM/yyyy') > SYSDATE THEN
      RAISE v_eControlDia;
    ELSE
      SELECT COUNT(*) INTO v_nCant
      FROM INT_VENTA
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;
      IF v_nCant > 0 THEN

-- 2011-12-20 JOLIVA: VERIFICA QUE EL ÚLTIMO DÍA DE VENTA ANTERIOR A LA FECHA DE PROCESO TENGA INTERFASE DE VENTA GENERADA
         SELECT COUNT(*)
         INTO v_nCant
         FROM INT_VENTA V
         WHERE V.COD_GRUPO_CIA = cCodGrupoCia_in
           AND V.COD_LOCAL = cCodLocal_in
           AND V.FEC_PROCESO = (SELECT TRUNC(MAX(FEC_PED_VTA)) FROM VTA_PEDIDO_VTA_CAB WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in AND EST_PED_VTA = 'C' AND FEC_PED_VTA < TO_DATE(vFecProceso_in,'dd/MM/yyyy') );

          IF v_nCant = 0 THEN
              DBMS_OUTPUT.PUT_LINE('EXISTEN FECHAS DE GENERACION DE INTERFASE PENDIENTES DE PROCESO');
              RAISE v_eControlFecha;
          END IF;

      ELSE  --VALIDA FECHA DE APERTURA DEL LOCAL
        OPEN curFechaComp;
        FETCH curFechaComp INTO v_dFechaComp;
        CLOSE curFechaComp;
        IF TO_CHAR(v_dFechaComp,'dd/MM/yyyy') <> vFecProceso_in THEN
          RAISE v_eControlFechaComp;
        END IF;
      END IF;

      CTIPDOC_IN := '01';
      INT_RESUMEN_VENTAS_BOLETAS(
        CCODGRUPOCIA_IN,
        CCODLOCAL_IN,
        CTIPDOC_IN,
        VFECPROCESO_IN
      );
--      DBMS_OUTPUT.put_line('PASO 1');

      CTIPDOC_IN := '02';
      INT_RESUMEN_VENTAS_FACTURAS(
        CCODGRUPOCIA_IN => CCODGRUPOCIA_IN,
        CCODLOCAL_IN => CCODLOCAL_IN,
        CTIPDOC_IN => CTIPDOC_IN,
        VFECPROCESO_IN => VFECPROCESO_IN
      );
--      DBMS_OUTPUT.put_line('PASO 2');

      CTIPDOC_IN := '01';
      INT_RESUMEN_VENTAS_DEVOLUCION(
        CCODGRUPOCIA_IN => CCODGRUPOCIA_IN,
        CCODLOCAL_IN => CCODLOCAL_IN,
        CTIPDOC_IN => CTIPDOC_IN,
        VFECPROCESO_IN => VFECPROCESO_IN
      );
--      DBMS_OUTPUT.put_line('PASO 3');

      CTIPDOC_IN := '02';
      INT_RESUMEN_VENTAS_DEVOLUCION(
        CCODGRUPOCIA_IN => CCODGRUPOCIA_IN,
        CCODLOCAL_IN => CCODLOCAL_IN,
        CTIPDOC_IN => CTIPDOC_IN,
        VFECPROCESO_IN => VFECPROCESO_IN
      );
--      DBMS_OUTPUT.put_line('PASO 4');

      CTIPDOC_IN := '04';
      CTIPDOCORIGEN_IN := '02';
      INT_RESUMEN_NOTA_CREDITO(
        CCODGRUPOCIA_IN => CCODGRUPOCIA_IN,
        CCODLOCAL_IN => CCODLOCAL_IN,
        CTIPDOC_IN => CTIPDOC_IN,
        CTIPDOCORIGEN_IN => CTIPDOCORIGEN_IN,
        VFECPROCESO_IN => VFECPROCESO_IN
      );
--      DBMS_OUTPUT.put_line('PASO 5');

      CTIPDOC_IN := '04';
      CTIPDOCORIGEN_IN := '01';
      INT_RESUMEN_NOTA_CREDITO(
        CCODGRUPOCIA_IN => CCODGRUPOCIA_IN,
        CCODLOCAL_IN => CCODLOCAL_IN,
        CTIPDOC_IN => CTIPDOC_IN,
        CTIPDOCORIGEN_IN => CTIPDOCORIGEN_IN,
        VFECPROCESO_IN => VFECPROCESO_IN
      );
--      DBMS_OUTPUT.put_line('PASO 6');

      --NUEVO DOCUMENTOS JCORTEZ 21.04.09
     CTIPDOC_IN := '05';
      INT_RES_VTS_TICKET_BOLETAS(
        CCODGRUPOCIA_IN,
        CCODLOCAL_IN,
        CTIPDOC_IN,
        VFECPROCESO_IN
      );
--      DBMS_OUTPUT.put_line('PASO 7');

     /* CTIPDOC_IN := '05';
      INT_RESUMEN_VENTAS_TICKET_BOLETAS(
        CCODGRUPOCIA_IN,
        CCODLOCAL_IN,
        CTIPDOC_IN,
        VFECPROCESO_IN
      );*/

       CTIPDOC_IN := '05';
      INT_RES_VTS_TICKET_DEVOLUCION(
        CCODGRUPOCIA_IN => CCODGRUPOCIA_IN,
        CCODLOCAL_IN => CCODLOCAL_IN,
        CTIPDOC_IN => CTIPDOC_IN,
        VFECPROCESO_IN => VFECPROCESO_IN
      );  -----
--      DBMS_OUTPUT.put_line('PASO 8');

     /* CTIPDOC_IN := '05';
      INT_RESUMEN_VTS_TICKET_FACT_DEVOLUCION(
        CCODGRUPOCIA_IN => CCODGRUPOCIA_IN,
        CCODLOCAL_IN => CCODLOCAL_IN,
        CTIPDOC_IN => CTIPDOC_IN,
        VFECPROCESO_IN => VFECPROCESO_IN
      );*/


         CTIPDOC_IN := '04';
      CTIPDOCORIGEN_IN := '05';
      INT_RES_NOTA_CRED_TICKET_BOL(
        CCODGRUPOCIA_IN => CCODGRUPOCIA_IN,
        CCODLOCAL_IN => CCODLOCAL_IN,
        CTIPDOC_IN => CTIPDOC_IN,
        CTIPDOCORIGEN_IN => CTIPDOCORIGEN_IN,
        VFECPROCESO_IN => VFECPROCESO_IN
      );   ---
--      DBMS_OUTPUT.put_line('PASO 9');

      /* CTIPDOC_IN := '04';
      CTIPDOCORIGEN_IN := '08';
      INT_RES_NOTA_CREDITO_TICKET_FACT(
        CCODGRUPOCIA_IN => CCODGRUPOCIA_IN,
        CCODLOCAL_IN => CCODLOCAL_IN,
        CTIPDOC_IN => CTIPDOC_IN,
        CTIPDOCORIGEN_IN => CTIPDOCORIGEN_IN,
        VFECPROCESO_IN => VFECPROCESO_IN
      );*/


      VALIDA_MONTO_VENTAS(cCodGrupoCia_in, cCodLocal_in,vFecProceso_in);
    END IF;
--    ROLLBACK;
    COMMIT;

    --30/01/2008 ERIOS Se verifica el orden de los documentos
   VALIDA_ORDEN_DOCUMENTO(cCodGrupoCia_in, cCodLocal_in,vFecProceso_in);

    --GENERA ARCHIVO
   INT_GET_RESUMEN_DIA(cCodGrupoCia_in, cCodLocal_in,vFecProceso_in);
  EXCEPTION
    WHEN v_eControlDia THEN
      DBMS_OUTPUT.PUT_LINE('NO PUEDE GENERAR LA INTERFACE EL MISMO DIA');
      --MAIL DE ERROR DE INTERFACE VENTAS
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE VENTAS PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||'NO PUEDE GENERAR LA INTERFACE EL MISMO DIA.'||'<B>');
    WHEN v_eControlFecha THEN
      DBMS_OUTPUT.PUT_LINE('LA INTERFACE TIENE FECHAS PENDIENTES DE PROCESO. VERIFIQUE');
      --MAIL DE ERROR DE INTERFACE VENTAS
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE VENTAS PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||'LA INTERFACE TIENE FECHAS PENDIENTES DE PROCESO.'||'<B>');
    WHEN v_eControlFechaComp THEN
      DBMS_OUTPUT.PUT_LINE('DEBE GENERAR LA INTERFACE A PARTIR DE LA FECHA DE APERTURA DEL LOCAL:'||TO_CHAR(v_dFechaComp,'dd/MM/yyyy'));
      --MAIL DE ERROR DE INTERFACE VENTAS
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE VENTAS PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||'DEBE GENERAR LA INTERFACE A PARTIR DE LA FECHA DE APERTURA DEL LOCAL:'||TO_CHAR(v_dFechaComp,'dd/MM/yyyy')||'<B>');
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE. VERIFIQUE: '||SQLCODE||' -ERROR- '||SQLERRM);
      --MAIL DE ERROR DE INTERFACE VENTAS
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE VENTAS PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');

  END;

  /****************************************************************************/
  PROCEDURE SET_MUN_SEC_PROCESO_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR, vFecProceso_in IN VARCHAR2, nNumSecDoc_in IN NUMBER, cNumCompI_in IN CHAR,cNumCompF_in IN CHAR)
  AS
  BEGIN

    UPDATE VTA_COMP_PAGO
    SET NUM_SEC_DOC_SAP = nNumSecDoc_in,
        FEC_PROCESO_SAP = SYSDATE,
        FEC_MOD_COMP_PAGO = SYSDATE,
        USU_MOD_COMP_PAGO = 'JB_INT_VENTA_24'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND SEC_COMP_PAGO IN (SELECT C.SEC_COMP_PAGO
                            FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
                            WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                  AND C.COD_LOCAL = cCodLocal_in
                                  AND B.TIP_COMP_PAGO = cTipDoc_in
                                  AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
                                  AND B.EST_PED_VTA = 'C'
                                  --AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
                                  AND C.NUM_SEC_DOC_SAP IS NULL
                                  AND C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
                                  AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
                                  AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                                  AND C.COD_LOCAL = B.COD_LOCAL
                                  AND C.NUM_PED_VTA = B.NUM_PED_VTA);
  END;

  /****************************************************************************/
  PROCEDURE SET_MUN_SEC_PROCESO_FACTURAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cSecComp_in IN CHAR, nNumSecDoc_in IN NUMBER)
  AS
  BEGIN
    UPDATE VTA_COMP_PAGO
    SET NUM_SEC_DOC_SAP = nNumSecDoc_in,
        FEC_PROCESO_SAP = SYSDATE,
        FEC_MOD_COMP_PAGO = SYSDATE,
        USU_MOD_COMP_PAGO = 'JB_INT_VENTA_25'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND SEC_COMP_PAGO = cSecComp_in;
  END;

  /****************************************************************************/
  PROCEDURE SET_MUN_SEC_PROCESO_DEVOL(cSecComp_in IN CHAR, nNumSecDoc_in IN NUMBER)
  AS
  BEGIN
    UPDATE VTA_COMP_PAGO
    SET NUM_SEC_DOC_SAP_ANUL  = nNumSecDoc_in,
        FEC_PROCESO_SAP_ANUL = SYSDATE,
        FEC_MOD_COMP_PAGO = SYSDATE,
        USU_MOD_COMP_PAGO = 'JB_INT_VENTA_26'
    WHERE SEC_COMP_PAGO = cSecComp_in;
  END;
  /****************************************************************************/
  --INTERFAZ INVENTARIO
  /****************************************************************************/
  PROCEDURE INT_RESUMEN_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  vFecProceso_in IN VARCHAR2, cIndUrgencia_in IN CHAR DEFAULT 'N',
  cIndPedAprov_in IN CHAR DEFAULT 'N')
  AS
    --Obtiene los grupos de reposicion
    CURSOR curGrupoRep IS
    SELECT COD_GRUPO_REP
    FROM LGT_GRUPO_REP
    ORDER BY 1;
    v_rCurGrupoRep curGrupoRep%ROWTYPE;
    --Obtiene los productos por grupo de reposición
    CURSOR curProd(cCodGrupoRep_in IN CHAR) IS
    SELECT D.COD_PROD,
            D.CANT_SOLICITADA,
              P.COD_GRUPO_QS,
              C.NUM_PED_REP,
              P.COD_GRUPO_REP
    FROM LGT_PED_REP_CAB C, LGT_PED_REP_DET D, LGT_PROD P
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.NUM_PED_REP =
              (SELECT MAX(NUM_PED_REP) FROM LGT_PED_REP_CAB
                WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                      AND COD_LOCAL = cCodLocal_in
                      AND TO_CHAR(FEC_CREA_PED_REP_CAB,'dd/MM/yyyy') = vFecProceso_in)
          AND C.EST_PED_REP = 'E'
          AND D.CANT_SOLICITADA > 0
          AND P.EST_PROD = 'A'
          AND C.FEC_PROCESO_SAP IS NULL
          AND P.COD_GRUPO_REP = cCodGrupoRep_in
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_REP = D.NUM_PED_REP
          AND D.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND D.COD_PROD = P.COD_PROD
    ORDER BY P.COD_GRUPO_QS,P.DESC_PROD,P.DESC_UNID_PRESENT;
    v_rCurProd curProd%ROWTYPE;

    j INTEGER:=0;
    jj INTEGER:=0;

    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;
    v_cNumSecPed LGT_PED_REP_CAB.NUM_PED_REP%TYPE := NULL;
    v_vIdUsu_in VARCHAR2(15) := 'PCK_INT_PEDREP';
  BEGIN

    FOR v_rCurGrupoRep IN curGrupoRep
    LOOP
      j := 0;
      OPEN curProd(v_rCurGrupoRep.COD_GRUPO_REP);
      --OPEN curProd;
      --DBMS_OUTPUT.PUT_LINE(v_rCurGrupo.COD_GRUPO_QS);
      LOOP
        FETCH curProd INTO v_rCurProd;
      EXIT WHEN curProd%NOTFOUND;
        IF j = 0 THEN
          v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntPed);
          --ACTUALIZAR NUMERACION
          Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntPed,v_vIdUsu_in);
        ELSIF j = g_cNumProdQuiebre THEN
          v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntPed);
          --ACTUALIZAR NUMERACION
          Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntPed,v_vIdUsu_in);
          j := 0;
        END IF;
        j := j+1;
        --DBMS_OUTPUT.PUT_LINE(cCodGrupoCia_in||','||cCodLocal_in||','||i||','||v_rCurGrupo.COD_GRUPO_QS||','|| v_rCurProd.COD_PROD||','||v_rCurProd.CANT_SOLICITADA);
        INSERT INTO INT_PEDIDO_REP(COD_GRUPO_CIA  ,
                                  COD_LOCAL  ,
                                  COD_SOLICITUD  ,
                                  COD_GRUPO_QS  ,
                                  FEC_PEDIDO  ,
                                  FEC_ENTREGA  ,
                                  IND_URGENCIA  ,
                                  COD_PROD  ,
                                  CANT_SOLICITADA  ,
                                  IND_PED_APROV)
        VALUES(cCodGrupoCia_in,
              cCodLocal_in,
              Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNumSecDoc,10,'0','I'),
              v_rCurProd.COD_GRUPO_QS,
              SYSDATE,
              SYSDATE+1,
              DECODE(cIndUrgencia_in,'S','X','N',' '),
              v_rCurProd.COD_PROD,
              v_rCurProd.CANT_SOLICITADA,
              DECODE(cIndPedAprov_in,'S','X','N',' '));

        v_cNumSecPed := v_rCurProd.NUM_PED_REP;
      END LOOP;
      CLOSE curProd;

      jj := jj + j;
    END LOOP;

      IF jj = 0 THEN
        RAISE_APPLICATION_ERROR(-20051,'NO HAY INFORMACION QUE ENVIAR. ESTO PUEDE DEBERSE A QUE YA GENERO EL PEDIDO.');
      END IF;

      --ACTUALIZAR CABECERA
      UPDATE LGT_PED_REP_CAB SET USU_MOD_PED_REP_CAB = v_vIdUsu_in ,FEC_MOD_PED_REP_CAB = SYSDATE,
            FEC_PROCESO_SAP = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND NUM_PED_REP = v_cNumSecPed;

      COMMIT;

      --AGREGADO 18/08/2006
      --MODIFICADO 28/12/2006: LOS DATOS SE GUARDAN EN EL DETALLE.
      /*DELETE FROM TMP_LGT_PROD_LOCAL_REP;

      INSERT INTO TMP_LGT_PROD_LOCAL_REP(COD_GRUPO_CIA,COD_LOCAL,COD_PROD,
                                          CANT_MIN_STK,CANT_MAX_STK,CANT_SUG,
                                          CANT_SOL,CANT_ROT,CANT_TRANSITO,
                                          CANT_DIA_ROT,NUM_DIAS,STK_FISICO,
                                          VAL_FRAC_LOCAL,CANT_EXHIB,
                                          USU_CREA_PROD_LOCAL_REP,
                                          FEC_CREA_PROD_LOCAL_REP,
                                          USU_MOD_PROD_LOCAL_REP,
                                          FEC_MOD_PROD_LOCAL_REP,
                                          TIPO  )
      SELECT COD_GRUPO_CIA,COD_LOCAL,COD_PROD,
              CANT_MIN_STK,CANT_MAX_STK,CANT_SUG,
              CANT_SOL,CANT_ROT,CANT_TRANSITO,
              CANT_DIA_ROT,NUM_DIAS,STK_FISICO,
              VAL_FRAC_LOCAL,CANT_EXHIB,
              USU_CREA_PROD_LOCAL_REP,
              FEC_CREA_PROD_LOCAL_REP,
              USU_MOD_PROD_LOCAL_REP,
              FEC_MOD_PROD_LOCAL_REP,
              TIPO
      FROM LGT_PROD_LOCAL_REP
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

      COMMIT;*/

      --INT_GET_RESUMEN_PED_REP(cCodGrupoCia_in, cCodLocal_in);

  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE. VERIFIQUE: '||SQLCODE||' -ERROR- '||SQLERRM);
      --MAIL DE ERROR DE INTERFACE DE INVENTARIO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE PEDIDO REPOSICION PARA LA FECHA: '||vFecProceso_in||'</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');

  END;

  PROCEDURE INT_RESUMEN_REC_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
    CURSOR curProd IS
    SELECT G.NUM_GUIA_REM,C.FEC_MOD_NOTA_ES_CAB,D.COD_PROD,D.CANT_MOV,D.NUM_LOTE_PROD,D.FEC_VCTO_PROD,Q.NUM_ENTREGA, Q.NUM_INTERNO_SOLICITUD,C.NUM_NOTA_ES,D.SEC_DET_NOTA_ES,D.POSICION
    FROM LGT_NOTA_ES_CAB C, LGT_NOTA_ES_DET D, LGT_GUIA_REM G , INT_RECEP_PROD_QS Q
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.TIP_NOTA_ES = '03' --RECEPCION
          AND G.IND_GUIA_CERRADA = 'S'
          AND D.IND_PROD_AFEC = 'S'
          AND D.FEC_PROCESO_SAP   IS NULL
          AND D.COD_GRUPO_CIA = C.COD_GRUPO_CIA
          AND D.COD_LOCAL = C.COD_LOCAL
          AND D.NUM_NOTA_ES = C.NUM_NOTA_ES
          AND G.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND G.COD_LOCAL = D.COD_LOCAL
          AND G.NUM_NOTA_ES = D.NUM_NOTA_ES
          AND G.SEC_GUIA_REM = D.SEC_GUIA_REM
          AND D.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
          AND D.COD_LOCAL = Q.COD_LOCAL
          AND G.NUM_ENTREGA = Q.NUM_ENTREGA
          AND Q.POSICION = D.POSICION
          --Autor: Arturo Escate
          --Fecha: 21/06/2012
          AND NOT exists (
                  SELECT 1
                  FROM INT_CONF_RECEP_PROD gy
                  WHERE gy.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND gy.COD_LOCAL       = cCodLocal_in
                  and G.NUM_ENTREGA      = gy.NUM_ENTREGA
                  and Q.POSICION         = gy.POSICION
                  )
          /*AND G.NUM_ENTREGA||Q.POSICION NOT IN (SELECT NUM_ENTREGA||POSICION FROM INT_CONF_RECEP_PROD
                                                WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in)
                                                */
          ORDER BY 1,11;
    v_rCurProd curProd%ROWTYPE;
    v_vIdUsu_in VARCHAR2(15) := 'PCK_INT_RECPROD';
  BEGIN
      FOR v_rCurProd IN curProd
      LOOP
        INSERT INTO INT_CONF_RECEP_PROD(COD_GRUPO_CIA  ,
                                        COD_LOCAL  ,
                                        NUM_INTERNO_SOLIC  ,
                                        NUM_GUIA_RECEPCION,
                                        CANT_CONFIRMADA  ,
                                        COD_PROD  ,
                                        FEC_INGRESO_MERCA,
                                        NUM_LOTE  ,
                                        FEC_VENC  ,
                                        NUM_ENTREGA,
                                        POSICION  )
        VALUES(cCodGrupoCia_in,
              cCodLocal_in,
              v_rCurProd.NUM_INTERNO_SOLICITUD,
              v_rCurProd.NUM_GUIA_REM,
              v_rCurProd.CANT_MOV,
              v_rCurProd.COD_PROD,
              v_rCurProd.FEC_MOD_NOTA_ES_CAB,
              v_rCurProd.NUM_LOTE_PROD,
              v_rCurProd.FEC_VCTO_PROD,
              v_rCurProd.NUM_ENTREGA,
              v_rCurProd.POSICION  );

        UPDATE LGT_NOTA_ES_DET
        SET FEC_PROCESO_SAP = SYSDATE,
                    FEC_MOD_NOTA_ES_DET = SYSDATE,
                    USU_MOD_NOTA_ES_DET = v_vIdUsu_in
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_NOTA_ES = v_rCurProd.NUM_NOTA_ES
              AND SEC_DET_NOTA_ES  = v_rCurProd.SEC_DET_NOTA_ES;

        UPDATE LGT_NOTA_ES_CAB
        SET FEC_PROCESO_SAP = SYSDATE,
                    FEC_MOD_NOTA_ES_CAB = SYSDATE,
                    USU_MOD_NOTA_ES_CAB = v_vIdUsu_in
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
              AND COD_LOCAL = cCodLocal_in
              AND NUM_NOTA_ES = v_rCurProd.NUM_NOTA_ES;
      END LOOP;
    COMMIT;
    INT_GET_RESUMEN_REC_PROD(cCodGrupoCia_in, cCodLocal_in);

  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE. VERIFIQUE: '||SQLCODE||' -ERROR- '||SQLERRM);
      --MAIL DE ERROR DE INTERFACE DE CONFIRMACION
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE RECEPCION PRODUCTOS.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');

  END;

  /***************************************************************************/
  PROCEDURE INT_RESUMEN_MOV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            nDiasAtras_in IN INTEGER DEFAULT 0)
  AS
    CURSOR curMov IS
    SELECT  D.SEC_DET_NOTA_ES as sec_det_nota,-- jquispe 21.04.2010 cambio intterfaz
            R.COD_GRUPO_CIA,
            R.COD_LOCAL,
            R.NUM_GUIA_REM AS NUM_GUIA,
            D.COD_PROD,
            (SELECT TRIM(TIP_MOV) FROM INT_REL_COD_MOV
              WHERE TIP_INTERFACE = '02' AND COD_MOT_KARDEX = C.TIP_MOT_NOTA_ES) AS TIP_MOV,--
            D.FEC_NOTA_ES_DET AS FEC_MOV,
            D.CANT_MOV,
            D.VAL_FRAC,
            (SELECT TRIM(MOTIVO_AJUSTE) FROM INT_REL_COD_MOV
              WHERE TIP_INTERFACE = '02' AND COD_MOT_KARDEX = C.TIP_MOT_NOTA_ES) AS MOTIVO,--
            C.NUM_NOTA_ES,
            D.SEC_DET_NOTA_ES,
            -- 2009-05-28 JOLIVA (SE AGREGA SECUENCIAL POR GUÍA SEGÚN ORDEN SOLICITADO: LABORATORIO Y COD_PROD)
            L.NOM_LAB,
            D.NUM_LOTE_PROD, --JCORTEZ 17/05/2010
            RANK() OVER(PARTITION BY R.NUM_GUIA_REM ORDER BY L.NOM_LAB, D.COD_PROD,D.SEC_DET_NOTA_ES) NUM_SEC_GUIA
    FROM LGT_NOTA_ES_CAB C, LGT_GUIA_REM R, LGT_NOTA_ES_DET D, LGT_LAB L, LGT_PROD P
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND R.NUM_GUIA_REM IS NOT NULL
          AND C.TIP_NOTA_ES = '02' --TRANSFERENCIAS
          AND C.COD_ORIGEN_NOTA_ES = cCodLocal_in
          AND C.COD_DESTINO_NOTA_ES = g_cCodMatriz --MATRIZ
          AND D.FEC_PROCESO_SAP IS NULL
          AND C.FEC_PROCESO_SAP IS NULL
          --AND C.FEC_NOTA_ES_CAB < TRUNC(SYSDATE-nDiasAtras_in)
          AND C.EST_NOTA_ES_CAB = 'A'
          AND C.COD_GRUPO_CIA = R.COD_GRUPO_CIA
          AND C.COD_LOCAL = R.COD_LOCAL
          AND C.NUM_NOTA_ES = R.NUM_NOTA_ES
          AND R.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND R.COD_LOCAL = D.COD_LOCAL
          AND R.NUM_NOTA_ES = D.NUM_NOTA_ES
          AND R.SEC_GUIA_REM = D.SEC_GUIA_REM
          AND P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND P.COD_PROD = D.COD_PROD
          AND P.COD_LAB = L.COD_LAB;

    v_rCurMov curMov%ROWTYPE;
    v_nCant LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    v_nFrac NUMBER;
    v_nMaxFrac NUMBER;
    v_nCantFrac LGT_NOTA_ES_DET.CANT_MOV%TYPE;
    v_cCodProdFrac LGT_PROD.COD_PROD_FRAC%TYPE;
  BEGIN
      FOR v_rCurMov IN curMov
      LOOP
        /*--VALIDA FRACCION PRODUCTO
        IF MOD(v_rCurMov.CANT_MOV,v_rCurMov.VAL_FRAC) > 0 THEN
           RAISE_APPLICATION_ERROR(-20052,'LA FRACCION DEL PRODUCTO:'||v_rCurMov.COD_PROD||' PARA LA GUÍA:'||v_rCurMov.NUM_GUIA||' NO PERMITE LA CONVERSION ENTERA.');
        ELSE

        END IF;*/

        --05/11/2007 ERIOS Se obtienen las cantidades de la devolucion.
        v_nCant := TRUNC(v_rCurMov.CANT_MOV/v_rCurMov.VAL_FRAC);
        v_nCantFrac := 0;
        IF MOD(v_rCurMov.CANT_MOV,v_rCurMov.VAL_FRAC) > 0 THEN
          v_nFrac := MOD(v_rCurMov.CANT_MOV,v_rCurMov.VAL_FRAC);
          v_nMaxFrac := INT_GET_CANT_PRESENT(cCodGrupoCia_in,v_rCurMov.COD_PROD,'X',v_rCurMov.VAL_FRAC);
          v_nCantFrac := (v_nFrac*v_nMaxFrac)/v_rCurMov.VAL_FRAC;

          SELECT COD_PROD_FRAC INTO v_cCodProdFrac
          FROM LGT_PROD
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_PROD = v_rCurMov.COD_PROD;
           IF v_cCodProdFrac IS NULL THEN
             RAISE_APPLICATION_ERROR(-20052,'NO SE DETERMINA EL CODIGO FRACCIONADO DEL PRODUCTO:'||v_rCurMov.COD_PROD||' PARA LA GUÍA:'||v_rCurMov.NUM_GUIA||'.');
           END IF;
        END IF;

    --dbms_output();

        IF v_nCant > 0 THEN
          INSERT INTO INT_MOVIMIENTO(COD_GRUPO_CIA  ,
                                    COD_LOCAL  ,
                                    NUM_GUIA  ,
                                    COD_PROD  ,
                                    TIP_MOV  ,
                                    FEC_MOV  ,
                                    CANT_MOV  ,
                                    MOTIVO  ,
                                    NUM_SEC_GUIA  ,
                                    SEC_NOTADET,--jquispe 21.04.2010
                                    NUM_LOTE_PROD)--JCORTEZ 17/05/2010
          VALUES (  v_rCurMov.COD_GRUPO_CIA  ,
                    v_rCurMov.COD_LOCAL  ,
                    v_rCurMov.NUM_GUIA  ,
                    v_rCurMov.COD_PROD  ,
                    v_rCurMov.TIP_MOV  ,
                    v_rCurMov.FEC_MOV  ,
                    v_nCant,
                    v_rCurMov.MOTIVO,
                    v_rCurMov.NUM_SEC_GUIA,
                    v_rCurMov.sec_det_nota,
                    v_rCurMov.NUM_LOTE_PROD);--JCORTEZ 17/05/2010
        END IF;

        --05/11/2007 ERIOS Se agrega las devoluciones fraccionadas.
        IF v_nCantFrac > 0 THEN
          INSERT INTO INT_MOVIMIENTO(COD_GRUPO_CIA  ,
                                    COD_LOCAL  ,
                                    NUM_GUIA  ,
                                    COD_PROD  ,
                                    TIP_MOV  ,
                                    FEC_MOV  ,
                                    CANT_MOV  ,
                                    MOTIVO  ,
                                    NUM_SEC_GUIA ,
                                    SEC_NOTADET,--jquispe 21.04.2010
                                    NUM_LOTE_PROD)--JCORTEZ 17/05/2010)
          VALUES (v_rCurMov.COD_GRUPO_CIA  ,
                    v_rCurMov.COD_LOCAL  ,
                    v_rCurMov.NUM_GUIA  ,
                    v_cCodProdFrac  ,
                    v_rCurMov.TIP_MOV  ,
                    v_rCurMov.FEC_MOV  ,
                    v_nCantFrac,
                    v_rCurMov.MOTIVO,
                    v_rCurMov.NUM_SEC_GUIA,
                    v_rCurMov.sec_det_nota,
                    v_rCurMov.NUM_LOTE_PROD);--JCORTEZ 17/05/2010
        END IF;

        UPDATE LGT_NOTA_ES_DET
        SET FEC_PROCESO_SAP = SYSDATE,
                    FEC_MOD_NOTA_ES_DET = SYSDATE,
                    USU_MOD_NOTA_ES_DET = 'PCK_INT_MOV'
        WHERE COD_GRUPO_CIA = v_rCurMov.COD_GRUPO_CIA
              AND COD_LOCAL = v_rCurMov.COD_LOCAL
              AND NUM_NOTA_ES = v_rCurMov.NUM_NOTA_ES
              AND SEC_DET_NOTA_ES = v_rCurMov.SEC_DET_NOTA_ES;

        UPDATE LGT_NOTA_ES_CAB
        SET FEC_PROCESO_SAP = SYSDATE,
                    FEC_MOD_NOTA_ES_CAB = SYSDATE,
                    USU_MOD_NOTA_ES_CAB = 'PCK_INT_MOV'
        WHERE COD_GRUPO_CIA = v_rCurMov.COD_GRUPO_CIA
              AND COD_LOCAL = v_rCurMov.COD_LOCAL
              AND NUM_NOTA_ES = v_rCurMov.NUM_NOTA_ES;
      END LOOP;

      COMMIT;
    INT_GET_RESUMEN_MOV(cCodGrupoCia_in, cCodLocal_in);
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE. VERIFIQUE: '||SQLCODE||' -ERROR- '||SQLERRM);
      --ENVIO MAIL DE ERROR DE INTERFACE DE MOVIMIENTO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE DEVOLUCIONES.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');

  END;

  /****************************************************************************/
  --INTERFAZ MOVIMIENTOS 2

  PROCEDURE INT_RESUMEN_MOV2(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2)
  AS
    CURSOR curTipMov IS
    -- ANTES 16.12.2013
    -- SELECT DISTINCT M.TIP_MOV,
	--ERIOS 27.10.2014 Cambios de JLUNA
    SELECT DISTINCT M.TIP_MOV,K.COD_PROD
    FROM LGT_KARDEX K, INT_REL_COD_MOV M
    WHERE K.COD_MOT_KARDEX = M.COD_MOT_KARDEX
          AND K.FEC_KARDEX < TRUNC(SYSDATE-nDiasAtras_in)
          AND K.NUM_AJUSTE IS NULL
          AND M.TIP_INTERFACE  = '01'
         -- AND K.COD_PROD = '132679'
          AND M.TIP_MOV IN ('AJ','CI','AD')--11/10/2007 ERIOS Se excluyen los RD
          and K.TIP_DOC_KARDEX = '02'
          AND not exists  
             (SELECT 1
                FROM LGT_NOTA_ES_CAB
               WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                 AND COD_LOCAL = cCodLocal_in
                 AND FEC_CREA_NOTA_ES_CAB < TRUNC(SYSDATE - nDiasAtras_in)
                 AND TIP_NOTA_ES = '01'
                 AND TIP_ORIGEN_NOTA_ES = '04'
                 AND EST_NOTA_ES_CAB = 'N'
                 and NUM_NOTA_ES=K.NUM_TIP_DOC)
    ORDER BY 1;
    v_rCurTipMov curTipMov%ROWTYPE;

    CURSOR curMov2(cTipMov_in IN CHAR,cCodProd_in in char) IS
    SELECT K.COD_GRUPO_CIA,
            K.COD_LOCAL,
            K.COD_PROD,
            M.TIP_MOV AS TIP_AJUSTE,--4
            DECODE(M.TIP_MOV,'CC',(SELECT FEC_NOTA_ES_CAB
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,
                             'RD',(SELECT FEC_NOTA_ES_CAB
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,
                                 K.FEC_KARDEX) AS FEC_AJUSTE,--5
            DECODE(M.TIP_MOV,'RD',(SELECT COD_ORIGEN_NOTA_ES  --AS COD_PROVEEDOR
                                  FROM LGT_NOTA_ES_CAB
                                  WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                        AND COD_LOCAL = K.COD_LOCAL
                                        AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS COD_PROV,--6
            DECODE(M.TIP_MOV,'AD',ABS(K.CANT_MOV_PROD/K.VAL_FRACC_PROD),
                            (ABS((K.CANT_MOV_PROD*INT_GET_CANT_PRESENT(cCodGrupoCia_in,K.COD_PROD,DECODE(K.VAL_FRACC_PROD,1,' ','X'),K.VAL_FRACC_PROD))/K.VAL_FRACC_PROD))) AS CANT_SOL,--7
            --ABS(K.CANT_MOV_PROD) AS CANT_SOL,
            DECODE(M.TIP_MOV,'CC',(SELECT VAL_PREC_TOTAL
                                    FROM LGT_NOTA_ES_DET
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC
                                          AND COD_PROD = K.COD_PROD) ,'') AS IMP_AJUSTE,--8
            M.MOTIVO_AJUSTE AS MOT_AJUSTE,--9
            DECODE(M.TIP_MOV,'AD',' ',
                            DECODE(K.VAL_FRACC_PROD,1,' ','X')) AS IND_VTA_FRACC,--10
            DECODE(M.TIP_MOV,'AJ',DECODE(K.CANT_MOV_PROD/ABS(K.CANT_MOV_PROD),-1,'-','+'),
                              'AD',DECODE(K.CANT_MOV_PROD/ABS(K.CANT_MOV_PROD),-1,'-','+'),
                              'RD',DECODE(K.CANT_MOV_PROD/ABS(K.CANT_MOV_PROD),-1,'-','+'),
                                  '') AS SIGNO,--11
            K.SEC_KARDEX,--12
            DECODE(M.TIP_MOV,'CC',(SELECT DESC_EMPRESA
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS NOM_TIENDA,--13
            DECODE(M.TIP_MOV,'CC',(SELECT DIR_EMPRESA
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS CIUDAD,--14
            DECODE(M.TIP_MOV,'CC',(SELECT RUC_EMPRESA
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS RUC_EMPRESA,--15
            DECODE(M.TIP_MOV,'CC',K.NUM_TIP_DOC,NULL) AS NUM_TIP_DOC, --16
-- 2009-01-15 Se agrega código, Indicador de Fracción y el factor de conversión del producto cambio de código
            CASE WHEN M.TIP_MOV ='AJ' AND K.COD_MOT_KARDEX = '507' THEN TRIM(K.DESC_GLOSA_AJUSTE)
          ELSE ' '
            END AS DESC_GLOSA_AJUSTE, --17 (FILTRO)

            CASE WHEN M.TIP_MOV ='AJ' AND K.COD_MOT_KARDEX = '507' AND SUBSTR(TRIM(K.DESC_GLOSA_AJUSTE),1,16) != 'VIENE DEL CODIGO' THEN SUBSTR(TRIM(K.DESC_GLOSA_AJUSTE),-6)
                 ELSE ' '
            END AS COD_PROD_DESTINO_CC, --17 (DATO A GRABAR)

            CASE WHEN M.TIP_MOV ='AJ' AND K.COD_MOT_KARDEX = '507' AND SUBSTR(TRIM(K.DESC_GLOSA_AJUSTE),1,15) = 'PASA AL CODIGO:' THEN INT_GET_IND_FRAC_NVO_CC(K.COD_GRUPO_CIA, K.COD_PROD, SUBSTR(TRIM(K.DESC_GLOSA_AJUSTE),-6))
                 ELSE ' '
            END AS IND_VTA_FRACC_CC, --18
            -- 2013-07-10 JOLIVA: SE CONSIDERA EL SIGNO PARA DETERMINAR EL ORIGEN Y DESTINO
            -- INICIO
            (
             select c.origen_1172
             from   aux_rel_mot_origen_envio c
             where  c.cod_mot_kardex = k.cod_mot_kardex
               AND C.SIGNO = (CASE WHEN K.CANT_MOV_PROD < 0 THEN '-' ELSE '+' END)
            ) AS "ORIGEN_1172",
            (
             select c.destino_1172
             from   aux_rel_mot_origen_envio c
             where  c.cod_mot_kardex = k.cod_mot_kardex
               AND C.SIGNO = (CASE WHEN K.CANT_MOV_PROD < 0 THEN '-' ELSE '+' END)
            ) AS "DESTINO_1172"
            -- 2013-07-10 JOLIVA: ALMACEN ORIGEN VA AL FINAL
            -- FIN
    FROM LGT_KARDEX K, INT_REL_COD_MOV M
    WHERE K.COD_GRUPO_CIA = cCodGrupoCia_in
    AND K.COD_LOCAL = cCodLocal_in
    -- DUBILLUZ 16.12.2013
    AND K.COD_PROD = cCodProd_in
    AND K.COD_MOT_KARDEX = M.COD_MOT_KARDEX
    AND K.FEC_KARDEX < TRUNC(SYSDATE-nDiasAtras_in)
    AND K.NUM_AJUSTE IS NULL
    AND M.TIP_INTERFACE  = '01' --MOV2
    AND M.TIP_MOV = cTipMov_in
    AND K.CANT_MOV_PROD <> 0
    and K.TIP_DOC_KARDEX = '02'
          AND not exists  
             (SELECT 1
                FROM LGT_NOTA_ES_CAB
               WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                 AND COD_LOCAL = cCodLocal_in
                 AND FEC_CREA_NOTA_ES_CAB < TRUNC(SYSDATE - nDiasAtras_in)
                 AND TIP_NOTA_ES = '01'
                 AND TIP_ORIGEN_NOTA_ES = '04'
                 AND EST_NOTA_ES_CAB = 'N'
                 and NUM_NOTA_ES=K.NUM_TIP_DOC)
    --and k.sec_kardex like '%1162083%'
    ORDER BY 4,16,11;
    v_rCurMov2 curMov2%ROWTYPE;

    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;

    curDetFrac FarmaCursor;
    v_cUnidVenta INT_VENTA.UNID_MED%TYPE;
    v_cFactConv INT_VENTA.FACT_CONVERSION%TYPE;

    v_cUnidVentaDestinoCC INT_VENTA.UNID_MED%TYPE;
    v_cFactConvDestinoCC INT_VENTA.FACT_CONVERSION%TYPE;

    v_cNumTipDoc LGT_KARDEX.NUM_TIP_DOC%TYPE := NULL;
    v_vIdUsu_in VARCHAR2(15) := 'PCK_INT_MOV2';
  BEGIN

--       DBMS_OUTPUT.put_line('ENTRA A PROCESO');
-- 2013-06-26 JOLIVA: SE DETIENE LA EJECUCION HASTA QUE SE CORRIJA EL ALMACEN ORIGEN Y DESTINO DE CADA AJUSTE
--       RETURN;
--     DBMS_OUTPUT.put_line('PASA QUIEBRE');

      FOR v_rCurTipMov IN curTipMov
      LOOP
        v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2);
        --ACTUALIZAR NUMERACION
        Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2,v_vIdUsu_in);

        FOR v_rCurMov2 IN curMov2(v_rCurTipMov.TIP_MOV,v_rCurTipMov.COD_PROD)
        LOOP

          IF v_rCurTipMov.TIP_MOV = 'CC' THEN
            IF (v_cNumTipDoc IS NOT NULL) AND (v_cNumTipDoc <> v_rCurMov2.NUM_TIP_DOC) THEN
              v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2);
              --ACTUALIZAR NUMERACION
              Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2,v_vIdUsu_in);
            END IF;
          END IF;

          IF v_rCurTipMov.TIP_MOV = 'RD' THEN
            IF v_rCurMov2.SIGNO = '-' THEN
              v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2);
              --ACTUALIZAR NUMERACION
              Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2,v_vIdUsu_in);
            END IF;
          END IF;

--          curDetFrac:=INT_GET_DET_FRAC_PROD(v_rCurMov2.COD_GRUPO_CIA,v_rCurMov2.COD_LOCAL,v_rCurMov2.IND_VTA_FRACC);
-- 2009-01-16 RCASTRO - Se corrige el tercer parámetro, se enviaba COD_LOCAL en lugar de COD_PROD
          curDetFrac:=INT_GET_DET_FRAC_PROD(v_rCurMov2.COD_GRUPO_CIA, v_rCurMov2.COD_PROD, v_rCurMov2.IND_VTA_FRACC);
          v_cUnidVenta := '';
          v_cFactConv := '';
          FETCH curDetFrac INTO v_cUnidVenta,v_cFactConv;
          CLOSE curDetFrac;

          IF (SUBSTR(v_rCurMov2.DESC_GLOSA_AJUSTE,1,16) != 'VIENE DEL CODIGO') THEN

              v_cUnidVentaDestinoCC := '';
              v_cFactConvDestinoCC := '';
--      DBMS_OUTPUT.PUT_LINE('SUBSTR(v_rCurMov2.DESC_GLOSA_AJUSTE,-6)=' || SUBSTR(v_rCurMov2.DESC_GLOSA_AJUSTE,-6));
--      DBMS_OUTPUT.PUT_LINE('v_rCurMov2.DESC_GLOSA_AJUSTE=' || v_rCurMov2.DESC_GLOSA_AJUSTE);
              curDetFrac:=INT_GET_DET_FRAC_PROD(v_rCurMov2.COD_GRUPO_CIA, SUBSTR(v_rCurMov2.DESC_GLOSA_AJUSTE,-6), v_rCurMov2.IND_VTA_FRACC_CC);

              FETCH curDetFrac INTO v_cUnidVentaDestinoCC, v_cFactConvDestinoCC;
              CLOSE curDetFrac;

      IF TRIM(v_cFactConvDestinoCC) IS NULL THEN
         v_cFactConvDestinoCC := 0;
      END IF;

/*
      IF TRIM(v_rCurMov2.COD_PROD_DESTINO_CC) IS NULL THEN
         v_cFactConv := 1;
      END IF;
*/

--      DBMS_OUTPUT.PUT_LINE('v_rCurMov2.COD_PROD_DESTINO_CC=' || v_rCurMov2.COD_PROD_DESTINO_CC || ', v_rCurMov2.IND_VTA_FRACC_CC=' || v_rCurMov2.IND_VTA_FRACC_CC || ', v_cFactConvDestinoCC=' || v_cFactConvDestinoCC ||'.');

          --INSERTA
              INSERT INTO INT_AJUSTE(COD_GRUPO_CIA,
                                      COD_LOCAL,
                                      SEC_KARDEX,
                                      NUM_AJUSTE,
                                      COD_PROD,
                                      TIP_AJUSTE,
                                      FEC_AJUSTE,
                                      COD_PROV,
                                      CANT_SOL,
                                      IMP_AJUSTE,
                                      MOT_AJUSTE,
                                      IND_VTA_FRACC,
                                      SIGNO,
                                      VAL_FACT_CONVERSION,
                                      NOM_TIENDA,
                                      CIUDAD,
                                      RUC_EMPRESA,
                                      COD_PROD_CC,
                                      IND_VTA_FRACC_CC,
                                      VAL_FACT_CONVERSION_CC,
                                      -- DUBILLUZ 05.01.2012
                                      -- INICIO
                                      ORIGEN_1172,
                                      DESTINO_1172
                                      -- FIN
                                      )
              VALUES(v_rCurMov2.COD_GRUPO_CIA,
                      v_rCurMov2.COD_LOCAL,
                      v_rCurMov2.SEC_KARDEX,
                      v_nNumSecDoc,
                      v_rCurMov2.COD_PROD,
                      v_rCurMov2.TIP_AJUSTE,
                      v_rCurMov2.FEC_AJUSTE,
                      v_rCurMov2.COD_PROV,
                      v_rCurMov2.CANT_SOL,
                      v_rCurMov2.IMP_AJUSTE,
                      v_rCurMov2.MOT_AJUSTE,
                      v_rCurMov2.IND_VTA_FRACC,
                      v_rCurMov2.SIGNO,
                      v_cFactConv,
                      REEMPLAZAR_CARACTERES(v_rCurMov2.NOM_TIENDA),
                      REEMPLAZAR_CARACTERES(v_rCurMov2.CIUDAD),
                      v_rCurMov2.RUC_EMPRESA,
                      v_rCurMov2.COD_PROD_DESTINO_CC,
                      v_rCurMov2.IND_VTA_FRACC_CC,
                      v_cFactConvDestinoCC,
                      -- DUBILLUZ 05.01.2012
                      -- INICIO
                      v_rCurMov2.ORIGEN_1172,
                      v_rCurMov2.DESTINO_1172
                      -- DUBILLUZ 05.01.2012
                      -- FIN
                      );

          END IF;

          --ACTUALIZA MOV 2
          UPDATE LGT_KARDEX SET USU_MOD_KARDEX = v_vIdUsu_in, FEC_MOD_KARDEX = SYSDATE,
              NUM_AJUSTE = v_nNumSecDoc,
              FEC_PROCESO_SAP = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCurMov2.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCurMov2.COD_LOCAL
                AND SEC_KARDEX = v_rCurMov2.SEC_KARDEX;

          --GUARDA EL VALOR DEL NUM_NOTA_ES
          v_cNumTipDoc :=  v_rCurMov2.NUM_TIP_DOC;
        END LOOP;

      END LOOP;

    --ROLLBACK;
--    COMMIT;

    INT_GET_RESUMEN_MOV2(cCodGrupoCia_in, cCodLocal_in);

  EXCEPTION
   WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE. VERIFIQUE: '||SQLCODE||' -ERROR- '||SQLERRM);
      --MAIL DE ERROR DE INTERFACE DE AJUSTES
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE AJUSTES.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');


  END;

  /****************************************************************************/
  PROCEDURE INT_GET_RESUMEN_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
    CURSOR curResumen IS
    SELECT COD_LOCAL||
          Farma_Utility.COMPLETAR_CON_SIMBOLO(COD_SOLICITUD,10,'0','I')||
          TO_CHAR(FEC_PEDIDO,'yyyyMMdd')||
          TO_CHAR(FEC_ENTREGA,'yyyyMMdd')||
          IND_URGENCIA||
          RPAD(COD_PROD,18,' ')||
          TO_CHAR(CANT_SOLICITADA,'999999999999')||--999999999999
          TRIM(TO_CHAR(MARGEN,'0.0000'))||
          TO_CHAR(ROTACION,'9999999990.0')||
          IND_PED_APROV
          AS RESUMEN
    FROM INT_PEDIDO_REP
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          --AND TO_CHAR(FEC_PEDIDO,'dd/MM/yyyy') = vFecProceso_in
          AND FEC_GENERACION IS NULL
     ORDER BY Farma_Utility.COMPLETAR_CON_SIMBOLO(COD_SOLICITUD,10,'0','I');
    v_rCurResumen curResumen%ROWTYPE;

    v_vNombreArchivo VARCHAR2(100);
    v_nCant INTEGER;
  BEGIN

    SELECT COUNT(*) INTO v_nCant
    FROM INT_PEDIDO_REP
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          --AND TO_CHAR(FEC_PEDIDO,'dd/MM/yyyy') = vFecProceso_in
          AND FEC_GENERACION IS NULL
          ;

    IF v_nCant > 0 THEN
      v_vNombreArchivo := 'SR'||cCodLocal_in||TO_CHAR(SYSDATE,'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));

      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      FOR v_rCurResumen IN curResumen
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumen.RESUMEN);
      END LOOP;
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);

      UPDATE INT_PEDIDO_REP
      SET FEC_GENERACION = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND FEC_GENERACION IS NULL;
      COMMIT;
      --MAIL DE EXITO DE INTERFACE DE INVENTARIO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'INTERFACE PEDIDO REPOSICON EXITOSO: ',
                                        'EXITO',
                                        'EL PROCESO SE EJECUTO CORRECTAMENTE PARA LA FECHA: '||SYSDATE||'</B>'||
                                        '<BR><I>ARCHIVO: </I>'||v_vNombreArchivo||'<B>');
    END IF;

  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR ARCHIVO: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR EL ARCHIVO DE PEDIDO REPOSICION.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');
  END;

  /***************************************************************************/
  PROCEDURE INT_GET_RESUMEN_REC_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
    CURSOR curResumen IS
    SELECT COD_LOCAL||
           LPAD(TRIM(NUM_GUIA_RECEPCION),10,' ')||
           TO_CHAR(FEC_INGRESO_MERCA,'yyyyMMdd')||
           Farma_Utility.COMPLETAR_CON_SIMBOLO(NUM_INTERNO_SOLIC,10,'0','I')||
           RPAD(COD_PROD,18,' ')||
           RPAD(NVL(NUM_LOTE,' '),10,' ')||
           LPAD(NVL(TO_CHAR(FEC_VENC,'yyyyMMdd'),' '),8,' ')||
           LPAD(CANT_CONFIRMADA,13,' ')||
       NUM_ENTREGA
           AS RESUMEN
    FROM INT_CONF_RECEP_PROD
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_GENERACION IS NULL;
    v_rCurResumen curResumen%ROWTYPE;

    v_vNombreArchivo VARCHAR2(100);
    v_nCant INTEGER;
  BEGIN
    SELECT COUNT(*) INTO v_nCant
    FROM INT_CONF_RECEP_PROD
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_GENERACION IS NULL
          ;

    IF v_nCant > 0 THEN
      v_vNombreArchivo := 'CI'||cCodLocal_in||TO_CHAR(SYSDATE,'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));

      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      FOR v_rCurResumen IN curResumen
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumen.RESUMEN);
      END LOOP;
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);

      UPDATE INT_CONF_RECEP_PROD
      SET FEC_GENERACION = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND FEC_GENERACION IS NULL;
      COMMIT;
      --MAIL DE EXITO DE INTERFACE DE INVENTARIO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'INTERFACE RECEPCION PRODUCTOS EXITOSO: ',
                                        'EXITO',
                                        'EL PROCESO SE EJECUTO CORRECTAMENTE PARA LA FECHA: '||SYSDATE||'</B>'||
                                        '<BR><I>ARCHIVO: </I>'||v_vNombreArchivo||'<B>');
    END IF;


   EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR ARCHIVO: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR EL ARCHIVO DE RECEPCION DE PRODUCTOS.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');
  END;

  /****************************************************************************/
  PROCEDURE INT_GET_RESUMEN_MOV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
    CURSOR curResumen IS
    SELECT COD_LOCAL||
            NUM_GUIA||
            LPAD(NUM_SEC_GUIA,3,'0')||
            TIP_MOV||
            TO_CHAR(FEC_MOV,'yyyyMMdd')||
            RPAD(I.COD_PROD,18,' ')||
            LPAD(CANT_MOV,13,' ')||
            LPAD(TRIM(MOTIVO),3,'0')||
-- 2010-06-21 JOLIVA: Se agrega caso en que el lote tenga menos de 10 caracteres
--            SUBSTR(I.NUM_LOTE_PROD,LENGTH(I.NUM_LOTE_PROD)-9,LENGTH(I.NUM_LOTE_PROD))--JCORTEZ 17/05/2010 Se ingresa solo 10 caracteres
-- 2011-09-28 JOLIVA: Se agregan campos por integración con BTL
--            RPAD(TRIM(I.NUM_LOTE_PROD),10,' ')||
-- 2011-11-09 JOLIVA: Se corrige el campo NUM_LOTE_PROD ya que con NULOS fallaba
            --RPAD(NVL(TRIM(I.NUM_LOTE_PROD),' '),10,' ')||
            REPLACE(RPAD(NVL(TRIM(I.NUM_LOTE_PROD),' '),10,' '),'Ñ','N')||
--            CASE WHEN LENGTH(TRIM(I.NUM_LOTE_PROD)) > 10 THEN SUBSTR(TRIM(I.NUM_LOTE_PROD),LENGTH(TRIM(I.NUM_LOTE_PROD))-9,LENGTH(TRIM(I.NUM_LOTE_PROD))) ELSE TRIM(I.NUM_LOTE_PROD) END ||
            '0001' || -- Almacén de origen
            '1172' || -- Centro de origen
            '1171'  -- Centro de destino
            AS RESUMEN,
            I.COD_LOCAL, I.NUM_GUIA, L.NOM_LAB, I.COD_PROD,i.sec_notadet
      FROM INT_MOVIMIENTO I,
           LGT_PROD P,
           LGT_LAB L
      WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
            AND I.COD_LOCAL = cCodLocal_in
            AND I.FEC_GENERACION IS NULL
            AND  P.COD_GRUPO_CIA = I.COD_GRUPO_CIA
            AND  P.COD_PROD = I.COD_PROD
            AND  P.COD_LAB = L.COD_LAB
     UNION
    SELECT COD_LOCAL||
            NUM_GUIA||
            LPAD(NUM_SEC_GUIA,3,'0')||
            TIP_MOV||
            TO_CHAR(FEC_MOV,'yyyyMMdd')||
            RPAD(I.COD_PROD,18,' ')||
            LPAD(CANT_MOV,13,' ')||
            LPAD(TRIM(MOTIVO),3,'0')||
-- 2010-06-21 JOLIVA: Se agrega caso en que el lote tenga menos de 10 caracteres
--            SUBSTR(I.NUM_LOTE_PROD,LENGTH(I.NUM_LOTE_PROD)-9,LENGTH(I.NUM_LOTE_PROD))--JCORTEZ 17/05/2010 Se ingresa solo 10 caracteres
-- 2011-09-28 JOLIVA: Se agregan campos por integración con BTL
--            RPAD(TRIM(I.NUM_LOTE_PROD),10,' ')||
-- 2011-11-09 JOLIVA: Se corrige el campo NUM_LOTE_PROD ya que con NULOS fallaba
            --RPAD(NVL(TRIM(I.NUM_LOTE_PROD),' '),10,' ')||
            REPLACE(RPAD(NVL(TRIM(I.NUM_LOTE_PROD),' '),10,' '),'Ñ','N')||
--            CASE WHEN LENGTH(TRIM(I.NUM_LOTE_PROD)) > 10 THEN SUBSTR(TRIM(I.NUM_LOTE_PROD),LENGTH(TRIM(I.NUM_LOTE_PROD))-9,LENGTH(TRIM(I.NUM_LOTE_PROD))) ELSE TRIM(I.NUM_LOTE_PROD) END ||
            '0001' || -- Almacén de origen
            '1172' || -- Centro de origen
            '1171'  -- Centro de destino
            AS RESUMEN,
            I.COD_LOCAL, I.NUM_GUIA, L.NOM_LAB, I.COD_PROD,i.sec_notadet
      FROM INT_MOVIMIENTO I,
           LGT_PROD P,
           LGT_LAB L
      WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
            AND I.COD_LOCAL = cCodLocal_in
            AND I.FEC_GENERACION IS NULL
            AND  P.COD_GRUPO_CIA = I.COD_GRUPO_CIA
            AND  P.COD_PROD_FRAC = I.COD_PROD
            AND  P.COD_LAB = L.COD_LAB
     ORDER BY 2, 3, 4, 5,6;

    v_rCurResumen curResumen%ROWTYPE;

    v_vNombreArchivo VARCHAR2(100);
    v_nCant INTEGER;
  BEGIN
    SELECT COUNT(*) INTO v_nCant
     FROM INT_MOVIMIENTO
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND FEC_GENERACION IS NULL;

    IF v_nCant > 0 THEN
      v_vNombreArchivo := 'CC'||cCodLocal_in||TO_CHAR(SYSDATE,'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));

      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      FOR v_rCurResumen IN curResumen
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumen.RESUMEN);
      END LOOP;
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);

      UPDATE INT_MOVIMIENTO
      SET FEC_GENERACION = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND FEC_GENERACION IS NULL;
      COMMIT;
      --MAIL DE EXITO DE INTERFACE DE INVENTARIO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'INTERFACE DEVOLUCIONES EXITOSO: ',
                                        'EXITO',
                                        'EL PROCESO SE EJECUTO CORRECTAMENTE PARA LA FECHA: '||SYSDATE||'</B>'||
                                        '<BR><I>ARCHIVO: </I>'||v_vNombreArchivo||'<B>');
    END IF;


   EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR ARCHIVO: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR EL ARCHIVO DE DEVOLUCIONES.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');
  END;

  /***************************************************************************/
  PROCEDURE INT_GET_RESUMEN_MOV2(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
     V_ALMACEN_DEST  CHAR(4) := '0001';  -- almacen Cotización de Competencia Centro 2
    CURSOR curResumen IS
    SELECT COD_LOCAL||
          LPAD(TRIM(NUM_AJUSTE),10,'0')||
          TIP_AJUSTE||
-- 2009-06-05 JOLIVA: Cuando un ajuste se hizo antes del mes pasado, tomar como fecha de ajuste el primer día del mes pasado
--          TO_CHAR(FEC_AJUSTE,'yyyyMMdd')||
          -- TO_CHAR(CASE WHEN FEC_AJUSTE < ADD_MONTHS(TRUNC(SYSDATE,'MM'),-1) THEN ADD_MONTHS(TRUNC(SYSDATE,'MM'),-1) ELSE FEC_AJUSTE END,'yyyyMMdd')||
          -- dubilluz 05.01.2012
          -- inicio
          decode
          (
          to_char(FEC_AJUSTE,'yyyymm'),
          '201110' , '20111201',
          '201111' , '20111201',
          --TO_CHAR(CASE WHEN FEC_AJUSTE < ADD_MONTHS(TRUNC(SYSDATE,'MM'),-1) THEN ADD_MONTHS(TRUNC(SYSDATE,'MM'),-1) ELSE FEC_AJUSTE END,'yyyyMMdd')
          TO_CHAR(
          CASE WHEN TO_NUMBER(TO_CHAR(SYSDATE,'dd'))<=g_cNumDias  AND FEC_AJUSTE BETWEEN ADD_MONTHS(TRUNC(SYSDATE ,'MM'),-1) AND SYSDATE
                         THEN FEC_AJUSTE
                         WHEN TO_NUMBER(TO_CHAR(SYSDATE,'dd'))> g_cNumDias  AND FEC_AJUSTE BETWEEN TRUNC(SYSDATE ,'MM') AND SYSDATE
                         THEN FEC_AJUSTE
                         ELSE TRUNC(SYSDATE ,'MM')
                         END
          ,'yyyyMMdd')
          )||
          -- dubilluz 05.01.2012
          -- fin
          LPAD(NVL(TRIM(COD_PROV),' '),10,' ')||
          LPAD(COD_PROD,18,' ')||
          LPAD(CANT_SOL,13,' ')||
          IND_VTA_FRACC||
          NVL(SIGNO,' ')||
          LPAD(DECODE(IMP_AJUSTE,NULL,' ',TRIM(TO_CHAR(IMP_AJUSTE,'9,999,990.000'))),13,' ')||
          LPAD(NVL(MOT_AJUSTE,' '),4,' ')||
          RPAD(NVL(NOM_TIENDA,' '),20,' ')  ||
          RPAD(NVL(CIUDAD,' '),10,' ')  ||
          RPAD(NVL(RUC_EMPRESA,' '),11,' ') ||
-- 2009-03-18 JOLIVA - Se agregan código, indicador de fraccion y factor de conversión del producto destino
          LPAD(NVL(COD_PROD_CC,' '),18,' ') ||
          NVL(IND_VTA_FRACC_CC,' ') ||
          LPAD(NVL(VAL_FACT_CONVERSION,1), 5, '0') ||
          LPAD(NVL(VAL_FACT_CONVERSION_CC,0), 5, '0')||
          --DECODE(TIP_AJUSTE,'CC',LPAD(' ',18,' '),'')  || '' ||  --MAT HIJO
          --DECODE(TIP_AJUSTE,'CC',LPAD(' ',18,' '),'') || '' ||  --MAT DEST HIJO
          --DECODE(TIP_AJUSTE,'CC',LPAD(' ',1,' '),'') || '' ||
          --DECODE(TIP_AJUSTE,'CC',V_ALMACEN_DEST,'')||
          -- DUBILLUZ 06.01.2012
          -- inicio
          LPAD(' ',18,' ') || '' ||  --MAT HIJO
          LPAD(' ',18,' ') || '' ||  --MAT DEST HIJO
          LPAD(' ',1,' ')  || '' ||
          DECODE(TIP_AJUSTE,'CC',V_ALMACEN_DEST,
                 'AJ',LPAD(NVL(DESTINO_1172,0),4,'0'),
                 'AD',LPAD(NVL(DESTINO_1172,0),4,'0'),
                 '')|| -- destino
          DECODE(TIP_AJUSTE,'CC','',
                 'AJ',LPAD(NVL(origen_1172,0),4,'0'),
                 'AD',LPAD(NVL(DESTINO_1172,0),4,'0'),
                 '')-- origen
          -- DUBILLUZ 06.01.2012
          -- FIN

          AS RESUMEN
    FROM INT_AJUSTE
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_GENERACION IS NULL
          AND TIP_AJUSTE <> 'CG'
    ORDER BY NUM_AJUSTE,TIP_AJUSTE,MOT_AJUSTE,COD_PROD;
    v_rCurResumen curResumen%ROWTYPE;

    v_vNombreArchivo VARCHAR2(100);
    v_nCant INTEGER;

    CURSOR curLog IS
    SELECT NUM_COMP_PAGO,T2.NOM_TIENDA,RUC_EMPRESA,
      TO_CHAR(T2.FEC_AJUSTE,'dd/MM/yyyy') AS FEC_DOC,
      TO_CHAR(MAX(T1.FEC_KARDEX),'dd/MM/yyyy HH24:MI:SS') AS FEC_ING,
      TO_CHAR(SUM(T2.IMP_AJUSTE),'99,999,990.00') AS MONTO,
      T2.NUM_AJUSTE
    FROM LGT_KARDEX T1,
    (SELECT *
    FROM INT_AJUSTE
    WHERE TIP_AJUSTE = 'CC'
          AND FEC_GENERACION IS NULL
          )T2
    WHERE T1.COD_GRUPO_CIA = cCodGrupoCia_in
          AND T1.COD_LOCAL = cCodLocal_in
          AND T1.COD_GRUPO_CIA = T2.COD_GRUPO_CIA
          AND T1.COD_LOCAL = T2.COD_LOCAL
          AND T1.SEC_KARDEX = T2.SEC_KARDEX
    GROUP BY NUM_COMP_PAGO,T2.NOM_TIENDA,RUC_EMPRESA,
      T2.FEC_AJUSTE,
      T2.NUM_AJUSTE;

    v_rCurLog curLog%ROWTYPE;
    mesg_body VARCHAR2(20000);
  BEGIN
    SELECT COUNT(*) INTO v_nCant
    FROM INT_AJUSTE
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_GENERACION IS NULL;

    IF v_nCant > 0 THEN
      v_vNombreArchivo := 'MC'||cCodLocal_in||TO_CHAR(SYSDATE,'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));

      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      FOR v_rCurResumen IN curResumen
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumen.RESUMEN);
      END LOOP;
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);

      --AGREGADO 12/09/2006 ERIOS
      mesg_body := mesg_body||'<h4>COMPRAS COTIZACION</h4>';
      mesg_body := mesg_body||'<table style="text-align: left; width: 95%;" border="1"';
      mesg_body := mesg_body||' cellpadding="2" cellspacing="1">';
      mesg_body := mesg_body||'  <tbody>';
      mesg_body := mesg_body||'    <tr>';
      mesg_body := mesg_body||'      <th><small>DOCUMENTO</small></th>';
      mesg_body := mesg_body||'      <th><small>TIENDA</small></th>';
      mesg_body := mesg_body||'      <th><small>RUC</small></th>';
      mesg_body := mesg_body||'      <th><small>FECHA DOCUMENTO</small></th>';
      mesg_body := mesg_body||'      <th><small>FECHA INGRESO</small></th>';
      mesg_body := mesg_body||'      <th><small>MONTO DOCUMENTO</small></th>';
      mesg_body := mesg_body||'      <th><small>NUM INTERNO</small></th>';
      mesg_body := mesg_body||'    </tr>';

      --CREAR CUERPO MENSAJE;
      FOR v_rCurLog IN curLog
      LOOP
        mesg_body := mesg_body||'   <tr>'||
                                '      <td><small>'||v_rCurLog.NUM_COMP_PAGO||'</small></td>'||
                                '      <td><small>'||v_rCurLog.NOM_TIENDA||'</small></td>'||
                                '      <td><small>'||v_rCurLog.RUC_EMPRESA||'</small></td>'||
                                '      <td><small>'||v_rCurLog.FEC_DOC||'</small></td>'||
                                '      <td><small>'||v_rCurLog.FEC_ING||'</small></td>'||
                                '      <td align = right><small>'||v_rCurLog.MONTO||'</small></td>'||
                                '      <td><small>'||v_rCurLog.NUM_AJUSTE||'</small></td>'||
                                '   </tr>';
      END LOOP;
      --FIN HTML
      mesg_body := mesg_body||'  </tbody>';
      mesg_body := mesg_body||'</table>';
      mesg_body := mesg_body||'<br>';

      UPDATE INT_AJUSTE
      SET FEC_GENERACION = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND FEC_GENERACION IS NULL
            AND TIP_AJUSTE <> 'CG';
      COMMIT;
      --MAIL DE EXITO DE INTERFACE DE INVENTARIO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'INTERFACE AJUSTES EXITOSO: ',
                                        'EXITO',
                                        'EL PROCESO SE EJECUTO CORRECTAMENTE PARA LA FECHA: '||SYSDATE||'</B>'||
                                        '<BR><I>ARCHIVO: </I>'||v_vNombreArchivo||'<B><BR><BR>'||mesg_body);
    END IF;


   EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR ARCHIVO: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR EL ARCHIVO DE AJUSTES.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');
  END;

  /***************************************************************************/
  PROCEDURE INT_EJECT_RESUMEN_DIA_RANGO(codigocompania_in IN CHAR,
                cCodLocal_in     IN CHAR,
              fechainicio_in      IN CHAR,
              fechafin_in         IN CHAR)
  IS
  v_numdias     NUMBER;
  v_dia         NUMBER;
    BEGIN
         v_numdias := TO_DATE(fechafin_in,'dd/MM/yyyy') - TO_DATE(fechainicio_in,'dd/MM/yyyy');
    FOR v_dia IN  0..v_numdias LOOP
      INT_EJECT_RESUMEN_DIA (codigocompania_in, cCodLocal_in, TO_CHAR(TO_DATE(fechainicio_in,'dd/MM/yyyy') + v_dia,'dd/MM/yyyy'));
    END LOOP;
    END;

  /***************************************************************************/
  PROCEDURE INT_GET_RESUMEN_DIA_RANGO (codigocompania_in IN CHAR,
                cCodLocal_in IN CHAR,
              fechainicio_in IN CHAR,
              fechafin_in IN CHAR)
  IS
  v_numdias     NUMBER;
  v_dia         NUMBER;
    BEGIN
         v_numdias := TO_DATE(fechafin_in,'dd/MM/yyyy') - TO_DATE(fechainicio_in,'dd/MM/yyyy');
    FOR v_dia IN  0..v_numdias LOOP
      INT_GET_RESUMEN_DIA (codigocompania_in, cCodLocal_in, TO_CHAR(TO_DATE(fechainicio_in,'dd/MM/yyyy') + v_dia,'dd/MM/yyyy'));
    END LOOP;
    END;

  /***************************************************************************/
  PROCEDURE VALIDA_MONTO_VENTAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2)
  AS
    v_nMontoVentas VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
    v_nMontoInterfaz VTA_PEDIDO_VTA_CAB.VAL_NETO_PED_VTA%TYPE;
  BEGIN
    SELECT SUM(VAL_NETO_PED_VTA)
      INTO v_nMontoVentas
    FROM pbl_local t1, vta_pedido_vta_cab t2
    WHERE t1.cod_grupo_cia = cCodGrupoCia_in
              AND t1.cod_local = cCodLocal_in
              AND t1.cod_grupo_cia = t2.cod_grupo_cia
              AND t1.cod_local = t2.cod_local
              AND FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in,'dd/MM/yyyy')+0.99999
              AND EST_PED_VTA in ('C','S')
    ;

    SELECT SUM(DECODE(CLASE_DOC,3,-1*NUM_MONTO_TOTAL,4,-1*NUM_MONTO_TOTAL,5,-1*NUM_MONTO_TOTAL,6,-1*NUM_MONTO_TOTAL,11,-1*NUM_MONTO_TOTAL, 10,-1*NUM_MONTO_TOTAL, NUM_MONTO_TOTAL))
      INTO v_nMontoInterfaz
    FROM int_venta
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND fec_proceso BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy')
                                              AND TO_DATE(vFecProceso_in,'dd/MM/yyyy')+0.99999
    ;
    --DBMS_OUTPUT.PUT_LINE('LOS MONTOS DE VENTAS: FECHA= ' || vFecProceso_in || ' VENTAS='||v_nMontoVentas||':: INTERFAZ='||v_nMontoInterfaz);
    IF v_nMontoVentas <> v_nMontoInterfaz THEN
      DBMS_OUTPUT.PUT_LINE('LOS MONTOS DE VENTAS NO COINCIDEN: FECHA= ' || vFecProceso_in || ' VENTAS='||v_nMontoVentas||':: INTERFAZ='||v_nMontoInterfaz);
      RAISE_APPLICATION_ERROR(-20001,'LOS MONTOS DE VENTAS NO COINCIDEN: FECHA= ' || vFecProceso_in || ' VENTAS='||v_nMontoVentas||':: INTERFAZ='||v_nMontoInterfaz);
    END IF;

  END;

  /****************************************************************************/
  FUNCTION INT_VERIFICA_COMP_DIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,vFecha_in IN VARCHAR2)
  RETURN NUMBER
  IS
    v_nCant INTEGER;
  BEGIN
    --v_nCant := 0;
    --VERIFICA SUMA BOLETAS
    SELECT COUNT(*)
      INTO v_nCant
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND B.TIP_COMP_PAGO IN ('01', '05')
          AND B.EST_PED_VTA = 'C'
          AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecha_in,'dd/MM/yyyy')+1)
          AND C.NUM_SEC_DOC_SAP IS NULL
          AND B.FEC_PED_VTA BETWEEN TO_DATE(vFecha_in,'dd/MM/yyyy') AND TO_DATE(vFecha_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND C.COD_LOCAL = B.COD_LOCAL
          AND C.NUM_PED_VTA = B.NUM_PED_VTA
      ;
      IF v_nCant = 0 THEN
        --VERIFICA SUMA FACTURAS
        SELECT COUNT(*)
          INTO v_nCant
        FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
        WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
              AND C.COD_LOCAL = cCodLocal_in
              AND B.TIP_COMP_PAGO = '02'
              AND B.EST_PED_VTA = 'C'
              --AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
              AND C.NUM_SEC_DOC_SAP IS NULL
              AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecha_in
              AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
              AND C.COD_LOCAL = B.COD_LOCAL
              AND C.NUM_PED_VTA = B.NUM_PED_VTA
        ;
          IF v_nCant = 0 THEN
            --VERIFICA SUMA DEVOLUCIONES BOLETAS
            SELECT COUNT(*)
              INTO v_nCant
            FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
            WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND C.COD_LOCAL = cCodLocal_in
                  AND B.TIP_COMP_PAGO IN ('01', '05')
                  AND C.IND_COMP_ANUL='S'
                  AND C.NUM_SEC_DOC_SAP IS NOT NULL
                  AND C.NUM_SEC_DOC_SAP_ANUL IS NULL
                  AND TO_CHAR(C.FEC_ANUL_COMP_PAGO,'dd/MM/yyyy') = vFecha_in
                  AND B.FEC_PED_VTA < TO_DATE(vFecha_in,'dd/MM/yyyy')
                  AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                  AND C.COD_LOCAL = B.COD_LOCAL
                  AND C.NUM_PED_VTA = B.NUM_PED_VTA;
            IF v_nCant = 0 THEN
              --VERIFICA SUMA NOTA CREDITO BOLETAS
               SELECT COUNT(*)
                INTO v_nCant
                FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B, VTA_PEDIDO_VTA_DET D, VTA_PEDIDO_VTA_CAB A
                WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND C.COD_LOCAL = cCodLocal_in
                      AND B.TIP_COMP_PAGO = '04'
                      AND A.TIP_COMP_PAGO IN ('01', '05')
                      AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecha_in
                      AND B.EST_PED_VTA = 'C'
                      AND C.NUM_SEC_DOC_SAP IS NULL
                      AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                      AND C.COD_LOCAL = B.COD_LOCAL
                      AND C.NUM_PED_VTA = B.NUM_PED_VTA
                      AND C.COD_GRUPO_CIA  = D.COD_GRUPO_CIA
                      AND C.COD_LOCAL  = D.COD_LOCAL
                      AND C.NUM_PED_VTA  = D.NUM_PED_VTA
                      AND C.SEC_COMP_PAGO  = D.SEC_COMP_PAGO
                      AND B.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                      AND B.COD_LOCAL = A.COD_LOCAL
                      AND B.NUM_PED_VTA_ORIGEN = A.NUM_PED_VTA;
              IF v_nCant = 0 THEN
                --VERIFICA SUMA NOTA CREDITO FACTURAS
                SELECT COUNT(*)
                INTO v_nCant
                FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B, VTA_PEDIDO_VTA_DET D, VTA_PEDIDO_VTA_CAB A
                WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND C.COD_LOCAL = cCodLocal_in
                      AND B.TIP_COMP_PAGO = '04'
                      AND A.TIP_COMP_PAGO = '02'
                      AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecha_in
                      AND B.EST_PED_VTA = 'C'
                      AND C.NUM_SEC_DOC_SAP IS NULL
                      AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                      AND C.COD_LOCAL = B.COD_LOCAL
                      AND C.NUM_PED_VTA = B.NUM_PED_VTA
                      AND C.COD_GRUPO_CIA  = D.COD_GRUPO_CIA
                      AND C.COD_LOCAL  = D.COD_LOCAL
                      AND C.NUM_PED_VTA  = D.NUM_PED_VTA
                      AND C.SEC_COMP_PAGO  = D.SEC_COMP_PAGO
                      AND B.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                      AND B.COD_LOCAL = A.COD_LOCAL
                      AND B.NUM_PED_VTA_ORIGEN = A.NUM_PED_VTA;
              END IF;
            END IF;
          END IF;
      END IF;

    RETURN v_nCant;
  END;

  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in      IN CHAR,
                                        cCodLocal_in         IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTERFACE;
    CCReceiverAddress VARCHAR2(120) := 'dubilluz;jmelgar;pmiguel;dflores';

    mesg_body VARCHAR2(32767);

    v_vDescLocal VARCHAR2(120);
  BEGIN
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO v_vDescLocal
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in ||
                          '</B></L>'  ;

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

  PROCEDURE INT_REVERTIR_VENTAS(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                vFecha_in IN VARCHAR2,
                                vIdUsu_in IN VARCHAR2)
  AS
    v_dFecha DATE;
    v_nNumSec INT_VENTA.NUM_SEC_DOC%TYPE;
  BEGIN
    v_dFecha := TO_DATE(vFecha_in,'dd/MM/yyyy');

    --BORRA LAS VENTAS EN LA INTERFAZ
    DELETE INT_VENTA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_PROCESO BETWEEN v_dFecha AND SYSDATE;

    --REVIERTE EL PROCESO DE LA INTERFAZ
    UPDATE VTA_COMP_PAGO
    SET NUM_SEC_DOC_SAP = NULL,
        FEC_PROCESO_SAP = NULL,
        NUM_SEC_DOC_SAP_ANUL = NULL,
        FEC_PROCESO_SAP_ANUL = NULL,
        FEC_MOD_COMP_PAGO = SYSDATE,
        USU_MOD_COMP_PAGO = vIdUsu_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_CREA_COMP_PAGO BETWEEN v_dFecha AND SYSDATE;

    UPDATE VTA_COMP_PAGO
    SET NUM_SEC_DOC_SAP_ANUL = NULL,
        FEC_PROCESO_SAP_ANUL = NULL,
        FEC_MOD_COMP_PAGO = SYSDATE,
        USU_MOD_COMP_PAGO = vIdUsu_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_ANUL_COMP_PAGO BETWEEN v_dFecha AND SYSDATE;

    --ACTUALIZA EL NUMERA PARA LA INTERFAZ DE VENTAS
    SELECT NVL(MAX(NUM_SEC_DOC)+1,1)
      INTO v_nNumSec
    FROM INT_VENTA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_PROCESO < v_dFecha;

    UPDATE PBL_NUMERA
    SET VAL_NUMERA = v_nNumSec,
        FEC_MOD_NUMERA = SYSDATE,
        USU_MOD_NUMERA = vIdUsu_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_NUMERA = g_cNumIntVentas;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('EXITO AL REVERTIR VENTAS. LOCAL:'||cCodLocal_in||' FECHA: '||v_dFecha);
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('ERROR AL REVERTIR VENTAS. '||SQLERRM);
  END;
  /****************************************************************************/
  --04/12/2007 DUBILLUZ  MODIFICACION
  PROCEDURE INT_RESUMEN_CLASMAT(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
  BEGIN
    INSERT INTO INT_CLASIF_MAT(COD_GRUPO_CIA,COD_PROD,CLA_MAT)
    SELECT P.COD_GRUPO_CIA, P.COD_PROD,
          DECODE(P.IND_PROD_PROPIO/*.IND_LAB_PROPIO*/,'S','1',
                                  DECODE(P.VAL_BONO_VIG,0,'3',
                                                        '2')) AS CLASF
    FROM LGT_PROD P--, LGT_LAB L
    WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND P.EST_PROD = 'A'
          --AND P.COD_LAB = L.COD_LAB
    --ORDER BY CLASF,COD_PROD
    ;
    COMMIT;
    INT_GET_RESUMEN_CLASMAT(cCodGrupoCia_in, cCodLocal_in);
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE. VERIFIQUE: '||SQLCODE||' -ERROR- '||SQLERRM);
      --MAIL DE ERROR DE INTERFACE DE CONFIRMACION
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE CLASIFICACION MATERIAL.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');

  END;
  /****************************************************************************/
  PROCEDURE INT_GET_RESUMEN_CLASMAT(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
    CURSOR curResumen IS
    SELECT COD_PROD||
           CLA_MAT
           AS RESUMEN
    FROM INT_CLASIF_MAT
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND FEC_GENERACION IS NULL
    ORDER BY COD_PROD;
    v_rCurResumen curResumen%ROWTYPE;

    v_vNombreArchivo VARCHAR2(100);
    v_nCant INTEGER;
  BEGIN
    SELECT COUNT(*) INTO v_nCant
    FROM INT_CLASIF_MAT
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND FEC_GENERACION IS NULL
          ;

    IF v_nCant > 0 THEN
      v_vNombreArchivo := 'MATCLAS.TXT';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));

      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      FOR v_rCurResumen IN curResumen
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumen.RESUMEN);
      END LOOP;
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);

      UPDATE INT_CLASIF_MAT
      SET FEC_GENERACION = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND FEC_GENERACION IS NULL;
      COMMIT;
      --MAIL DE EXITO DE INTERFACE DE INVENTARIO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'INTERFACE CLASIFICACION MATERIAL EXITOSO: ',
                                        'EXITO',
                                        'EL PROCESO SE EJECUTO CORRECTAMENTE PARA LA FECHA: '||SYSDATE||'</B>'||
                                        '<BR><I>ARCHIVO: </I>'||v_vNombreArchivo||'<B>');
    END IF;


   EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR ARCHIVO: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR EL ARCHIVO DE CLASIFICACION MATERIAL.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');
  END;

  /*****************************************************************************/
  PROCEDURE INT_GENERA_GUIA_PROV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  nDiasAtras_in IN INTEGER DEFAULT 1)
  AS
    CURSOR curProvs IS
    SELECT R.COD_PROV,TRUNC(C.FEC_PED_VTA) AS FECHA,COUNT(D.COD_PROD)
    FROM VTA_PEDIDO_VTA_CAB C, VTA_PEDIDO_VTA_DET D, LGT_PROD_REP_DIRECTA R
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.FEC_PED_VTA < TRUNC(SYSDATE-nDiasAtras_in)
          AND C.EST_PED_VTA = 'C'
          AND R.EST_PROD_REP = 'A'
          AND D.FEC_PROCESO_GUIA_RD IS NULL
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
          AND D.COD_GRUPO_CIA = R.COD_GRUPO_CIA
          AND D.COD_PROD = R.COD_PROD
    GROUP BY R.COD_PROV,TRUNC(C.FEC_PED_VTA)
    ORDER BY 2,1;
    curProv curProvs%ROWTYPE;
    --05/11/2007 ERIOS Se soluciono el problema en la venta de recargas.
    CURSOR curProdPos(cCodProv_in IN CHAR,dFecha_in IN DATE) IS
    SELECT D.COD_PROD,D.VAL_FRAC,SUM(D.CANT_ATENDIDA) AS CANT
    FROM VTA_PEDIDO_VTA_CAB C, VTA_PEDIDO_VTA_DET D, LGT_PROD_REP_DIRECTA R
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.FEC_PED_VTA BETWEEN dFecha_in AND (dFecha_in+0.99999)
          AND C.EST_PED_VTA = 'C'
          AND R.EST_PROD_REP = 'A'
          AND R.COD_PROV = cCodProv_in
          AND D.FEC_PROCESO_GUIA_RD IS NULL
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
          AND D.COD_GRUPO_CIA = R.COD_GRUPO_CIA
          AND D.COD_PROD = R.COD_PROD
    GROUP BY D.COD_PROD,D.VAL_FRAC
    HAVING SUM(D.CANT_ATENDIDA) > 0
    ;
    /*--01/08/2007  ERIOS  Hasta solucionar la cantidad de recarga
    CURSOR curProdPos(cCodProv_in IN CHAR,dFecha_in IN DATE) IS
    SELECT D.COD_PROD,D.VAL_FRAC,CASE WHEN V.TIP_PROD_VIRTUAL = 'R' THEN
                                            SUM(D.CANT_ATENDIDA*D.VAL_PREC_VTA)
                                       ELSE SUM(D.CANT_ATENDIDA)
                                  END AS CANT
    FROM VTA_PEDIDO_VTA_CAB C, VTA_PEDIDO_VTA_DET D, LGT_PROD_REP_DIRECTA R,
         LGT_PROD_VIRTUAL V
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.FEC_PED_VTA BETWEEN dFecha_in AND (dFecha_in+0.99999)
          AND C.EST_PED_VTA = 'C'
          AND R.EST_PROD_REP = 'A'
          AND R.COD_PROV = cCodProv_in
          AND D.FEC_PROCESO_GUIA_RD IS NULL
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
          AND D.COD_GRUPO_CIA = R.COD_GRUPO_CIA
          AND D.COD_PROD = R.COD_PROD
          AND R.COD_GRUPO_CIA = V.COD_GRUPO_CIA
          AND R.COD_PROD = V.COD_PROD
    GROUP BY D.COD_PROD,D.VAL_FRAC,V.TIP_PROD_VIRTUAL
    HAVING SUM(D.CANT_ATENDIDA) > 0
    ;*/
    --05/11/2007 ERIOS Se soluciono el problema en la venta de recargas.
    CURSOR curProdNeg(cCodProv_in IN CHAR,dFecha_in IN DATE) IS
    SELECT D.COD_PROD,D.VAL_FRAC,SUM(D.CANT_ATENDIDA) AS CANT
    FROM VTA_PEDIDO_VTA_CAB C, VTA_PEDIDO_VTA_DET D, LGT_PROD_REP_DIRECTA R
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.FEC_PED_VTA BETWEEN dFecha_in AND (dFecha_in+0.99999)
          AND C.EST_PED_VTA = 'C'
          AND R.EST_PROD_REP = 'A'
          AND R.COD_PROV = cCodProv_in
          AND D.FEC_PROCESO_GUIA_RD IS NULL
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
          AND D.COD_GRUPO_CIA = R.COD_GRUPO_CIA
          AND D.COD_PROD = R.COD_PROD
    GROUP BY D.COD_PROD,D.VAL_FRAC
    HAVING SUM(D.CANT_ATENDIDA) < 0
    ;
    /*--01/08/2007  ERIOS  Hasta solucionar la cantidad de recarga
    CURSOR curProdNeg(cCodProv_in IN CHAR,dFecha_in IN DATE) IS
    SELECT D.COD_PROD,D.VAL_FRAC,CASE WHEN V.TIP_PROD_VIRTUAL = 'R' THEN
                                            SUM(D.CANT_ATENDIDA*D.VAL_PREC_VTA)
                                       ELSE SUM(D.CANT_ATENDIDA)
                                  END AS CANT
    FROM VTA_PEDIDO_VTA_CAB C, VTA_PEDIDO_VTA_DET D, LGT_PROD_REP_DIRECTA R,
         LGT_PROD_VIRTUAL V
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND C.FEC_PED_VTA BETWEEN dFecha_in AND (dFecha_in+0.99999)
          AND C.EST_PED_VTA = 'C'
          AND R.EST_PROD_REP = 'A'
          AND R.COD_PROV = cCodProv_in
          AND D.FEC_PROCESO_GUIA_RD IS NULL
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_PED_VTA = D.NUM_PED_VTA
          AND D.COD_GRUPO_CIA = R.COD_GRUPO_CIA
          AND D.COD_PROD = R.COD_PROD
          AND R.COD_GRUPO_CIA = V.COD_GRUPO_CIA
          AND R.COD_PROD = V.COD_PROD
    GROUP BY D.COD_PROD,D.VAL_FRAC,V.TIP_PROD_VIRTUAL
    HAVING SUM(D.CANT_ATENDIDA) < 0
    ;*/

    curProd curProdPos%ROWTYPE;
    v_nNumGuiaProv LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_nNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_nCantPos INTEGER;
    v_nCantNeg INTEGER;
    v_vIdUsu_in VARCHAR2(15) := 'PCK_INT_RD';
  BEGIN
    --PROVEEDORES x dias!!!
    FOR curProv IN curProvs
    LOOP
      --VALIDAR FRACCION DE LOS PRODUCTOS
      --GUIA INGRESO
      --DBMS_OUTPUT.PUT_LINE(' PROV:'||curProv.COD_PROV||' FECHA:'||curProv.FECHA);
      BEGIN
        --DBMS_OUTPUT.PUT_LINE('INGRESO');
        SAVEPOINT GUIA_INGRESO;
        --EXECUTE IMMEDIATE 'SAVEPOINT GUIA_INGRESO_'||curProv.COD_PROV;
        v_nNumGuiaProv := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumGuiaProv),10,'0','I' );
        Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumGuiaProv, v_vIdUsu_in);

        v_nNumNota := PTOVENTA_INV.INV_AGREGA_CAB_GUIA_INGRESO(cCodGrupoCia_in,cCodLocal_in,
                TO_CHAR(curProv.FECHA,'dd/MM/yyyy'),
                                      '03',--g_cTipCompGuia := '03';
                                      v_nNumGuiaProv,
                                      '03',--g_cTipoOrigenProveedor := '03'
                                      curProv.COD_PROV,
                                      1,0,'','','',v_vIdUsu_in);

        v_nCantPos := 0;
        FOR curProd IN curProdPos(curProv.COD_PROV,curProv.FECHA)
        LOOP
          --INSERTAR PRODUCTOS POR GUIA
          PTOVENTA_INV.INV_AGREGA_DET_GUIA_INGRESO(cCodGrupoCia_in,cCodLocal_in,v_nNumNota,'03',--g_cTipoOrigenProveedor CHAR(2):= '03'
                curProd.COD_PROD, 0, 0, curProd.CANT,
                                      TO_CHAR(curProv.FECHA,'dd/MM/yyyy'), '',
                                      '',
                                      '103','02',curProd.VAL_FRAC,v_vIdUsu_in);
          v_nCantPos:=v_nCantPos+1;
        END LOOP;
        --VERIFICA CANTIDAD PROD
        IF v_nCantPos = 0 THEN
          ROLLBACK TO SAVEPOINT GUIA_INGRESO;
          --EXECUTE IMMEDIATE 'ROLLBACK TO SAVEPOINT GUIA_INGRESO_'||curProv.COD_PROV;
        ELSE
          --ACTUALIZA CANTIDAD PROD EN CABECERA
          UPDATE LGT_NOTA_ES_CAB
          SET FEC_MOD_NOTA_ES_CAB = SYSDATE, USU_MOD_NOTA_ES_CAB = v_vIdUsu_in,
              CANT_ITEMS = v_nCantPos
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND NUM_NOTA_ES = v_nNumNota;
          DBMS_OUTPUT.PUT_LINE('GRABO PEDIDO GENERADO:'||v_nNumNota||' PROV:'||curProv.COD_PROV||' FECHA:'||curProv.FECHA);
        END IF;
      END;
      --DBMS_OUTPUT.PUT_LINE('FIN INGRESO');
      --GUIA SALIDA
      BEGIN
        --DBMS_OUTPUT.PUT_LINE('SALIDA');
        SAVEPOINT GUIA_SALIDA;
        --EXECUTE IMMEDIATE 'SAVEPOINT GUIA_SALIDA_'||curProv.COD_PROV;
        v_nNumGuiaProv := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumGuiaProv),10,'0','I' );
        Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumGuiaProv, v_vIdUsu_in);

        v_nNumNota := PTOVENTA_INV.INV_AGREGA_CAB_GUIA_INGRESO(cCodGrupoCia_in,cCodLocal_in,
                TO_CHAR(curProv.FECHA,'dd/MM/yyyy'),
                                      '03',--g_cTipCompGuia := '03';
                                      v_nNumGuiaProv,
                                      '03',--g_cTipoOrigenProveedor := '03'
                                      curProv.COD_PROV,
                                      1,0,'','','',v_vIdUsu_in);

        v_nCantNeg := 0;
        FOR curProd IN curProdNeg(curProv.COD_PROV,curProv.FECHA)
        LOOP
          --INSERTAR PRODUCTOS POR GUIA
          PTOVENTA_INV.INV_AGREGA_DET_GUIA_INGRESO(cCodGrupoCia_in,cCodLocal_in,v_nNumNota,'03',--g_cTipoOrigenProveedor CHAR(2):= '03'
                curProd.COD_PROD, 0, 0, curProd.CANT,
                                      TO_CHAR(curProv.FECHA,'dd/MM/yyyy'), '',
                                      '',
                                      '103','02',curProd.VAL_FRAC,v_vIdUsu_in);
          v_nCantNeg:=v_nCantNeg+1;
        END LOOP;
        --VERIFICA CANTIDAD PROD
        IF v_nCantNeg = 0 THEN
          ROLLBACK TO SAVEPOINT GUIA_SALIDA;
          --EXECUTE IMMEDIATE 'ROLLBACK TO SAVEPOINT GUIA_SALIDA_'||curProv.COD_PROV;
        ELSE
          --ACTUALIZA CANTIDAD PROD EN CABECERA
          UPDATE LGT_NOTA_ES_CAB
          SET FEC_MOD_NOTA_ES_CAB = SYSDATE, USU_MOD_NOTA_ES_CAB = v_vIdUsu_in,
              CANT_ITEMS = v_nCantNeg
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND NUM_NOTA_ES = v_nNumNota;
          DBMS_OUTPUT.PUT_LINE('GRABO PEDIDO GENERADO:'||v_nNumNota||' PROV:'||curProv.COD_PROV||' FECHA:'||curProv.FECHA);
        END IF;
      END;
      --DBMS_OUTPUT.PUT_LINE('FIN SALIDA');
    END LOOP;

    --VALIDA
    UPDATE VTA_PEDIDO_VTA_DET
    SET FEC_MOD_PED_VTA_DET = SYSDATE,USU_MOD_PED_VTA_DET = v_vIdUsu_in,
        FEC_PROCESO_GUIA_RD = SYSDATE
    WHERE (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET) IN (SELECT D.COD_GRUPO_CIA,D.COD_LOCAL,D.NUM_PED_VTA,D.SEC_PED_VTA_DET
    FROM VTA_PEDIDO_VTA_CAB C, VTA_PEDIDO_VTA_DET D, LGT_PROD_REP_DIRECTA R
    WHERE C.FEC_PED_VTA < TRUNC(SYSDATE-nDiasAtras_in)
    AND C.EST_PED_VTA = 'C'
    AND R.EST_PROD_REP = 'A'
    AND D.FEC_PROCESO_GUIA_RD IS NULL
    AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
    AND C.COD_LOCAL = D.COD_LOCAL
    AND C.NUM_PED_VTA = D.NUM_PED_VTA
    AND D.COD_GRUPO_CIA = R.COD_GRUPO_CIA
    AND D.COD_PROD = R.COD_PROD);

    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                          'ERROR AL GENERAR GUIAS DE REPOSICION DIRECTA: ',
                                          'ALERTA',
                                          'HA OCURRIDO UN ERROR AL GENERAR LAS GUIAS DE REPOSICION DIRECTA.</B>'||
                                          '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');
  END;


  /*****************************************************************************/
  FUNCTION REEMPLAZAR_CARACTERES(vCadena_in IN VARCHAR2)
  RETURN VARCHAR2
  IS
    v_vCadenaOut VARCHAR2(250);
  BEGIN

    SELECT REGEXP_REPLACE(vCadena_in,'[^A-Z0-9.]',' ')
    INTO   v_vCadenaOut
    FROM   dual ;

    v_vCadenaOut := REPLACE(v_vCadenaOut,'Ç',' ');

    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ñ','N'),'ñ','n');

    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ä','A'),'ä','a');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ë','E'),'ë','e');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ï','I'),'ï','i');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ö','O'),'ö','o');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ü','U'),'ü','u');

    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Á','A'),'á','a');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'É','E'),'é','e');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Í','I'),'í','i');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ó','O'),'ó','o');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ú','U'),'ú','u');

    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'À','A'),'à','a');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'È','E'),'è','e');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ì','I'),'ì','i');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ò','O'),'ò','o');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ù','U'),'ù','u');

    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Â','A'),'â','a');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ê','E'),'ê','e');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Î','I'),'î','i');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Ô','O'),'ô','o');
    v_vCadenaOut := REPLACE(REPLACE(v_vCadenaOut,'Û','U'),'û','u');

    v_vCadenaOut := REPLACE(v_vCadenaOut,'°',' ');
    v_vCadenaOut := REPLACE(v_vCadenaOut,'´',' ');
    v_vCadenaOut := REPLACE(v_vCadenaOut,'¨',' ');
    v_vCadenaOut := REPLACE(v_vCadenaOut,'~',' ');
    v_vCadenaOut := REPLACE(v_vCadenaOut,'^',' ');
    v_vCadenaOut := REPLACE(v_vCadenaOut,'`',' ');

    RETURN v_vCadenaOut;
  END;
  /*****************************************************************************/
  PROCEDURE INT_RESUMEN_CC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2)
  AS


    CURSOR curTipMov IS
    SELECT DISTINCT M.TIP_MOV
    FROM LGT_KARDEX K, INT_REL_COD_MOV M
    WHERE K.COD_MOT_KARDEX = M.COD_MOT_KARDEX
          AND K.FEC_KARDEX < TRUNC(SYSDATE-nDiasAtras_in)
          AND K.NUM_AJUSTE IS NULL
          AND M.TIP_INTERFACE  = '01'
          AND M.TIP_MOV IN ('CC')
          AND (K.TIP_DOC_KARDEX,K.NUM_TIP_DOC) NOT IN (SELECT '02',NUM_NOTA_ES
                              FROM LGT_NOTA_ES_CAB
                              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND COD_LOCAL = cCodLocal_in
                                    AND FEC_CREA_NOTA_ES_CAB < TRUNC(SYSDATE-nDiasAtras_in)
                                    AND TIP_NOTA_ES = '01'
                                    AND TIP_ORIGEN_NOTA_ES = '04'
                                    AND EST_NOTA_ES_CAB = 'N'
                              )
    ORDER BY 1;
    v_rCurTipMov curTipMov%ROWTYPE;

    CURSOR curMov2(cTipMov_in IN CHAR) IS
    SELECT K.COD_GRUPO_CIA,
            K.COD_LOCAL,
            K.COD_PROD,
            M.TIP_MOV AS TIP_AJUSTE,--4
            DECODE(M.TIP_MOV,'CC',(SELECT FEC_NOTA_ES_CAB
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,
                             'RD',(SELECT FEC_NOTA_ES_CAB
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,
                                 K.FEC_KARDEX) AS FEC_AJUSTE,--5
            DECODE(M.TIP_MOV,'RD',(SELECT COD_ORIGEN_NOTA_ES  --AS COD_PROVEEDOR
                                  FROM LGT_NOTA_ES_CAB
                                  WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                        AND COD_LOCAL = K.COD_LOCAL
                                        AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS COD_PROV,--6
            DECODE(M.TIP_MOV,'AD',ABS(K.CANT_MOV_PROD/K.VAL_FRACC_PROD),
                            (ABS((K.CANT_MOV_PROD*INT_GET_CANT_PRESENT(cCodGrupoCia_in,K.COD_PROD,DECODE(K.VAL_FRACC_PROD,1,' ','X'),K.VAL_FRACC_PROD))/K.VAL_FRACC_PROD))) AS CANT_SOL,--7
            --ABS(K.CANT_MOV_PROD) AS CANT_SOL,
            DECODE(M.TIP_MOV,'CC',(SELECT VAL_PREC_TOTAL
                                    FROM LGT_NOTA_ES_DET
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC
                                          AND COD_PROD = K.COD_PROD) ,'') AS IMP_AJUSTE,--8
            M.MOTIVO_AJUSTE AS MOT_AJUSTE,--9
            DECODE(M.TIP_MOV,'AD',' ',
                            DECODE(K.VAL_FRACC_PROD,1,' ','X')) AS IND_VTA_FRACC,--10
            DECODE(M.TIP_MOV,'AJ',DECODE(K.CANT_MOV_PROD/ABS(K.CANT_MOV_PROD),-1,'-','+'),
                              'AD',DECODE(K.CANT_MOV_PROD/ABS(K.CANT_MOV_PROD),-1,'-','+'),
                              'RD',DECODE(K.CANT_MOV_PROD/ABS(K.CANT_MOV_PROD),-1,'-','+'),
                                  '') AS SIGNO,--11
            K.SEC_KARDEX,--12
            DECODE(M.TIP_MOV,'CC',(SELECT DESC_EMPRESA
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS NOM_TIENDA,--13
            DECODE(M.TIP_MOV,'CC',(SELECT DIR_EMPRESA
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS CIUDAD,--14
            DECODE(M.TIP_MOV,'CC',(SELECT RUC_EMPRESA
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS RUC_EMPRESA,--15
            DECODE(M.TIP_MOV,'CC',K.NUM_TIP_DOC,NULL) AS NUM_TIP_DOC,--16
            -- DUBILLUZ 05.01.2012
            -- INICIO
            (
             select c.origen_1172
             from   aux_rel_mot_origen_envio c
             where  c.cod_mot_kardex = k.cod_mot_kardex
            ) AS "ORIGEN_1172",
            (
             select c.destino_1172
             from   aux_rel_mot_origen_envio c
             where  c.cod_mot_kardex = k.cod_mot_kardex
            ) AS "DESTINO_1172"
            -- DUBILLUZ 05.01.2012
            -- FIN
    FROM LGT_KARDEX K, INT_REL_COD_MOV M
    WHERE K.COD_GRUPO_CIA = cCodGrupoCia_in
    AND K.COD_LOCAL = cCodLocal_in
    AND K.COD_MOT_KARDEX = M.COD_MOT_KARDEX
    AND K.FEC_KARDEX < TRUNC(SYSDATE-nDiasAtras_in)
    AND K.NUM_AJUSTE IS NULL
    AND M.TIP_INTERFACE  = '01' --MOV2
    AND M.TIP_MOV = cTipMov_in
    AND K.CANT_MOV_PROD <> 0
    AND (K.TIP_DOC_KARDEX,K.NUM_TIP_DOC) NOT IN (SELECT '02',NUM_NOTA_ES
                              FROM LGT_NOTA_ES_CAB
                              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND COD_LOCAL = cCodLocal_in
                                    AND FEC_CREA_NOTA_ES_CAB < TRUNC(SYSDATE-nDiasAtras_in)
                                    AND TIP_NOTA_ES = '01'
                                    AND TIP_ORIGEN_NOTA_ES = '04'
                                    AND EST_NOTA_ES_CAB = 'N'
                              )
    ORDER BY 4,16,11;
    v_rCurMov2 curMov2%ROWTYPE;

    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;

    curDetFrac FarmaCursor;
    v_cUnidVenta INT_VENTA.UNID_MED%TYPE;
    v_cFactConv INT_VENTA.FACT_CONVERSION%TYPE;

    v_cNumTipDoc LGT_KARDEX.NUM_TIP_DOC%TYPE := NULL;
    v_vIdUsu_in VARCHAR2(15) := 'PCK_INT_MOV_CC';
  BEGIN
      FOR v_rCurTipMov IN curTipMov
      LOOP
        v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2);
        --ACTUALIZAR NUMERACION
        Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2,v_vIdUsu_in);

        FOR v_rCurMov2 IN curMov2(v_rCurTipMov.TIP_MOV)
        LOOP

          IF v_rCurTipMov.TIP_MOV = 'CC' THEN
            IF (v_cNumTipDoc IS NOT NULL) AND (v_cNumTipDoc <> v_rCurMov2.NUM_TIP_DOC) THEN
              v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2);
              --ACTUALIZAR NUMERACION
              Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2,v_vIdUsu_in);
            END IF;
          END IF;

          IF v_rCurTipMov.TIP_MOV = 'RD' THEN
            IF v_rCurMov2.SIGNO = '-' THEN
              v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2);
              --ACTUALIZAR NUMERACION
              Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2,v_vIdUsu_in);
            END IF;
          END IF;

--          curDetFrac:=INT_GET_DET_FRAC_PROD(v_rCurMov2.COD_GRUPO_CIA,v_rCurMov2.COD_LOCAL,v_rCurMov2.IND_VTA_FRACC);
-- 2009-01-16 RCASTRO - Se corrige el tercer parámetro, se enviaba COD_LOCAL en lugar de COD_PROD
          curDetFrac:=INT_GET_DET_FRAC_PROD(v_rCurMov2.COD_GRUPO_CIA, v_rCurMov2.COD_PROD, v_rCurMov2.IND_VTA_FRACC);
          FETCH curDetFrac INTO v_cUnidVenta,v_cFactConv;
          CLOSE curDetFrac;
          --INSERTA
          INSERT INTO INT_AJUSTE(COD_GRUPO_CIA,
                                  COD_LOCAL,
                                  SEC_KARDEX,
                                  NUM_AJUSTE,
                                  COD_PROD,
                                  TIP_AJUSTE,
                                  FEC_AJUSTE,
                                  COD_PROV,
                                  CANT_SOL,
                                  IMP_AJUSTE,
                                  MOT_AJUSTE,
                                  IND_VTA_FRACC,
                                  SIGNO,
                                  VAL_FACT_CONVERSION,
                                  NOM_TIENDA,
                                  CIUDAD,
                                  RUC_EMPRESA,
                                  -- DUBILLUZ 05.01.2012
                                  -- INICIO
                                  ORIGEN_1172,
                                  DESTINO_1172
                                  -- FIN
                                  )
          VALUES(v_rCurMov2.COD_GRUPO_CIA,
                  v_rCurMov2.COD_LOCAL,
                  v_rCurMov2.SEC_KARDEX,
                  v_nNumSecDoc,
                  v_rCurMov2.COD_PROD,
                  v_rCurMov2.TIP_AJUSTE,
                  v_rCurMov2.FEC_AJUSTE,
                  v_rCurMov2.COD_PROV,
                  v_rCurMov2.CANT_SOL,
                  v_rCurMov2.IMP_AJUSTE,
                  v_rCurMov2.MOT_AJUSTE,
                  v_rCurMov2.IND_VTA_FRACC,
                  v_rCurMov2.SIGNO,
                  v_cFactConv,
                  REEMPLAZAR_CARACTERES(v_rCurMov2.NOM_TIENDA),
                  REEMPLAZAR_CARACTERES(v_rCurMov2.CIUDAD),
                  v_rCurMov2.RUC_EMPRESA,
                  v_rCurMov2.ORIGEN_1172,
                  v_rCurMov2.DESTINO_1172
                  );

          --ACTUALIZA MOV 2
          UPDATE LGT_KARDEX SET USU_MOD_KARDEX = v_vIdUsu_in, FEC_MOD_KARDEX = SYSDATE,
              NUM_AJUSTE = v_nNumSecDoc,
              FEC_PROCESO_SAP = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCurMov2.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCurMov2.COD_LOCAL
                AND SEC_KARDEX = v_rCurMov2.SEC_KARDEX;

          --GUARDA EL VALOR DEL NUM_NOTA_ES
          v_cNumTipDoc :=  v_rCurMov2.NUM_TIP_DOC;
        END LOOP;

      END LOOP;
    --ROLLBACK;
    COMMIT;
    INT_GET_RESUMEN_MOV2(cCodGrupoCia_in, cCodLocal_in);
  EXCEPTION
   WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE. VERIFIQUE: '||SQLCODE||' -ERROR- '||SQLERRM);
      --MAIL DE ERROR DE INTERFACE DE AJUSTES
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE AJUSTES.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');

  END;
  /*****************************************************************************/
  PROCEDURE INT_RESUMEN_RD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2)
  AS
    CURSOR curTipMov IS
    SELECT DISTINCT M.TIP_MOV
    FROM LGT_KARDEX K, INT_REL_COD_MOV M
    WHERE K.COD_MOT_KARDEX = M.COD_MOT_KARDEX
          AND K.FEC_KARDEX < TRUNC(SYSDATE-nDiasAtras_in)
          AND K.NUM_AJUSTE IS NULL
          AND M.TIP_INTERFACE  = '01'
          AND M.TIP_MOV IN ('RD')
          AND (K.TIP_DOC_KARDEX,K.NUM_TIP_DOC) NOT IN (SELECT '02',NUM_NOTA_ES
                              FROM LGT_NOTA_ES_CAB
                              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND COD_LOCAL = cCodLocal_in
                                    AND FEC_CREA_NOTA_ES_CAB < TRUNC(SYSDATE-nDiasAtras_in)
                                    AND TIP_NOTA_ES = '01'
                                    AND TIP_ORIGEN_NOTA_ES = '04'
                                    AND EST_NOTA_ES_CAB = 'N'
                              )
    ORDER BY 1;
    v_rCurTipMov curTipMov%ROWTYPE;

    CURSOR curMov2(cTipMov_in IN CHAR) IS
    SELECT K.COD_GRUPO_CIA,
            K.COD_LOCAL,
            K.COD_PROD,
            M.TIP_MOV AS TIP_AJUSTE,--4
            DECODE(M.TIP_MOV,'CC',(SELECT FEC_NOTA_ES_CAB
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,
                             'RD',(SELECT FEC_NOTA_ES_CAB
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,
                                 K.FEC_KARDEX) AS FEC_AJUSTE,--5
            /*
            LAVILA 16/05/2014
            En Mifarma, las recargas de Movistar y Claro deben tener como proveedor a Fasa (10035)
            */
            DECODE( M.TIP_MOV,'RD',DECODE ( ( SELECT l.cod_cia
                                               FROM ptoventa.pbl_local l
                                              WHERE l.cod_grupo_cia = k.cod_grupo_cia
                                                AND l.cod_local = k.cod_local )
                                          , '001', DECODE ( TRIM ( k.cod_prod )
                                                           , '510558', '10035'
                                                           , '510559', '10035'
                                                           , '' )
                                          , '' )
                   , '') AS COD_PROV,--6
/*
            DECODE(M.TIP_MOV,'RD',(SELECT COD_ORIGEN_NOTA_ES  --AS COD_PROVEEDOR
                                  FROM LGT_NOTA_ES_CAB
                                  WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                        AND COD_LOCAL = K.COD_LOCAL
                                        AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS COD_PROV,--6
*/
            DECODE(M.TIP_MOV,'AD',ABS(K.CANT_MOV_PROD/K.VAL_FRACC_PROD),
                            (ABS((K.CANT_MOV_PROD*INT_GET_CANT_PRESENT(cCodGrupoCia_in,K.COD_PROD,DECODE(K.VAL_FRACC_PROD,1,' ','X'),K.VAL_FRACC_PROD))/K.VAL_FRACC_PROD))) AS CANT_SOL,--7
            --ABS(K.CANT_MOV_PROD) AS CANT_SOL,
            DECODE(M.TIP_MOV,'CC',(SELECT VAL_PREC_TOTAL
                                    FROM LGT_NOTA_ES_DET
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC
                                          AND COD_PROD = K.COD_PROD) ,'') AS IMP_AJUSTE,--8
            M.MOTIVO_AJUSTE AS MOT_AJUSTE,--9
            DECODE(M.TIP_MOV,'AD',' ',
                            DECODE(K.VAL_FRACC_PROD,1,' ','X')) AS IND_VTA_FRACC,--10
            DECODE(M.TIP_MOV,'AJ',DECODE(K.CANT_MOV_PROD/ABS(K.CANT_MOV_PROD),-1,'-','+'),
                              'AD',DECODE(K.CANT_MOV_PROD/ABS(K.CANT_MOV_PROD),-1,'-','+'),
                              'RD',DECODE(K.CANT_MOV_PROD/ABS(K.CANT_MOV_PROD),-1,'-','+'),
                                  '') AS SIGNO,--11
            K.SEC_KARDEX,--12
            DECODE(M.TIP_MOV,'CC',(SELECT DESC_EMPRESA
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS NOM_TIENDA,--13
            DECODE(M.TIP_MOV,'CC',(SELECT DIR_EMPRESA
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS CIUDAD,--14
            DECODE(M.TIP_MOV,'CC',(SELECT RUC_EMPRESA
                                    FROM LGT_NOTA_ES_CAB
                                    WHERE COD_GRUPO_CIA = K.COD_GRUPO_CIA
                                          AND COD_LOCAL = K.COD_LOCAL
                                          AND NUM_NOTA_ES = K.NUM_TIP_DOC) ,'') AS RUC_EMPRESA,--15
            DECODE(M.TIP_MOV,'CC',K.NUM_TIP_DOC,NULL) AS NUM_TIP_DOC--16
    FROM LGT_KARDEX K, INT_REL_COD_MOV M
    WHERE K.COD_GRUPO_CIA = cCodGrupoCia_in
    AND K.COD_LOCAL = cCodLocal_in
    AND K.COD_MOT_KARDEX = M.COD_MOT_KARDEX
    AND K.FEC_KARDEX < TRUNC(SYSDATE-nDiasAtras_in)
    AND K.NUM_AJUSTE IS NULL
    AND M.TIP_INTERFACE  = '01' --MOV2
    AND M.TIP_MOV = cTipMov_in
    AND K.CANT_MOV_PROD <> 0
    AND (K.TIP_DOC_KARDEX,K.NUM_TIP_DOC) NOT IN (SELECT '02',NUM_NOTA_ES
                              FROM LGT_NOTA_ES_CAB
                              WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                    AND COD_LOCAL = cCodLocal_in
                                    AND FEC_CREA_NOTA_ES_CAB < TRUNC(SYSDATE-nDiasAtras_in)
                                    AND TIP_NOTA_ES = '01'
                                    AND TIP_ORIGEN_NOTA_ES = '04'
                                    AND EST_NOTA_ES_CAB = 'N'
                              )
    ORDER BY 4,16,11;
    v_rCurMov2 curMov2%ROWTYPE;

    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;

    curDetFrac FarmaCursor;
    v_cUnidVenta INT_VENTA.UNID_MED%TYPE;
    v_cFactConv INT_VENTA.FACT_CONVERSION%TYPE;

    v_cNumTipDoc LGT_KARDEX.NUM_TIP_DOC%TYPE := NULL;
    v_vIdUsu_in VARCHAR2(15) := 'PCK_INT_MOV_RD';
  BEGIN
      FOR v_rCurTipMov IN curTipMov
      LOOP
        v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2);
        --ACTUALIZAR NUMERACION
        Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2,v_vIdUsu_in);

        FOR v_rCurMov2 IN curMov2(v_rCurTipMov.TIP_MOV)
        LOOP

          IF v_rCurTipMov.TIP_MOV = 'CC' THEN
            IF (v_cNumTipDoc IS NOT NULL) AND (v_cNumTipDoc <> v_rCurMov2.NUM_TIP_DOC) THEN
              v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2);
              --ACTUALIZAR NUMERACION
              Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2,v_vIdUsu_in);
            END IF;
          END IF;

          IF v_rCurTipMov.TIP_MOV = 'RD' THEN
            IF v_rCurMov2.SIGNO = '-' THEN
              v_nNumSecDoc:=Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2);
              --ACTUALIZAR NUMERACION
              Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntMov2,v_vIdUsu_in);
            END IF;
          END IF;

--          curDetFrac:=INT_GET_DET_FRAC_PROD(v_rCurMov2.COD_GRUPO_CIA,v_rCurMov2.COD_LOCAL,v_rCurMov2.IND_VTA_FRACC);
-- 2009-01-16 RCASTRO - Se corrige el tercer parámetro, se enviaba COD_LOCAL en lugar de COD_PROD
          curDetFrac:=INT_GET_DET_FRAC_PROD(v_rCurMov2.COD_GRUPO_CIA, v_rCurMov2.COD_PROD, v_rCurMov2.IND_VTA_FRACC);
          FETCH curDetFrac INTO v_cUnidVenta,v_cFactConv;
          CLOSE curDetFrac;
          --INSERTA
          INSERT INTO INT_AJUSTE(COD_GRUPO_CIA,
                                  COD_LOCAL,
                                  SEC_KARDEX,
                                  NUM_AJUSTE,
                                  COD_PROD,
                                  TIP_AJUSTE,
                                  FEC_AJUSTE,
                                  COD_PROV,
                                  CANT_SOL,
                                  IMP_AJUSTE,
                                  MOT_AJUSTE,
                                  IND_VTA_FRACC,
                                  SIGNO,
                                  VAL_FACT_CONVERSION,
                                  NOM_TIENDA,
                                  CIUDAD,
                                  RUC_EMPRESA)
          VALUES(v_rCurMov2.COD_GRUPO_CIA,
                  v_rCurMov2.COD_LOCAL,
                  v_rCurMov2.SEC_KARDEX,
                  v_nNumSecDoc,
                  v_rCurMov2.COD_PROD,
                  v_rCurMov2.TIP_AJUSTE,
                  v_rCurMov2.FEC_AJUSTE,
                  v_rCurMov2.COD_PROV,
                  v_rCurMov2.CANT_SOL,
                  v_rCurMov2.IMP_AJUSTE,
                  v_rCurMov2.MOT_AJUSTE,
                  v_rCurMov2.IND_VTA_FRACC,
                  v_rCurMov2.SIGNO,
                  v_cFactConv,
                  REEMPLAZAR_CARACTERES(v_rCurMov2.NOM_TIENDA),
                  REEMPLAZAR_CARACTERES(v_rCurMov2.CIUDAD),
                  v_rCurMov2.RUC_EMPRESA);

          --ACTUALIZA MOV 2
          UPDATE LGT_KARDEX SET USU_MOD_KARDEX = v_vIdUsu_in, FEC_MOD_KARDEX = SYSDATE,
              NUM_AJUSTE = v_nNumSecDoc,
              FEC_PROCESO_SAP = SYSDATE
          WHERE COD_GRUPO_CIA = v_rCurMov2.COD_GRUPO_CIA
                AND COD_LOCAL = v_rCurMov2.COD_LOCAL
                AND SEC_KARDEX = v_rCurMov2.SEC_KARDEX;

          --GUARDA EL VALOR DEL NUM_NOTA_ES
          v_cNumTipDoc :=  v_rCurMov2.NUM_TIP_DOC;
        END LOOP;

      END LOOP;
    --ROLLBACK;
    COMMIT;
    --INT_GET_RESUMEN_RD(cCodGrupoCia_in, cCodLocal_in);
    --17/01/2008 ERIOS Para subir a produccion.
    INT_GET_RESUMEN_MOV2(cCodGrupoCia_in, cCodLocal_in);
  EXCEPTION
   WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE. VERIFIQUE: '||SQLCODE||' -ERROR- '||SQLERRM);
      --MAIL DE ERROR DE INTERFACE DE AJUSTES
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR INTERFACE: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE DE AJUSTES.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');

  END;
  /***************************************************************************/
  PROCEDURE INT_GET_RESUMEN_RD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
    CURSOR curResumen IS
    SELECT COD_LOCAL||
          LPAD(TRIM(NUM_AJUSTE),10,'0')||
          TIP_AJUSTE||
          TO_CHAR(FEC_AJUSTE,'yyyyMMdd')||
          LPAD(NVL(TRIM(COD_PROV),' '),10,' ')||
          LPAD(COD_PROD,18,' ')||
          LPAD(CANT_SOL,13,' ')||
          IND_VTA_FRACC||
          NVL(SIGNO,' ')||
          LPAD(DECODE(IMP_AJUSTE,NULL,' ',TRIM(TO_CHAR(IMP_AJUSTE,'9,999,990.000'))),13,' ')||
          LPAD(NVL(MOT_AJUSTE,' '),4,' ')||
          RPAD(NVL(NOM_TIENDA,' '),20,' ')  ||
          RPAD(NVL(CIUDAD,' '),10,' ')  ||
          RPAD(NVL(RUC_EMPRESA,' '),11,' ')
          AS RESUMEN
    FROM INT_AJUSTE
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_GENERACION IS NULL
    ORDER BY NUM_AJUSTE,TIP_AJUSTE,MOT_AJUSTE,COD_PROD;
    v_rCurResumen curResumen%ROWTYPE;

    v_vNombreArchivo VARCHAR2(100);
    v_nCant INTEGER;

    CURSOR curLog IS
    SELECT NUM_COMP_PAGO,T2.NOM_TIENDA,RUC_EMPRESA,
      TO_CHAR(T2.FEC_AJUSTE,'dd/MM/yyyy') AS FEC_DOC,
      TO_CHAR(MAX(T1.FEC_KARDEX),'dd/MM/yyyy HH24:MI:SS') AS FEC_ING,
      TO_CHAR(SUM(T2.IMP_AJUSTE),'99,999,990.00') AS MONTO,
      T2.NUM_AJUSTE
    FROM LGT_KARDEX T1,
    (SELECT *
    FROM INT_AJUSTE
    WHERE TIP_AJUSTE = 'CC'
          AND FEC_GENERACION IS NULL
          )T2
    WHERE T1.COD_GRUPO_CIA = cCodGrupoCia_in
          AND T1.COD_LOCAL = cCodLocal_in
          AND T1.COD_GRUPO_CIA = T2.COD_GRUPO_CIA
          AND T1.COD_LOCAL = T2.COD_LOCAL
          AND T1.SEC_KARDEX = T2.SEC_KARDEX
    GROUP BY NUM_COMP_PAGO,T2.NOM_TIENDA,RUC_EMPRESA,
      T2.FEC_AJUSTE,
      T2.NUM_AJUSTE;

    v_rCurLog curLog%ROWTYPE;
    mesg_body VARCHAR2(20000);
  BEGIN
    SELECT COUNT(*) INTO v_nCant
    FROM INT_AJUSTE
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_GENERACION IS NULL;

    IF v_nCant > 0 THEN
      v_vNombreArchivo := 'RD'||cCodLocal_in||TO_CHAR(SYSDATE,'yyyyMMdd')||TO_CHAR(SYSDATE,'HH24MISS')||'.TXT';
      DBMS_OUTPUT.PUT_LINE('Archivo:'||TRIM(v_vNombreArchivo));

      ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');
      FOR v_rCurResumen IN curResumen
      LOOP
        UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,v_rCurResumen.RESUMEN);
      END LOOP;
      UTL_FILE.FCLOSE(ARCHIVO_TEXTO);

      --AGREGADO 12/09/2006 ERIOS
      mesg_body := mesg_body||'<h4>COMPRAS COTIZACION</h4>';
      mesg_body := mesg_body||'<table style="text-align: left; width: 95%;" border="1"';
      mesg_body := mesg_body||' cellpadding="2" cellspacing="1">';
      mesg_body := mesg_body||'  <tbody>';
      mesg_body := mesg_body||'    <tr>';
      mesg_body := mesg_body||'      <th><small>DOCUMENTO</small></th>';
      mesg_body := mesg_body||'      <th><small>TIENDA</small></th>';
      mesg_body := mesg_body||'      <th><small>RUC</small></th>';
      mesg_body := mesg_body||'      <th><small>FECHA DOCUMENTO</small></th>';
      mesg_body := mesg_body||'      <th><small>FECHA INGRESO</small></th>';
      mesg_body := mesg_body||'      <th><small>MONTO DOCUMENTO</small></th>';
      mesg_body := mesg_body||'      <th><small>NUM INTERNO</small></th>';
      mesg_body := mesg_body||'    </tr>';

      --CREAR CUERPO MENSAJE;
      FOR v_rCurLog IN curLog
      LOOP
        mesg_body := mesg_body||'   <tr>'||
                                '      <td><small>'||v_rCurLog.NUM_COMP_PAGO||'</small></td>'||
                                '      <td><small>'||v_rCurLog.NOM_TIENDA||'</small></td>'||
                                '      <td><small>'||v_rCurLog.RUC_EMPRESA||'</small></td>'||
                                '      <td><small>'||v_rCurLog.FEC_DOC||'</small></td>'||
                                '      <td><small>'||v_rCurLog.FEC_ING||'</small></td>'||
                                '      <td align = right><small>'||v_rCurLog.MONTO||'</small></td>'||
                                '      <td><small>'||v_rCurLog.NUM_AJUSTE||'</small></td>'||
                                '   </tr>';
      END LOOP;
      --FIN HTML
      mesg_body := mesg_body||'  </tbody>';
      mesg_body := mesg_body||'</table>';
      mesg_body := mesg_body||'<br>';

      UPDATE INT_AJUSTE
      SET FEC_GENERACION = SYSDATE
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in
            AND FEC_GENERACION IS NULL;
      COMMIT;
      --MAIL DE EXITO DE INTERFACE DE INVENTARIO
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'INTERFACE AJUSTES EXITOSO: ',
                                        'EXITO',
                                        'EL PROCESO SE EJECUTO CORRECTAMENTE PARA LA FECHA: '||SYSDATE||'</B>'||
                                        '<BR><I>ARCHIVO: </I>'||v_vNombreArchivo||'<B><BR><BR>'||mesg_body);
    END IF;


   EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,cCodLocal_in,
                                        'ERROR AL GENERAR ARCHIVO: ',
                                        'ALERTA',
                                        'HA OCURRIDO UN ERROR AL GENERAR EL ARCHIVO DE AJUSTES.</B>'||
                                        '<BR> <I>VERIFIQUE:</I> <BR>'||SUBSTR(SQLERRM, 1, 250)||'<B>');
  END;
  /***************************************************************************/
  PROCEDURE VALIDA_ORDEN_DOCUMENTO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  vFecProceso_in IN VARCHAR2)
  AS
    CURSOR cur IS
    SELECT COD_GRUPO_CIA,COD_LOCAL,FEC_PROCESO,NUM_SEC_DOC
    FROM INT_VENTA
    WHERE (COD_GRUPO_CIA,COD_LOCAL,NUM_SEC_DOC,CORRELATIVO) IN (
    SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_SEC_DOC,MAX(CORRELATIVO) AS CORRELATIVO
    FROM INT_VENTA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_PROCESO = TO_DATE(vFecProceso_in,'dd/MM/yyyy')
          AND TIP_POSICION = 1
    GROUP BY COD_GRUPO_CIA,COD_LOCAL,NUM_SEC_DOC
    )
    AND NUM_MONTO_TOTAL = 0;
  BEGIN
    FOR fila IN cur
    LOOP
      --1.-MOVER CORRELATIVOS +100000
      UPDATE INT_VENTA
      SET CORRELATIVO = CORRELATIVO+100000
      WHERE COD_GRUPO_CIA = fila.COD_GRUPO_CIA
            AND COD_LOCAL = fila.COD_LOCAL
            AND FEC_PROCESO = fila.FEC_PROCESO
            AND NUM_SEC_DOC = fila.NUM_SEC_DOC
            AND TIP_POSICION <> 2;

      --2.-DETERMINAR ORDEN
      INSERT INTO AUX_CORRELATIVO_DOC(COD_GRUPO_CIA,COD_LOCAL,NUM_SEC_DOC,CORRELATIVO,NUEVO,USU_CREA)
      SELECT V.COD_GRUPO_CIA,V.COD_LOCAL,V.NUM_SEC_DOC,V.CORRELATIVO,LPAD(ROWNUM,6,'0') AS NUEVO,'PCK_ORD_DOC'
      FROM
      (
      SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_SEC_DOC,CORRELATIVO
      FROM INT_VENTA
      WHERE COD_GRUPO_CIA = fila.COD_GRUPO_CIA
            AND COD_LOCAL = fila.COD_LOCAL
            AND FEC_PROCESO = fila.FEC_PROCESO
            AND NUM_SEC_DOC = fila.NUM_SEC_DOC
            AND TIP_POSICION <> 2
      ORDER BY NUM_MONTO_TOTAL
      ) V;

      --3.-ACTUALIZAR CORRELATIVO
      UPDATE INT_VENTA I
      SET CORRELATIVO = (SELECT  NUEVO FROM AUX_CORRELATIVO_DOC
                        WHERE COD_GRUPO_CIA = I.COD_GRUPO_CIA
                              AND COD_LOCAL = I.COD_LOCAL
                              AND NUM_SEC_DOC = I.NUM_SEC_DOC
                              AND CORRELATIVO = I.CORRELATIVO)
      WHERE COD_GRUPO_CIA = fila.COD_GRUPO_CIA
            AND COD_LOCAL = fila.COD_LOCAL
            AND FEC_PROCESO = fila.FEC_PROCESO
            AND NUM_SEC_DOC = fila.NUM_SEC_DOC
            AND TIP_POSICION <> 2;

    END LOOP;
    COMMIT;
  END;
  /***************************************************************************/

  FUNCTION INT_GET_IND_FRAC_NVO_CC(cCodGrupoCia_in IN CHAR, cCodProdOrigenCC_in IN CHAR, cCodProdDestinoCC_in IN CHAR) RETURN CHAR
  IS
    v_vIndFracc CHAR(1);

  BEGIN
    SELECT DECODE(VAL_FRAC_DESTINO,1,' ','X')
    INTO v_vIndFracc
    FROM AUX_MOV_CAMBIO_CODIGO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_PROD_ORIGEN = TRIM(cCodProdOrigenCC_in)
          AND COD_PROD_DESTINO = TRIM(cCodProdDestinoCC_in);

    RETURN v_vIndFracc;
  END;

  /***************************************************************************/

 PROCEDURE INT_RES_VTS_TICKET_BOLETAS(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cTipDoc_in      IN CHAR,
                                      vFecProceso_in  IN VARCHAR2)
  AS
    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;
    v_vNumDocRef INT_VENTA.NUM_DOC_REF%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;

    curSeries FarmaCursor;
    v_cSerie VTA_SERIE_LOCAL.NUM_SERIE_LOCAL%TYPE;

    curProd FarmaCursor;
    v_cCodProd INT_VENTA.COD_PROD%TYPE;
    v_cTipoVenta INT_VENTA.TIP_VENTA%TYPE;
    v_vIndLab INT_VENTA.TIP_LABORATORIO%TYPE;

    curTotales FarmaCursor;
    v_cValPrecio INT_VENTA.PRECIO%TYPE;
    v_cIndFrac INT_VENTA.IND_VTA_FRACC%TYPE;
    v_cCantVend INT_VENTA.CANTIDAD%TYPE;
    v_cValTotal INT_VENTA.MONTO_TOTAL%TYPE;
    v_cValIgv INT_VENTA.MONTO_IGV%TYPE;

    curDetFrac FarmaCursor;
    v_cUnidVenta INT_VENTA.UNID_MED%TYPE;
    v_cFactConv INT_VENTA.FACT_CONVERSION%TYPE;

    curDocRef FarmaCursor;
    v_cNumCompI VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
    v_cNumCompF VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
  BEGIN

    --OBTENER SERIES ASIGNADAS AL LOCAL
    curSeries:=INT_GET_SERIES_LOCAL(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,vFecProceso_in);
    LOOP
      FETCH curSeries INTO v_cSerie;
    EXIT WHEN curSeries%NOTFOUND;
      --DBMS_OUTPUT.PUT_LINE(v_cSerie);
      --CAMBIAR POR CURSOR
      curDocRef:=INT_GET_NUM_DOC_TICKET_BOLETAS(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,v_cSerie ,vFecProceso_in);

      --OBTIENE LOS PRODUCTOS PARA EL RANGO DE BOLETAS VENDIDAS
      --DBMS_OUTPUT.PUT_LINE(curDocRef%ROWCOUNT);
      LOOP
        FETCH curDocRef INTO v_cNumCompI, v_cNumCompF;
      EXIT WHEN curDocRef%NOTFOUND;
        --DBMS_OUTPUT.PUT_LINE(v_cSecCompI||','|| v_cSecCompF);
        curProd:=INT_GET_PROD_VENDIDOS_BOLETAS(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,v_cSerie ,vFecProceso_in,v_cNumCompI, v_cNumCompF);
        --curProd:=INT_GET_PROD_VENDIDOS_BOLETAS(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,v_cSerie ,vFecProceso_in);
        v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);
        -- CLASE 7 TICKET
        v_vNumDocRef:=INT_GET_NUM_DOC_REF1(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,v_cSerie ,vFecProceso_in,v_cNumCompI, v_cNumCompF);
        LOOP
          FETCH curProd INTO v_cCodProd,v_cTipoVenta,v_vIndLab;
        EXIT WHEN curProd%NOTFOUND;
          --DBMS_OUTPUT.PUT_LINE(v_cCodProd||' '||v_cTipoVenta||' '||v_vIndLab||' '||j);
          --OBTIENE LOS TOTALES DE VENTA POR PRODUCTO
          curTotales:=INT_GET_TOTALES_PROD(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in, v_cSerie,vFecProceso_in, v_cTipoVenta, v_cCodProd,v_cNumCompI, v_cNumCompF);
          FETCH curTotales INTO v_cValPrecio,v_cIndFrac,v_cCantVend,v_cValTotal,v_cValIgv;
          CLOSE curTotales;
          --OBTIENE EL DETALLE DE FRACC DEL PRODUCTO
          curDetFrac:=INT_GET_DET_FRAC_PROD(cCodGrupoCia_in,v_cCodProd,v_cIndFrac);
          FETCH curDetFrac INTO v_cUnidVenta,v_cFactConv;
          CLOSE curDetFrac;
          --OBTENER CORRELATIVO
          v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc);

          --DBMS_OUTPUT.PUT_LINE(v_cCorr||','||v_vNumDocRef||','||v_cCodProd||','||v_cCantVend||','||v_cValTotal||','||v_cValIgv);
            INSERT INTO INT_VENTA(COD_GRUPO_CIA,COD_LOCAL,NUM_SEC_DOC,CORRELATIVO,
                                  FEC_PROCESO,
                                  RUC_CLI,
                                  RAZ_SOCIAL,
                                  CLASE_DOC,
                                  NUM_DOC_REF,
                                  TIP_VENTA,
                                  TIP_LABORATORIO,
                                  COD_PROD,
                                  CANTIDAD,
                                  UNID_MED,
                                  IND_VTA_FRACC,
                                  FACT_CONVERSION,
                                  PRECIO,
                                  MONTO_IGV,
                                  MONTO_TOTAL,
                                  NUM_CANTIDAD,
                                  NUM_FACT_CONVERSION,
                                  NUM_MONTO_TOTAL,
                                  TIP_POSICION  )
            VALUES(cCodGrupoCia_in, cCodLocal_in,v_nNumSecDoc,v_cCorr,
                              TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
                              '',
                              '',--RAZ_SOCIAL
                              '7',--TICKET
                              v_vNumDocRef,
                              v_cTipoVenta,
                              v_vIndLab,
                              v_cCodProd,
                              '0'||TRIM(v_cCantVend),
                              v_cUnidVenta,
                              v_cIndFrac,
                              DECODE(v_cFactConv,NULL,'     ','0'||TRIM(v_cFactConv)),
                              '0'||TRIM(v_cValPrecio),
                              '0'||TRIM(v_cValIgv),
                              '0'||TRIM(v_cValTotal),
                              TO_NUMBER(TRIM(v_cCantVend),'00000000000.000'),
                              DECODE(v_cFactConv,NULL,0,TO_NUMBER(v_cFactConv)),
                              TO_NUMBER(TRIM(v_cValTotal),'000000000.00'),
                              '1');

        END LOOP;
        --SET_REDONDEO_BOLETAS
        SET_REDONDEO_TICKET_BOLETAS(cCodGrupoCia_in, cCodLocal_in,v_nNumSecDoc, cTipDoc_in , v_cSerie ,vFecProceso_in ,v_vNumDocRef ,v_cNumCompI, v_cNumCompF);
        --SET PROCESO EN DOCUMENTOS
        SET_MUN_SEC_PROCESO_BOLETAS(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,v_cSerie ,vFecProceso_in, v_nNumSecDoc,v_cNumCompI, v_cNumCompF);

      END LOOP;
    END LOOP;
    v_gUltimoDoc := NULL;
  END;


 /****************************************************************************/
  FUNCTION INT_GET_NUM_DOC_TICKET_BOLETAS(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in    IN CHAR,
                                          cTipDoc_in      IN CHAR,
                                          cNumSerie_in    IN CHAR,
                                          vFecProceso_in  IN VARCHAR2)
  RETURN FarmaCursor
  IS
    CURSOR curBoletas IS
    SELECT C.SEC_COMP_PAGO,C.CANT_ITEM, C.NUM_COMP_PAGO
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND B.TIP_COMP_PAGO = cTipDoc_in
          AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
          AND B.EST_PED_VTA = 'C'
          AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
          AND C.NUM_SEC_DOC_SAP IS NULL
          AND B.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND C.COD_LOCAL = B.COD_LOCAL
          AND C.NUM_PED_VTA = B.NUM_PED_VTA
      ORDER BY 3;
    v_rCurBoletas curBoletas%ROWTYPE;

    CURSOR curProductos(cNumComp_in IN CHAR) IS
    SELECT D.COD_PROD,DECODE(B.TIP_PED_VTA,'01','3','02','4','03','5') AS TIP_VENTA,C.NUM_COMP_PAGO
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B,VTA_PEDIDO_VTA_DET D
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND B.TIP_COMP_PAGO = cTipDoc_in
          AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
          AND B.EST_PED_VTA = 'C'
          AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
          AND C.NUM_COMP_PAGO = cNumComp_in
          AND C.NUM_SEC_DOC_SAP IS NULL
          AND B.FEC_PED_VTA BETWEEN TO_DATE(vFecProceso_in,'dd/MM/yyyy') AND TO_DATE(vFecProceso_in||' 23:59:59','dd/MM/yyyy HH24:MI:SS')
          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND C.COD_LOCAL = B.COD_LOCAL
          AND C.NUM_PED_VTA = B.NUM_PED_VTA
          AND B.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND B.COD_LOCAL = D.COD_LOCAL
          AND B.NUM_PED_VTA = D.NUM_PED_VTA
          AND C.SEC_COMP_PAGO = D.SEC_COMP_PAGO
      ORDER BY 1;
    v_rCurProductos curProductos%ROWTYPE;

    v_nIndBoleta INTEGER:=1;
    i INTEGER;
    v_nTotalProd INTEGER;
    v_cant_rangos INTEGER;

    curRangos FarmaCursor;
  BEGIN

  dbms_output.put_line('vFecProceso_in-->'||vFecProceso_in);
    --INICIO
    DELETE FROM TMP_RANGO_TICKET_BOLETAS;
    DELETE FROM TMP_PROD_INT_VENTA_TICKET;

    --CUERPO
    OPEN curBoletas;
    LOOP
      IF v_nIndBoleta = 1 THEN
        FETCH curBoletas INTO v_rCurBoletas;
      END IF;

    EXIT WHEN curBoletas%NOTFOUND;
      --DBMS_OUTPUT.PUT_LINE(v_rCurBoletas.SEC_COMP_PAGO);
      i := 0;
      FOR v_rCurProductos IN curProductos(v_rCurBoletas.NUM_COMP_PAGO)
      LOOP
        BEGIN
          --DBMS_OUTPUT.PUT_LINE(v_rCurProductos.COD_PROD||','||v_rCurProductos.NUM_COMP_PAGO);
          i:=i+1;
          INSERT INTO TMP_PROD_INT_VENTA_TICKET(COD_PROD,
                                          TIP_VENTA,
                                          NUM_COMP_PAGO,
                                          NUM_COMP_PAGO_2)
          VALUES(v_rCurProductos.COD_PROD,v_rCurProductos.TIP_VENTA,v_rCurProductos.NUM_COMP_PAGO,v_rCurProductos.NUM_COMP_PAGO);

          --DBMS_OUTPUT.PUT_LINE(v_rCurProductos.COD_PROD||','||v_rCurProductos.SEC_COMP_PAGO);
        EXCEPTION
          WHEN OTHERS THEN
          -- DBMS_OUTPUT.PUT_LINE('DUPLICADO->'||v_rCurProductos.COD_PROD||','||v_rCurProductos.NUM_COMP_PAGO);
           IF i = v_rCurBoletas.CANT_ITEM THEN
            UPDATE TMP_PROD_INT_VENTA_TICKET
            SET NUM_COMP_PAGO = v_rCurProductos.NUM_COMP_PAGO
            WHERE COD_PROD = v_rCurProductos.COD_PROD
                  AND TIP_VENTA = v_rCurProductos.TIP_VENTA;
           END IF;
        END;

        SELECT COUNT(*)
          INTO v_nTotalProd
        FROM TMP_PROD_INT_VENTA_TICKET;

        IF v_nTotalProd = (g_cNumProdQuiebre-1) THEN --(-1) SE DEBE AL AJUSTE QUE SE APLICA AL COMPROBANTE.
          IF i<> v_rCurBoletas.CANT_ITEM THEN
          --DBMS_OUTPUT.PUT_LINE('Total pro->'||i);
          -- DBMS_OUTPUT.PUT_LINE('v_rCurBoletas.CANT_ITEM->'||v_rCurBoletas.CANT_ITEM);
          -- DBMS_OUTPUT.PUT_LINE('v_nTotalProd->'||v_nTotalProd);
          -- DBMS_OUTPUT.PUT_LINE('g_cNumProdQuiebre->'||g_cNumProdQuiebre);
          --DBMS_OUTPUT.PUT_LINE('BORRAR->'||v_rCurBoletas.SEC_COMP_PAGO);
            DELETE FROM TMP_PROD_INT_VENTA_TICKET
            WHERE NUM_COMP_PAGO = v_rCurBoletas.NUM_COMP_PAGO
                  OR NUM_COMP_PAGO_2 = v_rCurBoletas.NUM_COMP_PAGO ;
            v_nIndBoleta := 0;

          END IF;


          INSERT INTO TMP_RANGO_TICKET_BOLETAS
          SELECT MIN(NUM_COMP_PAGO_2),MAX(NUM_COMP_PAGO)
          FROM TMP_PROD_INT_VENTA_TICKET;


          --VERIFICAR
          /*INSERT INTO T_PROD_INT_VENTA(COD_PROD,TIP_VENTA,NUM_COMP_PAGO,NUM_COMP_PAGO_2,FECHA)
          SELECT COD_PROD,TIP_VENTA,NUM_COMP_PAGO,NUM_COMP_PAGO_2,SYSDATE
          FROM TMP_PROD_INT_VENTA;*/
          --FIN VERIFICAR
          DELETE FROM TMP_PROD_INT_VENTA_TICKET;
          --DBMS_OUTPUT.PUT_LINE('-------');
          EXIT;
        END IF;
        --DBMS_OUTPUT.PUT_LINE('CONTINUA');

        v_nIndBoleta := 1;
      END LOOP;
    END LOOP;

    -- AL TERMINAR DE RECORRER TODOS LOS PRODUCTOS, GRABO LO QUE QUEDA PENDIENTE
    SELECT COUNT(*) INTO v_cant_rangos
    FROM TMP_PROD_INT_VENTA_TICKET;

    IF (v_cant_rangos > 0) THEN
       INSERT INTO TMP_RANGO_TICKET_BOLETAS
       SELECT MIN(NUM_COMP_PAGO_2),MAX(NUM_COMP_PAGO)
       FROM TMP_PROD_INT_VENTA_TICKET;
       DELETE FROM TMP_PROD_INT_VENTA_TICKET;
    END IF;

    CLOSE curBoletas;
    --FIN
    OPEN curRangos FOR
    SELECT NUM_COMP_PAGO_I,NUM_COMP_PAGO_F
    FROM TMP_RANGO_TICKET_BOLETAS;

    RETURN curRangos;
  END;


  /**************************************************/
  FUNCTION INT_GET_NUM_DOC_REF1(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cTipDoc_in IN CHAR,
                                cNumSerie_in IN CHAR,
                                vFecProceso_in IN VARCHAR2,
                                cNumCompI_in IN CHAR,
                                cNumCompF_in IN CHAR)
  RETURN VARCHAR2
  IS
    v_vBoletas VARCHAR2(25);
    v_vBoletaInicial VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
    v_vBoletaFinal VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE;
  BEGIN
    --IF v_gUltimoDoc IS NULL THEN
      --GET ULTIMO DOCUMENTO PROCESADO

-- 2009-12-14 JOLIVA: Obtengo el último número del último grupo del último día procesado
/*
      SELECT cNumSerie_in||LPAD(NVL(TRIM(SUBSTR(MAX(NUM_DOC_REF),018)+1),'0000001'),7,'0')
        INTO v_gUltimoDoc
      FROM INT_VENTA
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND CLASE_DOC = 7 --TICKET
      AND SUBSTR(NUM_DOC_REF,0,8) LIKE '12-00'||TRIM(cNumSerie_in); --JCORTEZ 02/09/2009 Se obtiene ultimo documento por impresora
*/
      SELECT cNumSerie_in||LPAD(NVL(TRIM(SUBSTR(MAX(NUM_DOC_REF),018)+1),'0000001'),7,'0')
        INTO v_gUltimoDoc
      FROM INT_VENTA V
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND COD_LOCAL = cCodLocal_in
      AND CLASE_DOC = 7 --TICKET
      AND V.FEC_PROCESO = (SELECT MAX(FEC_PROCESO)
                          FROM INT_VENTA
                          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                          AND COD_LOCAL = cCodLocal_in
                          AND CLASE_DOC = 7 --TICKET
--                          AND FEC_PROCESO BETWEEN TRUNC(SYSDATE-10) AND TRUNC(SYSDATE)
                          AND SUBSTR(NUM_DOC_REF,0,8) LIKE '12-00'||TRIM(cNumSerie_in)
                          )
      AND SUBSTR(NUM_DOC_REF,0,8) LIKE '12-00'||TRIM(cNumSerie_in);

    --END IF;
    v_vBoletaInicial := v_gUltimoDoc;
    --DBMS_OUTPUT.PUT_LINE(v_vBoletaInicial);
    --DBMS_OUTPUT.PUT_LINE(cNumCompI_in||'>'||cNumCompF_in);
    --OBTIENE ULTIMO DOCUMENTO EMITIDO EN EL RANGO
    v_vBoletaFinal := TRIM(cNumCompF_in);
    --DBMS_OUTPUT.PUT_LINE(v_vBoletas);
    --DBMS_OUTPUT.PUT_LINE(v_vBoletaFinal||'<'||v_vBoletaInicial);
    IF v_vBoletaFinal < v_vBoletaInicial THEN
      v_vBoletaFinal := v_vBoletaInicial;
      --DBMS_OUTPUT.PUT_LINE(v_vBoletaFinal);
    END IF;
    v_gUltimoDoc := LPAD((v_vBoletaFinal+1),10,'0');

    --JCORTEZ 15.07.09 por solicitud de quimica de cambia para ticket formato 12-00
    v_vBoletas := '12-00'||cNumSerie_in||'-'||SUBSTR(v_vBoletaInicial,4)||'-'||SUBSTR(v_vBoletaFinal,4);
    RETURN v_vBoletas;
  END;


   /****************************************************************************/
  PROCEDURE SET_REDONDEO_TICKET_BOLETAS(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in IN CHAR,
                                        nNumSecDoc_in IN NUMBER,
                                        cTipDoc_in IN CHAR,
                                        cNumSerie_in IN CHAR,
                                        vFecProceso_in IN VARCHAR2,
                                        cNumDocRef_in IN CHAR,
                                        cNumCompI_in IN CHAR,
                                        cNumCompF_in IN CHAR)
  AS
    v_nCantRedondeo INT_VENTA.NUM_MONTO_TOTAL%TYPE;
    v_nMontoTotal INT_VENTA .TOT_DOCUMENTO%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;
  BEGIN
    --OBTENER NUM_SEC_DOC
    --v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);
    --OBTENER CORRELATIVO
    v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, nNumSecDoc_in);

    --GET MONTO TOTAL
    SELECT ROUND(SUM(C.VAL_NETO_COMP_PAGO),2)
    INTO v_nMontoTotal
    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocal_in
          AND B.TIP_COMP_PAGO = cTipDoc_in
          AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
          AND B.EST_PED_VTA = 'C'
          AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
          AND C.NUM_SEC_DOC_SAP IS NULL
          AND C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
          AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
          AND C.COD_LOCAL = B.COD_LOCAL
          AND C.NUM_PED_VTA = B.NUM_PED_VTA;

    --GET_REDONDEO
    SELECT SUM(VAL_REDONDEO_PED_VTA)
    INTO v_nCantRedondeo
          FROM VTA_PEDIDO_VTA_CAB
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_LOCAL = cCodLocal_in
                AND NUM_PED_VTA IN (SELECT DISTINCT B.NUM_PED_VTA
                                    FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
                                    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                          AND C.COD_LOCAL = cCodLocal_in
                                          AND B.TIP_COMP_PAGO = cTipDoc_in
                                          AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
                                          AND B.EST_PED_VTA = 'C'
                                          --AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
                                          AND C.NUM_SEC_DOC_SAP IS NULL
                                          AND C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
                                          AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
                                          AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                                          AND C.COD_LOCAL = B.COD_LOCAL
                                          AND C.NUM_PED_VTA = B.NUM_PED_VTA
                                      UNION
                                      SELECT DISTINCT C.NUM_PEDIDO_ANUL
                                      FROM VTA_COMP_PAGO C, VTA_PEDIDO_VTA_CAB B
                                      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND C.COD_LOCAL = cCodLocal_in
                                            AND B.TIP_COMP_PAGO = cTipDoc_in
                                            AND SUBSTR(C.NUM_COMP_PAGO,1,3) = cNumSerie_in
                                            AND B.EST_PED_VTA = 'C'
                                            --AND (C.IND_COMP_ANUL = 'N' OR C.FEC_ANUL_COMP_PAGO > TO_DATE(vFecProceso_in,'dd/MM/yyyy')+1)
                                            AND C.NUM_SEC_DOC_SAP IS NULL
                                            AND C.NUM_COMP_PAGO BETWEEN cNumCompI_in AND cNumCompF_in
                                            AND TO_CHAR(B.FEC_PED_VTA,'dd/MM/yyyy') = vFecProceso_in
                                            AND C.NUM_PEDIDO_ANUL IS NOT NULL
                                            AND C.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                                            AND C.COD_LOCAL = B.COD_LOCAL
                                            AND C.NUM_PEDIDO_ANUL = B.NUM_PED_VTA
                                    );
    IF v_nCantRedondeo <> 0 THEN
      INSERT INTO INT_VENTA(COD_GRUPO_CIA,
                            COD_LOCAL,
                            NUM_SEC_DOC,
                            CORRELATIVO,
                            FEC_PROCESO,
                            RUC_CLI,
                            RAZ_SOCIAL,
                            CLASE_DOC,
                            NUM_DOC_REF,
                            TIP_VENTA,
                            TIP_LABORATORIO,
                            COD_PROD,
                            CANTIDAD,
                            UNID_MED,
                            IND_VTA_FRACC,
                            FACT_CONVERSION,
                            PRECIO,
                            MONTO_IGV,
                            MONTO_TOTAL,
                            NUM_CANTIDAD,
                            NUM_FACT_CONVERSION,
                            NUM_MONTO_TOTAL,
                            TIP_POSICION  )--23
      VALUES(cCodGrupoCia_in, cCodLocal_in, nNumSecDoc_in,v_cCorr,
              TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
              '',
              '',--RAZ_SOCIAL
              '7',
              cNumDocRef_in,
              '',
              '',
              '',
              '000000000000.000',
              'UN',
              '',
              '',
              '000000000.00',
              '0000000000.00',
              '0'||TRIM(v_nCantRedondeo),
              0,
              0,
              v_nCantRedondeo,
              '2');
      END IF;
    --ACTUALIZAR MONTOS
    UPDATE INT_VENTA
    SET TOT_DOCUMENTO  = v_nMontoTotal,
        RED_DOCUMENTO  = v_nCantRedondeo
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND  NUM_SEC_DOC = nNumSecDoc_in;

  END;


  /****************************************************************************/
  --DEVOLUCION TIKCET--
  PROCEDURE INT_RES_VTS_TICKET_DEVOLUCION(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in IN CHAR,
                                          cTipDoc_in IN CHAR,
                                          vFecProceso_in IN VARCHAR2)
  AS
    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;

    curSeries FarmaCursor;
    v_cSerie VTA_SERIE_LOCAL.NUM_SERIE_LOCAL%TYPE;

    curFac FarmaCursor;
    v_cSecComp VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
    v_cRucCli INT_VENTA.RUC_CLI%TYPE;
    v_cRazSocCli INT_VENTA.RAZ_SOCIAL%TYPE;
    v_vNumDocRef INT_VENTA.NUM_DOC_REF%TYPE;
    v_cTipoVenta INT_VENTA.TIP_VENTA%TYPE;
  v_cNumComprobanteAnulado INT_VENTA.NUM_DOC_ANULADO%TYPE;

    curDet FarmaCursor;
    v_vIndLab INT_VENTA.TIP_LABORATORIO%TYPE;
    v_cCodProd INT_VENTA.COD_PROD%TYPE;
    v_cCantVend INT_VENTA.CANTIDAD%TYPE;
    v_cIndFrac INT_VENTA.IND_VTA_FRACC%TYPE;
    v_cValPrecio INT_VENTA.PRECIO%TYPE;
    v_cValIgv INT_VENTA.MONTO_IGV%TYPE;
    v_cValTotal INT_VENTA.MONTO_TOTAL%TYPE;

    curDetFrac FarmaCursor;
    v_cUnidVenta INT_VENTA.UNID_MED%TYPE;
    v_cFactConv INT_VENTA.FACT_CONVERSION%TYPE;
  BEGIN
    --OBTENER SERIES ASIGNADAS AL LOCAL
    curSeries:=INT_GET_SERIES_LOCAL(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in,vFecProceso_in);
    LOOP
      FETCH curSeries INTO v_cSerie;
    EXIT WHEN curSeries%NOTFOUND;
      --DBMS_OUTPUT.PUT_LINE(v_cSerie);
      curFac:=INT_GET_DEVOLUCION_LOCAL(cCodGrupoCia_in, cCodLocal_in, cTipDoc_in, vFecProceso_in,v_cSerie);
      LOOP
        FETCH curfac INTO v_cSecComp,v_cRucCli,v_cRazSocCli,v_vNumDocRef,v_cTipoVenta, v_cNumComprobanteAnulado;
      EXIT WHEN curFac%NOTFOUND;
        --DBMS_OUTPUT.PUT_LINE(v_cSecComp||'|'||v_cRucCli||'|'||v_cRazSocCli||'|'||v_vNumDocRef||'|'||v_cTipoVenta);
        --OBTENER NUM_SEC_DOC
        v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);

        --detalle por producto
        curDet:=INT_GET_DET_FACT(cCodGrupoCia_in, cCodLocal_in,v_cSecComp);
        LOOP
          FETCH curDet INTO v_vIndLab,v_cCodProd,v_cCantVend,v_cIndFrac,v_cValPrecio,v_cValIgv,v_cValTotal;
        EXIT WHEN curDet%NOTFOUND;
          --IMPRIME EL DETALLE
          --OBTIENE EL DETALLE DE FRACC DEL PRODUCTO
          curDetFrac:=INT_GET_DET_FRAC_PROD(cCodGrupoCia_in,v_cCodProd,v_cIndFrac);
          FETCH curDetFrac INTO v_cUnidVenta,v_cFactConv;
          CLOSE curDetFrac;
            --OBTENER CORRELATIVO
            v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc);

          INSERT INTO INT_VENTA(COD_GRUPO_CIA,
                                COD_LOCAL,
                                NUM_SEC_DOC,
                                CORRELATIVO,
                                FEC_PROCESO,
                                RUC_CLI,
                                RAZ_SOCIAL,
                                CLASE_DOC,
                                NUM_DOC_REF,
                                TIP_VENTA,
                                TIP_LABORATORIO,
                                COD_PROD,
                                CANTIDAD,
                                UNID_MED,
                                IND_VTA_FRACC,
                                FACT_CONVERSION,
                                PRECIO,
                                MONTO_IGV,
                                MONTO_TOTAL,
                                NUM_CANTIDAD,
                                NUM_FACT_CONVERSION,
                                NUM_MONTO_TOTAL,
                                TIP_POSICION,
                NUM_DOC_ANULADO)
          VALUES(cCodGrupoCia_in, cCodLocal_in,v_nNumSecDoc,v_cCorr,
                            TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
                            v_cRucCli,
                            REEMPLAZAR_CARACTERES(v_cRazSocCli),
                            DECODE(cTipDoc_in,'01',3,'02',4,'05',11),
                            v_vNumDocRef,
                            v_cTipoVenta,
                            v_vIndLab,
                            v_cCodProd,
                            '0'||TRIM(v_cCantVend),
                            v_cUnidVenta,
                            v_cIndFrac,
                            DECODE(v_cFactConv,NULL,'     ','0'||TRIM(v_cFactConv)),
                            '0'||TRIM(v_cValPrecio),
                            '0'||TRIM(v_cValIgv),
                            '0'||TRIM(v_cValTotal),
                            TO_NUMBER(v_cCantVend,'00000000000.000'),
                            DECODE(v_cFactConv,NULL,0,TO_NUMBER(v_cFactConv)),
                            TO_NUMBER(TRIM(v_cValTotal),'000000000.00'),
                            '1',
              v_cNumComprobanteAnulado
                            );

        END LOOP;
        SET_REDONDEO_DEVOLUCION(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc, cTipDoc_in, v_cSerie,vFecProceso_in,v_vNumDocRef,v_cSecComp);
        --SET PROCESO EN DOCUMENTOS
        SET_MUN_SEC_PROCESO_DEVOL(v_cSecComp, v_nNumSecDoc);
        --ACTUALIZAR NUMERACION
        --Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumIntVentas,'ADMIN');
      END LOOP;
    END LOOP;
  END;

/*****************************************************************************/
 --NOTA DE CREDITO
  PROCEDURE INT_RES_NOTA_CRED_TICKET_BOL(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in IN CHAR,
                                     cTipDoc_in IN CHAR,
                                     cTipDocOrigen_in IN CHAR,
                                     vFecProceso_in IN VARCHAR2)
  AS
    v_nNumSecDoc INT_VENTA.NUM_SEC_DOC%TYPE;
    v_cCorr INT_VENTA.CORRELATIVO%TYPE;


    v_cSecComp VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
    v_cRucCli INT_VENTA.RUC_CLI%TYPE;
    v_cRazSocCli INT_VENTA.RAZ_SOCIAL%TYPE;
    v_vNumDocRef INT_VENTA.NUM_DOC_REF%TYPE;
    v_cTipoVenta INT_VENTA.TIP_VENTA%TYPE;
    v_cSecCompNC VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE;
    v_vNumDocAnul INT_VENTA.NUM_DOC_ANULADO %TYPE;

    curDet FarmaCursor;
    v_vIndLab INT_VENTA.TIP_LABORATORIO%TYPE;
    v_cCodProd INT_VENTA.COD_PROD%TYPE;
    v_cCantVend INT_VENTA.CANTIDAD%TYPE;
    v_cIndFrac INT_VENTA.IND_VTA_FRACC%TYPE;
    v_cValPrecio INT_VENTA.PRECIO%TYPE;
    v_cValIgv INT_VENTA.MONTO_IGV%TYPE;
    v_cValTotal INT_VENTA.MONTO_TOTAL%TYPE;

    curDetFrac FarmaCursor;
    v_cUnidVenta INT_VENTA.UNID_MED%TYPE;
    v_cFactConv INT_VENTA.FACT_CONVERSION%TYPE;

    v_vNumDocRefAux INT_VENTA.NUM_DOC_REF%TYPE:='';

    v_nNumSecDoc_A INT_VENTA.NUM_SEC_DOC%TYPE := NULL;
    v_cSecComp_A VTA_COMP_PAGO.SEC_COMP_PAGO%TYPE := NULL;
    v_vNumDocRef_A INT_VENTA.NUM_DOC_REF%TYPE := NULL;
    v_vNumDocAnul_A INT_VENTA.NUM_DOC_ANULADO%TYPE := NULL;
  BEGIN

        --DBMS_OUTPUT.PUT_LINE(v_vNumDocRef||'|'||v_cTipoVenta);
        curDet:=INT_GET_DET_NC(cCodGrupoCia_in, cCodLocal_in,cTipDoc_in,cTipDocOrigen_in, vFecProceso_in);
        LOOP
          FETCH curDet INTO v_cSecComp,v_cRucCli,v_cRazSocCli,v_vNumDocRef,v_cTipoVenta,v_vIndLab,v_cCodProd,v_cCantVend,v_cIndFrac,v_cValPrecio,v_cValIgv,v_cValTotal,v_cSecCompNC,v_vNumDocAnul;
        EXIT WHEN curDet%NOTFOUND;
          --OBTIENE EL DETALLE DE FRACC DEL PRODUCTO
          curDetFrac:=INT_GET_DET_FRAC_PROD(cCodGrupoCia_in,v_cCodProd,v_cIndFrac);
          FETCH curDetFrac INTO v_cUnidVenta,v_cFactConv;
          CLOSE curDetFrac;

          --OBTENER NUM_SEC_DOC
          --IF v_vNumDocRefAux <> v_vNumDocRef THEN
          IF v_vNumDocRefAux <> v_cSecCompNC THEN
            v_nNumSecDoc:=INT_GET_SEC(cCodGrupoCia_in, cCodLocal_in);
            --v_vNumDocRefAux := v_vNumDocRef;
            v_vNumDocRefAux := v_cSecCompNC;
            IF v_nNumSecDoc_A IS NOT NULL THEN
              SET_REDONDEO_NC(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc_A, cTipDocOrigen_in,vFecProceso_in,v_vNumDocRef_A,v_cSecComp_A,v_vNumDocAnul_A);
            END IF;
          END IF;

          --OBTENER CORRELATIVO
            v_cCorr:=INT_GET_CORR(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc);
            --DBMS_OUTPUT.PUT_LINE(v_nNumSecDoc||'|'||v_cSecCompNC ||'|'||v_cCorr||'|'||v_vNumDocRef||'|'||v_cCantVend);
            INSERT INTO INT_VENTA(COD_GRUPO_CIA,
                                COD_LOCAL,
                                NUM_SEC_DOC,
                                CORRELATIVO,
                                FEC_PROCESO,
                                RUC_CLI,
                                RAZ_SOCIAL,
                                CLASE_DOC,
                                NUM_DOC_REF,
                                TIP_VENTA,
                                TIP_LABORATORIO,
                                COD_PROD,
                                CANTIDAD,
                                UNID_MED,
                                IND_VTA_FRACC,
                                FACT_CONVERSION,
                                PRECIO,
                                MONTO_IGV,
                                MONTO_TOTAL,
                                NUM_CANTIDAD,
                                NUM_FACT_CONVERSION,
                                NUM_MONTO_TOTAL,
                                TIP_POSICION,
                                NUM_DOC_ANULADO)
          VALUES(cCodGrupoCia_in, cCodLocal_in,v_nNumSecDoc,v_cCorr,
                            TO_DATE(vFecProceso_in,'dd/MM/yyyy'),
                            v_cRucCli,
                            REEMPLAZAR_CARACTERES(v_cRazSocCli),
                            DECODE(cTipDocOrigen_in,'01','6','02','5','05','10',NULL),
                            v_vNumDocRef,
                            v_cTipoVenta,
                            v_vIndLab,
                            v_cCodProd,
                            '0'||TRIM(v_cCantVend),
                            v_cUnidVenta,
                            v_cIndFrac,
                            DECODE(v_cFactConv,NULL,'     ','0'||TRIM(v_cFactConv)),
                            '0'||TRIM(v_cValPrecio),
                            '0'||TRIM(v_cValIgv),
                            '0'||TRIM(v_cValTotal),
                            TO_NUMBER(TRIM(v_cCantVend),'00000000000.000'),
                            DECODE(v_cFactConv,NULL,0,TO_NUMBER(v_cFactConv)),
                            TO_NUMBER(TRIM(v_cValTotal),'000000000.00'),
                            '1',
                            --TRIM(v_vNumDocRef)
                            TRIM(v_vNumDocAnul)
                            );

          --SET PROCESO EN DOCUMENTOS
          SET_MUN_SEC_PROCESO_FACTURAS(cCodGrupoCia_in, cCodLocal_in,v_cSecComp, v_nNumSecDoc);

          --GUARDA LOS VALORES PARA EL REDONDEO
          v_nNumSecDoc_A := v_nNumSecDoc;
          v_vNumDocRef_A := v_vNumDocRef;
          v_cSecComp_A := v_cSecCompNC;
          v_vNumDocAnul_A := v_vNumDocAnul;
        END LOOP;
        --PROCESA EL REDONDEO DEL ULTIMO COMPROBANTE
        SET_REDONDEO_NC(cCodGrupoCia_in, cCodLocal_in, v_nNumSecDoc_A, cTipDocOrigen_in,vFecProceso_in,v_vNumDocRef_A,v_cSecComp_A,v_vNumDocAnul_A);

  END;
END;
/

