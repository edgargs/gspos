CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_RCM" AS

    TYPE FarmaCursor IS REF CURSOR;

    CONS_ACTIVO CHAR(1) := 'A';

      COD_CIA_MARKET_01 CHAR(3):=  '004';                                     --ASOSA - 03/09/2014

    --Descripcion: Lista las formas farmaceuticas
    --Fecha       Usuario   Comentario
    --15/04/2013  ERIOS     Creacion
    FUNCTION GET_FORMA_FARMAC
    RETURN FarmaCursor;

    --Descripcion: Lista los insumos
    --Fecha       Usuario   Comentario
    --26/04/2013  Luigy T.  Creacion
    FUNCTION LISTA_INSUMOS
    RETURN FarmaCursor;

    --Descripcion: Obtiene informacion del insumo
    --Fecha       Usuario   Comentario
    --29/04/2013  Luigy T.  Creacion

    FUNCTION GET_INF_INSUMO (cCod_Insumo_in            IN CHAR)
    RETURN FarmaCursor;

    --Descripcion: Retorna correlativo del recetario
    --Fecha        Usuario		    Comentario
    --30/04/2013   Luigy Terrazos Creación
    --FUNCTION RCD_CORRE_NUM_RECETARIO(cCodGrupoCia_in          IN CHAR,
        --                             cCod_Cia_in              IN CHAR,
      --                               cCod_Local_in            IN CHAR)
    --RETURN CHAR;

    --Descripcion: Guarda la cabecera del recetario.
    --Fecha        Usuario		    Comentario
    --30/04/2013   Luigy Terrazos Creación
    FUNCTION RCM_GRAB_CAB(cCodGrupoCia_in          IN CHAR,
                          cCod_Cia_in              IN CHAR,
                          cCod_Local_in            IN CHAR,
                          cInd_Esteril_in          IN CHAR,
                          cCod_Forma_Farmac_in     IN CHAR,
                          nCant_Contenido_in       IN NUMBER,
                          nCant_Preparados_in      IN NUMBER,
                          cFec_Recetario_in        IN CHAR,
                          cCod_Paciente_in         IN VARCHAR2,
                          cCod_Medico_in           IN CHAR,
                          cFec_Entrega_in          IN CHAR,
                          cHora_Entrega_in         IN VARCHAR2,
                          cCod_Local_Entrega_in    IN CHAR,
                          cObs_Recetario_in        IN VARCHAR2,
                          nVal_Bruto_Recetario_in  IN NUMBER,
                          cEst_Pedido_Cab_in       IN CHAR,
                          cUsu_Crea_in             IN VARCHAR2,
                          cFec_Crea_in             IN CHAR,
                          cNum_Telefono_in         IN CHAR,
                          cNum_Guia_in             IN CHAR,
                          nValIgvRecetario_in      IN NUMBER,
                          nValVentaRecetario_in    IN NUMBER,
                          vValVentaRecSinRed_in    IN NUMBER)
    RETURN CHAR;

    --Descripcion: Guarda el detalle del recetario.
    --Fecha        Usuario		    Comentario
    --30/04/2013   Luigy Terrazos Creación
    PROCEDURE RCM_GRAB_DET(cCodGrupoCia_in          IN CHAR,
                           cCod_Cia_in              IN CHAR,
                           cCod_Local_in            IN CHAR,
                           cNum_Recetario_in        IN CHAR,
                           nCod_Insumo_in           IN CHAR,
                           nCant_Atendida_in        IN NUMBER,
                           cVal_Prec_Rec_in         IN NUMBER,
                           cVal_Prec_Total_in       IN NUMBER,
                           cUsu_Crea_in             IN VARCHAR2,
                           cFec_Crea_in             IN CHAR,
                           cCodUnidadVenta_in       IN CHAR,
                           cCant_Porcentaje_in      IN NUMBER);

    --Descripcion: Asigna un codigo de pedido a un recetario magistral
    --Fecha        Usuario  Comentario
    --22/05/2013   LLEIVA   Creacion
    PROCEDURE RCM_ASIGNA_PEDIDO_RCM(cCodGrupoCia_in          IN CHAR,
                                    cCod_Cia_in              IN CHAR,
                                    cCod_Local_in            IN CHAR,
                                    cNum_Recetario           IN CHAR,
                                    cNum_Pedido              IN CHAR);

    --Descripcion: Lista las guias que se van a enlazar con los recetarios
    --             magistrales
    --Fecha        Usuario  Comentario
    --23/05/2013   LLEIVA   Creacion
    FUNCTION RCM_LISTAR_GUIAS_RCM
    RETURN FarmaCursor;

    --Descripcion: Graba una cabecera de recetario magistral adjunto a una
    --             guia previamente seleccionada
    --Fecha        Usuario  Comentario
    --24/05/2013   LLEIVA   Creacion
    FUNCTION RCM_GUIA_GRAB_CAB(cCodGrupoCia_in              IN CHAR,
                                cCod_Cia_in                 IN CHAR,
                                cCod_Local_in               IN CHAR,
                                cNum_Ord_Prep_in            IN CHAR,
                                cUsu_Crea_in                IN CHAR)
    RETURN CHAR;

    --Descripcion: Obtiene el porcentaje del Igv
    --Fecha       Usuario   Comentario
    --28/05/2013  ERIOS     Creacion
    FUNCTION GET_PORC_IGV(cCodIgv_in IN CHAR) RETURN NUMBER;

    --Descripcion: Obtiene listado de productos virtuales
    --Fecha       Usuario   Comentario
    --29/05/2013  LLEIVA    Creacion
    FUNCTION LISTA_PROD_VIRTUALES
    RETURN FarmaCursor;

    --Descripcion: Obtiene unidades relacionadas a una unidad particular
    --             y que posea un factor de conversión
    --Fecha       Usuario   Comentario
    --04/06/2013  LLEIVA    Creacion
    FUNCTION LISTA_UNID_MEDIDA_REL(cCodUnidMed_in IN CHAR)
    RETURN FarmaCursor;

    --Descripcion: Obtiene el estado de un recetario indicado
    --Fecha       Usuario   Comentario
    --13/06/2013  LLEIVA    Creacion
    FUNCTION GET_EST_RECETARIO_PEDIDO(cCodGrupoCia_in     IN CHAR,
                                      cCod_Cia_in         IN CHAR,
                                      cCod_Local_in       IN CHAR,
                                      cNum_Pedido_in      IN CHAR)
    RETURN VARCHAR2;

    --Descripcion: Anula un recetario magistral que pertenesca a un numero de pedido
    --Fecha       Usuario   Comentario
    --13/06/2013  LLEIVA    Creacion
    PROCEDURE ANULA_RECETARIO_PEDIDO(cCodGrupoCia_in     IN CHAR,
                                      cCod_Cia_in         IN CHAR,
                                      cCod_Local_in       IN CHAR,
                                      cNum_Pedido_in      IN CHAR);

    --Descripcion: Actualiza un recetario magistral que pertenesca a un numero de pedido
    --             indicando que este ha sido pagado
    --Fecha       Usuario   Comentario
    --13/06/2013  LLEIVA    Creacion
    PROCEDURE ACTUALIZA_RECET_COBRADO(cCodGrupoCia_in     IN CHAR,
                                      cCod_Cia_in         IN CHAR,
                                      cCod_Local_in       IN CHAR,
                                      cNum_Recetario      IN CHAR);

    --Descripcion: Retorna el factor de conversion de unidades de un producto con una unidad
    --             de venta
    --Fecha       Usuario   Comentario
    --18/06/2013  LLEIVA    Creacion
    FUNCTION GET_FACTOR_CONVERSION(cCod_Producto       IN CHAR,
                                   cCod_UnidadVenta    IN CHAR)
    RETURN VARCHAR2;

	    --Descripcion: Generacion trama recetario.
    --Fecha       Usuario	Comentario
    --29/05/2013  ERIOS   Creacion
    FUNCTION GET_NUMERO_RECETARIO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedido_in IN CHAR) RETURN VARCHAR2;

    --Descripcion: Generacion trama recetario.
    --Fecha       Usuario	Comentario
    --29/05/2013  ERIOS   Creacion
    FUNCTION GET_TRAMA_RECETARIO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumRecetario_in IN CHAR) RETURN VARCHAR2;

    --Descripcion: Actualiza estado de recetario.
    --Fecha       Usuario	Comentario
    --29/05/2013  ERIOS   Creacion
    PROCEDURE ACTUALIZA_ESTADO_RECETARIO(cCodGrupoCia_in IN CHAR,
                                         cCodCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cNumRecetario_in IN CHAR,
                                         cEstado_in IN CHAR,
                                         vUsuario_in IN VARCHAR2);

    --Descripcion: Imprime Contrasenia Recetario Magistral
    --Fecha        Usuario  Comentario
    --21/05/2013   ERIOS    Creacion
    FUNCTION IMP_HTML_CONTRASENIA(cCodGrupoCia_in         IN CHAR,
                          cCod_Cia_in              IN CHAR,
                          cCod_Local_in            IN CHAR,
                          cNum_Recetario_in        IN CHAR)
    RETURN VARCHAR2;

    --Descripcion: Generacion trama actualización recetario en FASA.
    --Fecha       Usuario	Comentario
    --28/06/2013  ERIOS   Creacion
    FUNCTION GET_TRAMA_ACTUALIZA_RECETARIO(cCodGrupoCia_in IN CHAR,
                                           cCodCia_in IN CHAR,
                                           cCodLocal_in IN CHAR,
                                           cNumPedido_in IN CHAR,
                                           cAccion_in IN CHAR)
    RETURN VARCHAR2;

    --Descripción: Procedimiento Actualiza estado Pedido RCM anulado (N)
    --Fecha        Usuario   Comentario
    --21/05/2013   RUDY LL.  Creacion
    --===============================================================================================================
   PROCEDURE VTA_UPDATE_PEDIDO_CAB_RCM(cCodGrupoCia_in    IN CHAR,
                                              cCodCia_in 	 	     IN CHAR,
                                   	          cCodLocal_in    	 IN CHAR,
                								   	          cNumPedidoAnul_in  IN VARCHAR2,
                                              cEstPedCab_in      IN CHAR);


    --Descripción:   Función Valida si un pedido es del tipo RCM y devuelve estado actual del pedido.
    --Fecha          Usuario  Comentario
    --21/05/2013     RUDY LL. Creacion
--===============================================================================================================
   FUNCTION VTA_GET_VALIDA_RCM(cCodGrupoCia_in IN CHAR,
                                        --cCodCia_in 	 	     IN CHAR,
                                        cCod_Local_in      IN CHAR,
                                        cNum_Comp_Pago_in  IN CHAR)

   RETURN varchar;

--===============================================================================================================
  PROCEDURE VTA_RCM_VALIDA_RCM(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in IN CHAR,
                                        cNum_Comp_Pago_in IN CHAR);
 -- RETURN BOOLEAN;
--===============================================================================================================

    --Descripcion: Reporte de Recetario Magistral
    --Fecha       Usuario	Comentario
    --27/09/2013  LLEIVA  Creacion
    FUNCTION REPORTE_RECETARIO_MAGISTRAL(cCodGrupoCia_in   IN VARCHAR2,
                                         cCodCia_in        IN VARCHAR2,
                                         cCodLocal_in      IN VARCHAR2,
                                         cFecInicial_in    IN VARCHAR2,
                                         cFecFinal_in      IN VARCHAR2,
                                         cPaciente_in      IN VARCHAR2,
                                         cNumRecetario_in  IN VARCHAR2,
                                         cNumPedido_in     IN VARCHAR2)
    RETURN FarmaCursor;

    --Descripcion: Listado de Detalle de Reporte de Recetario Magistral
    --Fecha       Usuario	Comentario
    --27/09/2013  LLEIVA  Creacion
    FUNCTION LISTADO_DETALLE_RCM(cCodGrupoCia_in   IN CHAR,
                                       cCodCia_in        IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cNumRecetario_in  IN CHAR)
    RETURN FarmaCursor;

    --Descripcion: Detalle de Reporte de Recetario Magistral
    --Fecha       Usuario	Comentario
    --27/09/2013  LLEIVA  Creacion
    FUNCTION DETALLE_RCM(cCodGrupoCia_in   IN CHAR,
                         cCodCia_in        IN CHAR,
                         cCodLocal_in      IN CHAR,
                         cNumRecetario_in  IN CHAR)
    RETURN FarmaCursor;

    --Descripcion: Retorna listado de Ordenes de Preparado a recepcionar como productos
    --Fecha       Usuario	Comentario
    --25/10/2013  LLEIVA  Creacion
    FUNCTION LISTADO_RECEPCION_RCM(cCodLocal_in      IN CHAR,
                                   cFecha_in         IN CHAR,
                                   cNumOrdenPrep_in  IN CHAR)
    RETURN FarmaCursor;

    --Descripcion: Retorna listado de detalles de Ordenes de Preparado a recepcionar como productos
    --Fecha       Usuario	Comentario
    --25/10/2013  LLEIVA  Creacion
    FUNCTION LISTADO_RECEPCION_DETALLE_RCM(cNumOrdenPrep_in  IN CHAR)
    RETURN FarmaCursor;

END PTOVENTA_RCM;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_RCM" IS

    --===============================
    FUNCTION GET_FORMA_FARMAC
    RETURN FarmaCursor
    IS
        curListado FarmaCursor;
    BEGIN
        OPEN curListado FOR
            SELECT    (COD_FORMA_FARMAC ||'Ã'||
                      DESC_FORMA_FARMAC ||'Ã'||
                      TRIM(TO_CHAR(VAL_PREC,'0.000000'))) AS  RESULTADO
            FROM      RCM_FORMA_FARMAC
            WHERE     EST_FORMA = CONS_ACTIVO;
        RETURN curListado;
    END;
    --===============================
    FUNCTION LISTA_INSUMOS
    RETURN FarmaCursor
    IS
        curListado FarmaCursor;
    BEGIN
        OPEN curListado FOR
            SELECT    (RCM_IN.COD_INSUMO || 'Ã' ||
                      RCM_IN.DESC_INSUMO || 'Ã' ||
                      RCD_UM.DESC_UNIDAD_MEDIDA || 'Ã' ||
                      RTRIM(LTRIM(TO_CHAR(RCM_IN.VAL_PREC_VIG,'9,999,990.000000'))) || 'Ã' ||
                      ' ' || 'Ã' ||
                      ' ' || 'Ã' ||
                      ' ' || 'Ã' ||
                      ' ' || 'Ã' ||
                      ' ' ) AS  RESULTADO
            FROM      RCM_INSUMO RCM_IN JOIN RCM_UNIDAD_MEDIDA RCD_UM ON (RCM_IN.COD_UNIDAD_MEDIDA = RCD_UM.COD_UNIDAD_MEDIDA)
            WHERE     RCM_IN.EST_INSUMO = 'A';
        RETURN curListado;
    END;

    --===============================
    FUNCTION GET_INF_INSUMO (cCod_Insumo_in            IN CHAR)
    RETURN FarmaCursor
    IS
        curListado FarmaCursor;
    BEGIN
        OPEN curListado FOR
            SELECT    (RCM_IN.DESC_INSUMO || 'Ã' ||
                      RCD_UM.DESC_UNIDAD_MEDIDA || 'Ã' ||
                      TO_CHAR(RCM_IN.VAL_PREC_VIG,'9,999,990.000000') || 'Ã' ||
                      RCD_UM.COD_UNIDAD_MEDIDA ) AS  RESULTADO
            FROM      RCM_INSUMO RCM_IN JOIN RCM_UNIDAD_MEDIDA RCD_UM ON (RCM_IN.COD_UNIDAD_MEDIDA = RCD_UM.COD_UNIDAD_MEDIDA)
            WHERE     RCM_IN.EST_INSUMO = 'A'
            AND       RCM_IN.COD_INSUMO = cCod_Insumo_in;
        RETURN curListado;
    END;


    --==============================================================================
    FUNCTION RCD_CORRE_NUM_RECETARIO(cCodGrupoCia_in          IN CHAR,
                                     cCod_Cia_in              IN CHAR,
                                     cCod_Local_in            IN CHAR,
                                     cUsuCrea_in IN VARCHAR2)
      RETURN CHAR
      IS
        vNum_Rece RCM_PEDIDO_CAB.NUM_RECETARIO%TYPE;
        v_cCodNumera PBL_NUMERA.COD_NUMERA%TYPE := '076';
      BEGIN

      vNum_Rece :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCod_Local_in,v_cCodNumera),10,'0','I' );
      Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCod_Local_in, v_cCodNumera, cUsuCrea_in);

    RETURN vNum_Rece;
    END;

  --=================================================================================

/****************************************************************************/


    --=============================================================
    FUNCTION RCM_GRAB_CAB(cCodGrupoCia_in          IN CHAR,
                          cCod_Cia_in              IN CHAR,
                          cCod_Local_in            IN CHAR,
                          cInd_Esteril_in          IN CHAR,
                          cCod_Forma_Farmac_in     IN CHAR,
                          nCant_Contenido_in       IN NUMBER,
                          nCant_Preparados_in      IN NUMBER,
                          cFec_Recetario_in        IN CHAR,
                          cCod_Paciente_in         IN VARCHAR2,
                          cCod_Medico_in           IN CHAR,
                          cFec_Entrega_in          IN CHAR,
                          cHora_Entrega_in         IN VARCHAR2,
                          cCod_Local_Entrega_in    IN CHAR,
                          cObs_Recetario_in        IN VARCHAR2,
                          nVal_Bruto_Recetario_in  IN NUMBER,
                          cEst_Pedido_Cab_in       IN CHAR,
                          cUsu_Crea_in             IN VARCHAR2,
                          cFec_Crea_in             IN CHAR,
                          cNum_Telefono_in         IN CHAR,
                          cNum_Guia_in             IN CHAR,
                          nValIgvRecetario_in      IN NUMBER,
                          nValVentaRecetario_in    IN NUMBER,
                          vValVentaRecSinRed_in    IN NUMBER)
        RETURN CHAR
        IS
          vNum_Rece CHAR(10):=PTOVENTA_RCM.RCD_CORRE_NUM_RECETARIO(cCodGrupoCia_in,cCod_Cia_in,cCod_Local_in,cUsu_Crea_in);
          num_guia_temp char(10);
        BEGIN

        SELECT RTRIM(LTRIM(cNum_Guia_in)) INTO num_guia_temp FROM DUAL;

        IF (num_guia_temp = '') THEN
             num_guia_temp := null;
        END IF;
        INSERT INTO RCM_PEDIDO_CAB (COD_GRUPO_CIA,
                                    COD_CIA,
                                    COD_LOCAL,
                                    NUM_RECETARIO,
                                    IND_ESTERIL,
                                    COD_FORMA_FARMAC,
                                    CANT_CONTENIDO,
                                    CANT_PREPARADOS,
                                    FEC_RECETARIO,
                                    COD_PACIENTE,
                                    COD_MEDICO,
                                    FEC_ENTREGA,
                                    HORA_ENTREGA,
                                    COD_LOCAL_ENTREGA,
                                    OBS_RECETARIO,
                                    VAL_BRUTO_RECETARIO,
                                    EST_PEDIDO_CAB,
                                    USU_CREA,
                                    FEC_CREA,
                                    NUM_TELEFONO,
                                    COD_ORDEN_PREPARADO,
                                    VAL_IGV_RECETARIO,
                                    VAL_VENTA_RECETARIO,
                                    VAL_VENTA_SIN_RED)
        VALUES (cCodGrupoCia_in,
                cCod_Cia_in,
                cCod_Local_in,
                vNum_Rece,
                cInd_Esteril_in,
                cCod_Forma_Farmac_in,
                nCant_Contenido_in,
                nCant_Preparados_in,
                to_date(cFec_Recetario_in,'dd/MM/yyyy'),
                cCod_Paciente_in,
                cCod_Medico_in,
                to_date(cFec_Entrega_in,'dd/MM/yyyy'),
                cHora_Entrega_in,
                cCod_Local_Entrega_in,
                cObs_Recetario_in,
                nVal_Bruto_Recetario_in,
                cEst_Pedido_Cab_in,
                cUsu_Crea_in,
                to_date(cFec_Crea_in,'dd/MM/yyyy'),
                cNum_Telefono_in,
                num_guia_temp,
                nValIgvRecetario_in,
                nValVentaRecetario_in,
                vValVentaRecSinRed_in);

       RETURN vNum_Rece;
    END;

    --=============================================================
    PROCEDURE RCM_GRAB_DET(cCodGrupoCia_in          IN CHAR,
                           cCod_Cia_in              IN CHAR,
                           cCod_Local_in            IN CHAR,
                           cNum_Recetario_in        IN CHAR,
                           nCod_Insumo_in           IN CHAR,
                           nCant_Atendida_in        IN NUMBER,
                           cVal_Prec_Rec_in         IN NUMBER,
                           cVal_Prec_Total_in       IN NUMBER,
                           cUsu_Crea_in             IN VARCHAR2,
                           cFec_Crea_in             IN CHAR,
                           cCodUnidadVenta_in       IN CHAR,
                           cCant_Porcentaje_in      IN NUMBER)
    IS
      vSecDetalle NUMBER(4,0);
      vFacConv    NUMBER;
    BEGIN

      SELECT    (NVL(MAX(SEC_DETALLE),0)+1)
      INTO      vSecDetalle
      FROM      RCM_PEDIDO_DET
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_CIA = cCod_Cia_in
        AND COD_LOCAL = cCod_Local_in
        AND NUM_RECETARIO = cNum_Recetario_in;

      SELECT    TO_NUMBER(RCU.FAC_CONVERSION)
      into      vFacConv
      FROM      RCM_INSUMO RCM_IN
      JOIN      rcm_conversion_unidades RCU ON (RCM_IN.COD_UNIDAD_MEDIDA = RCU.COD_UNIDAD_ORIG AND
                                                RCU.COD_UNIDAD_CONV=cCodUnidadVenta_in)
      WHERE     RCM_IN.EST_INSUMO = 'A'
      AND       RCM_IN.COD_INSUMO = nCod_Insumo_in;

      INSERT INTO RCM_PEDIDO_DET(COD_GRUPO_CIA,
                                 COD_CIA,
                                 COD_LOCAL,
                                 NUM_RECETARIO,
                                 SEC_DETALLE,
                                 COD_INSUMO,
                                 CANT_ATENDIDA,
                                 VAL_PREC_REC,
                                 VAL_PREC_TOTAL,
                                 USU_CREA,
                                 FEC_CREA,
                                 COD_UNIDAD_VENTA,
                                 CANT_PORC)
      VALUES(cCodGrupoCia_in,
             cCod_Cia_in,
             cCod_Local_in,
             cNum_Recetario_in,
             vSecDetalle,
             nCod_Insumo_in,
             nCant_Atendida_in * vFacConv,
             cVal_Prec_Rec_in,
             cVal_Prec_Total_in,
             cUsu_Crea_in,
             to_date(cFec_Crea_in,'dd/MM/yyyy'),
             cCodUnidadVenta_in,
             cCant_Porcentaje_in);

    END;

    --=============================================================

    PROCEDURE RCM_ASIGNA_PEDIDO_RCM(cCodGrupoCia_in          IN CHAR,
                                    cCod_Cia_in              IN CHAR,
                                    cCod_Local_in            IN CHAR,
                                    cNum_Recetario           IN CHAR,
                                    cNum_Pedido              IN CHAR)
    IS
    BEGIN
      UPDATE RCM_PEDIDO_CAB
      SET NUM_PEDIDO = cNum_Pedido
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND
            COD_CIA = cCod_Cia_in AND
            COD_LOCAL = cCod_Local_in AND
            NUM_RECETARIO = cNum_Recetario;
    END;

    --=============================================================

    FUNCTION RCM_LISTAR_GUIAS_RCM
    RETURN FarmaCursor
    IS
        curListado FarmaCursor;
    BEGIN
         OPEN curListado FOR
         select (DECODE(TNUMEGUIATRAN,NULL,' ',TNUMEGUIATRAN) || 'Ã' ||
                 TFECHORPR || 'Ã' ||
                 TOBSEORPR || 'Ã' ||
                 TNOMBCLIE || 'Ã' ||
                 TCANTPROD || 'Ã' ||
                 DECODE(TNUMEORPR,NULL,' ',TNUMEORPR) ) AS  RESULTADO
         from rcm_orden_preparado;
         RETURN curListado;
    END;

    --=============================================================

    FUNCTION RCM_GUIA_GRAB_CAB(cCodGrupoCia_in              IN CHAR,
                                cCod_Cia_in                 IN CHAR,
                                cCod_Local_in               IN CHAR,
                                cNum_Ord_Prep_in            IN CHAR,
                                cUsu_Crea_in                IN CHAR)
    RETURN CHAR
    IS
         IND_ESTERIL CHAR(1);
         FORM_FARM CHAR(3);
         CANT_CONT number(9);
         CANT_PREP number(9);
         COD_PACIENTE varchar2(20);
         COD_MEDICO CHAR(10);
         FEC_ENTREGA DATE;
         HORA_ENTREGA varchar2(10);
         OBS_RECETARIO varchar2(100);
         VAL_BRUTO_RECET varchar2(20);
         EST_PEDIDO_CAB CHAR(1);
         NUM_TELEFONO CHAR(10);
         NUM_RECETARIO varchar2(10);
         VAL_IGV_RECET varchar2(20);
         VAL_VENTA_RECET varchar2(20);
    BEGIN
      select 'N',                                --cInd_Esteril_in          IN CHAR,
              ROP.TCONTENVA,                     --nCant_Contenido_in       IN NUMBER,
              ROP.TCANTENVA,                     --nCant_Preparados_in      IN NUMBER,
              ROP.TFECHPREP,                     --cFec_Entrega_in          IN CHAR,
              null,                              --cHora_Entrega_in         IN VARCHAR2,
              ROP.TOBSEORPR,                     --cObs_Recetario_in        IN VARCHAR2,
              ROP.TIMPOAFEC,                     --nVal_Bruto_Recetario_in  IN NUMBER,
              'G',                               --cEst_Pedido_Cab_in       IN CHAR,
              ROP.TNUMETEL,                      --cNum_Telefono_in         IN CHAR,
              ROP.TIMPOIMPU,                     --nValIgvRecetario_in      IN NUMBER,
              ROP.TIMPOTOTA,                     --nValVentaRecetario_in    IN NUMBER)
              LPAD(FF.COD_EQUI,3,'0')            --cod equivalencia forma farm
      into IND_ESTERIL,
           CANT_CONT,
           CANT_PREP,
           FEC_ENTREGA,
           HORA_ENTREGA,
           OBS_RECETARIO,
           VAL_BRUTO_RECET,
           EST_PEDIDO_CAB,
           NUM_TELEFONO,
           VAL_IGV_RECET,
           VAL_VENTA_RECET,
           FORM_FARM
      from RCM_ORDEN_PREPARADO ROP
      left join RCM_FORMA_FARMAC FF on LPAD(ROP.TFORMFARM,3,'0') = FF.COD_FORMA_FARMAC
      where ROP.TNUMEORPR = cNum_Ord_Prep_in;

      COD_PACIENTE := NULL;
      COD_MEDICO := NULL;

      NUM_RECETARIO := RCM_GRAB_CAB(cCodGrupoCia_in,
                                    cCod_Cia_in,
                                    cCod_Local_in,
                                    IND_ESTERIL,
                                    FORM_FARM,
                                    CANT_CONT,
                                    CANT_PREP,
                                    SYSDATE,
                                    COD_PACIENTE,
                                    COD_MEDICO,
                                    FEC_ENTREGA,
                                    HORA_ENTREGA,
                                    cCod_Local_in,
                                    OBS_RECETARIO,
                                    VAL_BRUTO_RECET ,
                                    EST_PEDIDO_CAB,
                                    cUsu_Crea_in,
                                    SYSDATE,
                                    NUM_TELEFONO,
                                    cNum_Ord_Prep_in,
                                    VAL_IGV_RECET,
                                    VAL_VENTA_RECET,
                                    VAL_VENTA_RECET);

      IF (NUM_RECETARIO is not null or
          NUM_RECETARIO != 'ERROR') THEN

          return NUM_RECETARIO || 'Ã' || TRIM(TO_CHAR(VAL_VENTA_RECET,'999999990.000000'));
      ELSE
          return NUM_RECETARIO;
      END IF;

    EXCEPTION
      when others then
        DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
        return null;
    END;
    ----
    FUNCTION GET_PORC_IGV(cCodIgv_in IN CHAR) RETURN NUMBER
    IS
        v_nPorcIgv PBL_IGV.PORC_IGV%TYPE;
    BEGIN

        SELECT PORC_IGV INTO v_nPorcIgv
        FROM PBL_IGV
        WHERE COD_IGV = cCodIgv_in;

        RETURN v_nPorcIgv;
    END;

    --=========================================================================

    FUNCTION LISTA_PROD_VIRTUALES
    RETURN FarmaCursor
    IS
        curListado FarmaCursor;
    BEGIN
        OPEN curListado FOR
            select COD_PROD AS  RESULTADO
            from lgt_prod_virtual
            where tip_prod_virtual = 'M'
            and EST_PROD_VIRTUAL = 'A';
        RETURN curListado;
    END;

    --=========================================================================

    FUNCTION LISTA_UNID_MEDIDA_REL(cCodUnidMed_in IN CHAR)
    RETURN FarmaCursor
    IS
        curListado FarmaCursor;
    BEGIN
        OPEN curListado FOR
        select (RUM.COD_UNIDAD_MEDIDA || 'Ã' ||
                RUM.DESC_UNIDAD_MEDIDA ) AS RESULTADO
        from ptoventa.rcm_unidad_medida rum
        inner join ptoventa.rcm_rel_unid rru on rru.cod_unid_medida = rum.cod_unidad_medida
        inner join (select cod_unidad_conv as cod_unidad_conv
                   from rcm_conversion_unidades
                   where cod_unidad_orig = cCodUnidMed_in) T1 ON T1.COD_UNIDAD_CONV = RUM.COD_UNIDAD_MEDIDA
        where rru.cod_rel in (select cod_rel
                              from ptoventa.rcm_rel_unid
                              where COD_UNID_MEDIDA = cCodUnidMed_in);
        return curListado;
    END;

    --=========================================================================

    FUNCTION GET_EST_RECETARIO_PEDIDO(cCodGrupoCia_in     IN CHAR,
                                      cCod_Cia_in         IN CHAR,
                                      cCod_Local_in       IN CHAR,
                                      cNum_Pedido_in      IN CHAR)
    RETURN VARCHAR2
    IS
        respuesta VARCHAR2(1);
    BEGIN
        select est_pedido_cab
               INTO respuesta
        from rcm_pedido_cab
        where COD_GRUPO_CIA=cCodGrupoCia_in
        AND COD_CIA=cCod_Cia_in
        AND COD_LOCAL=cCod_Local_in
        AND NUM_PEDIDO=cNum_Pedido_in;

        RETURN respuesta;
    EXCEPTION
      WHEN OTHERS THEN
        RETURN '';
    END;

    --=========================================================================

    PROCEDURE ANULA_RECETARIO_PEDIDO(cCodGrupoCia_in     IN CHAR,
                                      cCod_Cia_in         IN CHAR,
                                      cCod_Local_in       IN CHAR,
                                      cNum_Pedido_in      IN CHAR)
    IS
    BEGIN
        update rcm_pedido_cab
        set est_pedido_cab = 'N'
        where COD_GRUPO_CIA=cCodGrupoCia_in
        AND COD_CIA=cCod_Cia_in
        AND COD_LOCAL=cCod_Local_in
        AND NUM_PEDIDO=cNum_Pedido_in;
    END;

    --=========================================================================

    PROCEDURE ACTUALIZA_RECET_COBRADO(cCodGrupoCia_in     IN CHAR,
                                      cCod_Cia_in         IN CHAR,
                                      cCod_Local_in       IN CHAR,
                                      cNum_Recetario      IN CHAR)
    IS
    BEGIN
        update rcm_pedido_cab
        set est_pedido_cab = 'C'
        where COD_GRUPO_CIA=cCodGrupoCia_in
        AND COD_CIA=cCod_Cia_in
        AND COD_LOCAL=cCod_Local_in
        AND NUM_RECETARIO=cNum_Recetario;
    END;

        --=========================================================================

    FUNCTION GET_FACTOR_CONVERSION(cCod_Producto       IN CHAR,
                                   cCod_UnidadVenta    IN CHAR)
    RETURN VARCHAR2
    IS
        respuesta varchar2(15);
    BEGIN
      select TRIM(TO_CHAR(CU.FAC_CONVERSION,'9999999990.000000'))
      INTO respuesta
      from rcm_conversion_unidades CU
      inner join rcm_insumo RI ON RI.COD_UNIDAD_MEDIDA = CU.COD_UNIDAD_CONV
      where RI.COD_INSUMO = cCod_Producto
      AND CU.COD_UNIDAD_ORIG = cCod_UnidadVenta;

      return respuesta;
    EXCEPTION
     when OTHERS then
     return null;
    END;

    FUNCTION GET_NUMERO_RECETARIO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumPedido_in IN CHAR) RETURN VARCHAR2
    AS
        v_vNumRecetario RCM_PEDIDO_CAB.NUM_RECETARIO%TYPE;
        v_cEstado RCM_PEDIDO_CAB.EST_PEDIDO_CAB%TYPE;
    BEGIN
        BEGIN
            SELECT NUM_RECETARIO,EST_PEDIDO_CAB
                INTO v_vNumRecetario, v_cEstado
            FROM RCM_PEDIDO_CAB
            WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                AND COD_CIA = cCodCia_in
                AND COD_LOCAL = cCodLocal_in
                AND NUM_PEDIDO = cNumPedido_in
                ;
            --IF v_cEstado = 'G' THEN
               --RAISE_APPLICATION_ERROR(-20151,'El');
            --END;
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
            v_vNumRecetario := ' ';
            v_cEstado := ' ';
        END;
        RETURN v_vNumRecetario||'Ã'||v_cEstado;
    END;

    FUNCTION GET_TRAMA_RECETARIO(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cNumRecetario_in IN CHAR) RETURN VARCHAR2 AS

        v_vCantDetalle number;
        curListadoDetalle FarmaCursor;
        cTemp CLOB;
        cSep char;

        v_vTramaCabecera VARCHAR2(2000);
        v_vTramaCantDetalle VARCHAR2(10);
        v_vTramaDetalle VARCHAR2(2000);

        v_vTramaRecetario CLOB;
    BEGIN
        --cSep := chr(13);
        cSep := null;
        DECLARE
        BEGIN
            --trama cabecera
            SELECT '001006'||LPAD(SUBSTR(CAB.NUM_RECETARIO,5),6,'0')||cSep||                     --contraseña - Longitud: 6
                   '002003'||'001'||cSep||                                                       --numero preparado - Longitud: 3 (num_orden_preparado)
                   '003019'||TO_CHAR(CAB.FEC_RECETARIO,'DD/MM/YYYY HH24:MI:SS')||cSep||          --fecha del prepado DD/MM/YY HH:MM:SS - Longitud: 019
                   '004004'||LPAD(LOC.COD_LOCAL_MIGRA,4,0)||cSep||                               --codigo de local - Longitud: 4
                   '005001'||'N'||cSep||                                                         --Indicador N - Longitud: 1 (indicador de Urgencia S N)
                   '006012'||TRIM(TO_CHAR(CAB.VAL_BRUTO_RECETARIO,'0000000.0000'))||cSep||       --total afecto - Longitud: 12
                   '007012'||TRIM(TO_CHAR(CAB.VAL_IGV_RECETARIO,'0000000.0000'))||cSep||         --impuesto - Longitud: 12
                   '008012'||TRIM(TO_CHAR(CAB.VAL_VENTA_SIN_RED,'0000000.0000'))||cSep||       --Total - Longitud: 12
                   '009008'||LPAD(UL.COD_TRAB,8,'0')||cSep||                                     --vendedor - Longitud: 8 (codigo de preparador)
                   '010000'||cSep||                                                              --nro de documento - Longitud: 10 (num_doc_venta?) NO VA
                   '011000'||cSep||                                                              --FECHA DD//MM/YYYY - Longitud: 10 (fecha_venta) NO VA
                   '012000'||cSep||                                                              --caja - Longitud: 2 (caja_venta?) NO VA
                   '013000'||cSep||                                                              --guia - Longitud: 10 (guia de venta por convenio -o numdocventa o guia) NO VA
                   '014000'||cSep||                                                              --tipo de documento - Longitud: 3 (codigo tipo_doc_venta boleta/factura) NO VA
                   '015'||LPAD(LENGTH(TRIM(CLI.NOM_CLI||' '||CLI.APE_PAT_CLI||' '||CLI.APE_MAT_CLI)),3,'0')||''||TRIM(CLI.NOM_CLI||' '||CLI.APE_PAT_CLI||' '||CLI.APE_MAT_CLI)||cSep||   --nombre paciente
                   '016002'||LPAD(FFA.COD_EQUI,2,'0')||cSep||                                    --producto base - Longitud: 2
                   '017010'||LPAD(CAB.CANT_PREPARADOS,10,'0')||cSep||                            --cantidad de envase - Longitud: 10
                   '018010'||LPAD(CAB.CANT_CONTENIDO,10,'0')||cSep||                             --cantidad de contenido - Longitud: 2
                   '019'||LPAD(DECODE(CAB.OBS_RECETARIO,NULL,0,LENGTH(TRIM(CAB.OBS_RECETARIO))),3,'0')||TRIM(CAB.OBS_RECETARIO)||cSep||   --observacion
                   '020001'||LPAD(FFA.IND_MULT,1,'0')||cSep||                                    --indicador forma farm(Indicador esteril?) - Longitud: 1 (valores: 0 ó 1)
                   '021014'||TRIM(TO_CHAR(FFA.VAL_PREC,'000000000.0000'))||cSep||                --precio forma farm  - Longitud: 14
                   '022002'||LPAD(SUBSTR(UNM.ABREV_UNIDAD_MEDIDA,1,2),2)||cSep||                 --unmedida de forma farmace - Longitud: 2
                   '023004'||LPAD(LOC.COD_LOCAL_MIGRA,4,'0')                                     --LOCAL MIGRACION - Longitud: 4 (mismo cod local preparacion)
                   INTO v_vTramaCabecera
            FROM RCM_PEDIDO_CAB CAB
            LEFT JOIN PBL_LOCAL LOC ON (CAB.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA AND
                                  CAB.COD_LOCAL = LOC.COD_LOCAL)
            LEFT JOIN PBL_CLIENTE CLI ON (CAB.COD_PACIENTE = CLI.DNI_CLI)
            LEFT JOIN RCM_FORMA_FARMAC FFA ON (CAB.COD_FORMA_FARMAC = FFA.COD_FORMA_FARMAC)
            LEFT JOIN RCM_UNIDAD_MEDIDA UNM ON (FFA.COD_UNIDAD_MEDIDA = UNM.COD_UNIDAD_MEDIDA)
            LEFT JOIN PBL_USU_LOCAL UL ON UL.LOGIN_USU = CAB.USU_CREA
            WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                AND CAB.COD_CIA = cCodCia_in
                AND CAB.COD_LOCAL = cCodLocal_in
                AND CAB.NUM_RECETARIO = cNumRecetario_in;

            DBMS_OUTPUT.put_line('SELECT CABECERA CORRECTO');

            --Trama Cantidad Detalle
            SELECT COUNT(DET.SEC_DETALLE)
                INTO v_vCantDetalle
            FROM RCM_PEDIDO_CAB CAB
            LEFT JOIN RCM_PEDIDO_DET DET ON (CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                                            AND CAB.COD_CIA = DET.COD_CIA
                                            AND CAB.COD_LOCAL = DET.COD_LOCAL
                                            AND CAB.NUM_RECETARIO = DET.NUM_RECETARIO)
            WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND CAB.COD_CIA = cCodCia_in
                  AND CAB.COD_LOCAL = cCodLocal_in
                  AND CAB.NUM_RECETARIO = cNumRecetario_in;

            IF (v_vCantDetalle > 0) THEN
                  v_vTramaCantDetalle := '02X002'||LPAD(v_vCantDetalle,2,'0');

                  --Trama Detalle
                  OPEN curListadoDetalle FOR
                  SELECT '024006'||LPAD(INS.COD_EQUI,6,' ')||cSep||                                         --codigo peru - Longitud: 6
                         '025002'||(SELECT SUBSTR(UNM.ABREV_UNIDAD_MEDIDA,1,2)                             --unida media prepara segun peruu - Longitud: 2
                                    FROM RCM_UNIDAD_MEDIDA UNM
                                    WHERE UNM.COD_UNIDAD_MEDIDA = INS.COD_UNIDAD_MEDIDA)||cSep||
                         '026002'||(SELECT SUBSTR(UNM.ABREV_UNIDAD_MEDIDA,1,2)                              --unidm medida origen segun peruu - Longitud: 2
                                    FROM RCM_UNIDAD_MEDIDA UNM
                                    WHERE UNM.COD_UNIDAD_MEDIDA = DET.COD_UNIDAD_VENTA)||cSep||
                         '027011'||LPAD(DECODE(TRIM(DET.CANT_ATENDIDA),null,0,DET.CANT_ATENDIDA),11,'0')||cSep||  --Cantidad solicitada- Longitud: 11
                         '028009'||TRIM(TO_CHAR(CON.FAC_CONVERSION,'0000.0000'))||cSep||                          --(valor de conversion) - Longitud: 9
                         '029016'||TRIM(TO_CHAR(DET.VAL_PREC_REC,'000000000.000000'))||cSep||                      --precio unitario - Longitud: 14
                         '030016'||TRIM(TO_CHAR(DET.VAL_PREC_TOTAL,'000000000.000000'))||cSep||                     --preciot total - Longitud: 14
                         '031006'||LPAD(DECODE(TRIM(DET.CANT_PORC),null,0,DET.CANT_PORC),6,'0')            --porcentaje usado - Longitud: 6
                         INTO v_vTramaDetalle
                  FROM RCM_PEDIDO_CAB CAB
                  LEFT JOIN RCM_PEDIDO_DET DET ON (CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
                                              AND CAB.COD_CIA = DET.COD_CIA
                                              AND CAB.COD_LOCAL = DET.COD_LOCAL
                                              AND CAB.NUM_RECETARIO = DET.NUM_RECETARIO)
                  LEFT JOIN RCM_INSUMO INS ON (DET.COD_INSUMO = INS.COD_INSUMO)
                  LEFT JOIN RCM_CONVERSION_UNIDADES CON ON CON.COD_UNIDAD_ORIG = INS.COD_UNIDAD_MEDIDA AND
                                                      CON.COD_UNIDAD_CONV = DET.COD_UNIDAD_VENTA
                  WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                        AND CAB.COD_CIA = cCodCia_in
                        AND CAB.COD_LOCAL = cCodLocal_in
                        AND CAB.NUM_RECETARIO = cNumRecetario_in;

                  --se une la información de los detalles del recetario
                  LOOP
                      FETCH curListadoDetalle INTO cTemp;
                      EXIT WHEN curListadoDetalle%NOTFOUND;
                      v_vTramaDetalle := v_vTramaDetalle || cTemp || cSep;
                  END LOOP;
                  CLOSE curListadoDetalle;

                  v_vTramaRecetario := v_vTramaCabecera || cSep || v_vTramaCantDetalle || cSep || v_vTramaDetalle ;
            ELSE
                v_vTramaRecetario := 'ERROR';
            END IF;
            DBMS_OUTPUT.put_line('SELECT DETALLE CORRECTO');
        EXCEPTION
        WHEN OTHERS THEN
             DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
             v_vTramaRecetario := 'ERROR - EXCEPTION';
        END;
        RETURN v_vTramaRecetario;
    END;

    PROCEDURE ACTUALIZA_ESTADO_RECETARIO(cCodGrupoCia_in IN CHAR,
                                         cCodCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cNumRecetario_in IN CHAR,
                                         cEstado_in IN CHAR,
                                         vUsuario_in IN VARCHAR2)
    AS
    BEGIN
        UPDATE RCM_PEDIDO_CAB CAB
        SET EST_PEDIDO_CAB = cEstado_in,
            USU_MOD = vUsuario_in,
            FEC_MOD = SYSDATE
        WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                AND CAB.COD_CIA = cCodCia_in
                AND CAB.COD_LOCAL = cCodLocal_in
                AND CAB.NUM_RECETARIO = cNumRecetario_in;
    END;



     FUNCTION IMP_HTML_CONTRASENIA(cCodGrupoCia_in         IN CHAR,
                          cCod_Cia_in              IN CHAR,
                          cCod_Local_in            IN CHAR,
                          cNum_Recetario_in        IN CHAR)
  RETURN VARCHAR2
  IS
 C_INICIO_MSG  VARCHAR2(2000) := '<html>
                                  <head><style type="text/css"> body { font-family:Arial, Helvetica, sans-serif;} </style></head>
                                  <body><table width="335" border="0"><tr><td><table border="0">';

 C_FILA_VACIA  VARCHAR2(2000) :='<tr><td height="13" colspan="3"></td></tr> ';

 C_FIN_MSG     VARCHAR2(2000) := '</table></td></tr></table></body></html>';


  vMsg_out varchar2(32767):= '';

  vMensajeLocal varchar2(1000):= '';
  vMensajeRecetario varchar2(1000):= '';
  vObservaciones varchar2(100):= '';

  v_vVendedor VARCHAR2(100);
  v_cMovCaja VTA_PEDIDO_VTA_CAB.SEC_MOV_CAJA%TYPE;

   --INI ASOSA - 03/09/2014
     cCodMarca char(3) := '';
      vTextoMarca varchar2(50) := '';
      --FIN ASOSA - 03/09/2014
  BEGIN

         --INI ASOSA - 03/09/2014
         SELECT PL.COD_MARCA
         INTO cCodMarca
         FROM PBL_LOCAL PL
         WHERE PL.COD_GRUPO_CIA =    cCodGrupoCia_in
         AND PL.COD_CIA = cCod_Cia_in
         AND PL.COD_LOCAL = cCod_Local_in;

          IF cCodMarca <> COD_CIA_MARKET_01 THEN
                     vTextoMarca := 'BOTICAS ';
          END IF;
          --FIN ASOSA - 03/09/2014

        IF(cNum_Recetario_in IS NULL) THEN
               RETURN NULL;
        END IF;
        SELECT
        --'BOTICAS '||MG.NOM_MARCA ||
        vTextoMarca ||MG.NOM_MARCA || --ASOSA - 03/09/2014
        '<br>'|| PC.RAZ_SOC_CIA || ' RUC: '||PC.NUM_RUC_CIA ||
        '<br>'|| PC.DIR_CIA ||
        '<br>'|| 'LOCAL: ' ||PL.COD_LOCAL ||--' N° CAJA:01' ||
        '<br>'||PL.DIREC_LOCAL
        INTO vMensajeLocal
        FROM PBL_LOCAL PL
            JOIN PBL_MARCA_CIA MC ON (PL.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                        PL.COD_MARCA = MC.COD_MARCA AND
                                        PL.COD_CIA = MC.COD_CIA)
            JOIN PBL_MARCA_GRUPO_CIA MG ON (MG.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                            MG.COD_MARCA = MC.COD_MARCA)
            JOIN PBL_CIA PC ON (PC.COD_CIA = PL.COD_CIA)
        WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
            AND PL.COD_LOCAL = cCod_Local_in;

            --Nombre del Vendedor. Se obtiene de la cabecera por ser informativo.
            SELECT USU.NOM_USU ||' '|| USU.APE_PAT ,
                    CAB.SEC_MOV_CAJA
                    INTO v_vVendedor,v_cMovCaja
            FROM VTA_PEDIDO_VTA_CAB CAB JOIN PBL_USU_LOCAL USU ON (CAB.COD_GRUPO_CIA = USU.COD_GRUPO_CIA
                                                                    --AND CAB.COD_CIA = USU.COD_CIA
                                                                    AND CAB.COD_LOCAL = USU.COD_LOCAL
                                                                    AND CAB.USU_CREA_PED_VTA_CAB = USU.LOGIN_USU)
            WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
            AND CAB.COD_CIA = cCod_Cia_in
            AND CAB.COD_LOCAL = cCod_Local_in
            AND CAB.NUM_PED_VTA = (SELECT NUM_PEDIDO FROM RCM_PEDIDO_CAB WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND COD_CIA = cCod_Cia_in
                                            AND COD_LOCAL = cCod_Local_in
                                            AND NUM_RECETARIO = cNum_Recetario_in);

        SELECT
        '<br>'||'FECHA :' ||TO_CHAR(RC.FEC_RECETARIO,'DD/MM/YYYY') ||' HORA :' || TO_CHAR(SYSDATE,'HH24:MI:SS')||
        '<br>'||'VENDEDOR :'|| v_vVendedor ||
        '<br>'||'MOV.CAJA: '|| v_cMovCaja ||
        '<br>'||'Total : ' || TO_CHAR(RC.VAL_VENTA_RECETARIO,'999,990.0000') ||
        --'<br>'|| '- Dscto :  5.70'||
        --'<br>'|| 'Saldo :  51.31'||
        '<br><span style="font-weight:bold;">'|| 'PACIENTE: '|| (SELECT NOM_CLI||' '||APE_PAT_CLI||' '||APE_MAT_CLI FROM PBL_CLIENTE WHERE DNI_CLI = RC.COD_PACIENTE)||'</span>'||
        '<br>'|| 'TELEFONO: ' || RC.NUM_TELEFONO ||
        '<br>'|| 'LOCAL: '||RC.COD_LOCAL_ENTREGA ||' CONTRASEÑA : ' || RC.NUM_RECETARIO ||
        '<br><span style="font-weight:bold;">'|| 'RETIRAR: ' || TO_CHAR(RC.FEC_ENTREGA,'DD/MM/YYYY') ||' ' || RC.HORA_ENTREGA||'</span>'
        ,RC.OBS_RECETARIO
        INTO vMensajeRecetario,vObservaciones
        FROM RCM_PEDIDO_CAB RC
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_CIA = cCod_Cia_in
        AND COD_LOCAL = cCod_Local_in
        AND NUM_RECETARIO = cNum_Recetario_in;

    vMsg_out := C_INICIO_MSG ||
                 '<tr><td height="21" colspan="3" align="center"
                          style="font-size:17px;font-weight:bold;">
                          RECETARIO MAGISTRAL
                  </td></tr>'||
                 '<tr><td height="21" colspan="3" align="center">'||
                  vMensajeLocal||
                  '</td></tr>'||
                  '<tr><td height="21" colspan="3" align="left">'||
                  vMensajeRecetario||
                  '</td></tr>'||
                 '<tr><td height="21" colspan="3">El valor calculado para la fabricacion de esta receta es referencial y  podra quedar sujeto a modificaciones.</td></tr>'||
                  '<tr><td height="21" colspan="3">Rp N°:________________________________</td></tr>'||
                  '<tr><td height="21" colspan="3">OBSERVACIONES:</td></tr>'||
                  '<tr><td height="21" colspan="3" style="font-weight:bold;">'||vObservaciones||'</td></tr>'||
                 C_FIN_MSG ;

                 return vMsg_out;

  END;

  FUNCTION GET_TRAMA_ACTUALIZA_RECETARIO(cCodGrupoCia_in IN CHAR,
                                         cCodCia_in IN CHAR,
                                         cCodLocal_in IN CHAR,
                                         cNumPedido_in IN CHAR,
                                         cAccion_in IN CHAR)
  RETURN VARCHAR2
  IS
       resultado varchar2(50);
       csep char;
  BEGIN
       --cSep := chr(13);
       cSep := null;

       if(cAccion_in is not null) then
            --si la accion es actualizar
            if(cAccion_in = 'A') then
                 select 'A' || cSep ||
                       LPAD(R.COD_LOCAL_ENTREGA,4,'0') || cSep ||
                       DECODE(V.TIP_COMP_PAGO,'05','T','F') || cSep ||
                       LPAD(R.COD_ORDEN_PREPARADO,10,'0') || cSep ||
                       LPAD(V.NUM_COMP_PAGO,10,'0') || cSep ||
                       TO_CHAR(V.FEC_MOD_COMP_PAGO,'DD/MM/YYYY') into resultado
                from RCM_PEDIDO_CAB R
                INNER JOIN VTA_COMP_PAGO V ON V.NUM_PED_VTA = R.NUM_PEDIDO
                where r.cod_grupo_cia = cCodGrupoCia_in and
                      r.cod_cia = cCodCia_in and
                      r.cod_local = cCodLocal_in and
                      R.COD_ORDEN_PREPARADO is not null and
                      R.NUM_PEDIDO = cNumPedido_in;
            --si la accion es anular
            elsif(cAccion_in = 'N') then
                select 'N' || cSep ||
                       LPAD(R.COD_LOCAL_ENTREGA,4,'0') || cSep ||
                       LPAD(V.NUM_COMP_PAGO,10,'0') into resultado
                from RCM_PEDIDO_CAB R
                INNER JOIN VTA_COMP_PAGO V ON V.NUM_PED_VTA = R.NUM_PEDIDO
                where r.cod_grupo_cia = cCodGrupoCia_in and
                      r.cod_cia = cCodCia_in and
                      r.cod_local = cCodLocal_in and
                      R.COD_ORDEN_PREPARADO is not null and
                      R.NUM_PEDIDO = cNumPedido_in;
            else
                resultado := 'ERROR';
            end if;
       else
            resultado := 'ERROR';
       end if;
       return resultado;
  EXCEPTION
  when others then
       DBMS_OUTPUT.put_line(SQLCODE || SQLERRM);
       return 'ERROR';
  END;
--===============================================================================================================
    PROCEDURE VTA_UPDATE_PEDIDO_CAB_RCM(cCodGrupoCia_in 	 	   IN CHAR,
                                          cCodCia_in 	 	     IN CHAR,
                                     	    cCodLocal_in    	 IN CHAR,
                  								   	    cNumPedidoAnul_in  IN VARCHAR2,
                                          cEstPedCab_in      IN CHAR ) IS

      BEGIN
      UPDATE RCM_PEDIDO_CAB RC
         SET   RC.EST_PEDIDO_CAB = cEstPedCab_in

         WHERE RC.COD_GRUPO_CIA = cCodGrupoCia_in AND
             RC.COD_CIA = cCodCia_in AND
             RC.COD_LOCAL = cCodLocal_in AND
             RC.NUM_PEDIDO=cNumPedidoAnul_in;


      END;



--===============================================================================================================

       FUNCTION VTA_GET_VALIDA_RCM(cCodGrupoCia_in IN CHAR,
                                            cCod_Local_in      IN CHAR,
                                            cNum_Comp_Pago_in  IN CHAR)
         RETURN varchar
           IS
         vEst Char(2);

         BEGIN
        SELECT RC.EST_PEDIDO_CAB
            INTO vEst
             FROM RCM_PEDIDO_CAB RC
             WHERE RC.COD_GRUPO_CIA = cCodGrupoCia_in
              AND RC.COD_LOCAL = cCod_Local_in
              AND RC.NUM_PEDIDO=(SELECT VP.NUM_PED_VTA
                              FROM VTA_COMP_PAGO VP
                              WHERE
                              VP.COD_GRUPO_CIA = cCodGrupoCia_in AND
                              VP.COD_LOCAL = cCod_Local_in AND
                              NUM_COMP_PAGO=cNum_Comp_Pago_in );


         return vEst;

       EXCEPTION
         WHEN NO_DATA_FOUND THEN
           RAISE_APPLICATION_ERROR(-20198,'NO EXISTE EL NUMERO DE PEDIDO');

      END;


--===============================================================================================================
 PROCEDURE VTA_RCM_VALIDA_RCM(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in IN CHAR,
                                        cNum_Comp_Pago_in IN CHAR)
 --RETURN BOOLEAN
 IS
 v_est_rcm BOOLEAN;
 v_num_pedido_vta character;
 v_num_pedido_rcm character;
 BEGIN
   v_est_rcm := FALSE;
   v_num_pedido_vta:='';
   v_num_pedido_rcm:='';

   SELECT VT.NUM_PED_VTA
   INTO v_num_pedido_vta
   FROM VTA_COMP_PAGO VT
   WHERE
   VT.COD_GRUPO_CIA = cCodGrupoCia_in  AND
   VT.COD_LOCAL = cCod_Local_in  AND
   VT.NUM_COMP_PAGO=cNum_Comp_Pago_in;

   SELECT RC.NUM_PEDIDO
      INTO v_num_pedido_rcm
      FROM RCM_PEDIDO_CAB RC
      WHERE RC.COD_GRUPO_CIA = cCodGrupoCia_in
       AND RC.COD_LOCAL = cCod_Local_in
       AND RC.NUM_PEDIDO=(SELECT NUM_PED_VTA FROM VTA_COMP_PAGO WHERE NUM_COMP_PAGO=cNum_Comp_Pago_in );


   IF (v_num_pedido_vta=v_num_pedido_rcm) then
     v_est_rcm := TRUE;
     DBMS_OUTPUT.PUT_LINE('true');
   else
     DBMS_OUTPUT.PUT_LINE('false');
   END IF;
  -- return v_est_rcm;

END ;
--===============================================================================================================

    --Descripcion: Reporte de Recetario Magistral
    --Fecha       Usuario	Comentario
    --27/09/2013  LLEIVA  Creacion
    FUNCTION REPORTE_RECETARIO_MAGISTRAL(cCodGrupoCia_in   IN VARCHAR2,
                                         cCodCia_in        IN VARCHAR2,
                                         cCodLocal_in      IN VARCHAR2,
                                         cFecInicial_in    IN VARCHAR2,
                                         cFecFinal_in      IN VARCHAR2,
                                         cPaciente_in      IN VARCHAR2,
                                         cNumRecetario_in  IN VARCHAR2,
                                         cNumPedido_in     IN VARCHAR2)
    RETURN FarmaCursor
    IS
         query VARCHAR2(2000);
         curListado FarmaCursor;
    BEGIN
         query := 'select DECODE(RCM.COD_ORDEN_PREPARADO, null, '' '', RCM.COD_ORDEN_PREPARADO) || ''Ã'' ||
                          DECODE(RCM.FEC_RECETARIO, null, '' '',RCM.FEC_RECETARIO)  || ''Ã'' ||
                          DECODE(RCM.NUM_RECETARIO, null, '' '',RCM.NUM_RECETARIO)  || ''Ã'' ||
                          DECODE(CLI.NOM_CLI || '' '' || CLI.APE_PAT_CLI || '' '' || CLI.APE_MAT_CLI, null, '' '',CLI.NOM_CLI || '' '' || CLI.APE_PAT_CLI || '' '' || CLI.APE_MAT_CLI)  || ''Ã'' ||
                          DECODE(RCM.Num_Pedido, null, '' '',RCM.Num_Pedido)  || ''Ã'' ||
                          DECODE(rcm.est_pedido_cab,''A'',''Pendiente'',
                                                     ''C'',''Cobrado'',
                                                     ''E'',''Enviado'',
                                                     ''N'',''Anulado'',
                                                     ''G'',''Guía'',
                                                     '' '') AS resultado
          from rcm_pedido_cab RCM
          inner join PBL_CLIENTE CLI on CLI.DNI_CLI = RCM.COD_PACIENTE
          where 1 = 1 ';
          IF (cFecInicial_in IS NOT NULL) THEN
             query := query || 'AND RCM.FEC_RECETARIO >= TO_DATE(''' || cFecInicial_in || ''',''dd/MM/yyyy)'') ';
          END IF;
          IF (cFecFinal_in IS NOT NULL) THEN
             query := query || 'AND RCM.FEC_RECETARIO <= TO_DATE(''' || cFecFinal_in || ''',''dd/MM/yyyy)'') ';
          END IF;
          IF (cNumRecetario_in IS NOT NULL) THEN
             query := query || 'AND RCM.NUM_RECETARIO LIKE ''%' || cNumRecetario_in || '%'' ';
          END IF;
          IF (cPaciente_in IS NOT NULL) THEN
             query := query || 'AND CLI.NOM_CLI||'' ''||CLI.APE_PAT_CLI||'' ''||CLI.APE_MAT_CLI LIKE ''%' || cPaciente_in || '%'' ';
          END IF;
          IF (cNumPedido_in IS NOT NULL) THEN
             query := query || ' AND RCM.NUM_PEDIDO LIKE ''%' || cNumPedido_in || '%'' ';
          END IF;
          query := query || ' ORDER BY RCM.FEC_RECETARIO';

          DBMS_OUTPUT.put_line (query);
          OPEN curListado FOR query;

          return curListado;

    END;

    --Descripcion: Detalle de Reporte de Recetario Magistral
    --Fecha       Usuario	Comentario
    --27/09/2013  LLEIVA  Creacion
    FUNCTION LISTADO_DETALLE_RCM(cCodGrupoCia_in   IN CHAR,
                                       cCodCia_in        IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cNumRecetario_in  IN CHAR)
    RETURN FarmaCursor
    IS
         curListado FarmaCursor;
    BEGIN
         OPEN curListado FOR
         select DECODE(I.DESC_INSUMO, null, ' ', I.DESC_INSUMO)|| 'Ã' ||
                DECODE(PD.CANT_ATENDIDA, null, ' ',PD.CANT_ATENDIDA) || 'Ã' ||
                DECODE(UM.DESC_UNIDAD_MEDIDA, null, ' ',UM.DESC_UNIDAD_MEDIDA) || 'Ã' ||
                DECODE(PD.VAL_PREC_REC, null, ' ',PD.VAL_PREC_REC) || 'Ã' ||
                DECODE(PD.VAL_PREC_TOTAL, null, ' ',PD.VAL_PREC_TOTAL) as resultado
          from rcm_pedido_det PD
          LEFT JOIN RCM_INSUMO I on PD.COD_INSUMO=I.COD_INSUMO
          LEFT JOIN rcm_unidad_medida UM on PD.COD_UNIDAD_VENTA=UM.COD_UNIDAD_MEDIDA
          WHERE PD.NUM_RECETARIO = cNumRecetario_in
                AND PD.COD_GRUPO_CIA = cCodGrupoCia_in
                AND PD.COD_CIA = cCodCia_in
                AND PD.COD_LOCAL = cCodLocal_in;
        RETURN curListado;
    END;

    --Descripcion: Detalle de Reporte de Recetario Magistral
    --Fecha       Usuario	Comentario
    --27/09/2013  LLEIVA  Creacion
    FUNCTION DETALLE_RCM(cCodGrupoCia_in   IN CHAR,
                         cCodCia_in        IN CHAR,
                         cCodLocal_in      IN CHAR,
                         cNumRecetario_in  IN CHAR)
    RETURN FarmaCursor
    IS
         curListado FarmaCursor;
    BEGIN
         OPEN curListado FOR
         select DECODE(PC.NUM_RECETARIO, null, ' ', PC.NUM_RECETARIO) || 'Ã' ||
                DECODE(PC.IND_ESTERIL, null, ' ', PC.IND_ESTERIL) || 'Ã' ||
                DECODE(FF.DESC_FORMA_FARMAC, null, ' ', FF.DESC_FORMA_FARMAC) || 'Ã' ||
                DECODE(PC.CANT_CONTENIDO, null, ' ', PC.CANT_CONTENIDO) || 'Ã' ||
                DECODE(PC.CANT_PREPARADOS, null, ' ', PC.CANT_PREPARADOS) || 'Ã' ||
                DECODE(PC.FEC_RECETARIO, null, ' ', PC.FEC_RECETARIO) || 'Ã' ||
                DECODE(CLI.NOM_CLI || ' ' || CLI.APE_PAT_CLI || ' ' || CLI.APE_MAT_CLI, null, ' ',
                       CLI.NOM_CLI || ' ' || CLI.APE_PAT_CLI || ' ' || CLI.APE_MAT_CLI) || 'Ã' ||
                DECODE(VME.Nombre, null, ' ', VME.Nombre) || 'Ã' ||
                DECODE(PC.Val_Bruto_Recetario, null, ' ', PC.Val_Bruto_Recetario) || 'Ã' ||
                DECODE(PC.COD_ORDEN_PREPARADO, null, ' ', PC.COD_ORDEN_PREPARADO) || 'Ã' ||
                DECODE(PC.NUM_TELEFONO, null, ' ', PC.NUM_TELEFONO) || 'Ã' ||
                DECODE(PC.Fec_Entrega, null, ' ', PC.Fec_Entrega) || 'Ã' ||
                DECODE(LOC.DESC_LOCAL, null, ' ', LOC.DESC_LOCAL) || 'Ã' ||
                DECODE(PC.EST_PEDIDO_CAB, null, ' ', PC.EST_PEDIDO_CAB) || 'Ã' ||
                DECODE(PC.NUM_PEDIDO, null, ' ', PC.NUM_PEDIDO) || 'Ã' ||
                DECODE(PC.NUM_TELEFONO, null, ' ', PC.NUM_TELEFONO) as resultado
          from rcm_pedido_cab PC
          inner join RCM_FORMA_FARMAC FF on PC.COD_FORMA_FARMAC=FF.COD_FORMA_FARMAC
          inner join PBL_CLIENTE CLI on CLI.DNI_CLI = PC.COD_PACIENTE
          inner join vta_mae_medico VME on PC.COD_MEDICO = VME.Matricula AND
                                           VME.CDG_APM = 'CMP'
          inner join PBL_LOCAL LOC on LOC.COD_GRUPO_CIA = PC.COD_GRUPO_CIA AND
                                      LOC.COD_CIA = PC.COD_CIA AND
                                      LOC.COD_LOCAL = PC.COD_LOCAL_ENTREGA
          WHERE PC.NUM_RECETARIO = cNumRecetario_in
                AND PC.COD_GRUPO_CIA = cCodGrupoCia_in
                AND PC.COD_CIA = cCodCia_in
                AND PC.Cod_Local = cCodLocal_in;
          RETURN curListado;
    END;

    --Descripcion: Retorna listado de Ordenes de Preparado a recepcionar como productos
    --Fecha       Usuario	Comentario
    --25/10/2013  LLEIVA  Creacion
    FUNCTION LISTADO_RECEPCION_RCM(cCodLocal_in      IN CHAR,
                                   cFecha_in         IN CHAR,
                                   cNumOrdenPrep_in  IN CHAR)
    RETURN FarmaCursor
    IS
        query VARCHAR2(2000);
        curListado FarmaCursor;
    BEGIN
        query := 'select TNUMEORPR || ''Ã'' ||
                TFECHORPR || ''Ã'' ||
                TOBSEORPR || ''Ã'' ||
                TCANTPROD || ''Ã'' ||
                TIMPOTOTA || ''Ã'' ||
                (TCANTPROD * TIMPOTOTA) as resultado
        from rcm_orden_preparado
        where 1=1 ';

        if(cCodLocal_in IS NOT null) then
             query := query || 'AND TCODILOCADEST = '||cCodLocal_in;
        end if;
        if(cNumOrdenPrep_in IS NOT null) then
             query := query || 'AND TNUMEORPR LIKE ''%'|| cNumOrdenPrep_in ||'%''';
        end if;
        if(cFecha_in IS NOT null) then
             query := query || 'AND TO_CHAR(TFECHORPR,''dd/MM/yyyy'') = '''||cFecha_in||'''';
        end if;

        DBMS_OUTPUT.put_line (query);
        OPEN curListado FOR query;
        RETURN curListado;
    END;

    --Descripcion: Retorna listado de detalles de Ordenes de Preparado a recepcionar como productos
    --Fecha       Usuario	Comentario
    --25/10/2013  LLEIVA  Creacion
    FUNCTION LISTADO_RECEPCION_DETALLE_RCM(cNumOrdenPrep_in  IN CHAR)
    RETURN FarmaCursor
    IS
         curListado FarmaCursor;
    BEGIN
         OPEN curListado FOR
         select RMD.COD_INSUMO || 'Ã' ||
               INS.DESC_INSUMO || 'Ã' ||
               RMD.CANT_ATENDIDA || 'Ã' ||
               UNI.DESC_UNIDAD_MEDIDA || 'Ã' ||
               RMD.VAL_PREC_REC as resultado
        from rcm_pedido_det RMD
        inner join rcm_insumo INS ON RMD.COD_INSUMO=INS.COD_INSUMO
        inner join rcm_unidad_medida UNI ON RMD.COD_UNIDAD_VENTA=UNI.COD_UNIDAD_MEDIDA
        WHERE RMD.NUM_RECETARIO = cNumOrdenPrep_in;
        RETURN curListado;
    END;

END PTOVENTA_RCM;
/

