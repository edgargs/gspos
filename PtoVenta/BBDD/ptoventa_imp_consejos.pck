CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_IMP_CONSEJOS" AS

  TYPE FarmaCursor IS REF CURSOR;
  C_INDICADOR_NO CHAR(1) := 'N';
  C_INDICADOR_SI CHAR(1) := 'S';
  C_ESTADO_ACTIVO CHAR(1) := 'A';
  C_TITULAR CHAR(1) := 'T';

  C_SIZE_CONSEJO   VARCHAR2(200) := '18';
  C_SIZE_MSG_FINAL VARCHAR2(200) := '10';

/*
  C_INICIO_MSG  VARCHAR2(2000) := '<html>
                                <head>
                                <style type="text/css">
                                <!--
                                .style2 {font-size: '||C_SIZE_MSG_FINAL||'; }
                                .style5 {font-size: '||C_SIZE_CONSEJO||'; }
                                .style3 {font-family: Arial, Helvetica, sans-serif}
                                .style8 {font-size: 24; }
                                .style9 {font-size: larger}
                                .style12 {
                                 font-family: Arial, Helvetica, sans-serif;
                                 font-size: larger;
                                 font-weight: bold;
                                }
                                </style>
                                </head>
                                <body>

                                <table width="200" border="0">
                                  <tr>
                                    <td>&nbsp;&nbsp;</td>
                                    <td><table width="300" height="841" border="1">
                                ';*/

  --Modificado por fernando Veliz La Rosa 02.09.08
  C_INICIO_MSG  VARCHAR2(2000) := '<html>
                                  <head>
                                  <style type="text/css">
                                  .style2 {font-size: '||C_SIZE_MSG_FINAL||'; }
                                  .style5 {font-size: '||C_SIZE_CONSEJO||'; }

                                  .style3 {font-family: Arial, Helvetica, sans-serif}
                                  .style8 {font-size: 24; }
                                  .style9 {font-size: 14}
                                  .style12 {
                                   font-family: Arial, Helvetica, sans-serif;
                                   font-size: larger;
                                   font-weight: bold;
                                  }
                                  </style>
                                  </head>
                                  <body>

                                  <table width="200" border="0">
                                  <tr>
                                    <td>&nbsp;&nbsp;</td>
                                    <td>
                                    <table width="300" height="841" border="1">';

  C_FILA_VACIA  VARCHAR2(2000) :='<tr> '||
                                  '<td height="2" colspan="3"></td> '||
                                  ' </tr> ';

  C_FIN_MSG     VARCHAR2(2000) := ' </table></td>
                                      </tr>
                                    </table>
                                    <p><br>
                                      <br>
                                    </p>
                                    </body>
                                    </html> ';

  FUNCTION IMP_GET_SEPARADOR
  RETURN VARCHAR2;

  --Descripcion: Obtiene mensje 01 de consejo
  --Fecha       Usuario		Comentario
  --09/05/2008  DUBILLUZ  Creación
  FUNCTION IMP_GET_MSG_01_CONSEJO
  RETURN VARCHAR2;

  --Descripcion: Obtiene nombre del cliente
  --Fecha       Usuario		Comentario
  --09/05/2008  DUBILLUZ  Creación
  FUNCTION IMP_GET_NAME_CLI(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
                						cNumPedVta_in   	IN CHAR)
  RETURN VARCHAR2;

 FUNCTION IMP_GET_PIE_PAGINA(cCodGrupoCia_in 	IN CHAR,
                             cCodLocal_in    	IN CHAR,
                						 cNumPedVta_in   	IN CHAR)
  RETURN VARCHAR2;

  --Descripcion: Obtiene consejos
  --Fecha       Usuario		Comentario
  --09/05/2008  DUBILLUZ  Creación
  FUNCTION IMP_GET_CONSEJOS(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
                						cNumPedVta_in   	IN CHAR,
                            cLoginUsu_in     IN CHAR)
  RETURN VARCHAR2;

  FUNCTION IMP_PROCESA_CONSEJOS(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR,
                                cLoginUsu_in     IN CHAR,
                                --Se agrega cod_cia para obtener la ruta de la imagen
                                --Autor Luigy Terrazos    Fecha 04/03/2013
                                cCodCia_in in char
                							 )
  RETURN VARCHAR2;

  FUNCTION IMP_CONTIENE_CONSEJOS_PED(cCodGrupoCia_in 	IN CHAR,
                                     cCodLocal_in    	IN CHAR,
                        						 cNumPedVta_in   	IN CHAR)
  RETURN CHAR;

    FUNCTION IMP_CREA_CONSEJOS(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
                						cNumPedVta_in   	IN CHAR)
    RETURN CHAR;

  --Descripcion: Obtiene nombre del cliente
  FUNCTION IMP_GET_MAX_PROD_X_PED
  RETURN INTEGER;

  --Descripcion: Obtiene nombre del cliente
  FUNCTION IMP_GET_MAX_CONS_X_PROD
  RETURN INTEGER;

  --Descripcion: Obtiene nombre del cliente
  FUNCTION IMP_GET_IND_IMP_CUPON(cCodGrupoCia_in 	IN CHAR,
                                 cCodLocal_in    	IN CHAR)
  RETURN VARCHAR2;

  FUNCTION IMP_GET_NAME_IMP_CONSEJO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN VARCHAR2 ;

  FUNCTION IMP_GET_NAME_IMP_STICKER(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN VARCHAR2;
 
 /************************************************************/
 /**  Descripcion  :Obtiene la cantidad de                  **/
 /**    impresiones que se tiene que efectuar antes         **/
 /**    de mostrar mensaje de confirmacion                  **/
 /**  Fecha        :22.09.2015                              **/
 /**  Usuario      :Desarrollo5                             **/
 /**  Parametros   :                                        **/
 /**  Retorno      :Varchar2                                **/
 /************************************************************/   
 FUNCTION IMP_CANTIDAD_IMPRESIONES RETURN VARCHAR2;
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_IMP_CONSEJOS" AS



  FUNCTION IMP_GET_SEPARADOR
  RETURN VARCHAR2
  IS
--  vResultado varchar2(4000):= '';
  vResultado PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE := ' ';
  BEGIN
      BEGIN
      SELECT TRIM(T.LLAVE_TAB_GRAL)
      INTO   vResultado
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'SEPARADOR_CONS'
      AND    T.ID_TAB_GRAL = 203;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := ' ';
      END;

   RETURN vResultado;
  END;
 /* ***************************************************************** */

 /* ***************************************************************** */

 /* ***************************************************************** */
  FUNCTION IMP_GET_MSG_01_CONSEJO
  RETURN VARCHAR2
  IS
  vResultado varchar2(14000):= '';
  BEGIN
      BEGIN
      SELECT TRIM(T.LLAVE_TAB_GRAL)
      INTO   vResultado
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'MSG01_CONSEJOS'
      AND    T.ID_TAB_GRAL = 198;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := '';
      END;
   RETURN vResultado;
  END;
 /* ******************************************************************** */
  FUNCTION IMP_GET_NAME_CLI(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
                						cNumPedVta_in   	IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(13000):= '';
  BEGIN
      BEGIN
      SELECT TRIM(DECODE(C.NOM_CLI_PED_VTA,'','ESTIMADO CLIENTE','Sr.: '||C.NOM_CLI_PED_VTA))
      INTO   vResultado
      FROM   VTA_PEDIDO_VTA_CAB C
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL = cCodLocal_in
      AND    C.NUM_PED_VTA = cNumPedVta_in;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := '';
      END;

   RETURN vResultado;

  END;
 /* ******************************************************************** */
 FUNCTION IMP_GET_PIE_PAGINA(cCodGrupoCia_in 	IN CHAR,
                             cCodLocal_in    	IN CHAR,
                						 cNumPedVta_in   	IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(23000):= '';
  vCod_trab varchar2(100):= '';
  vFecha    varchar2(100):= '';
  vlocal    varchar2(100):= '';
  BEGIN
      BEGIN
          SELECT --NVL(/*U.COD_TRAB_RRHH*/u.sec_usu_local,U.COD_TRAB),
                 trim(u.sec_usu_local),
                 --TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY HH24:MI:SS'),
                 trim(TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY')),
                 L.COD_LOCAL --|| '-'||L.DESC_ABREV
          INTO   vCod_trab,vFecha,vlocal
          FROM   VTA_PEDIDO_VTA_CAB C,
                 CE_MOV_CAJA M       ,
                 PBL_USU_LOCAL U,
                 PBL_LOCAL L
          WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    C.COD_LOCAL = cCodLocal_in
          AND    C.NUM_PED_VTA = cNumPedVta_in
          AND    C.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND    C.COD_LOCAL = L.COD_LOCAL
          AND    C.COD_GRUPO_CIA = M.COD_GRUPO_CIA
          AND    C.COD_LOCAL = M.COD_LOCAL
          AND    C.SEC_MOV_CAJA = M.SEC_MOV_CAJA
          AND    M.COD_GRUPO_CIA = U.COD_GRUPO_CIA
          AND    M.COD_LOCAL = U.COD_LOCAL
          AND    M.SEC_USU_LOCAL = U.SEC_USU_LOCAL;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
       vCod_trab:='';
       vFecha:= '';
       vlocal := '';
      END;

      /*
      vResultado := ' <tr> '||
                    '<td width="10" height="38"><div align="center" class="style1 style3 style8">'||
                    vCod_trab ||
                    '</div></td> '||
                    '<td width="60"><div align="center" class="style1 style3 style8"> '||
                    '<div align="center">'||
                    vFecha||
                    '</div> '||
                    '</div></td>'||
                    '</td> '||
                    '<td width="60"><div align="center" class="style1 style3 style8"> '||
                    '<div align="center">'||
                    cNumPedVta_in||
                    '</div> '||
                    '</div></td> '||
                    '<td width="20"> '||
                    '<div align="center" class="style1 style3 style8">'||
                    vlocal||
                    '</div></td> '||
                    '</tr>';*/
      --Modificado por Dveliz 02.09.08
    /*  vResultado := '<table width="310" border="0">
                      <tr><td height="38" align="center" class="style3 style9">'|| vCod_trab ||'</td>
                          <td align="center" class="style3 style9">'||vFecha||'</td>
                          <td align="center" class="style3 style9">'||cNumPedVta_in||'</td>
                          <td align="center" class="style3 style9">'||vlocal||'</td>
                      </tr>
                    </table>';*/
      vResultado := '<table width="256" border="0">
        <tr><td height="38" align="center" class="style3 style9">'|| vCod_trab ||'</td>
            <td align="center" class="style3 style9">'||vFecha||'</td>
            <td align="center" class="style3 style9">'||cNumPedVta_in||'</td>
            <td align="center" class="style3 style9">'||vlocal||'</td>
        </tr>
      </table>';

   RETURN vResultado;

  END;
 /* ******************************************************************** */
  FUNCTION IMP_GET_CONSEJOS(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
                						cNumPedVta_in   	IN CHAR,
                            cLoginUsu_in     IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(13000):= '';
  vConsProd  varchar2(13000):= '';
  vSeparador varchar2(300):= '<br>';
    CURSOR curProds IS
    /*SELECT DISTINCT COD_PROD,DESC_PROD
    FROM (
    SELECT P.COD_PROD,P.DESC_PROD||' '||P.DESC_UNID_PRESENT AS DESC_PROD
    FROM TT_PROD_CONS_PED T,
         LGT_PROD P
    WHERE T.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND T.COD_PROD = P.COD_PROD
    ORDER BY T.ORD_PROD_CONS
    );*/
    SELECT COD_PROD,DESC_PROD
    FROM (
          SELECT P.COD_PROD,P.DESC_PROD/*||' '||P.DESC_UNID_PRESENT*/ AS DESC_PROD,min(T.ORD_PROD_CONS) ORD_PROD_CONS
          FROM   TT_PROD_CONS_PED T,
                 LGT_PROD P
          WHERE  T.COD_GRUPO_CIA = P.COD_GRUPO_CIA
            AND  T.COD_PROD = P.COD_PROD
          group by   P.COD_PROD,P.DESC_PROD,P.DESC_UNID_PRESENT
    )
    ORDER BY ORD_PROD_CONS;


    CURSOR curConsejo(cCodProd_in IN CHAR) IS
    SELECT C.COD_CONS, C.TEXTO_CONS
    FROM TT_PROD_CONS_PED T,
         VTA_CONSEJO C
    WHERE T.COD_PROD = cCodProd_in
          AND T.COD_CONS = C.COD_CONS
    ORDER BY T.ORD_PROD_CONS;

   vIndCreaConsejo char(1);
  BEGIN

   vIndCreaConsejo := IMP_CREA_CONSEJOS(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
   if  vIndCreaConsejo = 'N' then
       vResultado := 'N';
   elsif vIndCreaConsejo = 'S' then
   vSeparador := IMP_GET_SEPARADOR;
    FOR prod IN curProds
    LOOP
      vConsProd := '';
      /*
      vResultado := vResultado ||
                    '<tr><td colspan="4" align:"justify">
                    <span class="style3 style1 style9" style="font-weight:">
                    <strong>'||
                    prod.DESC_PROD
                    ||'</strong></span></td></tr>';*/
       --Modificado por Dveliz 02.09.08
       vResultado := vResultado ||
                    '<tr><td colspan="4" align="left"><span class="style12">
                    <strong>'||
                    prod.DESC_PROD
                    ||'</strong></span></td></tr>';
       FOR texto IN curConsejo(prod.COD_PROD)
       LOOP
       --AGREGANDO EL CONSEJO AL HTMl A GENERAR E IMPRIMIR
       vConsProd := vConsProd ||texto.TEXTO_CONS; --|| vSeparador;

       --INSERTANDO EL CONSEJO EN LA TABLA VTA_PEDIDO_CONSEJO
       INSERT INTO VTA_PEDIDO_CONSEJO (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_PROD,COD_CONS,USU_CREA_CONS,FEC_CREA_CONS)
       VALUES                         (cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, prod.COD_PROD, texto.cod_cons,cLoginUsu_in,sysdate );

       END LOOP;
        vResultado := vResultado ||
                      '<tr><td  height="114" colspan="4" class="style5">'
                      ||
                      vConsProd
                      ||'</td></tr>';



    END LOOP;

    -- commit para eliminar la tabla temporal
    commit;

    end if;
   RETURN vResultado;

  END;
 /* ******************************************************************** */
  FUNCTION IMP_PROCESA_CONSEJOS(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR,
                                cLoginUsu_in      IN CHAR,
                                --Se agrega COD_CIA para obetener ruta de la imagen
                                cCodCia_in in char
                							 )
  RETURN VARCHAR2
  IS
  vMsg_out varchar2(32767):= '';

  vFila_IMG_Cabecera_MF varchar2(2800):= '';
  vFila_IMG_Cabecera_Consejo varchar2(2800):= '';
  vFila_Cliente      varchar2(2800):= '';
  vFila_Num_Ped      varchar2(2800):= '';
  vFila_Msg_01       varchar2(2800):= '';
  vFila_Msg_02       varchar2(2800):= '';
  vFila_Pie_Pagina   varchar2(2800):= '';

  vFila_Consejos     varchar2(22767):= '';
    v_vCabecera1 VARCHAR2(500);
    v_vCabecera2 VARCHAR2(500);
  BEGIN

-- 2009-04-22 No se imprimirán consejos de ahora en adelante
   IF 'N' = 'S' THEN
--   IF IMP_GET_IND_IMP_CUPON(cCodGrupoCia_in,cCodLocal_in) = 'S' THEN

    v_vCabecera1 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_1;
    v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);

   if IMP_CONTIENE_CONSEJOS_PED(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in) = 'S' then
     -- FILA 01 : CABECERA CON IMAGEN
    vFila_IMG_Cabecera_Consejo:= '<tr> <td colspan="4"><div align="center" class="style8">'||
                         '<img src=file:'||
                         v_vCabecera1||
                         ' width="203" height="22" class="style3"></div></td>'||
                         ' </tr> ';

     vFila_IMG_Cabecera_MF:= '<tr> <td colspan="4">'||
                         '<img src=file:'||
                         v_vCabecera2||
                         ' width="300" height="90"></td>'||
                         '</tr> ';


     -- FILA 02 : NOMBRE DEL CLIENTE
     vFila_Cliente :=  '<tr><td colspan="4"><span class="style2 style3 style9" style="font-weight:">'||
                       '<strong>'||
                       IMP_GET_NAME_CLI(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in)||
                       '</strong></span></td>'||
                       '</tr>';


     -- FILA 03 : NUMERO DE PEDIDO
     vFila_Num_Ped := '<tr><td colspan="4"><div align="right" class="style1 style3 style8">'||
                      '<span class="style9"><strong>'||
                      'Nro:</strong> '||cNumPedVta_in|| '</span></div></td>'||
                      '</tr> ';

     -- FILA 04 : MENSAJE 01
     vFila_Msg_01 := '<tr><td colspan="4"  align="center" class="style1 style3 style9">
                     '||
                     IMP_GET_MSG_01_CONSEJO ||
                     '
                     </td> '||
                     '</tr>';

    -- FILA 05 : MENSAJE 02
     vFila_Msg_02 := '<tr><td colspan="4"  align="center" class="style3">
                     '||
                     PTOVENTA_IMP_CUPON.IMP_GET_MSG_02_CONSEJO(cCodGrupoCia_in,cCodCia_in,cCodLocal_in) ||
                     '</td> '||
                     '</tr>';

     --Nombre y consejo del producto
     vFila_Consejos   := --'<tr><td colspan="4" align="left"><span class="style12"><strong>APROFORTE 550 MG</strong></span></td></tr><tr><td  height="114" colspan="4" class="style5">EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE,hágalo teniendo la precaución de obtener mitades de tamaño similar. Si el comprimido o tableta es ranurado, debe partirlo usando la ranura central. El medicamento no perderá efecto si lo hace.  El Médico es el único autorizado para recetar medicamentos.</td></tr>';
                       IMP_GET_CONSEJOS(cCodGrupoCia_in ,cCodLocal_in,cNumPedVta_in,cLoginUsu_in);

     --Eslogan de Mifarma
     vFila_Pie_Pagina := IMP_GET_PIE_PAGINA(cCodGrupoCia_in ,cCodLocal_in,cNumPedVta_in);

     if vFila_Consejos != 'N' then
     vMsg_out := C_INICIO_MSG ||
                 vFila_IMG_Cabecera_Consejo  ||
                 vFila_IMG_Cabecera_MF  ||
                 --27.08.2008 DUBILLUZ MODIFICACION
                 --vFila_Cliente || vFila_Num_Ped || vFila_Msg_01 ||
                  /*C_FILA_VACIA ||*/ vFila_Consejos || vFila_Msg_02 || --vFila_Pie_Pagina ||

                  '<tr><td colspan="4" class="style2">'||
                  'Esta información no constituye publicidad de ningún medicamento en particular.Respete siempre las indicaciones de su Médico.'
                  ||'</td>'||
                  '</tr>
                  </table>' ||

                   vFila_Pie_Pagina
                  --------------
                  ||  C_FIN_MSG ;

    elsif vFila_Consejos = 'N' then
          vMsg_out := 'N';
    end if;

   else
      vMsg_out := 'N';
   end if;

   ELSE
      vMsg_out := 'N';
   end if;
     RETURN vMsg_out;

  END;

/* *************************************** */
  FUNCTION IMP_CONTIENE_CONSEJOS_PED(cCodGrupoCia_in 	IN CHAR,
                                     cCodLocal_in    	IN CHAR,
                        						 cNumPedVta_in   	IN CHAR)
  RETURN CHAR
  IS
    vCount number;
  BEGIN
     begin
       select count(1)
       into   vCount
       from   vta_pedido_vta_det d,
              lgt_consejo_producto c
       where  d.cod_grupo_cia = c.cod_grupo_cia
       and    d.cod_prod = c.cod_prod
       and    d.cod_grupo_cia = cCodGrupoCia_in
       and    d.cod_local = cCodLocal_in
       and    d.num_ped_vta =  cNumPedVta_in;
     exception
     when no_data_found then
       vCount := 0;
     end;

     if vCount = 0 then
     return 'N';
     end if;

     return 'S';

  END;

  /* ****************************************************************** */
    FUNCTION IMP_CREA_CONSEJOS(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
                						cNumPedVta_in   	IN CHAR)
    RETURN CHAR
    IS
  CURSOR cur IS
  select a.ord,a.cod_prod,(select count(1)
                           from   LGT_CONSEJO_PRODUCTO c1
                           where  c1.cod_grupo_cia=a.cod_grupo_cia
                           and    c1.cod_prod     =a.cod_prod) cantidad
  from
  (
    SELECT d.cod_grupo_cia,min(DECODE(C.TIP_CONS,'F',1,DECODE(P.IND_PROD_FARMA,'S',2,3))) ORD,
    D.COD_PROD
    FROM   LGT_PROD P,
           VTA_PEDIDO_VTA_DET D,
           LGT_CONSEJO_PRODUCTO C
    WHERE  P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
    AND    P.COD_PROD = D.COD_PROD
    AND    C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
    AND    C.COD_PROD = D.COD_PROD
    AND    D.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    D.COD_LOCAL = cCodLocal_in
    AND    D.NUM_PED_VTA = cNumPedVta_in
    group by d.cod_grupo_cia,d.cod_prod
  ) a
  ORDER BY 1 ASC;

  v_cCantCons INTEGER;
  v_cCantConsProd INTEGER;
  v_cCantConsVar INTEGER ;--maximo consejos por producto
  v_cCantProdVar INTEGER ;--maximo productos por pedido

  v_cAuxContCons INTEGER;
  v_cAuxCantCons INTEGER;
  v_cAuxCantProd INTEGER;

  v_Aux0 INTEGER;
  v_Aux1 INTEGER;
  v_Aux2 LGT_CONSEJO_PRODUCTO.COD_CONS%TYPE;

  v_cContConsPed INTEGER := 0;
BEGIN
  v_cCantConsVar := IMP_GET_MAX_CONS_X_PROD;
  v_cCantProdVar := IMP_GET_MAX_PROD_X_PED;

  v_cAuxCantProd := 0;

  IF v_cCantProdVar = 0 THEN
     RETURN 'N';
  ELSIF v_cCantProdVar > 0 THEN

  FOR fila IN cur
  LOOP
    DBMS_OUTPUT.PUT_LINE('v_cAuxCantProd: '||v_cAuxCantProd);
    EXIT WHEN v_cAuxCantProd = v_cCantProdVar;

    DBMS_OUTPUT.put_line('PROD: '||fila.COD_PROD);
    v_cCantCons := 0;

    v_cAuxContCons := 1;
    v_cCantConsProd := fila.cantidad;

    SELECT CASE WHEN v_cCantConsProd < v_cCantConsVar THEN v_cCantConsProd
                ELSE v_cCantConsVar END
           INTO v_cAuxCantCons
    FROM DUAL;
    DBMS_OUTPUT.PUT_LINE('v_cAuxCantCons: '||v_cAuxCantCons);

    WHILE v_cCantCons < v_cAuxCantCons
    LOOP
      DBMS_OUTPUT.PUT_LINE('v_cAuxContCons: '||v_cAuxContCons);
      IF v_cAuxContCons = 1 THEN --INSERTA CONSEJO FIJO
        DBMS_OUTPUT.PUT_LINE('1');
        BEGIN
          SELECT COUNT(*)
                 INTO v_Aux0
          FROM LGT_CONSEJO_PRODUCTO
          WHERE COD_PROD = fila.COD_PROD
                AND TIP_CONS = 'F'
                AND (COD_GRUPO_CIA,COD_PROD,COD_CONS) NOT IN (SELECT COD_GRUPO_CIA,COD_PROD,COD_CONS FROM TT_PROD_CONS_PED);

          IF v_Aux0 > 0 THEN
            SELECT ROUND(DBMS_RANDOM.value(1,v_Aux0))
                   INTO v_Aux1
            FROM DUAL;

            SELECT COD_CONS
                   INTO v_Aux2
            FROM (
              SELECT COD_CONS,ROW_NUMBER()OVER(ORDER BY COD_CONS) AS ORD
              FROM LGT_CONSEJO_PRODUCTO
              WHERE COD_PROD = fila.COD_PROD
                    AND TIP_CONS = 'F'
                    AND (COD_GRUPO_CIA,COD_PROD,COD_CONS) NOT IN (SELECT COD_GRUPO_CIA,COD_PROD,COD_CONS FROM TT_PROD_CONS_PED)
            )
            WHERE ORD = v_Aux1;
            DBMS_OUTPUT.PUT_LINE('COD_CONS:'||v_Aux2);
            v_cContConsPed := v_cContConsPed+1;
            INSERT INTO TT_PROD_CONS_PED(COD_GRUPO_CIA,COD_PROD,COD_CONS,Ord_Prod_Cons)
            VALUES('001',fila.COD_PROD,v_Aux2,v_cContConsPed);

            v_cCantCons := v_cCantCons+1;
          END IF;
        END;
        v_cAuxContCons := v_cAuxContCons+1;
      ELSIF v_cAuxContCons = 2 THEN --INSERTA CONSEJO VARIABLE
        DBMS_OUTPUT.PUT_LINE('2');
        BEGIN
          SELECT COUNT(*)
                 INTO v_Aux0
          FROM LGT_CONSEJO_PRODUCTO
          WHERE COD_PROD = fila.COD_PROD
                AND TIP_CONS = 'V'
                AND (COD_GRUPO_CIA,COD_PROD,COD_CONS) NOT IN (SELECT COD_GRUPO_CIA,COD_PROD,COD_CONS FROM TT_PROD_CONS_PED);

          IF v_Aux0 > 0 THEN
            SELECT ROUND(DBMS_RANDOM.value(1,v_Aux0))
                   INTO v_Aux1
            FROM DUAL;

            SELECT COD_CONS
                   INTO v_Aux2
            FROM (
              SELECT COD_CONS,ROW_NUMBER()OVER(ORDER BY COD_CONS) AS ORD
              FROM LGT_CONSEJO_PRODUCTO
              WHERE COD_PROD = fila.COD_PROD
                    AND TIP_CONS = 'V'
                    AND (COD_GRUPO_CIA,COD_PROD,COD_CONS) NOT IN (SELECT COD_GRUPO_CIA,COD_PROD,COD_CONS FROM TT_PROD_CONS_PED)
            )
            WHERE ORD = v_Aux1;
            DBMS_OUTPUT.PUT_LINE('COD_CONS:'||v_Aux2);
            v_cContConsPed := v_cContConsPed+1;
            INSERT INTO TT_PROD_CONS_PED(COD_GRUPO_CIA,COD_PROD,COD_CONS,Ord_Prod_Cons)
            VALUES('001',fila.COD_PROD,v_Aux2,v_cContConsPed);

            v_cCantCons := v_cCantCons+1;
          END IF;
        END;
        v_cAuxContCons := v_cAuxContCons+1;
      ELSE --INSERTA CONSEJO ALEATORIO
        DBMS_OUTPUT.PUT_LINE('3');
        BEGIN
          SELECT COUNT(*)
                 INTO v_Aux0
          FROM LGT_CONSEJO_PRODUCTO
          WHERE COD_PROD = fila.COD_PROD
                --AND TIP_CONS = 'F'
                AND (COD_GRUPO_CIA,COD_PROD,COD_CONS) NOT IN (SELECT COD_GRUPO_CIA,COD_PROD,COD_CONS FROM TT_PROD_CONS_PED);

          IF v_Aux0 > 0 THEN
            SELECT ROUND(DBMS_RANDOM.value(1,v_Aux0))
                   INTO v_Aux1
            FROM DUAL;

            SELECT COD_CONS
                   INTO v_Aux2
            FROM (
              SELECT COD_CONS,ROW_NUMBER()OVER(ORDER BY COD_CONS) AS ORD
              FROM LGT_CONSEJO_PRODUCTO
              WHERE COD_PROD = fila.COD_PROD
                    --AND TIP_CONS = 'F'
                    AND (COD_PROD,COD_CONS) NOT IN (SELECT COD_PROD,COD_CONS FROM TT_PROD_CONS_PED)
            )
            WHERE ORD = v_Aux1;
            DBMS_OUTPUT.PUT_LINE('COD_CONS:'||v_Aux2);
            v_cContConsPed := v_cContConsPed+1;
            INSERT INTO TT_PROD_CONS_PED(COD_GRUPO_CIA,COD_PROD,COD_CONS,Ord_Prod_Cons)
            VALUES('001',fila.COD_PROD,v_Aux2,v_cContConsPed);

            v_cCantCons := v_cCantCons+1;
          END IF;
        END;
        v_cAuxContCons := v_cAuxContCons+1;
      END IF;
    END LOOP;

    v_cAuxCantProd := v_cAuxCantProd+1;
  END LOOP;

   RETURN 'S';

  END IF;
END;
/* ***************************************************************************** */
  FUNCTION IMP_GET_MAX_PROD_X_PED
  RETURN INTEGER
  IS
  vResultado INTEGER;
  BEGIN
      BEGIN
      SELECT TO_NUMBER(TRIM(T.LLAVE_TAB_GRAL),'9999')
      INTO   vResultado
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'MAX_PROD_X_PED'
      AND    T.ID_TAB_GRAL = 201;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := 2;
      END;
   RETURN vResultado;
   END;
/* ***************************************************************************** */
  FUNCTION IMP_GET_MAX_CONS_X_PROD
  RETURN INTEGER
  IS
  vResultado INTEGER;
  BEGIN
      BEGIN
      SELECT TO_NUMBER(TRIM(T.LLAVE_TAB_GRAL),'9999')
      INTO   vResultado
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'MAX_CONS_X_PROD'
      AND    T.ID_TAB_GRAL = 202;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := 2;
      END;
   RETURN vResultado;
   END;
/* ************************************************************************ */
  FUNCTION IMP_GET_IND_IMP_CUPON(cCodGrupoCia_in 	IN CHAR,
                                 cCodLocal_in    	IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(14000):= '';
  BEGIN
      BEGIN
      SELECT L.IND_IMP_CONSEJO
      INTO   vResultado
      FROM   PBL_LOCAL L
      WHERE  L.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    L.COD_LOCAL = cCodLocal_in;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := 'N';
      END;
   RETURN vResultado;
   END;
/* ************************************************************************ */
  FUNCTION IMP_GET_NAME_IMP_CONSEJO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN VARCHAR2
  IS
     vResultado varchar2(14000):= '';
     vIP VARCHAR2(15); --JCG 26.06.2009.n
    BEGIN
    --JCG 26.06.2009.sn
    SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') INTO vIP FROM DUAL;

    SELECT A.DESC_IMPR_LOCAL_TERM
      INTO vResultado
      FROM VTA_IMPR_LOCAL_TERMICA A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in
       AND A.SEC_IMPR_LOC_TERM IN
           (SELECT B.SEC_IMPR_LOC_TERM
              FROM VTA_IMPR_IP B
             WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
               AND B.COD_LOCAL = cCodLocal_in
               AND B.IP = vIP);
    RETURN vResultado;
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
    --vResultado := 'CONSEJO1';
    SELECT TRIM(T.LLAVE_TAB_GRAL)
    INTO   vResultado
    FROM   PBL_TAB_GRAL T
    WHERE  T.COD_APL = 'PTO_VENTA'
    AND    T.COD_TAB_GRAL = 'NAME_IMP_CONSEJO'
    AND    T.ID_TAB_GRAL = 204;

    RETURN vResultado;

    END;

      --JCG 26.06.2009.en
      --JCG 26.06.2009.cn
   /*   BEGIN
      SELECT TRIM(T.LLAVE_TAB_GRAL)
      INTO   vResultado
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'NAME_IMP_CONSEJO'
      AND    T.ID_TAB_GRAL = 204;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := '';
      END;*/
      --JCG 26.06.2009.en


 FUNCTION IMP_GET_NAME_IMP_STICKER(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
  RETURN VARCHAR2
  IS
     vResultado varchar2(14000):= '';
     vIP VARCHAR2(15);
    BEGIN

    --SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') INTO vIP FROM DUAL;

    SELECT NVL(A.RUTA_STICKER,'N')
      INTO vResultado
      FROM PBL_LOCAL A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal_in;

    RETURN vResultado;

    END;
 
 /************************************************************/
 /**  Descripcion  :Obtiene la cantidad de                  **/
 /**    impresiones que se tiene que efectuar antes         **/
 /**    de mostrar mensaje de confirmacion                  **/
 /**  Fecha        :22.09.2015                              **/
 /**  Usuario      :Desarrollo5                             **/
 /**  Parametros   :                                        **/
 /**  Retorno      :Varchar2                                **/
 /************************************************************/   
 FUNCTION IMP_CANTIDAD_IMPRESIONES RETURN VARCHAR2
 IS
   vCantidadImpresiones VARCHAR2(500);
   BEGIN
     SELECT LLAVE_TAB_GRAL
       INTO vCantidadImpresiones
       FROM PBL_TAB_GRAL
      WHERE ID_TAB_GRAL = '557'
        AND COD_TAB_GRAL = 'CONT_IMPRESIONES';
     
     RETURN vCantidadImpresiones;
 END;

END;
/
