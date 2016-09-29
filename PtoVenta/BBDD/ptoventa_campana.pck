CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CAMPANA" AS

 TYPE FarmaCursor IS REF CURSOR;
 C_INDICADOR_NO CHAR(1) := 'N';
 C_INDICADOR_SI CHAR(1) := 'S';
 C_ESTADO_ACTIVO CHAR(1) := 'A';
 C_TITULAR CHAR(1) := 'T';

  C_SIZE_CONSEJO   VARCHAR2(200) := '18';
  C_SIZE_MSG_FINAL VARCHAR2(200) := '10';


 /*C_INICIO_MSG  VARCHAR2(2000) := '
                                  <html>
                                  <head>
                                  </head>
                                  <body>
                                  <table width="337" border="0">
                                  <tr>
                                   <td width="8">&nbsp;&nbsp;</td>
                                   <td width="319"><table width="316" height="293" border="0">
                                '; */

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
                                '<td height="13" colspan="3"></td> '||
                                ' </tr> ';

C_FIN_MSG     VARCHAR2(2000) := ' </table></td>
                                      </tr>
                                    </table>
                                    <p><br>
                                      <br>
                                    </p>
                                    </body>
                                    </html> ';


  /*C_FIN_MSG     VARCHAR2(2000) := '
                                 </table></td>
                                 </tr>
                                 </table>
                                 </body>
                                 </html>'; */

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario    Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_SEPARADOR
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 01 de consejo
 --Fecha       Usuario    Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_MSG_01_CONSEJO
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario    Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_PIE_PAGINA(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumPedVta_in     IN CHAR)
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario    Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION CAMP_F_VAR_MSJ_CAMPANA(cCodGrupoCia_in   IN CHAR,
                            cCodLocal_in      IN CHAR,
                            cNumPedVta_in     IN VARCHAR2
                           )
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario    Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_CUPONES_PEDIDO(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in      IN CHAR,
                                 cNumPedVta_in     IN CHAR)
 RETURN FARMACURSOR;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario    Comentario
 --09/05/2008  DUBILLUZ  Creación
 PROCEDURE IMP_UPDATE_IND_IMP(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                               cNumPedVta_in   IN CHAR,
                              cCodCupon       IN CHAR);


 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario    Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_MONTO_PORCENTAJE(cCodGrupoCia_in   IN CHAR,
                                   cCodCapana_in    IN CHAR
                                   )
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario    Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_MSG_CAMP_PROD(cCodGrupoCia_in IN CHAR,
                                cCodCapana_in   IN CHAR
                                )
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje de la campaña
 --Fecha       Usuario    Comentario
 --15/07/2008  JCORTEZ  Creación
 FUNCTION IMP_GET_MSG_03_CONSEJO(cCodGrupoCia_in   IN CHAR,
                                 CodCamp_in      IN CHAR)
 RETURN VARCHAR2;

 --Descripcion: obtiene fecha de vigencia del cupon
 --Fecha       Usuario    Comentario
 --30/07/2008  JCORTEZ  Creación
 FUNCTION IMP_GET_MSG_04_CONSEJO(cCodGrupoCia_in   IN CHAR,
                                  CodCamp_in        IN CHAR,
                                  CodCupon_in       IN CHAR)
 RETURN VARCHAR2;

  --Descripcion: Calcula EAN13
  --Fecha       Usuario    Comentario
  --23/05/2008  DUBILLUZ  Creacion
  FUNCTION GENERA_EAN13(vCodigo_in IN VARCHAR2)
  RETURN CHAR;


 FUNCTION IMP_GET_TIME_CAN_READ RETURN VARCHAR2;

  --Descipción: obtiene descuento
 FUNCTION GET_NUM_DSCTO_PROD_USO_CAMP (cCod_Grupo_Cia_in IN CHAR,
                                   cCod_Local_in IN CHAR,
                                   cCod_Camp_Cupon_in IN CHAR,
                                   cCod_Prod IN CHAR,
                                   cNumDocId_in in varchar2 default 'N')
 RETURN NUMBER;

END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CAMPANA" AS

  FUNCTION IMP_GET_SEPARADOR
  RETURN VARCHAR2
  IS
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
  /* ***************************************************************** */
  -- Retorna el mensaje de la campana que se imprimira el cupon
  FUNCTION IMP_GET_MSG_03_CONSEJO(cCodGrupoCia_in   IN CHAR,
                                  CodCamp_in        IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(13000):= '';
  BEGIN
      BEGIN
      SELECT TRIM(X.MENSAJE_CAMP)
      INTO   vResultado
      FROM   VTA_CAMPANA_CUPON X
      WHERE  X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_CAMP_CUPON=CodCamp_in;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := '';
      END;

   RETURN vResultado;
  END;

   /* ***************************************************************** */
  FUNCTION IMP_GET_MSG_04_CONSEJO(cCodGrupoCia_in   IN CHAR,
                                  CodCamp_in        IN CHAR,
                                  CodCupon_in       IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(13000):= '';
  BEGIN
      BEGIN
      SELECT 'Valido del '||TRUNC(X.FEC_INI)||' al '||TRUNC(X.FEC_FIN)
      INTO   vResultado
      FROM   VTA_CUPON X
      WHERE  X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_CAMPANA=CodCamp_in
      AND X.COD_CUPON=CodCupon_in;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := '';
      END;

   RETURN vResultado;
  END;
 /* ******************************************************************** */
  FUNCTION IMP_GET_PIE_PAGINA(cCodGrupoCia_in   IN CHAR,
                             cCodLocal_in      IN CHAR,
                             cNumPedVta_in     IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(23000):= '';
  vCod_trab varchar2(100):= '';
  vFecha    varchar2(100):= '';
  vlocal    varchar2(100):= '';
  BEGIN
      BEGIN
          SELECT NVL(U.COD_TRAB_RRHH,U.COD_TRAB),
                 TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY HH24:MI:SS'),
                 L.COD_LOCAL || '-'||L.DESC_ABREV
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


      vResultado := ' <tr> '||
                    '<td width="50" height="38"><div align="center" class="style1 style3 style8">'||
                    vCod_trab ||
                    '</div></td> '||
                    '<td width="160"><div align="center" class="style1 style3 style8"> '||
                    '<div align="center">'||
                    vFecha||
                    '</div> '||
                    '</div></td> '||
                    '<td width="92"> '||
                    '<div align="center" class="style1 style3 style8">'||
                    vlocal||
                    '</div></td> '||
                    '</tr>';
   RETURN vResultado;

  END;
 /* ******************************************************************** */

 FUNCTION CAMP_F_VAR_MSJ_CAMPANA(cCodGrupoCia_in   IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cNumPedVta_in     IN VARCHAR2 )
  RETURN VARCHAR2
  IS
  vMsg_out varchar2(32767):= 'S';
  vFila_MsgProd   varchar2(2800);
  vNom_Cli       varchar2(2800);
  vExiste        char(1):= 'S';
  vMensajeCamp     varchar2(13000);

  vDNI_CLI_in  VARCHAR2(20);
   CURSOR curTarjeta(vDni varchar2) IS
          select TRIM(cod_tarjeta) TARJ
          from fid_tarjeta
          where dni_cli = vDni;/* in
                       (SELECT DNI_CLI
                        FROM FID_TARJETA_PEDIDo
                        WHERE  COD_GRUPO_CIA =cCodGrupoCia_in
                        AND    COD_LOCAL = cCodLocal_in
                        AND    NUM_PEDIDO = cNumPedVta_in)*/

         curTarjeta_REC curTarjeta%ROWTYPE;

   CURSOR curCampana (COD_TARJETA  in char) IS
      SELECT mc.COD_CAMP_CUPON,Tarjeta_Ini,Tarjeta_Fin
      FROM   vta_camp_x_tarjeta ct,
             vta_campana_cupon mc
      WHERE mc.COD_GRUPO_CIA=cCodGrupoCia_in
      and   mc.cod_grupo_cia = ct.cod_grupo_cia
      and   mc.cod_camp_cupon = ct.cod_camp_cupon
      and   mc.estado = 'A'
      and   mc.fech_fin_uso >= trunc(sysdate)
      AND COD_TARJETA between Tarjeta_Ini  and  Tarjeta_Fin
      AND  ROWNUM = 1;--SOLO SE IMPRIMIRA UNA CAMPAÑA

         curCampana_REC curCampana%ROWTYPE;


  BEGIN

     BEGIN
     SELECT 'S' , C.NOM_CLI ||' '|| C.APE_PAT_CLI||' '|| C.APE_MAT_CLI,t.dni_cli
     into   vExiste, vNom_Cli,vDNI_CLI_in
     FROM
     FID_TARJETA_PEDIDO T,
     pbl_cliente C
     WHERE
     T.DNI_CLI=C.DNI_CLI
     AND    T.COD_GRUPO_CIA =  cCodGrupoCia_in
     AND    T.COD_LOCAL = cCodLocal_in
     AND    T.NUM_PEDIDO =cNumPedVta_in;
     exception
     when no_data_found then
          vExiste := 'N';
     end;


     if vExiste = 'S' then
     vNom_Cli:=  ' Sr. '   ||  vNom_Cli ;

     FOR curTarjeta_REC IN curTarjeta(vDNI_CLI_in)
     LOOP


      FOR curCampana_REC  IN curCampana (curTarjeta_REC.TARJ)
      LOOP
                --     dbms_output.put_line ('     aqui 1.4   ');
                     SELECT mensaje_camp
                     INTO    vMensajeCamp
                     FROM   VTA_CAMPANA_CUPON
                     WHERE COD_GRUPO_CIA= cCodGrupoCia_in
                     AND COD_CAMP_CUPON = curCampana_REC.Cod_Camp_Cupon;
      END
      LOOP;

     END
     LOOP;
     IF Length(TRIM(vMensajeCamp)) > 0 THEN
     --dbms_output.put_line ('Campañas por Tarjeta:'||vMensajeCamp );
     --dbms_output.put_line ('Campañas por Tarjeta:'||vNom_Cli );
     vFila_MsgProd :=vFila_MsgProd ||  '
               <tr>
                <td height="30" colspan="3"  align="lef" style="font:Arial, Helvetica, sans-serif"><B>'
                ||vMensajeCamp||'</B></td> </tr>';


                vMsg_out := C_INICIO_MSG || vNom_Cli ||
                  C_FILA_VACIA ||
                  vFila_MsgProd ||
                  '<tr>
                  <td height="21" colspan="3" align="left"
                          style="font:Arial, Helvetica, sans-serif">
                          "En MIifarma nos preocupamos por su salud"
                  </td>
                  </tr>' ||
                 C_FIN_MSG ;
          ELSE
          vMsg_out := 'N';
          END IF;
     else
          vMsg_out := 'N';

     end if;

     RETURN vMsg_out;

  END;

 /* ************************************************************************ */

 FUNCTION IMP_GET_CUPONES_PEDIDO(cCodGrupoCia_in   IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cNumPedVta_in     IN CHAR)
  RETURN FARMACURSOR
  IS
  vCursor FARMACURSOR;

  BEGIN

      /*SELECT C.COD_CAMP_CUPON,d.ind_multiuso INTO cCodCamp,cIndMultiUso
       FROM   VTA_CAMP_PEDIDO_CUPON C,
              VTA_CAMPANA_CUPON D
       WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    C.COD_LOCAL = cCodLocal_in
       AND    C.NUM_PED_VTA = cNumPedVta_in
       AND    C.ESTADO = 'E'
       AND   C.COD_GRUPO_CIA=D.COD_GRUPO_CIA
       AND   C.COD_CAMP_CUPON=D.COD_CAMP_CUPON;*/


      /*SELECT C.COD_CUPON
       FROM   VTA_CAMP_PEDIDO_CUPON C
       WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    C.COD_LOCAL = cCodLocal_in
       AND    C.NUM_PED_VTA = cNumPedVta_in
       AND    C.ESTADO = 'E';*/

      /* open vCursor for
       SELECT C.COD_CUPON,D.CANTIDAD,D.COD_CAMP_CUPON
       FROM   VTA_CAMP_PEDIDO_CUPON C,
              VTA_PEDIDO_CUPON D
       WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    C.COD_LOCAL = cCodLocal_in
       AND    C.NUM_PED_VTA = cNumPedVta_in
       AND    C.ESTADO = 'E'
       AND    C.COD_GRUPO_CIA=D.COD_GRUPO_CIA
       AND    C.NUM_PED_VTA=D.NUM_PED_VTA
       AND    C.COD_LOCAL=D.COD_LOCAL;*/

       --JCORTEZ 18.08.08
       open vCursor for
       SELECT C.COD_CUPON
       FROM   VTA_CAMP_PEDIDO_CUPON C,
              VTA_CAMPANA_CUPON D
       WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    C.COD_LOCAL = cCodLocal_in
       AND    C.NUM_PED_VTA = cNumPedVta_in
       AND    C.ESTADO = 'E'
       AND    D.IND_MULTIUSO='N'
       AND   C.COD_GRUPO_CIA=D.COD_GRUPO_CIA
       AND   C.COD_CAMP_CUPON=D.COD_CAMP_CUPON
       UNION ALL
       SELECT C.COD_CUPON
       FROM   VTA_CAMP_PEDIDO_CUPON C,
              VTA_CAMPANA_CUPON D,
              VTA_PEDIDO_CUPON E,
              LGT_PROD PVT--table pivot
       WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    C.COD_LOCAL = cCodLocal_in
       AND    C.NUM_PED_VTA = cNumPedVta_in
       AND    C.ESTADO = 'E'
       AND    D.IND_MULTIUSO='S'
       AND   C.COD_GRUPO_CIA=D.COD_GRUPO_CIA
       AND   C.COD_CAMP_CUPON=D.COD_CAMP_CUPON
       AND   C.NUM_PED_VTA=E.NUM_PED_VTA
       AND   ROWNUM<=E.CANTIDAD;

  return  vCursor ;

  END;

/* ********************************************************************* */
  PROCEDURE IMP_UPDATE_IND_IMP(cCodGrupoCia_in   IN CHAR,
                               cCodLocal_in      IN CHAR,
                               cNumPedVta_in     IN CHAR,
                               cCodCupon        IN CHAR)
  IS
  PRAGMA AUTONOMOUS_TRANSACTION;
  BEGIN
      UPDATE VTA_CAMP_PEDIDO_CUPON C
      SET    C.IND_IMPR = 'S',
             C.FEC_MOD_CUPON_PED = SYSDATE
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.COD_LOCAL     = cCodLocal_in
      AND    C.NUM_PED_VTA   = cNumPedVta_in
      AND    C.COD_CUPON     = cCodCupon
      AND    C.ESTADO = 'E';

      COMMIT;
 END ;

 /* *********************************************************************** */
 FUNCTION IMP_GET_MONTO_PORCENTAJE(cCodGrupoCia_in   IN CHAR,
                                   cCodCapana_in    IN CHAR
                                  )
 RETURN VARCHAR2
 IS
 vMensajePorcMonto varchar2(2000);
 nMonto number;
 vTipoCupon char(1);
 BEGIN

             SELECT C.VALOR_CUPON,C.TIP_CUPON
             INTO   nMonto,vTipoCupon
             FROM   VTA_CAMPANA_CUPON C
             WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
             AND    C.COD_CAMP_CUPON = cCodCapana_in;

             if vTipoCupon = 'P' then
                vMensajePorcMonto := nMonto || '% descuento';
             elsif vTipoCupon = 'M' then
                vMensajePorcMonto := 'S/.'||nMonto || ' descuento';
             end if;
    return vMensajePorcMonto;
 END;

/* *********************************************************************** */
 FUNCTION IMP_GET_MSG_CAMP_PROD(cCodGrupoCia_in   IN CHAR,
                                   cCodCapana_in    IN CHAR
                                  )
 RETURN VARCHAR2
 IS
 vMensajePorcMonto varchar2(2000);
 nCantidad number;
 vDescCampana varchar2(3000);
 vDescProd  varchar2(3000);
 BEGIN

             SELECT count(c.cod_prod)
             INTO   nCantidad
             FROM   vta_campana_prod_uso c
             WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
             AND    C.COD_CAMP_CUPON = cCodCapana_in;

             if nCantidad = 1 then

             SELECT nvl(p.desc_prod || ' ' || p.desc_unid_present,' ')
             INTO   vDescProd
             FROM   vta_campana_prod_uso c,
                    lgt_prod p
             WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
             AND    C.COD_CAMP_CUPON = cCodCapana_in
             and    c.cod_grupo_cia = p.cod_grupo_cia
             and    c.cod_prod = p.cod_prod;

                vMensajePorcMonto := 'En el producto: ' ||vDescProd;

               if nCantidad = 0 then
                  vMensajePorcMonto := ' ';
               end if;

             elsif nCantidad > 1 then

             SELECT nvl(c.desc_cupon,' ')
             INTO   vDescCampana
             FROM   vta_campana_cupon c
             WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
             AND    C.COD_CAMP_CUPON = cCodCapana_in;

                vMensajePorcMonto := ' En los productos de la campaña ' || vDescCampana;
             end if;
    return vMensajePorcMonto;
 END;
  /***************************************************************************/
  FUNCTION GENERA_EAN13(vCodigo_in IN VARCHAR2)
  RETURN CHAR
  IS
    cCodEan13 CHAR(13);
    vDig1 NUMBER(1);
    vDig2 NUMBER(1);
    vDig3 NUMBER(1);
    vDig4 NUMBER(1);
    vDig5 NUMBER(1);
    vDig6 NUMBER(1);
    vDig7 NUMBER(1);
    vDig8 NUMBER(1);
    vDig9 NUMBER(1);
    vDig10 NUMBER(1);
    vDig11 NUMBER(1);
    vDig12 NUMBER(1);
    vDig13 NUMBER(1);

    vSumImp NUMBER;
    vSumPar NUMBER;
    vAux NUMBER;
    vAux2 NUMBER;
  BEGIN
    vDig1 := TO_NUMBER(SUBSTR(vCodigo_in,1,1));
    vDig2 := TO_NUMBER(SUBSTR(vCodigo_in,2,1));
    vDig3 := TO_NUMBER(SUBSTR(vCodigo_in,3,1));
    vDig4 := TO_NUMBER(SUBSTR(vCodigo_in,4,1));
    vDig5 := TO_NUMBER(SUBSTR(vCodigo_in,5,1));
    vDig6 := TO_NUMBER(SUBSTR(vCodigo_in,6,1));
    vDig7 := TO_NUMBER(SUBSTR(vCodigo_in,7,1));
    vDig8 := TO_NUMBER(SUBSTR(vCodigo_in,8,1));
    vDig9 := TO_NUMBER(SUBSTR(vCodigo_in,9,1));
    vDig10 := TO_NUMBER(SUBSTR(vCodigo_in,10,1));
    vDig11 := TO_NUMBER(SUBSTR(vCodigo_in,11,1));
    vDig12 := TO_NUMBER(SUBSTR(vCodigo_in,12,1));

    vSumImp := (vDig12+vDig10+vDig8+vDig6+vDig4+vDig2)*3;
    vSumPar := vDig11+vDig9+vDig7+vDig5+vDig3+vDig1;

    vAux := vSumImp+vSumPar;
    vAux2 := MOD(vAux,10);
    --RAISE_APPLICATION_ERROR(-20005,vCodigo_in||'*'||vAux||'/'||vAux2);
    IF vAux2 = 0 THEN
      vDig13 := 0;
    ELSE
      vDig13 := 10 - vAux2;
    END IF;

    cCodEan13 := vCodigo_in||vDig13;

    RETURN cCodEan13;
  END;
  /***************************************************************************/
 FUNCTION IMP_GET_TIME_CAN_READ RETURN VARCHAR2 IS
   vTime varchar2(200);
 BEGIN

   begin
     select NVL(t.llave_tab_gral,'1')
       into vTime
       from pbl_tab_gral t
      where t.id_tab_gral = 255;

   exception
     when no_data_found then
       vTime := '1';
   end;
   return vTime;
 ENd;
  /***************************************************************************/

 FUNCTION GET_NUM_DSCTO_PROD_USO_CAMP (cCod_Grupo_Cia_in IN CHAR,
                                   cCod_Local_in IN CHAR,
                                   cCod_Camp_Cupon_in IN CHAR,
                                   cCod_Prod IN CHAR,
                                   cNumDocId_in in varchar2 default 'N')
 RETURN NUMBER IS
    vValor_Cupon NUMBER(8,3) := 0;
    vIndDocLocked_in   char(1) := PTOVENTA_FIDELIZACION.F_IS_DOC_LOCKED(cNumDocId_in);
 BEGIN
   
    SELECT 
    decode(vIndDocLocked_in,'S',nvl(nvl(U.LOCKED_VALOR_CUPON,C.LOCKED_VALOR_CUPON),NVL(U.VALOR_CUPON_PROD,C.VALOR_CUPON)),
          NVL(U.VALOR_CUPON_PROD,C.VALOR_CUPON)) INTO vValor_Cupon
      FROM VTA_CAMPANA_CUPON C, VTA_CAMPANA_PROD_USO U
     WHERE C.COD_GRUPO_CIA = cCod_Grupo_Cia_in
       AND C.COD_CAMP_CUPON = cCod_Camp_Cupon_in
       AND C.COD_GRUPO_CIA = U.COD_GRUPO_CIA
       AND NVL(C.COD_CAMP_CUPON_PROD_USO, C.COD_CAMP_CUPON) = U.COD_CAMP_CUPON--[Desarrollo5] 15.10.2015
       AND U.COD_PROD = cCod_Prod;
   
   RETURN vValor_Cupon;
 END;

  /***************************************************************************/

END;
/
