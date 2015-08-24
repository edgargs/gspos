CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_IMP_CUPON" AS

 TYPE FarmaCursor IS REF CURSOR;
 C_INDICADOR_NO CHAR(1) := 'N';
 C_INDICADOR_SI CHAR(1) := 'S';
 C_ESTADO_ACTIVO CHAR(1) := 'A';
 C_TITULAR CHAR(1) := 'T';

 C_INICIO_MSG  VARCHAR2(2000) := '
                                  <html>
                                  <head>
                                  </head>
                                  <body>

                                  <table width="337" border="0">
                                  <tr>
                                   <td width="8">&nbsp;&nbsp;</td>
                                   <td width="319"><table width="316" height="293" border="0">
                                ';
                                
 C_INICIO_MSG2  VARCHAR2(2000) := '
                                  <html>
                                  <head>
                                  </head>
                                  <body>

                                  <table width="337" border="0">
                                  <tr>
                                   <td><table border="0">
                                ';


 C_FILA_VACIA  VARCHAR2(2000) :='<tr> '||
                                '<td height="13" colspan="3"></td> '||
                                ' </tr> ';

 C_FIN_MSG     VARCHAR2(2000) := '
                                 </table></td>
                                 </tr>
                                 </table>
                                 </body>
                                 </html>';
                                 
   COD_IND_EMI_CUPON NUMBER(3) := 679;                    --ASOSA - 10/04/2015 - NECUPYAYAYAYA

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_SEPARADOR
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 01 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_MSG_01_CONSEJO
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 --07/08/2014  ERIOS        Se agrega parametros de local
 FUNCTION IMP_GET_MSG_02_CONSEJO(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR)
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_PIE_PAGINA(cCodGrupoCia_in 	IN CHAR,
                             cCodLocal_in    	IN CHAR,
                						 cNumPedVta_in   	IN CHAR)
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_PROCESA_CUPON(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
            								cNumPedVta_in   	IN CHAR,
                            cIpServ_in        IN CHAR,
                            cCodCupon_in      IN CHAR
                            --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                            ,cCodCia_in in char
              						 )
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_CUPONES_PEDIDO(cCodGrupoCia_in 	IN CHAR,
                                 cCodLocal_in    	IN CHAR,
                             	  cNumPedVta_in   	IN CHAR)
 RETURN FARMACURSOR;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 PROCEDURE IMP_UPDATE_IND_IMP(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                   						cNumPedVta_in   IN CHAR,
                              cCodCupon       IN CHAR);


 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_MONTO_PORCENTAJE(cCodGrupoCia_in 	IN CHAR,
                                   cCodCapana_in    IN CHAR
                							     )
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_MSG_CAMP_PROD(cCodGrupoCia_in IN CHAR,
                                cCodCapana_in   IN CHAR
                							  )
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje de la campaña
 --Fecha       Usuario		Comentario
 --15/07/2008  JCORTEZ  Creación
 FUNCTION IMP_GET_MSG_03_CONSEJO(cCodGrupoCia_in 	IN CHAR,
                                 CodCamp_in      IN CHAR)
 RETURN VARCHAR2;

 --Descripcion: obtiene fecha de vigencia del cupon
 --Fecha       Usuario		Comentario
 --30/07/2008  JCORTEZ  Creación
 FUNCTION IMP_GET_MSG_04_CONSEJO(cCodGrupoCia_in 	IN CHAR,
                                  CodCamp_in        IN CHAR,
                                  CodCupon_in       IN CHAR)
 RETURN VARCHAR2;

  --Descripcion: Calcula EAN13
  --Fecha       Usuario		Comentario
  --23/05/2008  DUBILLUZ  Creacion
  FUNCTION GENERA_EAN13(vCodigo_in IN VARCHAR2)
  RETURN CHAR;


 FUNCTION IMP_GET_TIME_CAN_READ RETURN VARCHAR2;

  --Descripcion: Obtiene formato html de cupon regalo
  --Fecha       Usuario		Comentario
  --18.08.09  JCORTEZ  Creacion
  FUNCTION IMP_PROCESA_CUPON_REGALO(cCodGrupoCia_in 	IN CHAR,
                                    cCodLocal_in    	IN CHAR,
                                    cIpServ_in        IN CHAR,
                                    cCodCupon_in      IN CHAR,
                                    cDni_in           IN CHAR)
  RETURN VARCHAR2;


  --Descripcion: Obtiene pie de pagina de cupon regalo
  --Fecha       Usuario		Comentario
  --18.08.09    JCORTEZ   Creacion
  FUNCTION IMP_GET_PIE_PAGINA2(cCodGrupoCia_in 	IN CHAR,
                              cCodLocal_in    	IN CHAR,
                              cDni              IN CHAR)
  RETURN VARCHAR2;

  PROCEDURE PROCESA_CUPON_SORTEO(cCodGrupoCia_in 	IN CHAR,
                               cCodLocal_in    	IN CHAR,
                    					 cNumPedVta_in   	IN CHAR);

  function  IMP_GET_CUPON_SORTEO(cCodGrupoCia_in 	IN CHAR,
                                 cCodLocal_in    	IN CHAR,
                      					 cCodCupon_in   	IN CHAR)
     return varchar2;


 FUNCTION IMP_PROCESA_CUPON_1(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
            								cNumPedVta_in   	IN CHAR,
                            cIpServ_in        IN CHAR,
                            cCodCupon_in      IN CHAR
                            --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                            ,cCodCia_in in char
              						 )
 RETURN VARCHAR2;

 FUNCTION IMP_PROCESA_CUPON_2(cCodGrupoCia_in 	IN CHAR,
                            cCodLocal_in    	IN CHAR,
            								cNumPedVta_in   	IN CHAR,
                            cIpServ_in        IN CHAR,
                            cCodCupon_in      IN CHAR
                            --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                            ,cCodCia_in in char
              						 )
 RETURN VARCHAR2;

 --Descripcion: Obtiene mensje 02 de consejo
 --Fecha       Usuario		Comentario
 --09/05/2008  DUBILLUZ  Creación
 FUNCTION IMP_GET_PIE_PAGINA_2(cCodGrupoCia_in 	IN CHAR,
                             cCodLocal_in    	IN CHAR,
                						 cNumPedVta_in   	IN CHAR)
 RETURN VARCHAR2;
 
 --Descripcion: Obtiene impresion para voucher de fidelizado.
 --Fecha       Usuario		Comentario
 --17/04/2015  ASOSA      Modificación PTOSYAYAYAYA
    FUNCTION F_IMPR_VOU_FID_TARJ_NEW(cCodGrupoCia_in   IN CHAR,
                                   cCodCia_in            IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cDNI_in         in varchar2,
                                   cUser_in        in char,
                                   tipo           in varchar2
                							 )
  RETURN VARCHAR2;
  
  FUNCTION IMP_GET_PIE_PAGINA_NEW(cCodGrupoCia_in 	IN CHAR,
                               cCodLocal_in    	IN CHAR,
                						   cNumPedVta_in   	IN CHAR)
  RETURN VARCHAR2;
  
  FUNCTION IMP_PROCESA_CUPON_1_NEW(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                              --  cIpServ_in        IN CHAR,
                                cCodCupon_in      IN CHAR
                                --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                                ,cCodCia_in in char
                							 )
  RETURN FARMACURSOR;
  
  FUNCTION IMP_PROCESA_CUPON_NEW(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR,
                                cCodCupon_in      IN CHAR
                                --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                                ,cCodCia_in in char
                							 )
  RETURN FARMACURSOR;
  
  FUNCTION IMP_GET_MSG_02_CONSEJO_NEW(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN VARCHAR2;
  
  FUNCTION IMP_GET_MONTO_PORCENTAJE_NEW(cCodGrupoCia_in 	IN CHAR,
                                   cCodCapana_in    IN CHAR
                							    )
 RETURN VARCHAR2;
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_IMP_CUPON" AS

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
  FUNCTION IMP_GET_MSG_02_CONSEJO(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(13000):= '';
	v_vNomMarca PBL_MARCA_GRUPO_CIA.NOM_MARCA%TYPE;
  BEGIN
      BEGIN
		--ERIOS 2.4.3 Se muestra el nombre de la marca.
		SELECT NOM_MARCA
		INTO v_vNomMarca
		FROM PBL_MARCA_GRUPO_CIA
		WHERE (COD_GRUPO_CIA,COD_MARCA) = (SELECT COD_GRUPO_CIA,COD_MARCA
			FROM PBL_LOCAL
			WHERE COD_GRUPO_CIA = cCodGrupoCia_in
			AND cod_local = cCodLocal_in
			);
		
      SELECT REPLACE(TRIM(T.LLAVE_TAB_GRAL),'{NOM_MARCA}',v_vNomMarca)
      INTO   vResultado
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'MSG02_CONSEJOS'
      AND    T.ID_TAB_GRAL = 199;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := '';
      END;

   RETURN vResultado;
  END;

  /* ***************************************************************** */
  FUNCTION IMP_GET_MSG_03_CONSEJO(cCodGrupoCia_in 	IN CHAR,
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
  FUNCTION IMP_GET_MSG_04_CONSEJO(cCodGrupoCia_in 	IN CHAR,
                                  CodCamp_in        IN CHAR,
                                  CodCupon_in       IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(13000):= '';
  BEGIN
      BEGIN
      --SELECT 'Valido del '||TRUNC(X.FEC_INI)||' al '||TRUNC(X.FEC_FIN)
      SELECT 'Vigencia del '||TRUNC(X.FEC_INI)||' al '||TRUNC(X.FEC_FIN)
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

 FUNCTION IMP_PROCESA_CUPON(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR,
                                cCodCupon_in      IN CHAR
                                --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                                ,cCodCia_in in char
                							 )
  RETURN VARCHAR2
  IS
  vMsg_out varchar2(32767):= '';


  BEGIN
        
       IF IMP_GET_CUPON_SORTEO (cCodGrupoCia_in, cCodLocal_in, cCodCupon_in) = 'N' THEN
          vMsg_out := IMP_PROCESA_CUPON_1 (cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, cIpServ_in, cCodCupon_in, cCodCia_in);
       ELSE
          vMsg_out := IMP_PROCESA_CUPON_2 (cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, cIpServ_in, cCodCupon_in, cCodCia_in);
       END IF;

     RETURN vMsg_out;

  END;
 /* ************************************************************************ */

 FUNCTION IMP_GET_CUPONES_PEDIDO(cCodGrupoCia_in 	IN CHAR,
                                  cCodLocal_in    	IN CHAR,
                              	  cNumPedVta_in   	IN CHAR)
  RETURN FARMACURSOR
  IS
  vCursor FARMACURSOR;
  cCodCamp  vta_campana_cupon.cod_camp_cupon%TYPE;
  cIndMultiUso  vta_campana_cupon.Ind_Multiuso%TYPE;
  ctdCampanaSorteo number(2);
  cCodCampanaSorteo varchar2(10);
                   vIndManual varchar2(10);  
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

               -- PROCESA CUPONES SORTEO
               -- DUBILLUZ 30/07/2013
               


  
              select nvl(c.ind_comp_manual,'N')
              into   vIndManual 
              from   vta_pedido_vta_cab c
              where  c.COD_GRUPO_CIA  = cCodGrupoCia_in
              AND    c.COD_LOCAL      = cCodLocal_in
              AND    c.NUM_PED_VTA    = cNumPedVta_in;
               
              if vIndManual = 'N' then
               select count(1)
               into ctdCampanaSorteo
               from   vta_campana_cupon c
               where  c.ind_sorteo = 'S'
               and    c.estado = 'A'
               and    sysdate between c.fech_inicio and c.fech_fin
                AND
                    (
                     C.IND_CADENA = 'S'
                     OR
                     (C.IND_CADENA = 'N' AND EXISTS (SELECT 1 FROM VTA_CAMP_X_LOCAL CL WHERE CL.COD_CAMP_CUPON = C.COD_CAMP_CUPON AND CL.COD_LOCAL = cCodLocal_in))
                    );
               
               -- dubilluz 16.10.2014     
               --if ctdCampanaSorteo = 1 then
               if ctdCampanaSorteo > 0 then
                  PROCESA_CUPON_SORTEO(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
                  -- DUBILLIZ 16.10.2014
                  -- ESTA PARTE YA NO ES NECESARIA SOLO EL METODO DEL PEDIDO SE CAMBIARA 
                   /*select c.cod_camp_cupon
                   into cCodCampanaSorteo
                   from   vta_campana_cupon c
                   where  c.ind_sorteo = 'S'
                   and    c.estado = 'A'
                   and    sysdate between c.fech_inicio and c.fech_fin
                    AND
                        (
                         C.IND_CADENA = 'S'
                         OR
                         (C.IND_CADENA = 'N' AND EXISTS (SELECT 1 FROM VTA_CAMP_X_LOCAL CL WHERE CL.COD_CAMP_CUPON = C.COD_CAMP_CUPON AND CL.COD_LOCAL = cCodLocal_in))
                        );*/

               end if;
               
             end if;  
               
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
               union
               -- CUPONES DE SORTEO
               select cp.cod_cupon
               from   vta_cupon cp
               where  cp.cod_grupo_cia = cCodGrupoCia_in
               --and    cp.cod_cupon like  cCodLocal_in || SUBSTR(cNumPedVta_in,-7) || '%'
               --and    1 = ctdCampanaSorteo
               and    cp.cod_cupon like  cp.cod_campana|| cCodLocal_in || SUBSTR(cNumPedVta_in,-7) || '%'
               and    cp.cod_campana in
               (
               select cod_camp_cupon from vta_campana_cupon C
               where ind_sorteo = 'S'
                 AND ESTADO = 'A'
                AND
                    (
                     C.IND_CADENA = 'S'
                     OR
                     (C.IND_CADENA = 'N' AND EXISTS (SELECT 1 FROM VTA_CAMP_X_LOCAL CL WHERE CL.COD_CAMP_CUPON = C.COD_CAMP_CUPON AND CL.COD_LOCAL = cCodLocal_in))
                    )
               );

  return  vCursor ;

  END;

/* ********************************************************************* */
  PROCEDURE IMP_UPDATE_IND_IMP(cCodGrupoCia_in 	IN CHAR,
                               cCodLocal_in    	IN CHAR,
                    					 cNumPedVta_in   	IN CHAR,
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
 FUNCTION IMP_GET_MONTO_PORCENTAJE(cCodGrupoCia_in 	IN CHAR,
                                   cCodCapana_in    IN CHAR
                							    )
 RETURN VARCHAR2
 IS
 vMensajePorcMonto varchar2(2000);
 nMonto number;
 vTipoCupon char(1);
 begin



             SELECT C.VALOR_CUPON,C.TIP_CUPON
             INTO   nMonto,vTipoCupon
             FROM   VTA_CAMPANA_CUPON C
             WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
             AND    C.COD_CAMP_CUPON = cCodCapana_in;

             if vTipoCupon = 'P' then
                vMensajePorcMonto := nMonto || '% descuento';
             elsif vTipoCupon = 'M' then
                --vMensajePorcMonto := 'S/.'||nMonto || ' descuento';--JCORTEZ 24/11/2009 por solicitud
                vMensajePorcMonto := nMonto || '% descuento';
             end if;



    return vMensajePorcMonto;
 END;

/* *********************************************************************** */
 FUNCTION IMP_GET_MSG_CAMP_PROD(cCodGrupoCia_in 	IN CHAR,
                                   cCodCapana_in    IN CHAR
                							    )
 RETURN VARCHAR2
 IS
 vMensajePorcMonto varchar2(2000);
 nMonto number;
 vTipoCupon char(1);

 nCantidad number;
 vDescCampana varchar2(3000);
 vDescProd  varchar2(3000);
 -- 2009-06-23 JOLIVA
 vTextoLargo VTA_CAMPANA_CUPON.TEXTO_LARGO%TYPE;
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

             if nCantidad = 0 then
                vMensajePorcMonto := ' ';
             end if;

                vMensajePorcMonto := 'En el producto: ' ||vDescProd;

               if nCantidad = 0 then
                  vMensajePorcMonto := ' ';
               end if;
               vMensajePorcMonto := vMensajePorcMonto || '<BR>';
             elsif nCantidad > 1 then

             SELECT nvl(c.desc_cupon,' ')
             INTO   vDescCampana
             FROM   vta_campana_cupon c
             WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
             AND    C.COD_CAMP_CUPON = cCodCapana_in;

               -- vMensajePorcMonto := ' En los productos de la campaña ' || vDescCampana;
               vMensajePorcMonto := '';
             end if;

             SELECT nvl(c.TEXTO_LARGO,' ')
             INTO   vTextoLargo
             FROM   vta_campana_cupon c
             WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
             AND    C.COD_CAMP_CUPON = cCodCapana_in;

                vMensajePorcMonto := vMensajePorcMonto ||vTextoLargo;

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
  FUNCTION IMP_PROCESA_CUPON_REGALO(cCodGrupoCia_in 	IN CHAR,
                                    cCodLocal_in    	IN CHAR,
                                    cIpServ_in        IN CHAR,
                                    cCodCupon_in      IN CHAR,
                                    cDni_in           IN CHAR)
  RETURN VARCHAR2
  IS
  vMsg_out varchar2(32767):= '';

  vFila_IMG_Cabecera_MF varchar2(2800):= '';
  vFila_Num_Ped      varchar2(2800):= '';
  vFila_Msg_02       varchar2(2800):= '';
  vFila_Pie_Pagina   varchar2(2800):= '';
  vFila_Msg_03       varchar2(2800):= '';--JCORTEZ 15/07/08
  vFila_Msg_04       varchar2(2800):= '';--JCORTEZ 30/07/08
  vFila_Msg_Medico   VARCHAR2(2800):= '';

  vFila_Cupon     varchar2(22767):= '';
  vFila_ValorCupon   varchar2(2800):= '';
  vFila_MsgProd   varchar2(2800):= '';
  vCodCampana CHAR(5);
  vRuta varchar2(500);

  vDatoCliente varchar2(2800):= '';
  vFilaCliente varchar2(2800):= '';
    v_vCabecera2 VARCHAR2(500);
	vCodcia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
   SELECT COD_CIA INTO vCodcia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
   vRuta := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\';
   v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,vCodcia,cCodLocal_in);
   select c.cod_campana
   into   vCodCampana
   from   vta_cupon c
   where  c.cod_grupo_cia = cCodGrupoCia_in
   and    c.cod_local = cCodLocal_in
   and    c.cod_cupon = cCodCupon_in;
   


     -- FILA 01 : CABECERA CON IMAGEN
     vFila_IMG_Cabecera_MF:= '<tr> <td colspan="3"><div align="center" class="style8">'||
                         '<img src=file:'||
                         v_vCabecera2||
                         ' width="236" height="64" class="style3"></div></td>'||
                         ' </tr>';

      -- FILA 03 : NUMERO CUPON
     vFila_Num_Ped := '<tr><td>'||cCodCupon_in||'</td><td colspan="2"><div align="right" class="style1 style3 style8">'||
                      '<span class="style9"><strong>'||
                      --'Nro:</strong> '||cNumPedVta_in|| '</span></div></td>'||
                      '</tr> ';

     -- FILA 05 : MENSAJE 02
     vFila_Msg_02 := '</tr><tr><td colspan="3" align="center" style="font:oblique, Helvetica, sans-serif">'
                     ||IMP_GET_MSG_02_CONSEJO(cCodGrupoCia_in,vCodcia,cCodLocal_in) ||
                     '</td>'||
                     '</tr>';

     vFila_Msg_Medico := '<tr><td  colspan="3" align="center" style="font:oblique, Helvetica, sans-serif">'||
                     '<b>NO SE AUTOMEDIQUE</b><br>Consulte siempre con su M&eacute;dico'||
                     '</td></tr>';

     vFila_Msg_03 := '<td  colspan="3" align="lef" style="font:oblique, Helvetica, sans-serif">'
                     ||IMP_GET_MSG_03_CONSEJO(cCodGrupoCia_in,vCodCampana) ||
                     '</td>'||
                     '</tr>';

     vFila_Msg_04 := '<tr><td  colspan="3" align="lef" style="font:oblique, Helvetica, sans-serif">'
                     ||IMP_GET_MSG_04_CONSEJO(cCodGrupoCia_in,vCodCampana,cCodCupon_in) ||
                     '</td>'||
                     '</tr>';



     vFila_Pie_Pagina := IMP_GET_PIE_PAGINA2(cCodGrupoCia_in ,cCodLocal_in,cDni_in); --NUEVO METODO

     vFila_Cupon := '<tr>' ||
                    '<td height="50" colspan="3">'||
                    '<div align="center" class="style8">'||
                    -- '<img src=file:'||vRuta||''||cCodCupon_in||'.jpg width="222" height="100" class="style3"></div></td>'||
                    '<<codigoBarra>></div></td>'|| -- kmoncada 30.07.2014 codigo de barra generado en el farmaventa
                     '</tr>';
     ----
     BEGIN
     select nvl(L.NOM_TRAB,'') || ' ' || nvl(L.APE_PAT_TRAB,'') || ' ' || nvl(L.APE_MAT_TRAB,'')
     into   vDatoCliente
     from   CE_MAE_TRAB L
     where  L.COD_CIA = cCodGrupoCia_in
     AND    L.NUM_DOC_IDEN=cDni_in;

     vDatoCliente := trim(vDatoCliente);
     vDatoCliente := 'Para: '||vDatoCliente;

     vFilaCliente := '
            <tr>
            <td height="30" colspan="3"  align="lef" style="font:Arial, Helvetica, sans-serif"><B>'
            ||vDatoCliente||'</B></td> </tr>';

     EXCEPTION
     WHEN OTHERS THEN
       vFilaCliente := '';
     END;

     vFila_ValorCupon := '
      <tr>
        <td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:30px">
		<B>'||IMP_GET_MONTO_PORCENTAJE(cCodGrupoCia_in,vCodCampana)||'</B>	</td>
      </tr>
     ';
     vFila_MsgProd := '
            <tr>
            <td height="30" colspan="3"  align="lef" style="text-align: justify;font:Arial, Helvetica, sans-serif"><B>'
            ||IMP_GET_MSG_CAMP_PROD(cCodGrupoCia_in,vCodCampana)||'</B></td> </tr>';


          vMsg_out := C_INICIO_MSG ||
                        vFila_IMG_Cabecera_MF  ||
                      C_FILA_VACIA ||
                        vFila_Cupon ||
                        vFila_ValorCupon ||
                        vFilaCliente ||
                        vFila_MsgProd ||
                        vFila_Num_Ped ||
                        vFila_Msg_02 ||
                        vFila_Msg_Medico||   --JMIRANDA 05.09.2011
                        vFila_Msg_04 || vFila_Pie_Pagina ||
                      C_FIN_MSG ;

     RETURN vMsg_out;

  END;



   /* ******************************************************************** */
  FUNCTION IMP_GET_PIE_PAGINA2(cCodGrupoCia_in 	IN CHAR,
                              cCodLocal_in    	IN CHAR,
                              cDni              IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(23000):= '';
  vCod_trab varchar2(100):= '';
  vFecha    varchar2(100):= '';
  vlocal    varchar2(100):= '';
  BEGIN
      BEGIN
          SELECT NVL(A.COD_TRAB_RRHH,A.COD_TRAB),
                 TO_CHAR(SYSDATE,'DD/MM/YYYY HH24:MI:SS'),V1.LOC
          INTO   vCod_trab,vFecha,vlocal
          FROM   CE_MAE_TRAB A,
                 (SELECT B.COD_LOCAL ||'-'||B.DESC_ABREV LOC
                         FROM PBL_LOCAL B
                          WHERE B.COD_GRUPO_CIA=cCodGrupoCia_in
                          AND B.COD_LOCAL=cCodLocal_in) V1
          WHERE  A.COD_CIA = cCodGrupoCia_in
          AND    A.NUM_DOC_IDEN=TRIM(cDni);

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

/* ********************************************************* */
-- DUBILLUZ 30/07/2013
PROCEDURE PROCESA_CUPON_SORTEO(cCodGrupoCia_in 	IN CHAR,
                               cCodLocal_in    	IN CHAR,
                    					 cNumPedVta_in   	IN CHAR)
  IS
  PRAGMA AUTONOMOUS_TRANSACTION;
  valNeto vta_pedido_vta_cab.val_neto_ped_vta%type;
  cMontoMinimo vta_campana_cupon.mont_min%type;
  cIndTodosProductos vta_campana_cupon.ind_todo_productos%type;
  cMonQuiebre vta_campana_cupon.MON_QUIEBRE%type;

  indTotalProductos number(8,3);
  cCodCampanaSorteo vta_campana_cupon.cod_camp_cupon%type;
  cExisteSorteo number;

  nCantidadCupones number(9);

  nCodigoCupon varchar2(20);

  v_SecEan  NUMBER;
  v_MaxCupones     NUMBER;
  
  -- DUBILLUZ 16.10.214
 CURSOR curCampanaSorteo is
        select c.cod_camp_cupon,c.mont_min,c.ind_todo_productos, 
               NVL(c.MON_QUIEBRE,0) mQuiebre, nvl(c.max_cupones,0) mMaxCupones
        from   vta_campana_cupon c
        where  c.ind_sorteo = 'S'
        and    c.estado = 'A'
        and    sysdate between c.fech_inicio and c.fech_fin
        AND
            (
             C.IND_CADENA = 'S'
             OR
             (C.IND_CADENA = 'N' AND EXISTS (SELECT 1 FROM VTA_CAMP_X_LOCAL CL WHERE CL.COD_CAMP_CUPON = C.COD_CAMP_CUPON AND CL.COD_LOCAL = cCodLocal_in))
            );

      
    fSC curCampanaSorteo%rowtype;  

  BEGIN
    select ca.val_neto_ped_vta
    into   valNeto
    from   vta_pedido_vta_cab ca
    where ca.cod_grupo_cia = cCodGrupoCia_in
    and   ca.cod_local = cCodLocal_in
    and   ca.num_ped_vta = cNumPedVta_in;

    /*select count(1)
    into   cExisteSorteo
    from   vta_campana_cupon c
    where  c.ind_sorteo = 'S'
    and    c.estado = 'A'
    and    sysdate between c.fech_inicio and c.fech_fin;*/

    -- 2014-02-20 JOLIVA: SE BUSCA QUE EXISTA SOLO 1
  SELECT COUNT(*)
  into   cExisteSorteo
  FROM VTA_CAMPANA_CUPON C
  WHERE C.IND_SORTEO = 'S'
  AND C.ESTADO = 'A'
    AND SYSDATE BETWEEN C.FECH_INICIO AND C.FECH_FIN
    AND
        (
         C.IND_CADENA = 'S'
         OR
         (C.IND_CADENA = 'N' AND EXISTS (SELECT 1 FROM VTA_CAMP_X_LOCAL CL WHERE CL.COD_CAMP_CUPON = C.COD_CAMP_CUPON AND CL.COD_LOCAL = cCodLocal_in))
        );

    --if cExisteSorteo = 1 then
    if cExisteSorteo >= 1 then
       open curCampanaSorteo;
      LOOP
        FETCH curCampanaSorteo INTO fSC;
        EXIT WHEN curCampanaSorteo%NOTFOUND;
        
       cCodCampanaSorteo := fSC.Cod_Camp_Cupon;
       cMontoMinimo := fSC.Mont_Min;
       cIndTodosProductos := fSC.Ind_Todo_Productos;
       cMonQuiebre := fSC.Mquiebre;
       v_MaxCupones := fSC.Mmaxcupones;
          
        if cIndTodosProductos = 'S' then
          select sum(de.val_prec_total)
          into   indTotalProductos
          from   vta_pedido_vta_det De
          where de.cod_grupo_cia = cCodGrupoCia_in
          and   de.cod_local = cCodLocal_in
          and   de.num_ped_vta = cNumPedVta_in;
        else
          select sum(de.val_prec_total)
          into   indTotalProductos
          from   vta_pedido_vta_det De
          where de.cod_grupo_cia = cCodGrupoCia_in
          and   de.cod_local = cCodLocal_in
          and   de.num_ped_vta = cNumPedVta_in
          and   exists (
                        select 1
                        from   vta_campana_prod p
                        where  p.cod_grupo_cia = cCodGrupoCia_in
                        and    p.cod_camp_cupon = cCodCampanaSorteo
                        and    p.cod_prod = de.cod_prod
                        );
        end if;
        dbms_output.put_line('valNeto > '||valNeto);
        -- proceso de calculo de cupones sorteo.
        dbms_output.put_line('cMontoMinimo > '||cMontoMinimo);
                dbms_output.put_line('indTotalProductos > '||indTotalProductos);
         -- CALCULO DE MONTO DE COMPRA SE TOMA LA SUMA DE PEDIDOO
        --if valNeto >= cMontoMinimo then

        --if valNeto >= cMontoMinimo THEN
        if indTotalProductos >= cMontoMinimo then
           --nCantidadCupones := 1;

           if trunc(indTotalProductos/cMonQuiebre) >= 1 then
             nCantidadCupones := LEAST(trunc(indTotalProductos/cMonQuiebre), v_MaxCupones);
           else
             nCantidadCupones := 1;
           end if ;

           dbms_output.put_line('nCantidadCupones=' || nCantidadCupones);

                      -- LE CORRESPONDE CUPONES DE SORTEO
           if nCantidadCupones >= 0 then
             --nCantidadCupones
             --Codigo de Cupon es VARCHAR2(20)
                for i in 1..nCantidadCupones
                loop

                  --Formato
                  --Local3 [CodCampana-5][NumeroPedido 10] >> 15
                  -- Secuencial de Cupones quedan 5 (Es Decir Por pedido Sera 99999 Cupones)
                  --nCodigoCupon := GENERA_EAN13( cCodLocal_in|| SUBSTR(cNumPedVta_in,-7) || LPAD(i,2,'0'));
                  nCodigoCupon :=  cCodCampanaSorteo||cCodLocal_in|| SUBSTR(cNumPedVta_in,-7) || LPAD(i,2,'0');

                  dbms_output.put_line('nCodigoCupon=' || nCodigoCupon);

                   insert into vta_cupon
                   (COD_CAMPANA,COD_LOCAL,SEC_CUPON,ESTADO,COD_CUPON,COD_GRUPO_CIA)
                   values
                   (cCodCampanaSorteo,cCodLocal_in, i,'A',nCodigoCupon,cCodGrupoCia_in);
                end loop;
           end if;
        end if;
        -- fin del calculo de cupones de sorteo.
        END LOOP;
        close curCampanaSorteo;  
     end if;
    commit;
    exception
      when others then
           DBMS_OUTPUT.put_line(SQLERRM);
        rollback;
end;
/* ********************************************************* */
-- DUBILLUZ 30/07/2013
function  IMP_GET_CUPON_SORTEO(cCodGrupoCia_in 	IN CHAR,
                               cCodLocal_in    	IN CHAR,
                    					 cCodCupon_in   	IN CHAR)
   return varchar2
  IS cResultado char(1) := 'N';
  BEGIN

    select c.ind_sorteo
    into  cResultado
    from   vta_cupon cp,
           vta_campana_cupon c
    where  cp.cod_grupo_cia = c.cod_grupo_cia
    and    cp.cod_campana = c.cod_camp_cupon
    and    cp.cod_cupon = cCodCupon_in;

    return cResultado;
end;
/* ********************************************************* */

 FUNCTION IMP_PROCESA_CUPON_1(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR,
                                cCodCupon_in      IN CHAR
                                --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                                ,cCodCia_in in char
                							 )
  RETURN VARCHAR2
  IS
  vMsg_out varchar2(32767):= '';

  vFila_IMG_Cabecera_MF varchar2(2800):= '';
  vFila_Num_Ped      varchar2(2800):= '';
  vFila_Msg_02       varchar2(2800):= '';
  vFila_Pie_Pagina   varchar2(2800):= '';
  vFila_Msg_03       varchar2(2800):= '';--JCORTEZ 15/07/08
  vFila_Msg_04       varchar2(2800):= '';--JCORTEZ 30/07/08
  vFila_Msg_Medico   VARCHAR2(2800):= ''; --JMIRANDA 05.09.2011

  vFila_Cupon     varchar2(22767):= '';

  vFila_ValorCupon   varchar2(2800):= '';
  vFila_MsgProd   varchar2(2800):= '';
  vCodCampana CHAR(5);

  vRuta varchar2(500);

  vDatoCliente varchar2(2800):= '';
  vFilaCliente varchar2(2800):= '';

  vTipCupon    CHAR(1);

-- 2010-04-22 JOLIVA: Nuevas variables
  vDescCortaCupon     varchar2(100);
  vMaxCantProd        VTA_CAMPANA_CUPON.UNID_MAX_PROD%TYPE;
  vMaxDctoDiaCadena   PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;-- VARCHAR2(4);
  vMsjCondiciones     VARCHAR2(6000);
  vMsgDiasValidos     VARCHAR2(128);

  -- dubilluz 09.06.2011
 vCodFPMax  varchar(20);
 vTextFPMax varchar(2000);
 vValorDctoMAX number(10);
 vCountFPActivo number;

 vCodFPMenor  varchar(20);
 vTextFPMenor varchar(2000);
 vValorDctoMenor number(10);
 vFila_MsgOTRAFORMA_PAGO varchar2(2800):= '';
        -- dubilluz 17.05.2012
  vIndCostoPromedio vta_campana_cupon.ind_val_costo_prom%type;
  vIndHasta VARCHAR(10000);
    v_vCabecera2 VARCHAR2(500);
  BEGIN

   vRuta := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\';
   v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);
   select c.cod_campana
   into   vCodCampana
   from   vta_cupon c
   where  c.cod_grupo_cia = cCodGrupoCia_in
   --and    c.cod_local = cCodLocal_in --JCORTEZ 15.08.08
   and    c.cod_cupon = cCodCupon_in;

     --JCORTEZ 24.11.2009
     -- 2010-04-22 JOLIVA: Se obtiene la nueva descripción corta de la campaña
  SELECT A.TIP_CUPON, A.DESC_CORTA_CUPON, A.UNID_MAX_PROD,
         -- dubilluz 17.05.2012
         a.ind_val_costo_prom
  INTO vTipCupon, vDescCortaCupon, vMaxCantProd,vIndCostoPromedio
  FROM VTA_CAMPANA_CUPON A
  WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
  AND A.COD_CAMP_CUPON=vCodCampana;

     -- 2010-04-22 JOLIVA: Se obtiene el máximo descuento diario por DNI
  SELECT LLAVE_TAB_GRAL
  INTO vMaxDctoDiaCadena
  FROM PBL_TAB_GRAL
  WHERE ID_TAB_GRAL = 274;

  vMsjCondiciones := '';
  vMsgDiasValidos:='';

  --- Pedido especial para esta campaña, add by JCT,05-DIC-12
  IF vCodCampana='12725' THEN
   vMsgDiasValidos:='Válido de Martes a Domingo - ';
   --vMsjCondiciones := vMsjCondiciones || ' NO VALIDO PARA DELIVERY - vCodCampana:'||vCodCampana;
   vMsjCondiciones := vMsjCondiciones || '. Descuento diario máximo por persona  de S/.' || vMaxDctoDiaCadena ||'';
   vMsjCondiciones := vMsjCondiciones || CASE WHEN vMaxCantProd = 1 THEN '. Descuento válido para 1 unidad' ELSE ' y/o ' || vMaxCantProd || ' unidades por producto' END;
   vMsjCondiciones := vMsjCondiciones || '(Restricción aplicable a todas las promociones y/o descuentos en toda la cadena)';
   vMsjCondiciones := vMsjCondiciones || '. Stock mínimo 100 unidades. No acumulable con otras promociones, descuentos y/o convenios ni ventas especiales.';



   vMsjCondiciones := vMsjCondiciones ||'Válido sólo en Local Mifarma - Jacarandá: CALL. LOS LAURELES#196 URB.VALLE HERMOSO RESIDENCIAL SANTIAGO SURCO'
   || ' No válido para Delivery ';
   vMsjCondiciones := vMsjCondiciones||' - Obligatorio presentar Cupón y DNI.';
  ELSE
   vMsjCondiciones := vMsjCondiciones || '. Máximo descuento diario por cliente S/.' || vMaxDctoDiaCadena ||'';
   vMsjCondiciones := vMsjCondiciones || CASE WHEN vMaxCantProd = 1 THEN '. Descuento válido para 1 unidad' ELSE ' o ' || vMaxCantProd || ' unidades por producto' END;
   vMsjCondiciones := vMsjCondiciones || '. No es acumulable con otros descuentos.';
   vMsjCondiciones := vMsjCondiciones || ' Válido sólo en locales';
  END IF;

     --JMIRANDA 07/07/09 MODIFICACION DE DIMENSIONES CABECERA
     -- FILA 01 : CABECERA CON IMAGEN
     vFila_IMG_Cabecera_MF:= '<tr> <td colspan="3"><div align="center" class="style8">'||
                         '<img src=file:'||
                         v_vCabecera2||
                         ' width="236" height="64" class="style3"></div></td>'||
--                         ' width="246" height="48" class="style3"></div></td>'||
--                         ' width="236" height="64" class="style3"></div></td>'||
                         ' </tr> ';

     --FV 19.08.08
     --vFila_IMG_Cabecera_MF:= '<tr> <td colspan="3"></td></tr> ';


      -- FILA 03 : NUMERO DE PEDIDO
     vFila_Num_Ped := '<tr><td>'||cCodCupon_in||'</td><td colspan="2"><div align="right" class="style1 style3 style8">'||
                      '<span class="style9"><strong>'||
                      'Nro:</strong> '||cNumPedVta_in|| '</span></div></td>'||
                      '</tr> ';

     -- FILA 05 : MENSAJE 02
     vFila_Msg_02 := --'<tr><td height="44" colspan="3" align="center" style="font:oblique, Helvetica, sans-serif">'
                     '<tr><br><tr><td colspan="3" align="center" style="font:oblique, Helvetica, sans-serif">'
                     ||
                     IMP_GET_MSG_02_CONSEJO(cCodGrupoCia_in,cCodCia_in,cCodLocal_in) ||
                     '</td>'||
                     '</tr>';

      --JMIRANDA 05.09.2011
           vFila_Msg_Medico := '<tr><td  colspan="3" align="center" style="font:oblique, Helvetica, sans-serif">'||
                     '<b>NO SE AUTOMEDIQUE</b><br>Consulte siempre con su M&eacute;dico'||
                     '</td></tr>';

      -- JCORTEZ 15/07/08 FILA 06 : MENSAJE CUPON
     vFila_Msg_03 := --'<td height="44" colspan="3" align="lef" style="font:oblique, Helvetica, sans-serif">'
                     '<td  colspan="3" align="lef" style="font:oblique, Helvetica, sans-serif">'
                     ||
                     IMP_GET_MSG_03_CONSEJO(cCodGrupoCia_in,vCodCampana) ||
                     '
                     </td> '||
                     '</tr>';

      -- JCORTEZ 30/07/08 FILA 07
     vFila_Msg_04 := --'<tr><td height="44" colspan="3" align="lef" style="font:oblique, Helvetica, sans-serif">'
                     '<tr><td  colspan="3" align="lef" style="font:oblique, Helvetica, sans-serif; font-size:9px">'
                     ||vMsgDiasValidos||
                     IMP_GET_MSG_04_CONSEJO(cCodGrupoCia_in,vCodCampana,cCodCupon_in) || vMsjCondiciones ||
                     '
                     </td> '||
                     '</tr>';
     vFila_Pie_Pagina := IMP_GET_PIE_PAGINA(cCodGrupoCia_in ,cCodLocal_in,cNumPedVta_in);

     --FV 19.08.08
     --JMIRANDA 07/07/2009
     vFila_Cupon := '<tr>' ||
                    '<td height="50" colspan="3">'||
                    '<div align="center" class="style8">'||
                     --'<img src=file://///C:/'||cCodCupon_in||'.jpg width="229" height="107" class="style3"></div></td>
                 --    '<img src=file://///'||vRuta||''||cCodCupon_in||'.jpg width="229" height="107" class="style3"></div></td>'||
--                     '<img src=file:'||vRuta||''||cCodCupon_in||'.jpg width="222" height="100" class="style3"></div></td>'||
                     '<<codigoBarra>></div></td>'|| -- kmoncada 30.07.2014 codigo de barra generado en el farmaventa
                     '</tr>';
     --vFila_Cupon := '<tr><td height="50" colspan="3"></td></tr>';
     ----
     BEGIN
     select nvl(l.nom_cli,'') || ' ' || nvl(l.ape_pat_cli,'') || ' ' || nvl(l.ape_mat_cli,'')
     into   vDatoCliente
     from   pbl_cliente l,
            fid_tarjeta_pedido t
     where  t.cod_grupo_cia = cCodGrupoCia_in
     and    t.cod_local = cCodLocal_in
     and    t.num_pedido = cNumPedVta_in
     and    t.dni_cli = l.dni_cli;

     vDatoCliente := trim(vDatoCliente);
     vDatoCliente := 'Para: '||vDatoCliente;

     vFilaCliente := '
            <tr>
            <td height="30" colspan="3"  align="lef" style="font:Arial, Helvetica, sans-serif"><B>'
            ||vDatoCliente||'</B></td> </tr>';

     EXCEPTION
     WHEN OTHERS THEN
       vFilaCliente := '';
     END;

     /*
     vFila_ValorCupon := '
      <tr>
        <td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:30px">
		<B>'||IMP_GET_MONTO_PORCENTAJE(cCodGrupoCia_in,vCodCampana)||'</B>	</td>
      </tr>
     ';
     */
     --inicio 09.06.2011

       select count(1)
       into   vCountFPActivo
       from   vta_camp_x_fpago_uso f
       where  f.cod_grupo_cia  = cCodGrupoCia_in
       and    f.cod_camp_cupon = vCodCampana
       and    f.estado = 'A';

       if vCountFPActivo > 0 then
               --asume q son 2 casos
               -- dubilluz 09.06.2011
               -- EFECTIVO  y TARJETA (1 sola opcion TODAS y/o una ESPECIFICA)
               select v.cod_forma_pago,v.porc_dcto
                 into vCodFPMax, vValorDctoMAX
                 from (select f.cod_forma_pago,
                              f.porc_dcto,
                              rank() OVER(ORDER BY porc_dcto desc) orden
                         from vta_camp_x_fpago_uso f
                        where f.cod_grupo_cia = cCodGrupoCia_in
                          and f.cod_camp_cupon = vCodCampana
                          and f.estado = 'A') v
                where orden = 1;


              -- El dato maximo a imprimir
              if vCodFPMax = 'E0000' then
                 vTextFPMax := 'solo en EFECTIVO';
              else
                  if vCodFPMax = 'T0000' then
                     vTextFPMax := 'solo en TARJETA DE CREDITO';
                  else
                     select 'solo en '|| UPPER(fl.desc_corta_forma_pago)
                     into   vTextFPMax
                     from   vta_forma_pago fl
                     where  fl.cod_grupo_cia  = cCodGrupoCia_in
                     and    fl.cod_forma_pago =  vCodFPMax;

                  end if;
              end if ;

               begin
              --dato MINIMO
               select f.cod_forma_pago,f.porc_dcto
               into   vCodFPMenor,vValorDctoMenor
               from   vta_camp_x_fpago_uso f
               where  f.cod_grupo_cia  = cCodGrupoCia_in
               and    f.cod_camp_cupon = vCodCampana
               and    f.cod_forma_pago not in (vCodFPMax)
               and    f.estado = 'A';

               if vCodFPMenor = 'E0000' then
                 vTextFPMenor := ' con EFECTIVO';
              else
                  if vCodFPMenor = 'T0000' then
                     vTextFPMenor := ' con cualquier TARJETA DE CREDITO';
                  else
                     select ' con '|| fl.desc_corta_forma_pago
                     into   vTextFPMenor
                     from   vta_forma_pago fl
                     where  fl.cod_grupo_cia  = cCodGrupoCia_in
                     and    fl.cod_forma_pago =  vCodFPMenor;

                  end if;
              end if ;


             vFila_MsgOTRAFORMA_PAGO := '
                    <tr>
                    <td height="30" colspan="3"  align="lef" style="font:Arial, Helvetica, sans-serif"><B>'
                    ||vValorDctoMenor || '% descuento '||vTextFPMenor||'</B></td> </tr>';

             if vValorDctoMenor = 0 then
                vFila_MsgOTRAFORMA_PAGO := '';
             end if;

               exception
               when no_data_found then
                    vCodFPMenor := 'N';
                    vFila_MsgOTRAFORMA_PAGO := 'N';
               end;

         /*
         DUBILLUZ  17.05.2012
         IND_VAL_COSTO_PROM
         indicador S: permite cobrar por debajo del costo promedio,
                   N:no permite vender debajo del costo promedio
         */
         SELECT decode(vIndCostoPromedio,'N','Hasta ','')
         INTO   vIndHasta
         FROM   DUAL;

             --imprime como siempre esta dado
         vFila_ValorCupon := '<tr>'||
                             '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:30px">'||
                        		 --'<B>'|| vValorDctoMAX || '% descuento'||'</B>	</td>'||
                             -- DUBILLUZ 17.05.2012
                             '<B>'||vIndHasta||vValorDctoMAX || '% descuento'||'</B>	</td>'||

                             '</tr>';
                             /*
                             '<tr>'||
                             '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:15px">'||
                                                                               --font:Arial, Helvetica, sans-serif;font-size:15px
                        		 '<B>'|| vTextFPMax ||'</B>	</td>'||
                             '</tr>'
                             */




        else
          --imprime como siempre esta dado

        /*
         10-ABR-15  se modifica para agregar hasta cuando sea aplicable, pedido Julio
         vFila_ValorCupon := '<tr>'||
                             '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:30px">'||
                        		 '<B>'||IMP_GET_MONTO_PORCENTAJE(cCodGrupoCia_in,vCodCampana)||'</B>	</td>'||
                             '</tr>';
        */
         SELECT decode(vIndCostoPromedio,'N','Hasta ','')
         INTO   vIndHasta
         FROM   DUAL;

         vFila_ValorCupon := '<tr>'||
                             '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:30px">'||
                        		 '<B>'||vIndHasta||IMP_GET_MONTO_PORCENTAJE(cCodGrupoCia_in,vCodCampana)||'</B>	</td>'||
                             '</tr>';

        end if;


     --fin  09.06.2011

     -- 2010-04-22 JOLIVA: Se muestra la descripción corta de la campaña
     IF TRIM(vDescCortaCupon) IS NOT NULL THEN
         vFila_ValorCupon := vFila_ValorCupon || '
          <tr>
            <td height="20" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:15px">
    		<B>'|| vDescCortaCupon ||'</B>	</td>
          </tr>'||
         '<tr>'||
         '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:15px">'||
         --font:Arial, Helvetica, sans-serif;font-size:15px
    		 '<B>'|| vTextFPMax ||'</B>	</td>'||
         '</tr>';
     else
         vFila_ValorCupon := vFila_ValorCupon ||
         '<tr>'||
         '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:15px">'||
         --font:Arial, Helvetica, sans-serif;font-size:15px
    		 '<B>'|| vTextFPMax ||'</B>	</td>'||
         '</tr>';
     END IF;

     vFila_MsgProd := '
            <tr>
            <td height="30" colspan="3"  align="lef" style="font:Arial, Helvetica, sans-serif"><B>'
            ||IMP_GET_MSG_CAMP_PROD(cCodGrupoCia_in,vCodCampana)||'</B></td> </tr>';


     --JCORTEZ 24/11/2009 Si la campaña es de tipo monto no se muestra imagen de codigo de barra
     IF vTipCupon='M' THEN
        vFila_Cupon:=' ';
     END IF;

     vMsg_out := C_INICIO_MSG ||
                 vFila_IMG_Cabecera_MF  ||
                  C_FILA_VACIA ||
                   vFila_Cupon ||
                   --vFilaCliente ||
                   vFila_ValorCupon ||
                    vFilaCliente ||
                  vFila_MsgProd ||
                  -- fila de otro descuento
                  replace(vFila_MsgOTRAFORMA_PAGO,'N','')||
                  '<tr>
                  <td height="21" colspan="3" align="left"
                          style="font:Arial, Helvetica, sans-serif">
                          En su proxima Compra
                  </td>
                  </tr>' ||
                -- vFila_Msg_03 ||

                 vFila_Num_Ped ||
                 vFila_Msg_02 ||
                 vFila_Msg_Medico|| --JMIRANDA 05.09.2011
                 vFila_Msg_04 || vFila_Pie_Pagina ||
                 C_FIN_MSG ;



     RETURN vMsg_out;

  END;

/* ********************************************************* */

 FUNCTION IMP_PROCESA_CUPON_2(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR,
                                cCodCupon_in      IN CHAR
                                --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                                ,cCodCia_in in char
                							 )
  RETURN VARCHAR2
  IS
  vMsg_out varchar2(32767):= '';

  vFila_IMG_Cabecera_MF varchar2(2800):= '';
  vFila_Num_Ped      varchar2(2800):= '';
  vFila_Msg_02       varchar2(2800):= '';
  vFila_Pie_Pagina   varchar2(2800):= '';
  vFila_Msg_03       varchar2(2800):= '';--JCORTEZ 15/07/08
  vFila_Msg_04       varchar2(2800):= '';--JCORTEZ 30/07/08
  vFila_Msg_Medico   VARCHAR2(2800):= ''; --JMIRANDA 05.09.2011

  vFila_Cupon     varchar2(22767):= '';

  vFila_ValorCupon   varchar2(2800):= '';
  vFila_MsgProd   varchar2(2800):= '';
  vCodCampana CHAR(5);

  vRuta varchar2(500);

  vDatoCliente varchar2(2800):= '';
  vFilaCliente varchar2(2800):= '';

  vTipCupon    CHAR(1);

-- 2010-04-22 JOLIVA: Nuevas variables
  vDescCortaCupon     varchar2(100);
  vMaxCantProd        VTA_CAMPANA_CUPON.UNID_MAX_PROD%TYPE;
  vMaxDctoDiaCadena   PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;-- VARCHAR2(4);
  vMsjCondiciones     VARCHAR2(6000);
  vMsgDiasValidos     VARCHAR2(128);

  -- dubilluz 09.06.2011
 vCodFPMax  varchar(20);
 vTextFPMax varchar(2000);
 vValorDctoMAX number(10);
 vCountFPActivo number;

 vCodFPMenor  varchar(20);
 vTextFPMenor varchar(2000);
 vValorDctoMenor number(10);
 vFila_MsgOTRAFORMA_PAGO varchar2(2800):= '';
        -- dubilluz 17.05.2012
  vIndCostoPromedio vta_campana_cupon.ind_val_costo_prom%type;
  vIndHasta VARCHAR(10000);

  vTextoLargo              VARCHAR2(4000) := '';
  vTextoLargoPie              VARCHAR2(1000) := '';
    v_vCabecera2 VARCHAR2(500);
  BEGIN

   v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);

      SELECT CC.TEXTO_LARGO, CC.TEXTO_LARGO_PIE
      INTO vTextoLargo, vTextoLargoPie
      FROM VTA_CUPON C,
           VTA_CAMPANA_CUPON CC
      WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
        AND C.COD_CUPON = cCodCupon_in
        AND CC.COD_GRUPO_CIA = C.COD_GRUPO_CIA
        AND CC.COD_CAMP_CUPON = C.COD_CAMPANA;

     --JMIRANDA 07/07/09 MODIFICACION DE DIMENSIONES CABECERA
     -- FILA 01 : CABECERA CON IMAGEN
     vFila_IMG_Cabecera_MF:= '<tr> <td colspan="3"><div align="center" class="style8">'||
                         '<img src=file:'||
                         v_vCabecera2||
                         ' width="236" height="64" class="style3"></div></td>'||
--                         ' width="246" height="48" class="style3"></div></td>'||
--                         ' width="236" height="64" class="style3"></div></td>'||
                         ' </tr> ';

     --FV 19.08.08
     --vFila_IMG_Cabecera_MF:= '<tr> <td colspan="3"></td></tr> ';


      -- FILA 03 : NUMERO DE PEDIDO
     vFila_Num_Ped := '';

     -- FILA 05 : MENSAJE 02
     vFila_Msg_02 := '<tr><td colspan="3">' ||
                     vTextoLargo ||
                     '</td>'||
                     '</tr>';

/*
     vFila_Msg_02 := vFila_Msg_02 ||
                     '<tr><td colspan="3" align="left" style="font:oblique, Helvetica, sans-serif">' ||
                     'Nombre: .............................................. <BR>' ||
                     '</td>'||
                     '</tr>';

     vFila_Msg_02 := vFila_Msg_02 ||
                     '<tr><td colspan="3" align="left" style="font:oblique, Helvetica, sans-serif">' ||
                     'App Paterno:.............................................. <BR>' ||
                     '</td>'||
                     '</tr>';

     vFila_Msg_02 := vFila_Msg_02 ||
                     '<tr><td colspan="3" align="left" style="font:oblique, Helvetica, sans-serif">' ||
                     'App Materno: .............................................. <BR>' ||
                     '</td>'||
                     '</tr>';

     vFila_Msg_02 := vFila_Msg_02 ||
                     '<tr><td colspan="3" align="left" style="font:oblique, Helvetica, sans-serif">' ||
                     'DNI/CE: .................... <BR>' ||
                     '</td>'||
                     '</tr>';

     vFila_Msg_02 := vFila_Msg_02 ||
                     '<tr><td colspan="3" align="left" style="font:oblique, Helvetica, sans-serif">' ||
                     'Teléfono fijo: ...................... <BR>' ||
                     '</td>'||
                     '</tr>';

     vFila_Msg_02 := vFila_Msg_02 ||
                     '<tr><td colspan="3" align="left" style="font:oblique, Helvetica, sans-serif">' ||
                     'Teléfono celular: ...................... <BR>' ||
                     '</td>'||
                     '</tr>';

     vFila_Msg_02 := vFila_Msg_02 ||
                     '<tr><td colspan="3" align="left" style="font:oblique, Helvetica, sans-serif">' ||
                     'e-mail: ............................@.............................. <BR>' ||
                     '</td>'||
                     '</tr>';

     vFila_Msg_02 := vFila_Msg_02 ||
                     '<tr><td colspan="3" align="left" style="font:oblique, Helvetica, sans-serif">' ||
                     '<BR>' ||
                     '</td>'||
                     '</tr>';

     vFila_Msg_02 := vFila_Msg_02 ||
                     '<tr><td colspan="3" align="left" style="font:oblique, Helvetica, sans-serif">' ||
                     '<BR>' ||
                     '</td>'||
                     '</tr>';

     vFila_Msg_02 := vFila_Msg_02 ||
                     '<tr><td colspan="3" align="left" style="font:oblique, Helvetica, sans-serif">' ||
                     '<BR>' ||
                     '</td>'||
                     '</tr>';
*/

     vFila_Msg_02 :=  vFila_Msg_02 ||
                     '<tr><td colspan="3">' ||
                     vTextoLargoPie ||
                     '</td>'||
                     '</tr>';

      --JMIRANDA 05.09.2011
           vFila_Msg_Medico := '';

      -- JCORTEZ 15/07/08 FILA 06 : MENSAJE CUPON
     vFila_Msg_03 := '';

      -- JCORTEZ 30/07/08 FILA 07
     vFila_Msg_04 := '';

     vFila_Pie_Pagina := IMP_GET_PIE_PAGINA_2(cCodGrupoCia_in ,cCodLocal_in,cNumPedVta_in);


     vMsg_out := C_INICIO_MSG ||
                 vFila_IMG_Cabecera_MF  ||
                  C_FILA_VACIA ||
                   vFila_Cupon ||
                   --vFilaCliente ||
                   vFila_ValorCupon ||
                    vFilaCliente ||
                  vFila_MsgProd ||
                  -- fila de otro descuento
                  replace(vFila_MsgOTRAFORMA_PAGO,'N','')||

                 vFila_Num_Ped ||
                 vFila_Msg_02 ||
                 vFila_Msg_Medico|| --JMIRANDA 05.09.2011
                 vFila_Msg_04 || vFila_Pie_Pagina ||
                 C_FIN_MSG ;





     RETURN vMsg_out;

  END;

 /* ******************************************************************** */
  FUNCTION IMP_GET_PIE_PAGINA_2(cCodGrupoCia_in 	IN CHAR,
                             cCodLocal_in    	IN CHAR,
                						 cNumPedVta_in   	IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(23000):= '';
  vCod_trab varchar2(100):= '';
  vFecha    varchar2(100):= '';
  vlocal    varchar2(100):= '';
  vTipoNumComprobante       VARCHAR2(20) := '';
  vNumPedVta                VARCHAR2(10) := '';
  BEGIN
      BEGIN
		--ERIOS 20.10.2014 Se agrega filtro por COD_CIA
                SELECT
                       NVL(T.NUM_DOC_IDEN,NVL(U.COD_TRAB_RRHH,U.COD_TRAB))    COD_TRAB,
                       TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY HH24:MI:SS')    FECHAHORA,
                       L.COD_LOCAL || '-'||L.DESC_ABREV                  LOCAL,
                       C.NUM_PED_VTA   "COMPROB",
--                       (SELECT MAX(DECODE(CP.TIP_COMP_PAGO,'01','BOL-','02','FAC-','05','TB-') || SUBSTR(CP.SEC_COMP_PAGO,1,3) ||'-'|| SUBSTR(CP.SEC_COMP_PAGO,-7)) FROM VTA_COMP_PAGO CP WHERE CP.COD_GRUPO_CIA = C.COD_GRUPO_CIA AND CP.COD_LOCAL = C.COD_LOCAL AND CP.NUM_PED_VTA = C.NUM_PED_VTA) "COMPROB",
                       C.NUM_PED_VTA
                INTO   vCod_trab,vFecha,vlocal, vTipoNumComprobante, vNumPedVta
                FROM   VTA_PEDIDO_VTA_CAB C,
                       CE_MOV_CAJA M       ,
                       PBL_USU_LOCAL U,
                       PBL_LOCAL L,
                       CE_MAE_TRAB T
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
                AND    M.SEC_USU_LOCAL = U.SEC_USU_LOCAL
                AND    U.COD_TRAB = T.COD_TRAB(+)
				AND  T.COD_CIA = C.COD_CIA
          ;

      EXCEPTION
      WHEN NO_DATA_FOUND THEN
       vCod_trab:='';
       vFecha:= '';
       vlocal := '';
       vTipoNumComprobante := '';
       vNumPedVta          := '';

      END;


      vResultado :=
                    ' <tr> '||
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
                    '</tr>'
                    ;
      vResultado := vResultado ||
                    ' <tr> '||
                    '<td width="50" height="38"><div align="center" class="style1 style3 style8">'||
                    '&nbsp;' ||
                    '</div></td> '||
                    '<td width="160"><div align="center" class="style1 style3 style8"> '||
                    '<div align="center">'||
                    vTipoNumComprobante ||
                    '</div> '||
                    '</div></td> '||
                    '<td width="92"> '||
                    '<div align="center" class="style1 style3 style8">'||
                    '&nbsp;' ||
                    '</div></td> '||
                    '</tr>'
                    ;
   RETURN vResultado;

  END;
  
 /* ********************************************************* */
  
   FUNCTION F_IMPR_VOU_FID_TARJ_NEW(cCodGrupoCia_in   IN CHAR,
                                   cCodCia_in            IN CHAR,
                                   cCodLocal_in      IN CHAR,
                                   cDNI_in         in varchar2,
                                   cUser_in        in char,
                                   tipo           in varchar2
                							 )
  RETURN VARCHAR2
  IS
  
    dni_cli	varchar2(20) := '';
    nom_cli	varchar2(50);
    ape_pat_cli	varchar2(50);
    ape_mat_cli	varchar2(50);
    fec_nac_cli	varchar2(20);
    sexo_cli	VARchar2(20);
    
    nombreCompleto varchar2(200) := '';
    
    --sign too
    
    cell_cli	varchar2(100);
    fono_cli	varchar2(100);
    email	varchar2(100); 
    dir_cli	varchar2(150);
    
    --codigo de barras
    
    vendedor	varchar2(200);
    descLocal	varchar2(50);
    
  vMsg_out varchar2(32767):= '';

  vFila_IMG_Cabecera_MF varchar2(2800):= '';

  vFila_Cupon     varchar2(1000):= '';

  vRuta varchar2(500);

  vFilaCliente varchar2(20000):= '';
  vFilaCliente_2 varchar2(20000):= '';


    v_vCabecera2 VARCHAR2(500);

    codAutorizacion number(3) := 687;
    autorizacion varchar2(4000) := '';
    codCondLegales number(3) := 506;
    condLegales varchar2(4000) := '';
    
    vRuc varchar2(20) := '';
    vRazSoc varchar2(50) := '';
    sexChar varchar2(2) := '';
    
  BEGIN
                                   
    select a.desc_larga
    into autorizacion
    from pbl_tab_gral a
    where a.id_tab_gral = codAutorizacion;
    
    select a.desc_larga
    into condLegales
    from pbl_tab_gral a
    where a.id_tab_gral = codCondLegales;
                                   
--otros datos

    select pc.num_ruc_cia, pc.raz_soc_cia
    into vRuc, vRazSoc
    from pbl_cia pc
    where pc.cod_cia = cCodCia_in;

    select nvl(A.CELL_CLI,'_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _'),
           nvl(A.FONO_CLI,'_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _'),
           nvl(A.EMAIL,'_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _'),
           nvl(a.dir_cli,'_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _')
    INTO  cell_cli,
          fono_cli,
          email,
          dir_cli
    from PBL_CLIENTE A
    WHERE A.DNI_CLI = cDNI_in;
   -- 0015123
    --if dni_cli is null or dni_cli = '' or 
    --  nom_cli is null or nom_cli = '' then
        
        select nvl(a.nom_cli || ' ' || a.ape_pat_cli || ' ' || a.ape_mat_cli,'_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _'),
               nvl(decode(a.sexo_cli,'M','MASCULINO','FEMENINO'),'_ _ '),
               nvl(a.sexo_cli,'M'),
               nvl(to_char(a.fec_nac_cli,'dd/MM/yyyy'),'_ _ _ _ _ '),
               a.dni_cli
        INTO  nombreCompleto,
              sexo_cli,
              sexChar,
              fec_nac_cli,
              dni_cli
        from PBL_CLIENTE A
        WHERE A.DNI_CLI = cDNI_in;
    -- 0015123
    --end if;
    
    --nombreCompleto := nom_cli || ' ' || ape_pat_cli || ' ' || ape_mat_cli;
    

    SELECT B.NOM_USU
    INTO vendedor
    FROM PBL_USU_LOCAL B
    WHERE B.SEC_USU_LOCAL = cUser_in;
    
    SELECT L.DESC_CORTA_LOCAL
    INTO descLocal
    FROM PBL_LOCAL L
    WHERE L.COD_LOCAL = cCodLocal_in;

-------------

   vRuta := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\';
   v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);
  
     -- FILA 01 : CABECERA CON IMAGEN
     vFila_IMG_Cabecera_MF:= '<tr> <td colspan="30"><div align="center" class="style8">'||
                         '<img src=file:'||
                         v_vCabecera2||
                         ' width="236" height="64" class="style3"></div></td>'||
                         ' </tr> ';

     vFila_Cupon := '<tr>' ||
                    '<td height="50" colspan="30">'||
                    '<div align="center" class="style8">'||
                     '<<codigoBarra>></div></td>'||
                     '</tr>';

     vFilaCliente := 
            '<tr>
            <td height="15" width="50" colspan="30" align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>' || vRazSoc || ' - RUC: ' || vRuc || '</B></td>' ||
            '</tr>' ||
     
            '<tr>
            <td height="15" width="50" colspan="1" align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>DNI/CE: </B></td>' ||
            '<td height="20" colspan="29" align="lef" style="font:Arial, Helvetica, sans-serif">
            ' || dni_cli || '</td>' ||
            '</tr>' ||
            
            '<tr>
            <td height="15" width="50" colspan="1" align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>Nombre : </B></td>' ||
            '<td height="20" colspan="29" align="lef" style="font:Arial, Helvetica, sans-serif">
            ' || nombreCompleto || '</td>' ||
            '</tr>' ||
            
            '<tr>
            <td height="15" width="50" colspan="1" align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>FN : </B></td>' ||
            '<td height="20" colspan="29" align="lef" style="font:Arial, Helvetica, sans-serif">
            ' || fec_nac_cli || ' - <b>Sexo:</b> ' || sexChar || '</td>' ||
            '</tr>' ||
            
            '<tr>
            <td height="30" width="50" colspan="1"  align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>Email: </B></td>' ||
            '<td height="30" colspan="29"  align="lef" style="font:Arial, Helvetica, sans-serif">
            ' || email || '</td>' ||
            '</tr>' ||
            
            '<tr>
            <td height="30" width="50" colspan="1"  align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>Celular: </B></td>' ||
            '<td height="30" colspan="29"  align="lef" style="font:Arial, Helvetica, sans-serif">
            ' || cell_cli || '</td>' ||
            '</tr>' ||
            
            '<tr>
            <td height="30" width="50" colspan="1"  align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>Telf. fijo: </B></td>' ||
            '<td height="30" colspan="29"  align="lef" style="font:Arial, Helvetica, sans-serif">
            ' || fono_cli || '</td>' ||
            '</tr>' ||            
            
            '<tr>
            <td height="30" width="50" colspan="1"  align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>Dirección: </B></td>' ||
            '<td height="30" colspan="29"  align="lef" style="font:Arial, Helvetica, sans-serif">
            ' || dir_cli || '</td>' ||
            '</tr>' ||
            
            
            '<tr>
            <td height="30" width="50" colspan="1"  align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>&nbsp</B></td>' ||
            '<td height="30" colspan="29"  align="lef" style="font:Arial, Helvetica, sans-serif">
            ' || '_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _' || '</td>' ||
            '</tr>' ||
            '<tr>
            <td height="30" width="50" colspan="1"  align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>Firma: </B></td>' ||
            '<td height="30" colspan="29"  align="lef" style="font:Arial, Helvetica, sans-serif">
            _________________</td>' ||
            '</tr>' ||
            
            C_FILA_VACIA ||
                       
            vFila_Cupon;
            
            
     vFilaCliente_2 := '<tr>
            <td height="30" colspan="30"  align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>CodV: </B>' || cUser_in || ' - <b>CodL: </b>' || cCodLocal_in || '</td></tr>' ||
            
            '<tr>
            <td height="30" colspan="30"  align="lef" style="font:Arial, Helvetica, sans-serif">
            <B>Fecha actual: </B>' || to_char(sysdate,'dd/MM/yyyy HH12:MI:SS A.M.') || '</td></tr>';

     if tipo = 'A' THEN
       
           vMsg_out := C_INICIO_MSG2 ||
                     vFilaCliente;
                 
       ELSIF tipo = 'B' THEN
       
             vMsg_out := '<tr>
                  <td height="30" colspan="30"  align="justify" style="font:Arial, Helvetica, sans-serif">
                  <font size="-1">' || autorizacion || '</font></td></tr>';
       
         ELSIF tipo = 'C' THEN
           
               vMsg_out := '<tr>
                  <td height="30" colspan="30"  align="justify" style="font:Arial, Helvetica, sans-serif">
                  <font size="-1">' || condLegales || '</font></td></tr>';
         ELSIF tipo = 'D' THEN
               
               vMsg_out := vFilaCliente_2 ||
                           C_FIN_MSG;
               
           END IF;

     RETURN vMsg_out;

  END;

/* ********************************************************* */

  FUNCTION IMP_PROCESA_CUPON_1_NEW(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                               -- cIpServ_in        IN CHAR,
                                cCodCupon_in      IN CHAR
                                --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                                ,cCodCia_in in char
                							 )
  RETURN FARMACURSOR
  IS
  vMsg_out varchar2(32767):= '';

  vFila_IMG_Cabecera_MF varchar2(2800):= '';
  vFila_Num_Ped      varchar2(2800):= '';
  vFila_Msg_02       varchar2(2800):= '';
  vFila_Pie_Pagina   varchar2(2800):= '';
  vFila_Msg_03       varchar2(2800):= '';--JCORTEZ 15/07/08
  vFila_Msg_04       varchar2(2800):= '';--JCORTEZ 30/07/08
  vFila_Msg_Medico   VARCHAR2(2800):= ''; --JMIRANDA 05.09.2011

  vFila_Cupon     varchar2(22767):= '';

  vFila_ValorCupon   varchar2(2800):= '';
  vFila_MsgProd   varchar2(2800):= '';
  vCodCampana CHAR(5);

  vRuta varchar2(500);

  vDatoCliente varchar2(2800):= '';
  vFilaCliente varchar2(2800):= '';

  vTipCupon    CHAR(1);

-- 2010-04-22 JOLIVA: Nuevas variables
  vDescCortaCupon     varchar2(100);
  vMaxCantProd        VTA_CAMPANA_CUPON.UNID_MAX_PROD%TYPE;
  vMaxDctoDiaCadena   PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE;-- VARCHAR2(4);
  vMsjCondiciones     VARCHAR2(6000);
  vMsgDiasValidos     VARCHAR2(128);

  -- dubilluz 09.06.2011
 vCodFPMax  varchar(20);
 vTextFPMax varchar(2000);
 vValorDctoMAX number(10);
 vCountFPActivo number;

 vCodFPMenor  varchar(20);
 vTextFPMenor varchar(2000);
 vValorDctoMenor number(10);
 vFila_MsgOTRAFORMA_PAGO varchar2(2800):= '';
        -- dubilluz 17.05.2012
  vIndCostoPromedio vta_campana_cupon.ind_val_costo_prom%type;
  vIndHasta VARCHAR(10000);
    v_vCabecera2 VARCHAR2(500);
    
    vIdDoc VARCHAR2(100);
    vIpPc  VARCHAR2(100);
    
    vCurDocumento FARMACURSOR;
  BEGIN
    vIdDoc := FARMA_PRINTER.F_GENERA_ID_DOC;
    vIpPc  := FARMA_PRINTER.F_GET_IP_SESS;
    
  

   vRuta := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\';
   v_vCabecera2 := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_RUTA_IMG_CABECERA_2||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);
   select c.cod_campana
   into   vCodCampana
   from   vta_cupon c
   where  c.cod_grupo_cia = cCodGrupoCia_in
   --and    c.cod_local = cCodLocal_in --JCORTEZ 15.08.08
   and    c.cod_cupon = cCodCupon_in;

     --JCORTEZ 24.11.2009
     -- 2010-04-22 JOLIVA: Se obtiene la nueva descripción corta de la campaña
  SELECT A.TIP_CUPON, A.DESC_CORTA_CUPON, A.UNID_MAX_PROD,
         -- dubilluz 17.05.2012
         a.ind_val_costo_prom
  INTO vTipCupon, vDescCortaCupon, vMaxCantProd,vIndCostoPromedio
  FROM VTA_CAMPANA_CUPON A
  WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
  AND A.COD_CAMP_CUPON=vCodCampana;

     -- 2010-04-22 JOLIVA: Se obtiene el máximo descuento diario por DNI
  SELECT LLAVE_TAB_GRAL
  INTO vMaxDctoDiaCadena
  FROM PBL_TAB_GRAL
  WHERE ID_TAB_GRAL = 274;

  vMsjCondiciones := '';
  vMsgDiasValidos:='';

  --- Pedido especial para esta campaña, add by JCT,05-DIC-12
  IF vCodCampana='12725' THEN
   vMsgDiasValidos:='Válido de Martes a Domingo - ';
   --vMsjCondiciones := vMsjCondiciones || ' NO VALIDO PARA DELIVERY - vCodCampana:'||vCodCampana;
   vMsjCondiciones := vMsjCondiciones || '. Descuento diario máximo por persona  de S/.' || vMaxDctoDiaCadena ||'';
   vMsjCondiciones := vMsjCondiciones || CASE WHEN vMaxCantProd = 1 THEN '. Descuento válido para 1 unidad' ELSE ' y/o ' || vMaxCantProd || ' unidades por producto' END;
   vMsjCondiciones := vMsjCondiciones || '(Restricción aplicable a todas las promociones y/o descuentos en toda la cadena)';
   vMsjCondiciones := vMsjCondiciones || '. Stock mínimo 100 unidades. No acumulable con otras promociones, descuentos y/o convenios ni ventas especiales.';



   vMsjCondiciones := vMsjCondiciones ||'Válido sólo en Local Mifarma - Jacarandá: CALL. LOS LAURELES#196 URB.VALLE HERMOSO RESIDENCIAL SANTIAGO SURCO'
   || ' No válido para Delivery ';
   vMsjCondiciones := vMsjCondiciones||' - Obligatorio presentar Cupón y DNI.';
  ELSE
   vMsjCondiciones := vMsjCondiciones || '. Máximo descuento diario por cliente S/.' || vMaxDctoDiaCadena ||'';
   vMsjCondiciones := vMsjCondiciones || CASE WHEN vMaxCantProd = 1 THEN '. Descuento válido para 1 unidad' ELSE ' o ' || vMaxCantProd || ' unidades por producto' END;
   vMsjCondiciones := vMsjCondiciones || '. No es acumulable con otros descuentos.';
   vMsjCondiciones := vMsjCondiciones || ' Válido sólo en locales';
  END IF;

     --JMIRANDA 07/07/09 MODIFICACION DE DIMENSIONES CABECERA
     -- FILA 01 : CABECERA CON IMAGEN
     FARMA_PRINTER.P_AGREGA_LOGO_MARCA(vIdDoc_in => vIdDoc, 
                                            vIpPc_in => vIpPc, 
                                            vCodGrupoCia => cCodGrupoCia_in, 
                                            vCodLocal_in => cCodLocal_in);
     FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
     /*                                       
     vFila_IMG_Cabecera_MF:= '<tr> <td colspan="3"><div align="center" class="style8">'||
                         '<img src=file:'||
                         v_vCabecera2||
                         ' width="236" height="64" class="style3"></div></td>'||
--                         ' width="246" height="48" class="style3"></div></td>'||
--                         ' width="236" height="64" class="style3"></div></td>'||
                         ' </tr> ';*/

     --FV 19.08.08
     --vFila_IMG_Cabecera_MF:= '<tr> <td colspan="3"></td></tr> ';
     
     IF vTipCupon != 'M' THEN
       FARMA_PRINTER.P_AGREGA_BARCODE_EAN13(vIdDoc_in => vIdDoc, 
                                               vIpPc_in => vIpPc, 
                                               vValor_in => cCodCupon_in);
       FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
     END IF;
     
     /*vFila_Cupon := '<tr>' ||
                    '<td height="50" colspan="3">'||
                    '<div align="center" class="style8">'||
                     --'<img src=file://///C:/'||cCodCupon_in||'.jpg width="229" height="107" class="style3"></div></td>
                 --    '<img src=file://///'||vRuta||''||cCodCupon_in||'.jpg width="229" height="107" class="style3"></div></td>'||
--                     '<img src=file:'||vRuta||''||cCodCupon_in||'.jpg width="222" height="100" class="style3"></div></td>'||
                     '<<codigoBarra>></div></td>'|| -- kmoncada 30.07.2014 codigo de barra generado en el farmaventa
                     '</tr>';*/
     --vFila_Cupon := '<tr><td height="50" colspan="3"></td></tr>';
     ----
     
     select count(1)
     into   vCountFPActivo
     from   vta_camp_x_fpago_uso f
     where  f.cod_grupo_cia  = cCodGrupoCia_in
     and    f.cod_camp_cupon = vCodCampana
     and    f.estado = 'A';
     
     if vCountFPActivo > 0 then
       --asume q son 2 casos
       -- dubilluz 09.06.2011
       -- EFECTIVO  y TARJETA (1 sola opcion TODAS y/o una ESPECIFICA)
       select v.cod_forma_pago,v.porc_dcto
         into vCodFPMax, vValorDctoMAX
         from (select f.cod_forma_pago,
                      f.porc_dcto,
                      rank() OVER(ORDER BY porc_dcto desc) orden
                 from vta_camp_x_fpago_uso f
                where f.cod_grupo_cia = cCodGrupoCia_in
                  and f.cod_camp_cupon = vCodCampana
                  and f.estado = 'A') v
        where orden = 1;
        
        SELECT decode(vIndCostoPromedio,'N','Hasta ','')
        INTO   vIndHasta
        FROM   DUAL;

             --imprime como siempre esta dado
        vFila_ValorCupon := vIndHasta||vValorDctoMAX || '% descuento';
        IF vIndHasta IS NULL THEN
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                            vIpPc_in => vIpPc, 
                                            vValor_in => vValorDctoMAX || '% descuento', 
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_3, 
                                            vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                            vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                            vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
        ELSE
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                            vIpPc_in => vIpPc, 
                                            vValor_in => vIndHasta||vValorDctoMAX || '%', 
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_3, 
                                            vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                            vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                            vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
          FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                            vIpPc_in => vIpPc, 
                                            vValor_in => 'descuento', 
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_3, 
                                            vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                            vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                            vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
        END IF;
                                          
        if vCodFPMax = 'E0000' then
           vTextFPMax := 'solo en EFECTIVO';
        else
            if vCodFPMax = 'T0000' then
               vTextFPMax := 'solo en TARJETA DE CREDITO';
            else
               select 'solo en '|| UPPER(fl.desc_corta_forma_pago)
               into   vTextFPMax
               from   vta_forma_pago fl
               where  fl.cod_grupo_cia  = cCodGrupoCia_in
               and    fl.cod_forma_pago =  vCodFPMax;

            end if;
        end if ;
     else
       SELECT decode(vIndCostoPromedio,'N','Hasta ','')
       INTO   vIndHasta
       FROM   DUAL;

       vFila_ValorCupon := vIndHasta||IMP_GET_MONTO_PORCENTAJE_NEW(cCodGrupoCia_in,vCodCampana);
       
       IF vIndHasta IS NULL THEN
         FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                           vIpPc_in => vIpPc, 
                                           vValor_in => vFila_ValorCupon||' descuento', 
                                           vTamanio_in => FARMA_PRINTER.TAMANIO_3, 
                                           vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                           vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                           vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);  
       ELSE
         FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                           vIpPc_in => vIpPc, 
                                           vValor_in => vFila_ValorCupon, 
                                           vTamanio_in => FARMA_PRINTER.TAMANIO_3, 
                                           vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                           vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                           vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
         FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                            vIpPc_in => vIpPc, 
                                            vValor_in => 'descuento', 
                                            vTamanio_in => FARMA_PRINTER.TAMANIO_3, 
                                            vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                            vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                            vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
       END IF;
     end if;
     
     IF TRIM(vDescCortaCupon) IS NOT NULL THEN
       FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
       FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                          vIpPc_in => vIpPc, 
                                          vValor_in => vDescCortaCupon, 
                                          vTamanio_in => FARMA_PRINTER.TAMANIO_2, 
                                          vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                          vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                          vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);  
      
       FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                          vIpPc_in => vIpPc, 
                                          vValor_in => vTextFPMax,
                                          vTamanio_in => FARMA_PRINTER.TAMANIO_2, 
                                          vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                          vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                          vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);  
                                          
  /*       vFila_ValorCupon := vFila_ValorCupon || '
          <tr>
            <td height="20" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:15px">
    		<B>'|| vDescCortaCupon ||'</B>	</td>
          </tr>'||
         '<tr>'||
         '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:15px">'||
         --font:Arial, Helvetica, sans-serif;font-size:15px
    		 '<B>'|| vTextFPMax ||'</B>	</td>'||
         '</tr>';*/
     else
       FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                          vIpPc_in => vIpPc, 
                                          vValor_in => vTextFPMax,
                                          vTamanio_in => FARMA_PRINTER.TAMANIO_2, 
                                          vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                          vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                          vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
        /* vFila_ValorCupon := vFila_ValorCupon ||
         '<tr>'||
         '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:15px">'||
         --font:Arial, Helvetica, sans-serif;font-size:15px
    		 '<B>'|| vTextFPMax ||'</B>	</td>'||
         '</tr>';*/
     END IF;
     
     
     BEGIN
       select nvl(l.nom_cli,'') || ' ' || nvl(l.ape_pat_cli,'') || ' ' || nvl(l.ape_mat_cli,'')
       into   vDatoCliente
       from   pbl_cliente l,
              fid_tarjeta_pedido t
       where  t.cod_grupo_cia = cCodGrupoCia_in
       and    t.cod_local = cCodLocal_in
       and    t.num_pedido = cNumPedVta_in
       and    t.dni_cli = l.dni_cli;

       vDatoCliente := trim(vDatoCliente);
       vDatoCliente := 'Para: '||vDatoCliente;

       /*vFilaCliente := '
              <tr>
              <td height="30" colspan="3"  align="lef" style="font:Arial, Helvetica, sans-serif"><B>'
              ||vDatoCliente||'</B></td> </tr>';*/
       FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                         vIpPc_in => vIpPc, 
                                         vValor_in =>  vDatoCliente, 
                                         vTamanio_in =>  FARMA_PRINTER.TAMANIO_1, 
                                         vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                         vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                         vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);

     EXCEPTION
     WHEN OTHERS THEN
       NULL;--vFilaCliente := '';
     END;
     
     FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
     FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                       vIpPc_in => vIpPc, 
                                       vValor_in => IMP_GET_MSG_CAMP_PROD(cCodGrupoCia_in,vCodCampana), 
                                       vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                       vAlineado_in => FARMA_PRINTER.ALING_IZQ, 
                                       vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                       vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
/*     vFila_MsgProd := '
            <tr>
            <td height="30" colspan="3"  align="lef" style="font:Arial, Helvetica, sans-serif"><B>'
            ||IMP_GET_MSG_CAMP_PROD(cCodGrupoCia_in,vCodCampana)||'</B></td> </tr>';
*/

      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
      

      if vCountFPActivo > 0 then
        begin
          --dato MINIMO
          select f.cod_forma_pago,f.porc_dcto
          into   vCodFPMenor,vValorDctoMenor
          from   vta_camp_x_fpago_uso f
          where  f.cod_grupo_cia  = cCodGrupoCia_in
          and    f.cod_camp_cupon = vCodCampana
          and    f.cod_forma_pago not in (vCodFPMax)
          and    f.estado = 'A';

          if vCodFPMenor = 'E0000' then
           vTextFPMenor := ' con EFECTIVO';
          else
            if vCodFPMenor = 'T0000' then
               vTextFPMenor := ' con cualquier TARJETA DE CREDITO';
            else
               select ' con '|| fl.desc_corta_forma_pago
               into   vTextFPMenor
               from   vta_forma_pago fl
               where  fl.cod_grupo_cia  = cCodGrupoCia_in
               and    fl.cod_forma_pago =  vCodFPMenor;

            end if;
          end if ;


          vFila_MsgOTRAFORMA_PAGO := vValorDctoMenor || '% descuento '||vTextFPMenor;

          if vValorDctoMenor = 0 then
            vFila_MsgOTRAFORMA_PAGO := '';
          end if;

        exception
          when no_data_found then
              vCodFPMenor := 'N';
              vFila_MsgOTRAFORMA_PAGO := 'N';
          end;
          
          vFila_MsgOTRAFORMA_PAGO := replace(vFila_MsgOTRAFORMA_PAGO ,'N','');
          
          IF vFila_MsgOTRAFORMA_PAGO IS NOT NULL THEN
            FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                              vIpPc_in => vIpPc, 
                                              vValor_in => vFila_MsgOTRAFORMA_PAGO, 
                                              vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                              vAlineado_in => FARMA_PRINTER.ALING_IZQ, 
                                              vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                              vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
          END IF;
      end if;
      
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => 'En su próxima Compra', 
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                        vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
      
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => RPAD(cCodCupon_in,24,' ')||LPAD('Nro: '||cNumPedVta_in,24,' '), 
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ,
                                        vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
      -- FILA 03 : NUMERO DE PEDIDO
/*     vFila_Num_Ped := '<tr><td>'||cCodCupon_in||'</td><td colspan="2"><div align="right" class="style1 style3 style8">'||
                      '<span class="style9"><strong>'||
                      'Nro:</strong> '||cNumPedVta_in|| '</span></div></td>'||
                      '</tr> ';*/

      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
      
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => IMP_GET_MSG_02_CONSEJO_NEW(cCodGrupoCia_in,cCodCia_in,cCodLocal_in), 
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                        vAlineado_in => FARMA_PRINTER.ALING_CEN,
                                        vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);

  /*    -- FILA 05 : MENSAJE 02
     vFila_Msg_02 := --'<tr><td height="44" colspan="3" align="center" style="font:oblique, Helvetica, sans-serif">'
                     '<tr><br><tr><td colspan="3" align="center" style="font:oblique, Helvetica, sans-serif">'
                     ||
                     IMP_GET_MSG_02_CONSEJO(cCodGrupoCia_in,cCodCia_in,cCodLocal_in) ||
                     '</td>'||
                     '</tr>';*/
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => 'NO SE AUTOMEDIQUE', 
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                        vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                        vNegrita_in => FARMA_PRINTER.BOLD_ACT,
                                        vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => 'Consulte siempre a su Médico',
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                        vAlineado_in => FARMA_PRINTER.ALING_CEN, 
                                        vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
/*      --JMIRANDA 05.09.2011
           vFila_Msg_Medico := '<tr><td  colspan="3" align="center" style="font:oblique, Helvetica, sans-serif">'||
                     '<b>NO SE AUTOMEDIQUE</b><br>Consulte siempre con su M&eacute;dico'||
                     '</td></tr>';
*/


      vFila_Msg_04 := vMsgDiasValidos || IMP_GET_MSG_04_CONSEJO(cCodGrupoCia_in,vCodCampana,cCodCupon_in) || vMsjCondiciones;
      FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
      FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => vFila_Msg_04,
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_1, 
                                        vAlineado_in => FARMA_PRINTER.ALING_JUSTI, 
                                        vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
      
/*      -- JCORTEZ 30/07/08 FILA 07
     vFila_Msg_04 := --'<tr><td height="44" colspan="3" align="lef" style="font:oblique, Helvetica, sans-serif">'
                     '<tr><td  colspan="3" align="lef" style="font:oblique, Helvetica, sans-serif; font-size:9px">'
                     ||vMsgDiasValidos||
                     IMP_GET_MSG_04_CONSEJO(cCodGrupoCia_in,vCodCampana,cCodCupon_in) || vMsjCondiciones ||
                     '
                     </td> '||
                     '</tr>';*/



      -- JCORTEZ 15/07/08 FILA 06 : MENSAJE CUPON
     vFila_Msg_03 := --'<td height="44" colspan="3" align="lef" style="font:oblique, Helvetica, sans-serif">'
                     '<td  colspan="3" align="lef" style="font:oblique, Helvetica, sans-serif">'
                     ||
                     IMP_GET_MSG_03_CONSEJO(cCodGrupoCia_in,vCodCampana) ||
                     '
                     </td> '||
                     '</tr>';

      
     vFila_Pie_Pagina := IMP_GET_PIE_PAGINA_NEW(cCodGrupoCia_in ,cCodLocal_in,cNumPedVta_in);

     FARMA_PRINTER.P_AGREGA_LINEA_BLANCO(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
     FARMA_PRINTER.P_AGREGA_TEXTO(vIdDoc_in => vIdDoc, 
                                        vIpPc_in => vIpPc, 
                                        vValor_in => vFila_Pie_Pagina,
                                        vTamanio_in => FARMA_PRINTER.TAMANIO_0, 
                                        vAlineado_in => FARMA_PRINTER.ALING_IZQ, 
                                        vInterlineado_in => FARMA_PRINTER.LINEADO_DOBLE);
     

/*
     

       if vCountFPActivo > 0 then
               --asume q son 2 casos
               -- dubilluz 09.06.2011
               -- EFECTIVO  y TARJETA (1 sola opcion TODAS y/o una ESPECIFICA)
               select v.cod_forma_pago,v.porc_dcto
                 into vCodFPMax, vValorDctoMAX
                 from (select f.cod_forma_pago,
                              f.porc_dcto,
                              rank() OVER(ORDER BY porc_dcto desc) orden
                         from vta_camp_x_fpago_uso f
                        where f.cod_grupo_cia = cCodGrupoCia_in
                          and f.cod_camp_cupon = vCodCampana
                          and f.estado = 'A') v
                where orden = 1;


              -- El dato maximo a imprimir
              if vCodFPMax = 'E0000' then
                 vTextFPMax := 'solo en EFECTIVO';
              else
                  if vCodFPMax = 'T0000' then
                     vTextFPMax := 'solo en TARJETA DE CREDITO';
                  else
                     select 'solo en '|| UPPER(fl.desc_corta_forma_pago)
                     into   vTextFPMax
                     from   vta_forma_pago fl
                     where  fl.cod_grupo_cia  = cCodGrupoCia_in
                     and    fl.cod_forma_pago =  vCodFPMax;

                  end if;
              end if ;

               begin
              --dato MINIMO
               select f.cod_forma_pago,f.porc_dcto
               into   vCodFPMenor,vValorDctoMenor
               from   vta_camp_x_fpago_uso f
               where  f.cod_grupo_cia  = cCodGrupoCia_in
               and    f.cod_camp_cupon = vCodCampana
               and    f.cod_forma_pago not in (vCodFPMax)
               and    f.estado = 'A';

               if vCodFPMenor = 'E0000' then
                 vTextFPMenor := ' con EFECTIVO';
              else
                  if vCodFPMenor = 'T0000' then
                     vTextFPMenor := ' con cualquier TARJETA DE CREDITO';
                  else
                     select ' con '|| fl.desc_corta_forma_pago
                     into   vTextFPMenor
                     from   vta_forma_pago fl
                     where  fl.cod_grupo_cia  = cCodGrupoCia_in
                     and    fl.cod_forma_pago =  vCodFPMenor;

                  end if;
              end if ;


             vFila_MsgOTRAFORMA_PAGO := '
                    <tr>
                    <td height="30" colspan="3"  align="lef" style="font:Arial, Helvetica, sans-serif"><B>'
                    ||vValorDctoMenor || '% descuento '||vTextFPMenor||'</B></td> </tr>';

             if vValorDctoMenor = 0 then
                vFila_MsgOTRAFORMA_PAGO := '';
             end if;

               exception
               when no_data_found then
                    vCodFPMenor := 'N';
                    vFila_MsgOTRAFORMA_PAGO := 'N';
               end;

         \*
         DUBILLUZ  17.05.2012
         IND_VAL_COSTO_PROM
         indicador S: permite cobrar por debajo del costo promedio,
                   N:no permite vender debajo del costo promedio
         *\
         SELECT decode(vIndCostoPromedio,'N','Hasta ','')
         INTO   vIndHasta
         FROM   DUAL;

             --imprime como siempre esta dado
         vFila_ValorCupon := '<tr>'||
                             '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:30px">'||
                        		 --'<B>'|| vValorDctoMAX || '% descuento'||'</B>	</td>'||
                             -- DUBILLUZ 17.05.2012
                             '<B>'||vIndHasta||vValorDctoMAX || '% descuento'||'</B>	</td>'||

                             '</tr>';
                             \*
                             '<tr>'||
                             '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:15px">'||
                                                                               --font:Arial, Helvetica, sans-serif;font-size:15px
                        		 '<B>'|| vTextFPMax ||'</B>	</td>'||
                             '</tr>'
                             *\




        else
          
          --imprime como siempre esta dado

        \*
         10-ABR-15  se modifica para agregar hasta cuando sea aplicable, pedido Julio
         vFila_ValorCupon := '<tr>'||
                             '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:30px">'||
                        		 '<B>'||IMP_GET_MONTO_PORCENTAJE(cCodGrupoCia_in,vCodCampana)||'</B>	</td>'||
                             '</tr>';
        *\
         SELECT decode(vIndCostoPromedio,'N','Hasta ','')
         INTO   vIndHasta
         FROM   DUAL;

         vFila_ValorCupon := '<tr>'||
                             '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:30px">'||
                        		 '<B>'||vIndHasta||IMP_GET_MONTO_PORCENTAJE(cCodGrupoCia_in,vCodCampana)||'</B>	</td>'||
                             '</tr>';

        end if;


     --fin  09.06.2011

     -- 2010-04-22 JOLIVA: Se muestra la descripción corta de la campaña
     IF TRIM(vDescCortaCupon) IS NOT NULL THEN
         vFila_ValorCupon := vFila_ValorCupon || '
          <tr>
            <td height="20" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:15px">
    		<B>'|| vDescCortaCupon ||'</B>	</td>
          </tr>'||
         '<tr>'||
         '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:15px">'||
         --font:Arial, Helvetica, sans-serif;font-size:15px
    		 '<B>'|| vTextFPMax ||'</B>	</td>'||
         '</tr>';
     else
         vFila_ValorCupon := vFila_ValorCupon ||
         '<tr>'||
         '<td height="30" colspan="3"  align="center" style="font:Arial, Helvetica, sans-serif;font-size:15px">'||
         --font:Arial, Helvetica, sans-serif;font-size:15px
    		 '<B>'|| vTextFPMax ||'</B>	</td>'||
         '</tr>';
     END IF;

     
*/

     --JCORTEZ 24/11/2009 Si la campaña es de tipo monto no se muestra imagen de codigo de barra
     
     
     
     
     /*
     
     vMsg_out := C_INICIO_MSG ||
                 vFila_IMG_Cabecera_MF  ||
                  C_FILA_VACIA ||
                   vFila_Cupon ||
                   --vFilaCliente ||
                   vFila_ValorCupon ||
                    vFilaCliente ||
                  vFila_MsgProd ||
                  -- fila de otro descuento
                  replace(vFila_MsgOTRAFORMA_PAGO ,'N','')||
                  '<tr>
                  <td height="21" colspan="3" align="left"
                          style="font:Arial, Helvetica, sans-serif">
                          En su proxima Compra
                  </td>
                  </tr>' ||
                -- vFila_Msg_03 ||

                 vFila_Num_Ped ||
                 vFila_Msg_02 ||
                 vFila_Msg_Medico|| --JMIRANDA 05.09.2011
                 vFila_Msg_04 || vFila_Pie_Pagina ||
                 C_FIN_MSG ;*/


     vCurDocumento:= FARMA_PRINTER.F_CUR_OBTIENE_DOC_IMPRIMIR(vIdDoc_in => vIdDoc, vIpPc_in => vIpPc);
     RETURN vCurDocumento;

  END;
  
  FUNCTION IMP_GET_PIE_PAGINA_NEW(cCodGrupoCia_in 	IN CHAR,
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
      
      vResultado := RPAD(vCod_trab,21,' ')||' '||vFecha ||' ' ||LPAD(vlocal,20,' ');
/*      vResultado := ' <tr> '||
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
                    '</tr>';*/
   RETURN vResultado;

  END;
  
  FUNCTION IMP_PROCESA_CUPON_NEW(cCodGrupoCia_in 	IN CHAR,
                                cCodLocal_in    	IN CHAR,
                								cNumPedVta_in   	IN CHAR,
                                cIpServ_in        IN CHAR,
                                cCodCupon_in      IN CHAR
                                --Se agrega este parametro para obtener la ruta de la imagen   Autor Luigy Terrazos Fecha 04/03/2013
                                ,cCodCia_in in char
                							 )
  RETURN FARMACURSOR
  IS
  vMsg_out varchar2(32767):= '';
  vDocumento FARMACURSOR;

  BEGIN
        
       IF IMP_GET_CUPON_SORTEO (cCodGrupoCia_in, cCodLocal_in, cCodCupon_in) = 'N' THEN
          vDocumento := IMP_PROCESA_CUPON_1_NEW (cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, cCodCupon_in, cCodCia_in);
       ELSE
         NULL;
          --vMsg_out := IMP_PROCESA_CUPON_2 (cCodGrupoCia_in, cCodLocal_in, cNumPedVta_in, cIpServ_in, cCodCupon_in, cCodCia_in);
       END IF;

     RETURN vDocumento;

  END;
  
  FUNCTION IMP_GET_MSG_02_CONSEJO_NEW(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(13000):= '';
	v_vNomMarca PBL_MARCA_GRUPO_CIA.NOM_MARCA%TYPE;
  BEGIN
      BEGIN
		--ERIOS 2.4.3 Se muestra el nombre de la marca.
		SELECT NOM_MARCA
		INTO v_vNomMarca
		FROM PBL_MARCA_GRUPO_CIA
		WHERE (COD_GRUPO_CIA,COD_MARCA) = (SELECT COD_GRUPO_CIA,COD_MARCA
			FROM PBL_LOCAL
			WHERE COD_GRUPO_CIA = cCodGrupoCia_in
			AND cod_local = cCodLocal_in
			);
		
      SELECT REPLACE(TRIM(T.LLAVE_TAB_GRAL),'{NOM_MARCA}',v_vNomMarca)
      INTO   vResultado
      FROM   PBL_TAB_GRAL T
      WHERE  T.COD_APL = 'PTO_VENTA'
      AND    T.COD_TAB_GRAL = 'MSG02_CONSEJOS'
      AND    T.ID_TAB_GRAL = 199;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := '';
      END;
      
      vResultado := REPLACE(vResultado,'&quot;','"');
   RETURN vResultado;
  END;
  
  
  /* *********************************************************************** */
 FUNCTION IMP_GET_MONTO_PORCENTAJE_NEW(cCodGrupoCia_in 	IN CHAR,
                                   cCodCapana_in    IN CHAR
                							    )
 RETURN VARCHAR2
 IS
 vMensajePorcMonto varchar2(2000);
 nMonto number;
 vTipoCupon char(1);
 begin



             SELECT C.VALOR_CUPON,C.TIP_CUPON
             INTO   nMonto,vTipoCupon
             FROM   VTA_CAMPANA_CUPON C
             WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
             AND    C.COD_CAMP_CUPON = cCodCapana_in;

             if vTipoCupon = 'P' then
                vMensajePorcMonto := nMonto || '%';
             elsif vTipoCupon = 'M' then
                --vMensajePorcMonto := 'S/.'||nMonto || ' descuento';--JCORTEZ 24/11/2009 por solicitud
                vMensajePorcMonto := nMonto || '%';
             end if;



    return vMensajePorcMonto;
 END;

END;
/
