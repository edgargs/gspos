CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_GRAL" AS

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Obtiene el registro de un listado de maestros.
  --Fecha       Usuario	  Comentario
  --15/02/2006  ERIOS     Creacion
  --06/03/2013  ERIOS     Se agrega el parametro cCodCia_in
  FUNCTION BUSCA_REGISTRO_LISTA_MAESTROS(cTipoMaestro_in IN CHAR, cCodBusqueda_in IN CHAR, cCodGrupoCia IN CHAR, cCodCia_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de maestros.
  --Fecha       Usuario	  Comentario
  --15/02/2006  ERIOS     Creacion
  --06/03/2013  ERIOS     Se agrega el parametro cCodCia_in
  FUNCTION LISTA_MAESTROS(cTipoMaestro_in IN CHAR, cCodGrupoCia IN CHAR, cCodCia_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el listado de filtros.
  --Fecha       Usuario		Comentario
  --22/02/2006  LMESIA     Creación
  FUNCTION LISTA_FILTROS(cTipoFiltro_in IN CHAR,
  		   				 cTipoProd_in	IN CHAR)
  		   RETURN FarmaCursor;
  --Descripcion: Valor para el tipo A
  --Fecha       Usuario		Comentario
  --11/10/2006  ERIOS     Creacion
  FUNCTION GET_ABC_VALOR_A RETURN INTEGER;

  --Descripcion: Valor para el tipo B
  --Fecha       Usuario		Comentario
  --11/10/2006  ERIOS     Creacion
  FUNCTION GET_ABC_VALOR_B RETURN INTEGER;

  --Descripcion: Obtiene la direccion Domicilio Fiscal
  --Fecha       Usuario	    Comentario
  --06/06/2013  ERIOS       Creacion
  FUNCTION GET_DIRECCION_FISCAL(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene la indicador de direccion local
  --Fecha       Usuario	    Comentario
  --12/09/2013  ERIOS       Creacion
  FUNCTION VTA_F_CHAR_IND_OBT_DIR_LOCAL
  RETURN CHAR;

  --Descripcion: Se obtiene mensaje de ticket delivery
  --Fecha       Usuario	    Comentario
  --12/09/2013  ERIOS       Creacion
  FUNCTION VTA_F_GET_MENS_TICKET(cCodCia_in    IN CHAR,
                                cCod_local_in  IN CHAR)
  RETURN CHAR;

  --Descripcion: Obtiene la indicador de registro de venta restringida
  --Fecha       Usuario	    Comentario
  --12/09/2013  ERIOS       Creacion
  FUNCTION GET_IND_REG_VTA_RESTRIG
  RETURN CHAR;

  --Descripcion: Ruta del icono
  --Fecha       Usuario		         Comentario
  --28/02/2013  Luigy Terrazos     Creacion
  FUNCTION GET_RUTA_IMAGEN_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR) RETURN char;
  --Descripcion: Razon Social del CIA
  --Fecha       Usuario		         Comentario
  --28/02/2013  Luigy Terrazos     Creacion
  FUNCTION GET_RAZ_SOC_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR) RETURN char;
  --Descripcion: Direc Fiscal CIA
  --Fecha       Usuario		         Comentario
  --28/02/2013  Luigy Terrazos     Creacion
  FUNCTION GET_DIR_FIS_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR) RETURN varchar2;

  --Descripcion: Telefono CIA
  --Fecha       Usuario		         Comentario
  --28/02/2013  Luigy Terrazos     Creacion
  FUNCTION GET_TELF_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR) RETURN char;

  --Descripcion: Marca CIA
  --Fecha       Usuario     Comentario
  --06/03/2013  ERIOS       Creacion
  FUNCTION GET_NOMBRE_MARCA_CIA(cCodGrupoCia_in in char, cCodLocal_in in CHAR) RETURN VARCHAR2;

  --Descripcion: Obtiene indicador de Nuevo Cobro
  --Fecha       Usuario     Comentario
  --01/04/2013  ERIOS       Creacion
  FUNCTION GET_IND_NUEVO_COBRO RETURN CHAR;

  --Descripcion: Obtener la informacion de la tarjeta ingresada y la forma de pago a la que pertenece
  --Fecha       Usuario   Comentario
  --24/02/2010  ASOSA     Creacion
  --12/Sep/2013 LLEIVA    Modificacion
  --20/12/2013  ERIOS     Se agrega parametros cCodCia_in,cCodLocal_in
  FUNCTION PVTA_F_OBTENER_TARJETA(cCodGrupoCia_in IN CHAR,
								  cCodCia_in IN CHAR,
								  cCodLocal_in IN CHAR,
                                  cCodTarj_in     IN VARCHAR,
                                  cTipOrigen_in   IN VARCHAR)
  RETURN FarmaCursor;

  --Descripcion: Obtiene Directorio raiz
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_DIRECTORIO_RAIZ RETURN CHAR;

  --Descripcion: Obtiene Directorio de imagenes
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_DIRECTORIO_IMAGENES RETURN CHAR;

  --Descripcion: Obtiene Imagen cabecera consejos
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_RUTA_IMG_CABECERA_1 RETURN CHAR;

  --Descripcion: Obtiene Imagen cabecera consejos
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_RUTA_IMG_CABECERA_2 RETURN CHAR;

  --Descripcion: Obtiene Imagen Digemid
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_IMG_LIST_DIGEMID RETURN CHAR;

  --Descripcion: Obtiene Directorio de impresion
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_DIRECTORIO_IMPRESION RETURN CHAR;

  --Descripcion: Obtiene Directorio de logss
  --Fecha       Usuario     Comentario
  --25/06/2013  ERIOS       Creacion
  FUNCTION GET_DIRECTORIO_LOG RETURN CHAR;

  --Descripcion: Obtiene indicador de servicios FarmaSix
  --Fecha       Usuario     Comentario
  --16/07/2013  ERIOS       Creacion
  FUNCTION GET_IND_FARMASIX(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR, cCodLoca_in IN CHAR) RETURN CHAR;

  --Descripcion: Obtiene indicador de Pinpad
  --Fecha       Usuario     Comentario
  --16/08/2013  ERIOS       Creacion
  FUNCTION GET_IND_PINPAD(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR, cCodLoca_in IN CHAR) RETURN CHAR;

  --Descripcion: Obtener los menús que serán bloqueados en la aplicación
  --Fecha       Usuario   Comentario
  --18/10/2013  CVILCA    Creación
  FUNCTION GET_OPCION_BLOQUEADA(cCodGrupoCia_in IN CHAR,
                                                cCodCia_in IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Logo Marca
  --Fecha       Usuario     Comentario
  --28/10/2013  ERIOS       Creacion
  FUNCTION GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in in char, cCodCia_in in CHAR, cCodLocal_in in CHAR)
  RETURN char;

  --Descripcion: Indicador impresion url web
  --Fecha       Usuario     Comentario
  --28/10/2013  ERIOS       Creacion
  --07.08.2014  ERIOS       Se agrega parametros de local
  FUNCTION GET_IND_IMPR_WEB(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN CHAR;

  --Descripcion: Indicador conciliacion online
  --Fecha       Usuario     Comentario
  --29/11/2013  ERIOS       Creacion
  FUNCTION GET_IND_CONCILIAC_ONLINE RETURN CHAR;

  --Descripcion: Indicador mostrar descuentos
  --Fecha       Usuario     Comentario
  --24/09/2014  ERIOS       Creacion
  FUNCTION GET_IND_MOSTRAR_DESC RETURN CHAR;

  --Descripcion: Busca Todos los datos del proveedor
  --Fecha       Usuario     Comentario
  --10/12/2013  CHUANES       Creacion
    FUNCTION BUSCA_DATOS_PROVEEDOR(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProveedor_in IN CHAR)

  Return FarmaCursor ;

  --Descripcion: Indicador recaudacion centralizada
  --Fecha       Usuario     Comentario
  --28/05/2014  ERIOS       Creacion
  FUNCTION GET_IND_RECAUDAC_CENTRA RETURN NUMBER;

  --Descripcion: Margen impresion comprobantes
  --Fecha       Usuario     Comentario
  --17/06/2014  ERIOS       Creacion
  FUNCTION GET_MARGEN_IMP_COMP RETURN NUMBER;

  --Descripcion: 
  --Fecha       Usuario     Comentario
  --23/10/2014  DUBILLUZ    Creacion
  FUNCTION GET_LOGIN_UNICA_VEZ_CAJA
    RETURN CHAR;
    
  --Descripcion: 
  --Fecha       Usuario     Comentario
  --05/11/2014  KMONCADA    Creacion
  FUNCTION GET_RUC_TRANSPORTISTA(vCodLocal_in IN CHAR) 
    RETURN LGT_TRANSP_CIEGA.RUC_TRANSP%TYPE;

  --Descripcion: Indicador mostrar Garantizados
  --Fecha       Usuario     Comentario
  --12/01/2015  ERIOS       Creacion
  FUNCTION GET_IND_ACT_GARANTIZADOS RETURN CHAR;
  
  FUNCTION LISTA_FILTRO_TIPO_PRODUCTO
    RETURN FarmaCursor;
  FUNCTION LISTA_FILTROS_CANT(cTipoFiltro_in IN CHAR,
                             cTipoProd_in  IN CHAR)
  RETURN FarmaCursor;
  
  --Descripcion: Mensaje de Garantizados
  --Fecha       Usuario     Comentario
  --12/01/2015  ERIOS       Creacion  
  FUNCTION GET_MENSAJE_GARANTIZADOS(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR)

  RETURN VARCHAR2;
  
 END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_GRAL" AS

  FUNCTION BUSCA_REGISTRO_LISTA_MAESTROS(cTipoMaestro_in IN CHAR,cCodBusqueda_in IN CHAR, cCodGrupoCia IN CHAR, cCodCia_in IN CHAR)
  RETURN FarmaCursor
  IS
    curGral FarmaCursor;
  BEGIN
        IF(cTipoMaestro_in = '1') THEN --LOCAL
           OPEN curGral FOR
            SELECT NVL(COD_LOCAL,' ') || 'Ã' ||
                       NVL(DESC_CORTA_LOCAL,' ')
            FROM  PBL_LOCAL
            WHERE COD_GRUPO_CIA = cCodGrupoCia
                  AND COD_CIA = cCodCia_in
                  AND TIP_LOCAL = 'V'
                  AND EST_LOCAL = 'A'
                  AND COD_LOCAL = cCodBusqueda_in;
        ELSIF(cTipoMaestro_in = '2') THEN --MATRIZ
           OPEN curGral FOR
            SELECT NVL(COD_LOCAL,' ') || 'Ã' ||
                       NVL(DESC_CORTA_LOCAL,' ')
            FROM PBL_LOCAL
            WHERE COD_GRUPO_CIA = cCodGrupoCia
                  --AND COD_CIA = cCodCia_in
                  AND TIP_LOCAL = 'A'
                  AND EST_LOCAL = 'A'
                  AND COD_LOCAL = cCodBusqueda_in;
        ELSIF(cTipoMaestro_in = '3') THEN --PROVEEDOR
        --ERIOS 22.07.2013 Muestra listado de la tabla LGT_PROV
             OPEN curGral FOR
              SELECT (COD_PROV || 'Ã' ||
                  NOM_PROV
             ) AS RESULTADO

              FROM LGT_PROV
              WHERE COD_PROV = cCodBusqueda_in;
        ELSIF(cTipoMaestro_in = '4') THEN --COMPETENCIA
             OPEN curGral FOR
                SELECT NVL(LLAVE_TAB_GRAL,' ') || 'Ã' ||
                           NVL(DESC_CORTA,' ')
                FROM  PBL_TAB_GRAL
                WHERE COD_APL = 'PTO_VENTA'
                      AND COD_TAB_GRAL = 'COMPETENCIA'
                      AND LLAVE_TAB_GRAL = cCodBusqueda_in;
        ELSIF(cTipoMaestro_in = '13') THEN --TRANSPORTISTA
             OPEN curGral FOR
    -- SELECT NVL(RUC_TRANSPORTISTA,' ')|| 'Ã' ||
      --                 NVL(NOM_TRANSPORTISTA,' ')
        --        FROM LGT_TRANSPORTISTA
    -- WHERE RUC_TRANSPORTISTA=cCodBusqueda_in;
  SELECT        NVL(cod_transp,' ') || 'Ã' ||
                   NVL(ruc_transp,' ')|| 'Ã' ||
                   NVL(nom_transp,' ')|| 'Ã' ||
                     NVL(direc_transp,' ')
             FROM LGT_TRANSP_CIEGA
    WHERE ruc_transp=cCodBusqueda_in
    AND ESTADO='A';

        ELSIF (cTipoMaestro_in ='15') THEN --ASOSA 05.04.2010
             OPEN curGral FOR
             SELECT NVL(cod_transp,' ') || 'Ã' || NVL(nom_transp,' ')
             FROM LGT_TRANSP_CIEGA
             WHERE cod_transp=cCodBusqueda_in
             AND  ESTADO='A';

 --SELECT NVL(COD_TRANSPORTISTA,' ')|| 'Ã' || NVL(RUC_TRANSPORTISTA,' ')|| 'Ã' ||
   --                    NVL(NOM_TRANSPORTISTA,' ') || 'Ã' ||  NVL(DIREC_TRANSPORTISTA,' ')
     --           FROM LGT_TRANSPORTISTA
     --WHERE RUC_TRANSPORTISTA=cCodBusqueda_in;


        END IF;

  RETURN curGral;
  END;

  /* ***************************************************************** */

  FUNCTION LISTA_MAESTROS(cTipoMaestro_in IN CHAR, cCodGrupoCia IN CHAR, cCodCia_in IN CHAR)
  RETURN FarmaCursor
  IS
    curGral FarmaCursor;
  BEGIN
    IF(cTipoMaestro_in = '1') THEN --LOCAL
           OPEN curGral FOR
            SELECT NVL(COD_LOCAL,' ') || 'Ã' ||
                       NVL(DESC_CORTA_LOCAL,' ')
            FROM  PBL_LOCAL
            WHERE COD_GRUPO_CIA = cCodGrupoCia
                  AND COD_CIA = cCodCia_in
                  AND TIP_LOCAL = 'V'
                  AND EST_LOCAL = 'A';
        ELSIF(cTipoMaestro_in = '2') THEN --MATRIZ
           OPEN curGral FOR
            SELECT NVL(COD_LOCAL,' ') || 'Ã' ||
                       NVL(DESC_CORTA_LOCAL,' ')
            FROM PBL_LOCAL
            WHERE COD_GRUPO_CIA = cCodGrupoCia
                  --AND COD_CIA = cCodCia_in
                  AND TIP_LOCAL = 'A'
                  AND EST_LOCAL = 'A';
        ELSIF(cTipoMaestro_in = '3') THEN --PROVEEDOR
        --ERIOS 22.07.2013 Muestra listado de la tabla LGT_PROV
           OPEN curGral FOR
            SELECT (COD_PROV || 'Ã' ||
                      NOM_PROV || 'Ã' ||
                      RUC_PROV || 'Ã' ||
                      DIRECC_PROV
              ) AS RESULTADO
              FROM LGT_PROV
              order by nom_prov asc;
         ELSIF(cTipoMaestro_in = '4') THEN --COMPETENCIA
           OPEN curGral FOR
            SELECT NVL(LLAVE_TAB_GRAL,' ') || 'Ã' ||
                       NVL(DESC_CORTA,' ')
            FROM  PBL_TAB_GRAL
            WHERE COD_APL = 'PTO_VENTA'
                  AND COD_TAB_GRAL = 'COMPETENCIA';

    ELSIF(cTipoMaestro_in = '5') THEN
     OPEN curGral FOR
     SELECT cod_ope_tarj || 'Ã' ||
         desc_ope_tarj
     FROM PBL_OPE_TARJ;
        ELSIF(cTipoMaestro_in = '7') THEN --LABORATORIO
     OPEN curGral FOR
     SELECT NVL(LAB.COD_LAB,' ') || 'Ã' ||
                             NVL(LAB.NOM_LAB,' ')
                  FROM   LGT_LAB LAB;
        ELSIF(cTipoMaestro_in = '13') THEN --TRANSPORTISTA
             OPEN curGral FOR
  --   SELECT NVL(RUC_TRANSPORTISTA,' ')|| 'Ã' ||
    --                   NVL(NOM_TRANSPORTISTA,' ')
      --          FROM LGT_TRANSPORTISTA;
         SELECT NVL(COD_TRANSP,' ') || 'Ã' ||
         NVL(NOM_TRANSP,' ')
         FROM LGT_TRANSP_CIEGA
         WHERE  ESTADO='A' ORDER BY NOM_TRANSP;
     END IF;
  RETURN curGral;
  END;
  /* ***************************************************************** */

  FUNCTION LISTA_FILTROS(cTipoFiltro_in IN CHAR,
                  cTipoProd_in  IN CHAR)
  RETURN FarmaCursor
  IS
    curGral FarmaCursor;
  BEGIN
       IF(cTipoFiltro_in = '0') THEN -- SINTOMAS
       
       OPEN curGral FOR
        SELECT NVL(SINTOMA.COD_SINTOMA,' ') || 'Ã' ||
             NVL(SINTOMA.DESC_SINTOMA,' ')
        FROM   PBL_SINTOMA SINTOMA;
       
       ELSIF(cTipoFiltro_in = '1') THEN --principio activo
         OPEN curGral FOR
        SELECT NVL(PRINC_ACT.COD_PRINC_ACT,' ') || 'Ã' ||
             NVL(PRINC_ACT.DESC_PRINC_ACT,' ')
        FROM   LGT_PRINC_ACT PRINC_ACT
        WHERE  PRINC_ACT.IND_PRINC_ACT_FARMA = cTipoProd_in;
     ELSIF(cTipoFiltro_in = '2') THEN --accion terapeutica
          OPEN curGral FOR
           SELECT NVL(ACC_TERAP.COD_ACC_TERAP,' ') || 'Ã' ||
             NVL(ACC_TERAP.DESC_ACC_TERAP,' ')
        FROM   LGT_ACC_TERAP ACC_TERAP
        WHERE  ACC_TERAP.IND_ACC_TERAP_FARMA = cTipoProd_in;
     ELSIF(cTipoFiltro_in = '3') THEN --laboratorio
          OPEN curGral FOR
           SELECT NVL(LAB.COD_LAB,' ') || 'Ã' ||
             NVL(LAB.NOM_LAB,' ')
        FROM   LGT_LAB LAB;
     END IF;
  RETURN curGral;
  END;
  /****************************************************************************/
  FUNCTION GET_ABC_VALOR_A
  RETURN INTEGER
  IS
    v_vServer INTEGER;
  BEGIN

    SELECT TO_NUMBER(DESC_CORTA,'99.00')
      INTO v_vServer
    FROM PBL_TAB_GRAL
    WHERE --ID_TAB_GRAL = 143 AND
          COD_APL = 'PTO_VENTA'
          AND COD_TAB_GRAL = 'ABC'
          AND LLAVE_TAB_GRAL = '01'
    ;
    RETURN v_vServer;
  END;
  /****************************************************************************/
  FUNCTION GET_ABC_VALOR_B
  RETURN INTEGER
  IS
    v_vServer INTEGER;
  BEGIN

    SELECT TO_NUMBER(DESC_CORTA,'99.00')
      INTO v_vServer
    FROM PBL_TAB_GRAL
    WHERE --ID_TAB_GRAL = 144 AND
          COD_APL = 'PTO_VENTA'
          AND COD_TAB_GRAL = 'ABC'
          AND LLAVE_TAB_GRAL = '02'
    ;
    RETURN v_vServer;
  END;
  /****************************************************************************/
  FUNCTION GET_DIRECCION_FISCAL(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR, cCodLocal_in IN CHAR)
  --Parametros reservados para uso futuro
  RETURN FarmaCursor
  IS
    curGral FarmaCursor;
  BEGIN
    OPEN curGral FOR
    SELECT DESC_LARGA || 'Ã' ||
            DESC_CORTA
    FROM PBL_TAB_GRAL
    WHERE --ID_TAB_GRAL = '329' AND
        COD_APL = 'PTO_VENTA'
        AND COD_TAB_GRAL = 'DIR_MIFARMA_MATRIZ'
        AND EST_TAB_GRAL = 'A'
        AND TRUNC(SYSDATE)
        BETWEEN FECH_INI_VIG
                    AND NVL(FECH_FIN_VIG,SYSDATE+1)
    ORDER BY LLAVE_TAB_GRAL;
    RETURN curGral;
  END;
  /****************************************************************************/
  FUNCTION VTA_F_CHAR_IND_OBT_DIR_LOCAL
  RETURN CHAR
  IS
   vIndDir CHAR;
  BEGIN
   BEGIN
   SELECT G.LLAVE_TAB_GRAL INTO vIndDir
     FROM PBL_TAB_GRAL G
    WHERE --ID_TAB_GRAL = '336' AND
        COD_APL = 'PTO_VENTA'
        AND COD_TAB_GRAL = 'IND_DIR_LOCAL'
        AND EST_TAB_GRAL = 'A';

   EXCEPTION WHEN OTHERS THEN
     vIndDir := 'N';
   END;
   RETURN vIndDir;
  END;
  /****************************************************************************/
  FUNCTION VTA_F_GET_MENS_TICKET(cCodCia_in    IN CHAR,
                                cCod_local_in  IN CHAR)
  RETURN CHAR
  IS
  cMensaje VARCHAR2(100);
    cMensajeAux VARCHAR2(100);
    v_cVerifica CHAR(1);
        v_cProv CHAR(1);

  BEGIN


      SELECT X.IND_LOCAL_PROV INTO v_cProv
      FROM PBL_LOCAL X
      WHERE X.COD_GRUPO_CIA='001'
      AND X.COD_LOCAL=cCod_local_in;

      SELECT A.LLAVE_TAB_GRAL INTO  cMensajeAux
      FROM PBL_TAB_GRAL A
      WHERE A.ID_TAB_GRAL=293;

  IF((cCod_local_in='044' OR  cCod_local_in='061' OR cCod_local_in='024' OR cCod_local_in='026') AND v_cProv='S') THEN

   cMensaje:=cMensajeAux;

  ELSIF (v_cProv='N')THEN

    SELECT A.DESC_CORTA INTO  cMensaje
      FROM PBL_TAB_GRAL A
      WHERE --A.ID_TAB_GRAL=293
        COD_APL = 'PTO_VENTA'
        AND COD_TAB_GRAL = 'MENS_TICKET_DELIVERY'
        AND LLAVE_TAB_GRAL = (SELECT COD_CIA FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodCia_in AND COD_LOCAL = cCod_local_in)
        AND EST_TAB_GRAL = 'A'
        --AND TRUNC(SYSDATE)
        --BETWEEN FECH_INI_VIG
                    --AND NVL(FECH_FIN_VIG,SYSDATE+1)
    --ORDER BY LLAVE_TAB_GRAL
    ;

  ELSIF (v_cProv='S')THEN

   cMensaje:='N';

  END IF;

    RETURN  cMensaje;
  EXCEPTION
      WHEN NO_DATA_FOUND THEN
                RETURN 'N';

  END;
  /****************************************************************************/
  FUNCTION GET_IND_REG_VTA_RESTRIG
  RETURN CHAR
  IS
   vIndDir CHAR;
  BEGIN
   BEGIN
   SELECT G.LLAVE_TAB_GRAL INTO vIndDir
     FROM PBL_TAB_GRAL G
    WHERE --ID_TAB_GRAL = '363' AND
        COD_APL = 'PTO_VENTA'
        AND COD_TAB_GRAL = 'IND_REG_VTA_RESTRIG'
        AND EST_TAB_GRAL = 'A';

   EXCEPTION WHEN OTHERS THEN
     vIndDir := 'N';
   END;
   RETURN vIndDir;
  END;
  /****************************************************************************/
  FUNCTION GET_RUTA_IMAGEN_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR)
  RETURN char
      IS
      vresultado  varchar(50);
  begin
   select PBL_CIA.LOG_CIA
   into vresultado
   from   PBL_CIA
   where  PBL_CIA.COD_GRUPO_CIA=cCodGrupoCia_in
   and    PBL_CIA.COD_CIA=cCodCia_in;
  RETURN vresultado ;
  end;
  /*************************************************************/

  /*************************************************************/
  FUNCTION GET_RAZ_SOC_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR)
  RETURN char
      IS
      vresultado  varchar(100);
  begin
   select PBL_CIA.RAZ_SOC_CIA
   into vresultado
   from   PBL_CIA
   where  PBL_CIA.COD_GRUPO_CIA=cCodGrupoCia_in
   and    PBL_CIA.COD_CIA=cCodCia_in;
  RETURN vresultado ;
  end;
  /*************************************************************/

  /*************************************************************/
  FUNCTION GET_DIR_FIS_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR)
  RETURN varchar2
      IS
      vresultado  varchar2(250);
  begin
   select PBL_CIA.DIR_CIA
   into vresultado
   from   PBL_CIA
   where  PBL_CIA.COD_GRUPO_CIA=cCodGrupoCia_in
   and    PBL_CIA.COD_CIA=cCodCia_in;
  RETURN vresultado ;
  end;
  /*************************************************************/

  /*************************************************************/
  FUNCTION GET_TELF_CIA(cCodGrupoCia_in in char, cCodCia_in in CHAR)
  RETURN char
      IS
      vresultado  varchar(50);
  begin
   select PBL_CIA.TELF_CIA
   into vresultado
   from   PBL_CIA
   where  PBL_CIA.COD_GRUPO_CIA=cCodGrupoCia_in
   and    PBL_CIA.COD_CIA=cCodCia_in;
  RETURN vresultado ;
  end;
  /*************************************************************/

  FUNCTION GET_NOMBRE_MARCA_CIA(cCodGrupoCia_in in char, cCodLocal_in in CHAR) RETURN VARCHAR2
  IS
    v_vNombreMarca PBL_MARCA_GRUPO_CIA.NOM_MARCA%TYPE;
  BEGIN
    SELECT MGC.NOM_MARCA
        INTO v_vNombreMarca
    FROM PBL_LOCAL LOC JOIN
        PBL_MARCA_CIA MCI ON (LOC.COD_GRUPO_CIA = MCI.COD_GRUPO_CIA AND
                                LOC.COD_MARCA = MCI.COD_MARCA AND
                                LOC.COD_CIA = MCI.COD_CIA)
        JOIN PBL_MARCA_GRUPO_CIA  MGC ON (MCI.COD_GRUPO_CIA = MGC.COD_GRUPO_CIA AND
                                    MCI.COD_MARCA = MGC.COD_MARCA)
    WHERE LOC.COD_GRUPO_CIA = cCodGrupoCia_in
    AND LOC.COD_LOCAL = cCodLocal_in;

    RETURN v_vNombreMarca;
  END;
  /*************************************************************/

  FUNCTION GET_IND_NUEVO_COBRO RETURN CHAR
  IS
    v_cIndicador CHAR(1);
  BEGIN
    SELECT TRIM(a.llave_tab_gral) INTO v_cIndicador
     FROM pbl_tab_gral a
     WHERE
     --a.id_tab_gral='332' AND
     a.cod_apl='PTO_VENTA'
     AND a.cod_tab_gral = 'IND_NEW_COBRO'
     AND a.est_tab_gral='A';

     RETURN v_cIndicador;
  END;

  /*************************************************************************************************************/

  FUNCTION PVTA_F_OBTENER_TARJETA(cCodGrupoCia_in IN CHAR,
                  cCodCia_in IN CHAR,
                  cCodLocal_in IN CHAR,
                                  cCodTarj_in     IN VARCHAR,
                                  cTipOrigen_in   IN VARCHAR)
  RETURN FarmaCursor
  IS
       cur FarmaCursor;
     v_vIndicador CHAR(1);
  BEGIN

      v_vIndicador := GET_IND_FARMASIX(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);

    IF v_vIndicador = 'S' THEN
    OPEN cur FOR
            SELECT a.bin  || 'Ã' ||
                   a.desc_prod  || 'Ã' ||
                   a.cod_forma_pago  || 'Ã' ||
                   b.desc_corta_forma_pago
            FROM vta_fpago_tarj a
            inner join vta_forma_pago b on a.cod_grupo_cia=b.cod_grupo_cia
            WHERE a.cod_grupo_cia=cCodGrupoCia_in
            AND a.cod_forma_pago=b.cod_forma_pago
            AND cCodTarj_in LIKE trim(a.bin)||'%'
            AND TIP_ORIGEN_PAGO = cTipOrigen_in;
    ELSE
      --ERIOS 20.12.2013 No se considera pago con CMR
      --ERIOS 21.01.2014 Para POS si condiera CMR
    OPEN cur FOR
            SELECT a.bin  || 'Ã' ||
                   a.desc_prod  || 'Ã' ||
                   a.cod_forma_pago  || 'Ã' ||
                   b.desc_corta_forma_pago
            FROM vta_fpago_tarj a
            inner join vta_forma_pago b on a.cod_grupo_cia=b.cod_grupo_cia
            WHERE a.cod_grupo_cia=cCodGrupoCia_in
            AND a.cod_forma_pago=b.cod_forma_pago
            AND cCodTarj_in LIKE trim(a.bin)||'%'
            AND TIP_ORIGEN_PAGO = cTipOrigen_in
            --LLEIVA 10-Abr-2014 ahora si se permitira los pagos de CMR en visa
            --AND (B.COD_FORMA_PAGO NOT IN ('00024') --CMR
            --OR 'POS' = cTipOrigen_in
      --)
            ;
    END IF;

       RETURN cur;
  END;

  /*************************************************************************************************************/

  FUNCTION GET_DIRECTORIO_RAIZ RETURN CHAR
  IS
    v_vRaiz PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    SELECT RUTA INTO v_vRaiz
    FROM (
    SELECT DESC_CORTA RUTA
    FROM PBL_TAB_GRAL
    WHERE COD_APL = 'PTO_VENTA'
    AND COD_TAB_GRAL = 'DIRECTORIO_RAIZ'
    AND EST_TAB_GRAL = 'A'
    ORDER BY LLAVE_TAB_GRAL
    ) WHERE ROWNUM = 1;

    RETURN v_vRaiz;
  END;

  FUNCTION GET_DIRECTORIO_IMAGENES RETURN CHAR
  IS
    v_vRuta PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    SELECT RUTA INTO v_vRuta
    FROM (
    SELECT DESC_CORTA RUTA
    FROM PBL_TAB_GRAL
    WHERE COD_APL = 'PTO_VENTA'
    AND COD_TAB_GRAL = 'DIRECTORIO_IMAGENES'
    AND EST_TAB_GRAL = 'A'
    ORDER BY LLAVE_TAB_GRAL
    ) WHERE ROWNUM = 1;

    RETURN v_vRuta;
  END;

  FUNCTION GET_RUTA_IMG_CABECERA_1 RETURN CHAR
  IS
    v_vImg PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    SELECT RUTA INTO v_vImg
    FROM (
    SELECT DESC_CORTA RUTA
    FROM PBL_TAB_GRAL
    WHERE COD_APL = 'PTO_VENTA'
    AND COD_TAB_GRAL = 'RUTA_IMG_CABECERA_1'
    AND EST_TAB_GRAL = 'A'
    ORDER BY LLAVE_TAB_GRAL
    ) WHERE ROWNUM = 1;

    RETURN v_vImg;
  END;

  FUNCTION GET_RUTA_IMG_CABECERA_2 RETURN CHAR
  IS
    v_vImg PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    SELECT RUTA INTO v_vImg
    FROM (
    SELECT DESC_CORTA RUTA
    FROM PBL_TAB_GRAL
    WHERE COD_APL = 'PTO_VENTA'
    AND COD_TAB_GRAL = 'RUTA_IMG_CABECERA_2'
    AND EST_TAB_GRAL = 'A'
    ORDER BY LLAVE_TAB_GRAL
    ) WHERE ROWNUM = 1;

    RETURN v_vImg;
  END;

  FUNCTION GET_IMG_LIST_DIGEMID RETURN CHAR
  IS
    v_vImg PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    SELECT RUTA INTO v_vImg
    FROM (
    SELECT DESC_CORTA RUTA
    FROM PBL_TAB_GRAL
    WHERE COD_APL = 'PTO_VENTA'
    AND COD_TAB_GRAL = 'IMG_LIST_DIGEMID'
    AND EST_TAB_GRAL = 'A'
    ORDER BY LLAVE_TAB_GRAL
    ) WHERE ROWNUM = 1;

    RETURN v_vImg;
  END;

  FUNCTION GET_DIRECTORIO_IMPRESION RETURN CHAR
  IS
    v_vRaiz PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    SELECT RUTA INTO v_vRaiz
    FROM (
    SELECT DESC_CORTA RUTA
    FROM PBL_TAB_GRAL
    WHERE COD_APL = 'PTO_VENTA'
    AND COD_TAB_GRAL = 'DIRECTORIO_IMPRESION'
    AND EST_TAB_GRAL = 'A'
    ORDER BY LLAVE_TAB_GRAL
    ) WHERE ROWNUM = 1;

    RETURN v_vRaiz;
  END;

  FUNCTION GET_DIRECTORIO_LOG RETURN CHAR
  IS
    v_vRaiz PBL_TAB_GRAL.DESC_CORTA%TYPE;
  BEGIN
    SELECT RUTA INTO v_vRaiz
    FROM (
    SELECT DESC_CORTA RUTA
    FROM PBL_TAB_GRAL
    WHERE COD_APL = 'PTO_VENTA'
    AND COD_TAB_GRAL = 'DIRECTORIO_LOG'
    AND EST_TAB_GRAL = 'A'
    ORDER BY LLAVE_TAB_GRAL
    ) WHERE ROWNUM = 1;

    RETURN v_vRaiz;
  END;

    FUNCTION GET_IND_FARMASIX(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR, cCodLoca_in IN CHAR)
    RETURN CHAR
    IS
         v_vIndicador CHAR(1);
    BEGIN
        SELECT DESC_CORTA
            INTO v_vIndicador
        FROM PBL_TAB_GRAL
        WHERE COD_APL = 'PTO_VENTA'
        AND COD_TAB_GRAL = 'IND_FARMASIX'
        AND EST_TAB_GRAL = 'A'
    AND COD_CIA = cCodCia_in;
        RETURN v_vIndicador;
    END;

    FUNCTION GET_IND_PINPAD(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR, cCodLoca_in IN CHAR)
    RETURN CHAR
    IS
         v_vIndicador CHAR(1);
    BEGIN
        SELECT DESC_CORTA
            INTO v_vIndicador
        FROM PBL_TAB_GRAL
        WHERE COD_APL = 'PTO_VENTA'
        AND COD_TAB_GRAL = 'IND_PINPAD'
        AND EST_TAB_GRAL = 'A';
        RETURN v_vIndicador;
    END;

    FUNCTION GET_OPCION_BLOQUEADA(cCodGrupoCia_in IN CHAR,
                                                cCodCia_in IN CHAR)
    RETURN FarmaCursor
    IS
       cur FarmaCursor;
    BEGIN
       OPEN cur FOR
            SELECT  OD.NOM_OBJETO RESULTADO
              FROM PBL_OPCION_DENEGADA OD
             WHERE OD.COD_GRUPO_CIA = cCodGrupoCia_in
               AND OD.COD_CIA = cCodCia_in
               AND OD.IND_EST = 'A';
       RETURN cur;
    END;
  /*************************************************************/
  FUNCTION GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in in char, cCodCia_in in CHAR, cCodLocal_in in CHAR)
  RETURN char
      IS
      vresultado  varchar(50);
  begin
   select LOG_CIA
   into vresultado
   from   PBL_MARCA_GRUPO_CIA
   where  (COD_GRUPO_CIA,COD_MARCA) = (
   SELECT COD_GRUPO_CIA,COD_MARCA FROM PBL_LOCAL
   WHERE COD_GRUPO_CIA = cCodGrupoCia_in
   AND COD_LOCAL = cCodLocal_in);
  RETURN vresultado ;
  end;
  /*************************************************************/
  FUNCTION GET_IND_IMPR_WEB(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR) RETURN CHAR
  IS
    v_cIndicador CHAR(100);
  BEGIN
       -- segundos de espera
   --dbms_lock.sleep(10);

    SELECT TRIM(a.DESC_CORTA) INTO v_cIndicador
     FROM pbl_tab_gral a
     WHERE
     a.cod_apl='PTO_VENTA'
     AND a.cod_tab_gral = 'IND_IMPR_URL_WEB'
     AND a.est_tab_gral='A';

	 IF v_cIndicador = 'S' THEN
		 --ERIOS 2.4.3 Pagina web
		 SELECT 'www.'||LOWER(NOM_MARCA)||'.com.pe' INTO v_cIndicador
		 FROM PBL_MARCA_GRUPO_CIA
		 WHERE COD_GRUPO_CIA = cCodGrupoCia_in
			AND COD_MARCA = (SELECT COD_MARCA FROM PBL_LOCAL
							WHERE COD_GRUPO_CIA = cCodGrupoCia_in
							AND COD_LOCAL = cCodLocal_in);
	END IF;

     RETURN v_cIndicador;
  END;
  /*************************************************************/
  FUNCTION GET_IND_CONCILIAC_ONLINE RETURN CHAR
  IS
    v_cIndicador CHAR(1);
  BEGIN
    SELECT TRIM(a.DESC_CORTA) INTO v_cIndicador
     FROM pbl_tab_gral a
     WHERE
     a.cod_apl='PTO_VENTA'
     AND a.cod_tab_gral = 'IND_CONCILIAC_ONLINE'
     AND a.est_tab_gral='A';

     RETURN v_cIndicador;
  END;
  /*************************************************************/
  FUNCTION GET_IND_MOSTRAR_DESC RETURN CHAR
  IS
    v_cIndicador CHAR(1);
  BEGIN
    SELECT TRIM(a.DESC_CORTA) INTO v_cIndicador
     FROM pbl_tab_gral a
     WHERE
     a.cod_apl='PTO_VENTA'
     AND a.cod_tab_gral = 'IND_MOSTRAR_DESC'
     AND a.est_tab_gral='A';

     RETURN v_cIndicador;
  END;
  /*************************************************************/
  /*Cesar Huanes -Busca todos los datos del proveedor 11/12/2013*/
  FUNCTION BUSCA_DATOS_PROVEEDOR(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
  cCodProveedor_in IN CHAR)

  Return FarmaCursor
  IS
  curProveedor FarmaCursor;
  BEGIN
  OPEN curProveedor  FOR
  SELECT NVL(COD_PROV,' ' )|| 'Ã' ||
             NVL(NOM_PROV,' ') || 'Ã' ||
             NVL(RUC_PROV,' ') || 'Ã' ||
             NVL(DIRECC_PROV,' ')
             FROM LGT_PROV
             WHERE COD_PROV =  cCodProveedor_in;

  RETURN curProveedor;

  END;

  /* ***************************************************************************** */
  --Descripcion: Indicador recaudacion centralizada
  --Fecha       Usuario     Comentario
  --28/05/2014  ERIOS       Creacion
  FUNCTION GET_IND_RECAUDAC_CENTRA RETURN NUMBER
  IS
    v_nIndicador NUMBER;
  BEGIN
    SELECT TO_NUMBER(TRIM(a.DESC_CORTA)) INTO v_nIndicador
     FROM pbl_tab_gral a
     WHERE
     a.cod_apl='PTO_VENTA'
     AND a.cod_tab_gral = 'IND_RECAUDAC_CENTRA'
     AND a.est_tab_gral='A';

     RETURN v_nIndicador;
  END;
  /* ***************************************************************************** */
  --Descripcion: Margen impresion comprobantes
  --Fecha       Usuario     Comentario
  --17/06/2014  ERIOS       Creacion
  FUNCTION GET_MARGEN_IMP_COMP RETURN NUMBER
  IS
    v_nIndicador NUMBER;
  BEGIN
    SELECT TO_NUMBER(TRIM(a.DESC_CORTA)) INTO v_nIndicador
     FROM pbl_tab_gral a
     WHERE
     a.cod_apl='PTO_VENTA'
     AND a.cod_tab_gral = 'MARGEN_IMP_COMP'
     AND a.est_tab_gral='A';

     RETURN v_nIndicador;
  END;
  /* ***************************************************************************** */  
  FUNCTION GET_LOGIN_UNICA_VEZ_CAJA
    RETURN CHAR
    IS
         v_vIndicador CHAR(1);
    BEGIN
        SELECT LLAVE_TAB_GRAL
            INTO v_vIndicador
        FROM PBL_TAB_GRAL A
        WHERE A.ID_TAB_GRAL =616;
        RETURN v_vIndicador;
    END;
  /* ***************************************************************************** */
  FUNCTION GET_RUC_TRANSPORTISTA(vCodLocal_in IN CHAR) 
    RETURN LGT_TRANSP_CIEGA.RUC_TRANSP%TYPE
  IS
    v_ruc_transporte LGT_TRANSP_CIEGA.RUC_TRANSP%TYPE;
  BEGIN
    BEGIN 
      SELECT NVL(RUC_TRANSP, ' ')
      INTO   v_ruc_transporte
      FROM   LGT_TRANSP_CIEGA
      WHERE  ESTADO = 'A'
      AND    COD_TRANSP = vCodLocal_in;
    EXCEPTION 
      WHEN NO_DATA_FOUND THEN
        v_ruc_transporte := ' ';
    END;
    RETURN v_ruc_transporte;     
  END;
  /* ***************************************************************************** */
  FUNCTION GET_IND_ACT_GARANTIZADOS RETURN CHAR
  IS
    v_cIndicador CHAR(1);
  BEGIN
    SELECT TRIM(a.DESC_CORTA) INTO v_cIndicador
     FROM pbl_tab_gral a
     WHERE
     a.cod_apl='PTO_VENTA'
     AND a.cod_tab_gral = 'IND_ACT_GARANTIZADOS'
     AND a.est_tab_gral='A';

     RETURN v_cIndicador;
  END;
  /* ***************************************************************************** */
  
  FUNCTION LISTA_FILTRO_TIPO_PRODUCTO
    RETURN FarmaCursor
  IS
    curFiltro FarmaCursor;
  BEGIN
    OPEN curFiltro FOR
      SELECT DESC_CORTA|| 'Ã' ||LLAVE_TAB_GRAL
		FROM PBL_TAB_GRAL
		WHERE COD_APL = 'PTO_VENTA'
		AND COD_TAB_GRAL = 'REL_TIPO_PRODUCTO'
		AND EST_TAB_GRAL = 'A'
		ORDER BY ID_TAB_GRAL;
    RETURN curFiltro;
 END;
 
 
 FUNCTION LISTA_FILTROS_CANT(cTipoFiltro_in IN CHAR,
                             cTipoProd_in  IN CHAR)
  RETURN FarmaCursor
  IS
    curGral FarmaCursor;
  BEGIN
    IF(cTipoFiltro_in = '0') THEN -- SINTOMAS
      OPEN curGral FOR
        SELECT NVL(SINTOMA.COD_SINTOMA, ' ')|| 'Ã' ||
               NVL(SINTOMA.DESC_SINTOMA, ' ')|| 'Ã' ||
               '0' CTD
          FROM PBL_SINTOMA SINTOMA
			WHERE EXISTS (SELECT COUNT(1)
                  FROM PBL_SINTOMA_PROD A, LGT_PROD P
                 WHERE A.COD_GRUPO_CIA = '001'
                   AND A.COD_SINTOMA = SINTOMA.COD_SINTOMA
                   AND P.COD_GRUPO_CIA = '001'
                   AND P.est_prod = 'A'
                   AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                   AND A.COD_PROD = P.COD_PROD);
    ELSIF(cTipoFiltro_in = '1') THEN --principio activo
      OPEN curGral FOR
        SELECT NVL(PRINC_ACT.COD_PRINC_ACT, ' ')|| 'Ã' ||
              NVL(PRINC_ACT.DESC_PRINC_ACT, ' ')|| 'Ã' ||
              '0' CTD
        FROM LGT_PRINC_ACT PRINC_ACT
        WHERE PRINC_ACT.IND_PRINC_ACT_FARMA = cTipoProd_in
		AND EXISTS (SELECT 1
                 FROM LGT_PRINC_ACT_PROD A, LGT_PROD P
                WHERE A.COD_GRUPO_CIA = '001'
                  AND A.COD_PRINC_ACT = PRINC_ACT.COD_PRINC_ACT
                  AND P.COD_GRUPO_CIA = '001'
                  AND P.est_prod = 'A'
                  AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                  AND A.COD_PROD = P.COD_PROD);
    ELSIF(cTipoFiltro_in = '2') THEN --accion terapeutica
      OPEN curGral FOR
        SELECT NVL(ACC_TERAP.COD_ACC_TERAP, ' ')|| 'Ã' ||
               NVL(ACC_TERAP.DESC_ACC_TERAP, ' ')|| 'Ã' ||
               '0' CTD
          FROM LGT_ACC_TERAP ACC_TERAP
         WHERE ACC_TERAP.IND_ACC_TERAP_FARMA = cTipoProd_in
		 AND EXISTS (SELECT 1
                  FROM LGT_ACC_TERAP_PROD A, LGT_PROD P
                 WHERE A.COD_GRUPO_CIA = '001'
                   AND A.COD_ACC_TERAP = ACC_TERAP.COD_ACC_TERAP
                   AND P.COD_GRUPO_CIA = A.COD_GRUPO_CIA
                   AND P.est_prod = 'A'
                   AND A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
                   AND A.COD_PROD = P.COD_PROD);
    ELSIF(cTipoFiltro_in = '3') THEN --laboratorio
      OPEN curGral FOR
        SELECT NVL(LAB.COD_LAB, ' ')|| 'Ã' ||
               NVL(LAB.NOM_LAB, ' ')|| 'Ã' ||
               '0'
        FROM LGT_LAB LAB
		WHERE EXISTS (SELECT 1
                  FROM LGT_PROD A
                 WHERE A.COD_GRUPO_CIA = '001'
                   AND A.EST_PROD = 'A'
                   AND A.COD_LAB = LAB.COD_LAB);
    END IF;
  RETURN curGral;
  END;
  
  FUNCTION GET_MENSAJE_GARANTIZADOS(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR)

  RETURN VARCHAR2
  IS
	 vRetorno PBL_TAB_GRAL.DESC_LARGA%type;
  BEGIN
	SELECT UPPER(DESC_LARGA)
	 INTO vRetorno
	FROM PBL_TAB_GRAL
	WHERE COD_APL = 'PTO_VENTA'
	AND COD_TAB_GRAL = 'MENSAJE_GARANTIZADOS'
	AND LLAVE_TAB_GRAL = '01';
	RETURN vRetorno;
  END;
END;
/
