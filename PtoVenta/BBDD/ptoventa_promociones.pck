CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_PROMOCIONES" AS

	TYPE FarmaCursor IS REF CURSOR;
  C_INDICADOR_NO CHAR(1) := 'N';
  C_INDICADOR_SI CHAR(1) := 'S';
  C_ESTADO_ACTIVO CHAR(1) := 'A';



  --PROCEDURE PROMO_LISTA_PROMOCIONES;

  --Descripcion: Obtiene el listado de las promociones
  --Fecha       Usuario		       Comentario
  --13/06/2007  Jorge Cortez     Creacion
  --28/02/2008 DUBILLUZ MODIFICACION
  FUNCTION PROMOCIONES_LISTADO(cCodGrupoCia_in   IN CHAR,
    		                       cCodLocal_in	     IN CHAR,
                               vIsFidelizado_in  IN CHAR DEFAULT 'N')
  RETURN FARMACURSOR;

  --07/08/2014  ERIOS     Se agregan parametros de local
 PROCEDURE PROMO_LISTA_VENDEDOR(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR);
  --PROCEDURE PROMO_LISTA_PROMOCIONES;

  --Descripcion: Obtiene el listado de las promociones por producto
  --Fecha       Usuario		       Comentario
  --22/06/2007  Jorge Cortez     Creacion
  --28/02/2008 DUBILLUZ MODIFICACION
  FUNCTION PROMOCIONES_LISTADOPORPRODUCTO(cCodGrupoCia_in    IN CHAR,
                		                      cCodLocal_in	     IN CHAR,
                                          vCodProd           IN CHAR,
                                          vIsFidelizado_in   IN CHAR DEFAULT 'N')
   RETURN FARMACURSOR;

  --Descripcion: Obtiene el listado de las promociones
  --Fecha       Usuario		       Comentario
  --13/06/2007  Jorge Cortez     Creacion
  FUNCTION PROMOCIONES_DETALLE
  RETURN FARMACURSOR;

  --Descripcion: Obtiene el listado de las promociones del paquete 1
  --Fecha       Usuario		       Comentario
  --13/06/2007  Jorge Cortez     Creacion
  --28/02/2008 DUBILLUZ MODIFICACION
  FUNCTION PROMOCIONES_LISTADO_PAQUETE1(cCodGrupoCia_in IN CHAR,
  		   				                             cCodLocal_in	 IN CHAR,
                                             vCodProm IN CHAR)
  RETURN FARMACURSOR;

  --Descripcion: Obtiene el listado de las promociones del paquete 2
  --Fecha       Usuario		       Comentario
  --13/06/2007  Jorge Cortez     Creacion
  --28/02/2008 DUBILLUZ MODIFICACION
  FUNCTION PROMOCIONES_LISTADO_PAQUETE2(cCodGrupoCia_in IN CHAR,
  		   				                             cCodLocal_in	 IN CHAR,
                                             vCodProm IN CHAR)
  RETURN FARMACURSOR;

  --Descripcion: Obtiene el listado de ambos paquetes
  --Fecha       Usuario		       Comentario
  --18/06/2007  Jorge Cortez     Creacion
  --28/02/2008 DUBILLUZ MODIFICACION
  FUNCTION PROMOCIONES_LISTADO_PAQUETES(cCodGrupoCia_in IN CHAR,
  		   				                         cCodLocal_in	 IN CHAR,
                                         vCodProm IN CHAR)
  RETURN FARMACURSOR;

  --Descripcion: Verfica si se permite la venta de promocion en el local
  --Fecha       Usuario		  Comentario
  --27/02/2008  dubilluz     Creacion
  FUNCTION PROMOCIONES_PERMITE_EN_LOCAL(cCodGrupoCia_in IN CHAR,
  		   				                        cCodLocal_in	  IN CHAR,
                                        vCodProm        IN CHAR)
  RETURN  CHAR;

  --Descripcion: Procesa los pack que dan productos regalo
  -- para añadirlo en el pedido
  --Fecha       Usuario		  Comentario
  --11/06/2008  dubilluz     Creacion
  PROCEDURE PROCESO_PROM_REGALO(cCodGrupoCia_in IN CHAR,
  		   				                cCodLocal_in	  IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                cSecUsu_in      IN CHAR,
                                cLogin_in       IN CHAR,
                                cIp_in          IN CHAR);



   PROCEDURE VTA_P_GRABA_PROM_NO_AUTOMAT(cCodGrupoCia_in 	 	  IN CHAR,
                                      cCodLocal_in    	 	  IN CHAR,
                            				  cNumPedVta_in   	 	  IN CHAR,
                                       cCodProm                	  IN CHAR,
                            				  nCantAtendida_in	 	  IN NUMBER,
                            				  cUsuCreaPedVtaDet_in	IN CHAR,
                                      -- KMONCADA 2016.01.22 GENERA PROFORMA [LOCAL M]
                                      cGeneraProforma_in  IN  CHAR DEFAULT 'N'
                                      );
/*------------------------------------------------------------------------------------------------------------------
GOAL : Actualizar el Porcentaje de Descuento para Aquellos Regalos que aplica PRECIO FIJO x Producto
Ammedments:
When          Who      What
22-AGO-13     TCT      Create
--------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_UPD_DSCTO_REGA_PROD(cCodGrupoCia_in IN CHAR,
  		   				                         cCodLocal_in	 IN CHAR,
                                         vCodProd IN CHAR);
/*------------------------------------------------------------------------------------------------------------------
GOAL : Actualizar el Porcentaje de Descuento para Aquellos Regalos que aplica PRECIO FIJO
Ammedments:
--------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_UPDATE_DSCTO_REGA_PROM(cCodGrupoCia_in IN CHAR,
  		   				                         cCodLocal_in	 IN CHAR,
                                         vCodProm IN CHAR);
/****************************************************************************************************************/

 --Descripcion: Obtener el máximo número de elementos para vender.
  --Fecha       Usuario		  Comentario
  --22/10/2014  ASOSA     Creacion
FUNCTION GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in IN CHAR,
                                                             cCodLocal_in IN CHAR,
                                                             cCodProd_in IN CHAR)
                                                             
    RETURN NUMBER;
    
/****************************************************************************************************************/

      --Descripcion: Determinar si un producto es parte de algun pack.
  --Fecha       Usuario		  Comentario
  --23/10/2014  ASOSA     Creacion
FUNCTION GET_IND_PROD_IN_PACK(cCodGrupoCia_in IN CHAR,
                                                             cCodLocal_in IN CHAR,
                                                             cCodProd_in IN CHAR)
    RETURN CHAR;

END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_PROMOCIONES" AS

  PROCEDURE PROMO_LISTA_VENDEDOR(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR)

  AS
    mesg_body VARCHAR2(20000);
    v_cod_local CHAR(3);
    v_cod_grupo_cia CHAR(3);
    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_CAMBIOS;
    CCReceiverAddress VARCHAR2(120) ;
    v_vDescLocal VARCHAR2(120);


    CURSOR curPromociones(v_cod_local CHAR,v_cod_grupo_cia CHAR) IS
        SELECT LOCAL.COD_LOCAL ||' ' || LOCAL.Desc_Corta_Local TIENDA,
               to_char(det.fec_crea_ped_vta_det,'MONTH') ||' - ' || to_char(det.fec_crea_ped_vta_det,'YYYY')MES,
               --lp.cod_prod CODIGO,
               --lp.desc_prod DESCRIPCION,
               usu.nom_usu ||' '|| usu.ape_pat||' '||usu.ape_mat USUARIO,
               SUM(mp.val_promocion) ACUMULADO
        FROM   vta_pedido_vta_det det,
               pbl_local LOCAL,
               pbl_mae_promociones mp,
               lgt_prod lp,
               pbl_usu_local usu
        WHERE  det.cod_grupo_cia = v_cod_grupo_cia
        AND    det.cod_local = v_cod_local
        AND    det.fec_crea_ped_vta_det BETWEEN '01/01/2006' AND trunc(SYSDATE)
        AND    det.cod_grupo_cia = LOCAL.Cod_Grupo_Cia
        AND    det.cod_local = LOCAL.Cod_Local
        AND    det.cod_prod = mp.cod_prod
        AND    det.cod_prod = lp.cod_prod
        AND    det.sec_usu_local = usu.sec_usu_local
        GROUP BY LOCAL.COD_LOCAL ||' ' || LOCAL.Desc_Corta_Local ,
                 to_char(det.fec_crea_ped_vta_det,'MONTH') ||' - ' || to_char(det.fec_crea_ped_vta_det,'YYYY'),
                 --lp.cod_prod,
                 --lp.desc_prod,
                 usu.nom_usu ||' '|| usu.ape_pat||' '||usu.ape_mat
        ORDER BY 2,3 ;
      v_rCurPromociones curPromociones%ROWTYPE;

      BEGIN

        --ERIOS 2.4.5 Cambios proyecto Conveniencia

        SELECT COD_LOCAL ||' - '|| DESC_LOCAL
               INTO v_vDescLocal
        FROM   PBL_LOCAL
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_LOCAL = cCodLocal_in;

                mesg_body := mesg_body||'<h4>REPORTE DE PUNTOS POR VENTAS POR PRODUCTO LABORATORIO YOBEL</h4>';
                mesg_body := mesg_body||'<table style="text-align: left; width: 95%;" border="1"';
                mesg_body := mesg_body||' cellpadding="2" cellspacing="1">';
                mesg_body := mesg_body||'  <tbody>';
                mesg_body := mesg_body||'    <tr>';
                mesg_body := mesg_body||'      <th><small>LOCAL</small></th>';
                mesg_body := mesg_body||'      <th><small>FECHA</small></th>';
  --              mesg_body := mesg_body||'      <th><small>DESCRIPCION</small></th>';
                mesg_body := mesg_body||'      <th><small>USUARIO</small></th>';
                mesg_body := mesg_body||'      <th><small>ACUMULADO</small></th>';
                mesg_body := mesg_body||'    </tr>';

                --CREAR CUERPO MENSAJE;
                FOR v_rCurPromociones IN curPromociones (cCodLocal_in, cCodGrupoCia_in)
                LOOP
                  mesg_body := mesg_body||'   <tr>'||
                                          '      <td><small>'||v_rCurPromociones.Tienda||'</small></td>'||
                                          '      <td><small>'||v_rCurPromociones.Mes||'</small></td>'||
                                          --'      <td><small>'||v_rCurPromociones.Descripcion||'</small></td>'||
                                          '      <td><small>'||v_rCurPromociones.Usuario||'</small></td>'||
                                          '      <td><small>'||v_rCurPromociones.Acumulado||'</small></td>'||
                                          '   </tr>';
                END LOOP;
                --FIN HTML
                mesg_body := mesg_body||'  </tbody>';
                mesg_body := mesg_body||'</table>';
                mesg_body := mesg_body||'<br>';


                  FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                           ReceiverAddress,
                                           'REPORTE DE PROMOCIONES',
                                           'PROMOCIONES',--'EXITO',
                                           mesg_body,
                                           CCReceiverAddress,
                                           FARMA_EMAIL.GET_EMAIL_SERVER,
                                           true);
    END;


   /****************************************************************************/
  --LISTADO DE PROMOCIONES
   FUNCTION PROMOCIONES_LISTADO(cCodGrupoCia_in IN CHAR,
    		                        cCodLocal_in	   IN CHAR,
                                vIsFidelizado_in   IN CHAR DEFAULT 'N')
    RETURN FARMACURSOR
    IS
        CURPROM FARMACURSOR;
      BEGIN
        OPEN CURPROM FOR
/*            SELECT COD_PROM  || 'Ã' ||
                   DESC_LARGA_PROM || 'Ã' ||
                   DESC_CORTA_PROM || 'Ã' ||
                   COD_PAQUETE_1 || 'Ã' ||
                   COD_PAQUETE_2

            FROM  VTA_PROMOCION
            WHERE ESTADO='A';
*/

/*          SELECT P.COD_PROM   || 'Ã' ||
                 P.DESC_LARGA_PROM || 'Ã' ||
                 --TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO((V1.SUMA+V2.SUMA)),'999,990.00') || 'Ã' || --ERIOS 14/04/2008    --JCHAVEZ 29102009  precio redondeado
                 TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO((V1.SUMA+V2.SUMA)),'999,990.000') || 'Ã' || --ERIOS 14/04/2008    --JCHAVEZ 29102009  precio redondeado --ASOSA, 20.05.2010, a 3 decimales
                 TO_CHAR(NVL(V7.STK_PROM,0),'999,990') || 'Ã' || --ERIOS 16/04/2008
                 DECODE(       (SELECT count(1)
                  FROM   VTA_PROMOCION A,
                         VTA_PROD_PAQUETE C,
                         LGT_PROD_LOCAL F
                   \*  WHERE (SELECT SYSDATE  FROM DUAL)
                     BETWEEN
                        TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                   AND  TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                    WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                  AND A.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                  AND F.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    F.COD_LOCAL = cCodLocal_in
                  AND    C.COD_PROD=F.COD_PROD
                  AND (   A.COD_PAQUETE_1 = C.COD_PAQUETE
                    OR    A.COD_PAQUETE_2 = C.COD_PAQUETE)
                  and    MOD(C.CANTIDAD * F.VAL_FRAC_LOCAL,C.VAL_FRAC) <> 0
                  and    a.cod_prom = p.cod_prom),0,' ','NO APLICABLE')|| 'Ã' ||
                 DECODE(       (SELECT count(1)
                  FROM   VTA_PROMOCION A,
                         VTA_PROD_PAQUETE C,
                         LGT_PROD_LOCAL F
                       \*   WHERE (SELECT SYSDATE  FROM DUAL)
                          BETWEEN
                           TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                      AND  TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                       WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                       AND A.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                  AND  F.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    F.COD_LOCAL = cCodLocal_in
                  AND    C.COD_PROD=F.COD_PROD
                  AND (   A.COD_PAQUETE_1 = C.COD_PAQUETE
                    OR    A.COD_PAQUETE_2 = C.COD_PAQUETE)
                  and    MOD(C.CANTIDAD * F.VAL_FRAC_LOCAL,C.VAL_FRAC) <> 0
                  and    a.cod_prom = p.cod_prom),0,'S','N')|| 'Ã' ||
                 P.DESC_CORTA_PROM || 'Ã' ||
                 P.COD_PAQUETE_1 || 'Ã'||
                 P.COD_PAQUETE_2 || 'Ã'||
                 NVL(P.DESC_LARGA_PROM_1,' ')
          FROM
             VTA_PROMOCION P,
             (  --SELECT SUM(((F.VAL_PREC_VTA)* (F.VAL_FRAC_LOCAL)*(C.CANTIDAD)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                      --SELECT SUM((round(F.VAL_PREC_VTA,2)* (F.VAL_FRAC_LOCAL)*(C.CANTIDAD/C.VAL_FRAC)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                      SELECT SUM((round(F.VAL_PREC_VTA,3)* (F.VAL_FRAC_LOCAL)*(C.CANTIDAD/C.VAL_FRAC)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,  --ASOSA, 20.05.2010
                           a.cod_prom promo
                    FROM   VTA_PROMOCION A,
                           VTA_PROD_PAQUETE C,
                           LGT_PROD_LOCAL F,
                           LGT_PROD D,
                           LGT_LAB E
                    \* WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                           TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                      AND  TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                       WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                       AND A.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                     AND   C.COD_PROD=F.COD_PROD
                     AND   C.COD_PROD=D.COD_PROD
                     AND   D.COD_LAB=E.COD_LAB
                    AND d.cod_grupo_cia=cCodGrupoCia_in
                    AND d.cod_grupo_cia=f.cod_grupo_cia
                    AND f.cod_local=cCodLocal_in
                    AND A.COD_PAQUETE_1(+)=C.COD_PAQUETE
                     group by a.cod_prom
                   )v1,
              (  -- SELECT SUM(((F.VAL_PREC_VTA)*(F.VAL_FRAC_LOCAL)*(C.CANTIDAD)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                    SELECT SUM((round(F.VAL_PREC_VTA,2)*(F.VAL_FRAC_LOCAL)*(C.CANTIDAD/C.VAL_FRAC)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                           a.cod_prom promo
                    FROM   VTA_PROMOCION A,
                           VTA_PROD_PAQUETE C,
                           LGT_PROD_LOCAL F,
                           LGT_PROD D,
                           LGT_LAB E
                 \*    WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                       TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                      AND  TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                     WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                     AND A.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                     AND C.COD_PROD=F.COD_PROD
                     AND   C.COD_PROD=D.COD_PROD
                     AND   D.COD_LAB=E.COD_LAB
                    AND d.cod_grupo_cia=cCodGrupoCia_in
                    AND d.cod_grupo_cia=f.cod_grupo_cia
                    AND f.cod_local=cCodLocal_in
                    AND A.COD_PAQUETE_2(+)=C.COD_PAQUETE
                     group by a.cod_prom
                   )v2,
                (
                  SELECT V6.COD_PROM,FLOOR(MIN(MAX_PAQ)) AS STK_PROM
                    FROM
                    (
                      SELECT V5.COD_PROM,V5.COD_PROD,
                      NVL(V4.STK_DISPONIBLE,0)/CANT_PROD_PAQ AS MAX_PAQ
                      FROM
                      (
                      SELECT V2.COD_PROM,PL.COD_PROD,((PL.STK_FISICO)/PL.VAL_FRAC_LOCAL) AS STK_DISPONIBLE
                      FROM LGT_PROD_LOCAL PL,
                           (SELECT V1.COD_PROM,COD_PROD
                            FROM VTA_PROD_PAQUETE,
                                 (SELECT COD_PROM,COD_PAQUETE_1
                                  FROM VTA_PROMOCION

                                 \* WHERE (SELECT SYSDATE FROM DUAL)
                                  BETWEEN

                                     TO_DATE( TO_CHAR(FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                                    WHERE   FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                                  AND

                                  ESTADO = 'A'
                                  UNION
                                  SELECT COD_PROM,COD_PAQUETE_2
                                  FROM VTA_PROMOCION

                                \*  WHERE (SELECT SYSDATE FROM DUAL)
                                  BETWEEN
                                   TO_DATE( TO_CHAR(FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                                 WHERE   FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                                  AND
                                  ESTADO = 'A') V1
                            WHERE COD_PAQUETE = V1.COD_PAQUETE_1
                            ) V2
                      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                            AND COD_LOCAL = cCodLocal_in
                            AND PL.COD_PROD = V2.COD_PROD
                      ) V4,
                      (
                      SELECT V3.COD_PROM,COD_PROD,SUM((CANTIDAD/VAL_FRAC)) AS CANT_PROD_PAQ
                      FROM VTA_PROD_PAQUETE,
                           (SELECT COD_PROM,COD_PAQUETE_1
                            FROM VTA_PROMOCION

                           \* WHERE (SELECT SYSDATE FROM DUAL)
                            BETWEEN
                                     TO_DATE( TO_CHAR(FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')*\
                              WHERE   FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                            AND

                            ESTADO = 'A'
                            UNION
                            SELECT COD_PROM,COD_PAQUETE_2
                            FROM VTA_PROMOCION


                           \* WHERE (SELECT SYSDATE FROM DUAL)
                            BETWEEN

                             TO_DATE( TO_CHAR(FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                       WHERE   FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                            AND
                            ESTADO = 'A') V3
                      WHERE COD_PAQUETE = V3.COD_PAQUETE_1
                      GROUP BY V3.COD_PROM,COD_PROD
                      ) V5
                      WHERE V5.COD_PROM = V4.COD_PROM(+)
                            AND V5.COD_PROD = V4.COD_PROD(+)
                    ) V6
                    GROUP BY V6.COD_PROM
                  ) V7

         \* WHERE (SELECT SYSDATE FROM DUAL)
                            BETWEEN
                                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\

            WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
          AND P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
          AND P.COD_GRUPO_CIA = cCodGrupoCia_in
          AND P.ESTADO = 'A'
          AND P.cod_prom = v1.promo(+)
          AND P.cod_prom = v2.promo(+)
          AND P.COD_PROM = V7.COD_PROM(+);*/
SELECT distinct P.COD_PROM || 'Ã' || P.DESC_LARGA_PROM || 'Ã' ||
                TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO((V1.SUMA +
                                                                V2.SUMA)),
                        '999,990.000') || 'Ã' || --ERIOS 14/04/2008 --JCHAVEZ 29102009  precio redondeado --ASOSA, 20.05.2010
                TO_CHAR(NVL(V7.STK_PROM, 0), '999,990') || 'Ã' || --ERIOS 16/04/20080
                DECODE((SELECT count(1)
                         FROM VTA_PROMOCION    A,
                              VTA_PROD_PAQUETE C,
                              LGT_PROD_LOCAL   F
                        WHERE A.FEC_PROMOCION_INICIO <= TRUNC(SYSDATE)
                          AND A.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
                          AND F.COD_GRUPO_CIA = cCodGrupoCia_in
                          AND F.COD_LOCAL = cCodLocal_in
                          AND C.COD_PROD = F.COD_PROD
                          AND (A.COD_PAQUETE_1 = C.COD_PAQUETE OR
                              A.COD_PAQUETE_2 = C.COD_PAQUETE)
                          and MOD(C.CANTIDAD * F.VAL_FRAC_LOCAL, C.VAL_FRAC) <> 0
                          and a.cod_prom = p.cod_prom),
                       0,
                       ' ',
                       'NO APLICABLE') || 'Ã' ||
                DECODE((SELECT count(1)
                         FROM VTA_PROMOCION    A,
                              VTA_PROD_PAQUETE C,
                              LGT_PROD_LOCAL   F
                        WHERE A.FEC_PROMOCION_INICIO <= TRUNC(SYSDATE)
                          AND A.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
                          AND F.COD_GRUPO_CIA = cCodGrupoCia_in
                          AND F.COD_LOCAL = cCodLocal_in
                          AND C.COD_PROD = F.COD_PROD
                          AND (A.COD_PAQUETE_1 = C.COD_PAQUETE OR
                              A.COD_PAQUETE_2 = C.COD_PAQUETE)
                          and MOD(C.CANTIDAD * F.VAL_FRAC_LOCAL, C.VAL_FRAC) <> 0
                          and a.cod_prom = p.cod_prom),
                       0,
                       'S',
                       'N') || 'Ã' || P.DESC_CORTA_PROM || 'Ã' ||
                P.COD_PAQUETE_1 || 'Ã' || P.COD_PAQUETE_2 || 'Ã' ||
                NVL(P.DESC_LARGA_PROM_1, ' ')
  FROM VTA_PROMOCION P,
       --VTA_PAQUETE B,
       VTA_PROD_PAQUETE C,
       (SELECT SUM((round(F.VAL_PREC_VTA, 2) * (F.VAL_FRAC_LOCAL) *
                   (C.CANTIDAD / C.VAL_FRAC) *
                   DECODE(((100 - C.PORC_DCTO) / 100),
                           1,
                           1,
                           (100 - C.PORC_DCTO) / 100))) SUMA,
               a.cod_prom promo
          FROM VTA_PROMOCION    A,
               VTA_PROD_PAQUETE C,
               LGT_PROD_LOCAL   F,
               LGT_PROD         D,
               LGT_LAB          E
         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
           AND A.ESTADO = 'A'
           AND A.FEC_PROMOCION_INICIO <= TRUNC(SYSDATE)
           AND A.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
           AND (A.COD_GRUPO_CIA, A.COD_PROM) IN
               (SELECT CC.COD_GRUPO_CIA, CC.COD_PROM
                  FROM VTA_PROMOCION CC, VTA_PROD_PAQUETE CP
                 WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND CC.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                   AND CC.COD_PAQUETE_1 = CP.COD_PAQUETE
                UNION
                SELECT CC.COD_GRUPO_CIA, CC.COD_PROM
                  FROM VTA_PROMOCION CC, VTA_PROD_PAQUETE CP
                 WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND CC.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                   AND CC.COD_PAQUETE_2 = CP.COD_PAQUETE)
           AND A.COD_PAQUETE_1 = C.COD_PAQUETE
           AND C.COD_GRUPO_CIA = F.COD_GRUPO_CIA
           AND C.COD_PROD = F.COD_PROD
           AND F.COD_GRUPO_CIA = D.COD_GRUPO_CIA
           AND F.COD_PROD = D.COD_PROD
           AND D.COD_LAB = E.COD_LAB
           AND f.cod_local = cCodLocal_in
         group by a.cod_prom) v1,
       (SELECT SUM((round(F.VAL_PREC_VTA, 2) * (F.VAL_FRAC_LOCAL) *
                   (C.CANTIDAD / C.VAL_FRAC) *
                   DECODE(((100 - C.PORC_DCTO) / 100),
                           1,
                           1,
                           (100 - C.PORC_DCTO) / 100))) SUMA,
               a.cod_prom promo
          FROM VTA_PROMOCION    A,
               VTA_PROD_PAQUETE C,
               LGT_PROD_LOCAL   F,
               LGT_PROD         D,
               LGT_LAB          E
         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
           AND A.ESTADO = 'A'
           AND A.FEC_PROMOCION_INICIO <= TRUNC(SYSDATE)
           AND A.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
           AND (A.COD_GRUPO_CIA, A.COD_PROM) IN
               (SELECT CC.COD_GRUPO_CIA, CC.COD_PROM
                  FROM VTA_PROMOCION CC, VTA_PROD_PAQUETE CP
                 WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND CC.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                   AND CC.COD_PAQUETE_1 = CP.COD_PAQUETE
                UNION
                SELECT CC.COD_GRUPO_CIA, CC.COD_PROM
                  FROM VTA_PROMOCION CC, VTA_PROD_PAQUETE CP
                 WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND CC.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                   AND CC.COD_PAQUETE_2 = CP.COD_PAQUETE)
           AND A.COD_PAQUETE_2 = C.COD_PAQUETE
           AND C.COD_GRUPO_CIA = F.COD_GRUPO_CIA
           AND C.COD_PROD = F.COD_PROD
           AND F.COD_GRUPO_CIA = D.COD_GRUPO_CIA
           AND F.COD_PROD = D.COD_PROD
           AND D.COD_LAB = E.COD_LAB
           AND f.cod_local = cCodLocal_in
         group by a.cod_prom) v2,
       (SELECT V6.COD_PROM, FLOOR(MIN(MAX_PAQ)) AS STK_PROM
          FROM (SELECT V5.COD_PROM,
                       V5.COD_PROD,
                       NVL(V4.STK_DISPONIBLE, 0) / CANT_PROD_PAQ AS MAX_PAQ
                  FROM (SELECT V2.COD_PROM,
                               PL.COD_PROD,
                               --((PL.STK_FISICO) / PL.VAL_FRAC_LOCAL) AS STK_DISPONIBLE
                               --INI - ASOSA - 22/10/2014 - PANHD
                               (
                               (DECODE(GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                          cCodLocal_in,
                                                                                                          PL.COD_PROD),0,PL.STK_FISICO,GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                                                                                                                  cCodLocal_in,
                                                                                                                                                                                                  PL.COD_PROD)))        
                               
                                               / PL.VAL_FRAC_LOCAL
                               
                               ) AS STK_DISPONIBLE
                               --INI - ASOSA - 22/10/2014 - PANHD
                          FROM LGT_PROD_LOCAL PL,
                               (SELECT DISTINCT TD.COD_PROM, TD.COD_PROD
                                  FROM (SELECT V3.COD_PROM, PD.COD_PROD
                                          FROM VTA_PROD_PAQUETE VQ,
                                               VTA_PROMOCION    V3,
                                               VTA_PROD_PAQUETE PD
                                         WHERE VQ.COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND V3.ESTADO = 'A'
                                           AND VQ.COD_PAQUETE = V3.COD_PAQUETE_1
                                           AND V3.FEC_PROMOCION_INICIO <=
                                               TRUNC(SYSDATE)
                                           AND V3.FEC_PROMOCION_FIN >=
                                               TRUNC(SYSDATE)
                                           AND V3.COD_GRUPO_CIA =
                                               PD.COD_GRUPO_CIA
                                           AND V3.COD_PAQUETE_1 = PD.COD_PAQUETE
                                        UNION
                                        SELECT V3.COD_PROM, PD.COD_PROD
                                          FROM VTA_PROD_PAQUETE VQ,
                                               VTA_PROMOCION    V3,
                                               VTA_PROD_PAQUETE PD
                                         WHERE VQ.COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND V3.ESTADO = 'A'
                                           AND VQ.COD_PAQUETE = V3.COD_PAQUETE_1
                                           AND V3.FEC_PROMOCION_INICIO <=
                                               TRUNC(SYSDATE)
                                           AND V3.FEC_PROMOCION_FIN >=
                                               TRUNC(SYSDATE)
                                           AND V3.COD_GRUPO_CIA =
                                               PD.COD_GRUPO_CIA
                                           AND V3.COD_PAQUETE_2 = PD.COD_PAQUETE) TD) V2
                         WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
                           AND PL.COD_LOCAL = cCodLocal_in
                           AND PL.COD_PROD = V2.COD_PROD) V4,
                       (SELECT TT.COD_PROM,
                               TT.COD_PROD,
                               SUM(TT.CANT_PROD_PAQ) AS "CANT_PROD_PAQ"
                          FROM (SELECT V3.COD_PROM,
                                       VQ.COD_PROD,
                                       SUM((VQ.CANTIDAD / VQ.VAL_FRAC)) AS CANT_PROD_PAQ
                                  FROM VTA_PROD_PAQUETE VQ, VTA_PROMOCION V3
                                 WHERE VQ.COD_GRUPO_CIA = cCodGrupoCia_in
                                   AND V3.ESTADO = 'A'
                                   AND VQ.COD_PAQUETE = V3.COD_PAQUETE_1
                                   AND V3.FEC_PROMOCION_INICIO <=
                                       TRUNC(SYSDATE)
                                   AND V3.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
                                 GROUP BY V3.COD_PROM, VQ.COD_PROD
                                UNION
                                SELECT V3.COD_PROM,
                                       VQ.COD_PROD,
                                       SUM((VQ.CANTIDAD / VQ.VAL_FRAC)) AS CANT_PROD_PAQ
                                  FROM VTA_PROD_PAQUETE VQ, VTA_PROMOCION V3
                                 WHERE VQ.COD_GRUPO_CIA = cCodGrupoCia_in
                                   AND V3.ESTADO = 'A'
                                   AND VQ.COD_PAQUETE = V3.COD_PAQUETE_2
                                   AND V3.FEC_PROMOCION_INICIO <=
                                       TRUNC(SYSDATE)
                                   AND V3.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
                                 GROUP BY V3.COD_PROM, VQ.COD_PROD) TT
                         GROUP BY TT.COD_PROM, TT.COD_PROD) V5
                 WHERE V5.COD_PROM = V4.COD_PROM(+)
                   AND V5.COD_PROD = V4.COD_PROD(+)) V6
         GROUP BY V6.COD_PROM) V7
 WHERE P.IND_DELIVERY = 'N' --JCORTEZ 16.10.09 solo para locales
   AND P.COD_GRUPO_CIA = cCodGrupoCia_in
   AND P.ESTADO = 'A'
   AND P.FEC_PROMOCION_INICIO <= TRUNC(SYSDATE)
   AND P.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
   AND (p.COD_PAQUETE_1 = C.COD_PAQUETE or p.COD_PAQUETE_2 = C.COD_PAQUETE)
   and P.cod_prom = v1.promo
   and P.cod_prom = v2.promo
   AND P.COD_PROM = V7.COD_PROM
   AND (P.IND_FID_USO = 'N' OR P.IND_FID_USO = vIsFidelizado_in);

         RETURN CURPROM ;
      END;



      /****************************************************************************/
  --LISTADO DE PROMOCIONES POR CODIGO DE PRODUCTO
    FUNCTION PROMOCIONES_LISTADOPORPRODUCTO(cCodGrupoCia_in    IN CHAR,
                		                        cCodLocal_in	     IN CHAR,
                                            vCodProd           IN CHAR,
                                            vIsFidelizado_in   IN CHAR DEFAULT 'N')
    RETURN FARMACURSOR
    IS
        CURPROM FARMACURSOR;
      BEGIN
        OPEN CURPROM FOR
/*            SELECT DISTINCT   A.COD_PROM || 'Ã' ||
                   A.DESC_LARGA_PROM || 'Ã' ||
                   A.DESC_CORTA_PROM || 'Ã' ||
                   A.COD_PAQUETE_1|| 'Ã' ||
                   A.COD_PAQUETE_2

            FROM  VTA_PROMOCION A,VTA_PAQUETE B,VTA_PROD_PAQUETE C
            WHERE A.ESTADO='A'
            AND   (A.COD_PAQUETE_1=C.COD_PAQUETE or A.COD_PAQUETE_2=C.COD_PAQUETE)
             AND   C.COD_PROD=vCodProd;*/

       /*   SELECT DISTINCT P.COD_PROM   || 'Ã' ||
                 P.DESC_LARGA_PROM || 'Ã' ||
                 --TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO((V1.SUMA+V2.SUMA)),'999,990.00') || 'Ã' || --ERIOS 14/04/2008 --JCHAVEZ 29102009  precio redondeado
                 TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO((V1.SUMA+V2.SUMA)),'999,990.000') || 'Ã' || --ERIOS 14/04/2008 --JCHAVEZ 29102009  precio redondeado --ASOSA, 20.05.2010
                 TO_CHAR(NVL(V7.STK_PROM,0),'999,990') || 'Ã' || --ERIOS 16/04/20080
                 DECODE(       (SELECT count(1)
                  FROM   VTA_PROMOCION A,
                         VTA_PROD_PAQUETE C,
                         LGT_PROD_LOCAL F
                 \* WHERE (SELECT SYSDATE FROM DUAL)
                  BETWEEN
                                     TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\

                  WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                  AND F.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    F.COD_LOCAL = cCodLocal_in
                  AND    C.COD_PROD=F.COD_PROD
                  AND (   A.COD_PAQUETE_1 = C.COD_PAQUETE
                    OR    A.COD_PAQUETE_2 = C.COD_PAQUETE)
                  and    MOD(C.CANTIDAD * F.VAL_FRAC_LOCAL,C.VAL_FRAC) <> 0
                  and    a.cod_prom = p.cod_prom),0,' ','NO APLICABLE')|| 'Ã' ||
                 DECODE(       (SELECT count(1)
                  FROM   VTA_PROMOCION A,
                         VTA_PROD_PAQUETE C,
                         LGT_PROD_LOCAL F

                   \*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                                     TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                      WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                     AND
                         F.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    F.COD_LOCAL = cCodLocal_in
                  AND    C.COD_PROD=F.COD_PROD
                  AND (   A.COD_PAQUETE_1 = C.COD_PAQUETE
                    OR    A.COD_PAQUETE_2 = C.COD_PAQUETE)
                  and    MOD(C.CANTIDAD * F.VAL_FRAC_LOCAL,C.VAL_FRAC) <> 0
                  and    a.cod_prom = p.cod_prom),0,'S','N')|| 'Ã' ||
                 P.DESC_CORTA_PROM || 'Ã' ||
                 P.COD_PAQUETE_1 || 'Ã'||
                 P.COD_PAQUETE_2|| 'Ã'||
                 NVL(P.DESC_LARGA_PROM_1,' ')
          FROM
               VTA_PROMOCION P,VTA_PAQUETE B,VTA_PROD_PAQUETE C,
               (  --SELECT SUM(((F.VAL_PREC_VTA)* (F.VAL_FRAC_LOCAL)*(C.CANTIDAD)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                      --SELECT SUM((round(F.VAL_PREC_VTA,2)* (F.VAL_FRAC_LOCAL)*(C.CANTIDAD/C.VAL_FRAC)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                      SELECT SUM((round(F.VAL_PREC_VTA,3)* (F.VAL_FRAC_LOCAL)*(C.CANTIDAD/C.VAL_FRAC)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA, --ASOSA, 20.05.2010
                           a.cod_prom promo
                    FROM   VTA_PROMOCION A,
                           VTA_PROD_PAQUETE C,
                           LGT_PROD_LOCAL F,
                           LGT_PROD D,
                           LGT_LAB E
                  \*
                      WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                                     TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\

                       WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                     AND

                     C.COD_PROD=F.COD_PROD
                     AND   C.COD_PROD=D.COD_PROD
                     AND   D.COD_LAB=E.COD_LAB
                    AND d.cod_grupo_cia=cCodGrupoCia_in
                    AND d.cod_grupo_cia=f.cod_grupo_cia
                    AND f.cod_local=cCodLocal_in
                    AND A.COD_PAQUETE_1(+)=C.COD_PAQUETE
                     group by a.cod_prom
                   )v1,
              (  -- SELECT SUM(((F.VAL_PREC_VTA)*(F.VAL_FRAC_LOCAL)*(C.CANTIDAD)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                    SELECT SUM((round(F.VAL_PREC_VTA,2)*(F.VAL_FRAC_LOCAL)*(C.CANTIDAD/C.VAL_FRAC)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                           a.cod_prom promo
                    FROM   VTA_PROMOCION A,
                           VTA_PROD_PAQUETE C,
                           LGT_PROD_LOCAL F,
                           LGT_PROD D,
                           LGT_LAB E
                  \*
                      WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                                     TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                       WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                     AND
                     C.COD_PROD=F.COD_PROD
                     AND   C.COD_PROD=D.COD_PROD
                     AND   D.COD_LAB=E.COD_LAB
                    AND d.cod_grupo_cia=cCodGrupoCia_in
                    AND d.cod_grupo_cia=f.cod_grupo_cia
                    AND f.cod_local=cCodLocal_in
                    AND A.COD_PAQUETE_2(+)=C.COD_PAQUETE
                     group by a.cod_prom
                   )v2   ,
                   (
                  SELECT V6.COD_PROM,FLOOR(MIN(MAX_PAQ)) AS STK_PROM
                    FROM
                    (
                      SELECT V5.COD_PROM,V5.COD_PROD,
                      NVL(V4.STK_DISPONIBLE,0)/CANT_PROD_PAQ AS MAX_PAQ
                      FROM
                      (
                      SELECT V2.COD_PROM,PL.COD_PROD,((PL.STK_FISICO)/PL.VAL_FRAC_LOCAL) AS STK_DISPONIBLE
                      FROM LGT_PROD_LOCAL PL,
                           (SELECT V1.COD_PROM,COD_PROD
                            FROM VTA_PROD_PAQUETE,
                                 (SELECT COD_PROM,COD_PAQUETE_1
                                  FROM VTA_PROMOCION
                                  \*WHERE (SELECT SYSDATE FROM DUAL)
                                  BETWEEN

                                   TO_DATE(TO_CHAR(FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')

                                  *\
                                    WHERE   FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                                  AND ESTADO = 'A'
                                  UNION
                                  SELECT COD_PROM,COD_PAQUETE_2
                                  FROM VTA_PROMOCION
                                \*  WHERE (SELECT SYSDATE FROM DUAL)
                                 BETWEEN
                                   TO_DATE( TO_CHAR(FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                                 WHERE   FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND FEC_PROMOCION_FIN>=TRUNC(SYSDATE)

                                 AND ESTADO = 'A') V1
                            WHERE COD_PAQUETE = V1.COD_PAQUETE_1
                            ) V2
                      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                            AND COD_LOCAL = cCodLocal_in
                            AND PL.COD_PROD = V2.COD_PROD
                      ) V4,
                      (
                      SELECT V3.COD_PROM,COD_PROD,SUM((CANTIDAD/VAL_FRAC)) AS CANT_PROD_PAQ
                      FROM VTA_PROD_PAQUETE,
                           (SELECT COD_PROM,COD_PAQUETE_1
                            FROM VTA_PROMOCION
                           \* WHERE (SELECT SYSDATE FROM DUAL)
                            BETWEEN
                             TO_DATE( TO_CHAR(FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                              WHERE   FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                            AND  ESTADO = 'A'
                            UNION
                            SELECT COD_PROM,COD_PAQUETE_2
                            FROM VTA_PROMOCION

                           \* WHERE (SELECT SYSDATE FROM DUAL)
                            BETWEEN
                             TO_DATE(TO_CHAR(FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     *\
                              WHERE  FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                            AND ESTADO = 'A') V3
                      WHERE COD_PAQUETE = V3.COD_PAQUETE_1
                      GROUP BY V3.COD_PROM,COD_PROD
                      ) V5
                      WHERE V5.COD_PROM = V4.COD_PROM(+)
                            AND V5.COD_PROD = V4.COD_PROD(+)
                    ) V6
                    GROUP BY V6.COD_PROM
                  ) V7

           \* WHERE (SELECT SYSDATE FROM DUAL)
                            BETWEEN

                                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')*\

            WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
            AND    P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
            AND    P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    P.ESTADO = 'A'
            AND   (p.COD_PAQUETE_1=C.COD_PAQUETE or p.COD_PAQUETE_2=C.COD_PAQUETE)
            AND   C.COD_PROD= vCodProd
          and   P.cod_prom = v1.promo(+)
          and   P.cod_prom = v2.promo(+)
          AND P.COD_PROM = V7.COD_PROM(+)
            ;
*/
SELECT P.COD_PROM || 'Ã' || P.DESC_LARGA_PROM || 'Ã' ||
                TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO((V1.SUMA +
                                                                V2.SUMA)),
                        '999,990.000') || 'Ã' || --ERIOS 14/04/2008 --JCHAVEZ 29102009  precio redondeado --ASOSA, 20.05.2010
                TO_CHAR(NVL(V7.STK_PROM, 0), '999,990') || 'Ã' || --ERIOS 16/04/20080
                DECODE((SELECT count(1)
                         FROM VTA_PROMOCION    A,
                              VTA_PROD_PAQUETE C,
                              LGT_PROD_LOCAL   F
                        WHERE A.FEC_PROMOCION_INICIO <= TRUNC(SYSDATE)
                          AND A.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
                          AND F.COD_GRUPO_CIA = cCodGrupoCia_in
                          AND F.COD_LOCAL = cCodLocal_in
                          AND C.COD_PROD = F.COD_PROD
                          AND (A.COD_PAQUETE_1 = C.COD_PAQUETE OR
                              A.COD_PAQUETE_2 = C.COD_PAQUETE)
                          and MOD(C.CANTIDAD * F.VAL_FRAC_LOCAL, C.VAL_FRAC) <> 0
                          and a.cod_prom = p.cod_prom),
                       0,
                       ' ',
                       'NO APLICABLE') || 'Ã' ||
                DECODE((SELECT count(1)
                         FROM VTA_PROMOCION    A,
                              VTA_PROD_PAQUETE C,
                              LGT_PROD_LOCAL   F
                        WHERE A.FEC_PROMOCION_INICIO <= TRUNC(SYSDATE)
                          AND A.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
                          AND F.COD_GRUPO_CIA = cCodGrupoCia_in
                          AND F.COD_LOCAL = cCodLocal_in
                          AND C.COD_PROD = F.COD_PROD
                          AND (A.COD_PAQUETE_1 = C.COD_PAQUETE OR
                              A.COD_PAQUETE_2 = C.COD_PAQUETE)
                          and MOD(C.CANTIDAD * F.VAL_FRAC_LOCAL, C.VAL_FRAC) <> 0
                          and a.cod_prom = p.cod_prom),
                       0,
                       'S',
                       'N') || 'Ã' || P.DESC_CORTA_PROM || 'Ã' ||
                P.COD_PAQUETE_1 || 'Ã' || P.COD_PAQUETE_2 || 'Ã' ||
                NVL(P.DESC_LARGA_PROM_1, ' ')
  FROM VTA_PROMOCION P,
       --VTA_PAQUETE B,
       VTA_PROD_PAQUETE C,
       (SELECT SUM((round(F.VAL_PREC_VTA, 2) * (F.VAL_FRAC_LOCAL) *
                   (C.CANTIDAD / C.VAL_FRAC) *
                   DECODE(((100 - C.PORC_DCTO) / 100),
                           1,
                           1,
                           (100 - C.PORC_DCTO) / 100))) SUMA,
               a.cod_prom promo
          FROM VTA_PROMOCION    A,
               VTA_PROD_PAQUETE C,
               LGT_PROD_LOCAL   F,
               LGT_PROD         D,
               LGT_LAB          E
         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
           AND A.ESTADO = 'A'
           AND A.FEC_PROMOCION_INICIO <= TRUNC(SYSDATE)
           AND A.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
           AND (A.COD_GRUPO_CIA, A.COD_PROM) IN
               (SELECT CC.COD_GRUPO_CIA, CC.COD_PROM
                  FROM VTA_PROMOCION CC, VTA_PROD_PAQUETE CP
                 WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND CP.COD_PROD = vCodProd
                   AND CC.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                   AND CC.COD_PAQUETE_1 = CP.COD_PAQUETE
                UNION
                SELECT CC.COD_GRUPO_CIA, CC.COD_PROM
                  FROM VTA_PROMOCION CC, VTA_PROD_PAQUETE CP
                 WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND CP.COD_PROD = vCodProd
                   AND CC.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                   AND CC.COD_PAQUETE_2 = CP.COD_PAQUETE)
           AND A.COD_PAQUETE_1 = C.COD_PAQUETE
           AND C.COD_GRUPO_CIA = F.COD_GRUPO_CIA
           AND C.COD_PROD = F.COD_PROD
           AND F.COD_GRUPO_CIA = D.COD_GRUPO_CIA
           AND F.COD_PROD = D.COD_PROD
           AND D.COD_LAB = E.COD_LAB
           AND f.cod_local = cCodLocal_in
         group by a.cod_prom) v1,
       (SELECT SUM((round(F.VAL_PREC_VTA, 2) * (F.VAL_FRAC_LOCAL) *
                   (C.CANTIDAD / C.VAL_FRAC) *
                   DECODE(((100 - C.PORC_DCTO) / 100),
                           1,
                           1,
                           (100 - C.PORC_DCTO) / 100))) SUMA,
               a.cod_prom promo
          FROM VTA_PROMOCION    A,
               VTA_PROD_PAQUETE C,
               LGT_PROD_LOCAL   F,
               LGT_PROD         D,
               LGT_LAB          E
         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
           AND A.ESTADO = 'A'
           AND A.FEC_PROMOCION_INICIO <= TRUNC(SYSDATE)
           AND A.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
           AND (A.COD_GRUPO_CIA, A.COD_PROM) IN
               (SELECT CC.COD_GRUPO_CIA, CC.COD_PROM
                  FROM VTA_PROMOCION CC, VTA_PROD_PAQUETE CP
                 WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND CP.COD_PROD = vCodProd
                   AND CC.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                   AND CC.COD_PAQUETE_1 = CP.COD_PAQUETE
                UNION
                SELECT CC.COD_GRUPO_CIA, CC.COD_PROM
                  FROM VTA_PROMOCION CC, VTA_PROD_PAQUETE CP
                 WHERE CP.COD_GRUPO_CIA = cCodGrupoCia_in
                   AND CP.COD_PROD = vCodProd
                   AND CC.COD_GRUPO_CIA = CP.COD_GRUPO_CIA
                   AND CC.COD_PAQUETE_2 = CP.COD_PAQUETE)
           AND A.COD_PAQUETE_2 = C.COD_PAQUETE
           AND C.COD_GRUPO_CIA = F.COD_GRUPO_CIA
           AND C.COD_PROD = F.COD_PROD
           AND F.COD_GRUPO_CIA = D.COD_GRUPO_CIA
           AND F.COD_PROD = D.COD_PROD
           AND D.COD_LAB = E.COD_LAB
           AND f.cod_local = cCodLocal_in
         group by a.cod_prom) v2,
       (SELECT V6.COD_PROM, FLOOR(MIN(MAX_PAQ)) AS STK_PROM
          FROM (SELECT V5.COD_PROM,
                       V5.COD_PROD,
                       NVL(V4.STK_DISPONIBLE, 0) / CANT_PROD_PAQ AS MAX_PAQ
                  FROM (SELECT V2.COD_PROM,
                               PL.COD_PROD,
                               --((PL.STK_FISICO) / PL.VAL_FRAC_LOCAL) AS STK_DISPONIBLE
                                --INI - ASOSA - 22/10/2014 - PANHD
                               (
                               (DECODE(GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                          cCodLocal_in,
                                                                                                          PL.COD_PROD),0,PL.STK_FISICO,GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                                                                                                                  cCodLocal_in,
                                                                                                                                                                                                  PL.COD_PROD)))        
                               
                                               / PL.VAL_FRAC_LOCAL
                               
                               ) AS STK_DISPONIBLE
                               --INI - ASOSA - 22/10/2014 - PANHD
                          FROM LGT_PROD_LOCAL PL,
                               (SELECT DISTINCT TD.COD_PROM, TD.COD_PROD
                                  FROM (SELECT V3.COD_PROM, PD.COD_PROD
                                          FROM VTA_PROD_PAQUETE VQ,
                                               VTA_PROMOCION    V3,
                                               VTA_PROD_PAQUETE PD
                                         WHERE VQ.COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND VQ.COD_PROD = vCodProd
                                           AND V3.ESTADO = 'A'
                                           AND VQ.COD_PAQUETE = V3.COD_PAQUETE_1
                                           AND V3.FEC_PROMOCION_INICIO <=
                                               TRUNC(SYSDATE)
                                           AND V3.FEC_PROMOCION_FIN >=
                                               TRUNC(SYSDATE)
                                           AND V3.COD_GRUPO_CIA =
                                               PD.COD_GRUPO_CIA
                                           AND V3.COD_PAQUETE_1 = PD.COD_PAQUETE
                                        UNION
                                        SELECT V3.COD_PROM, PD.COD_PROD
                                          FROM VTA_PROD_PAQUETE VQ,
                                               VTA_PROMOCION    V3,
                                               VTA_PROD_PAQUETE PD
                                         WHERE VQ.COD_GRUPO_CIA = cCodGrupoCia_in
                                           AND VQ.COD_PROD = vCodProd
                                           AND V3.ESTADO = 'A'
                                           AND VQ.COD_PAQUETE = V3.COD_PAQUETE_1
                                           AND V3.FEC_PROMOCION_INICIO <=
                                               TRUNC(SYSDATE)
                                           AND V3.FEC_PROMOCION_FIN >=
                                               TRUNC(SYSDATE)
                                           AND V3.COD_GRUPO_CIA =
                                               PD.COD_GRUPO_CIA
                                           AND V3.COD_PAQUETE_2 = PD.COD_PAQUETE) TD) V2
                         WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
                           AND PL.COD_LOCAL = cCodLocal_in
                           AND PL.COD_PROD = V2.COD_PROD) V4,
                       (SELECT TT.COD_PROM,
                               TT.COD_PROD,
                               SUM(TT.CANT_PROD_PAQ) AS "CANT_PROD_PAQ"
                          FROM (SELECT V3.COD_PROM,
                                       VQ.COD_PROD,
                                       SUM((VQ.CANTIDAD / VQ.VAL_FRAC)) AS CANT_PROD_PAQ
                                  FROM VTA_PROD_PAQUETE VQ, VTA_PROMOCION V3
                                 WHERE VQ.COD_GRUPO_CIA = cCodGrupoCia_in
                                   AND VQ.COD_PROD = vCodProd
                                   AND V3.ESTADO = 'A'
                                   AND VQ.COD_PAQUETE = V3.COD_PAQUETE_1
                                   AND V3.FEC_PROMOCION_INICIO <=
                                       TRUNC(SYSDATE)
                                   AND V3.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
                                 GROUP BY V3.COD_PROM, VQ.COD_PROD
                                UNION
                                SELECT V3.COD_PROM,
                                       VQ.COD_PROD,
                                       SUM((VQ.CANTIDAD / VQ.VAL_FRAC)) AS CANT_PROD_PAQ
                                  FROM VTA_PROD_PAQUETE VQ, VTA_PROMOCION V3
                                 WHERE VQ.COD_GRUPO_CIA = cCodGrupoCia_in
                                   AND VQ.COD_PROD = vCodProd
                                   AND V3.ESTADO = 'A'
                                   AND VQ.COD_PAQUETE = V3.COD_PAQUETE_2
                                   AND V3.FEC_PROMOCION_INICIO <=
                                       TRUNC(SYSDATE)
                                   AND V3.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
                                 GROUP BY V3.COD_PROM, VQ.COD_PROD) TT
                         GROUP BY TT.COD_PROM, TT.COD_PROD) V5
                 WHERE V5.COD_PROM = V4.COD_PROM(+)
                   AND V5.COD_PROD = V4.COD_PROD(+)) V6
         GROUP BY V6.COD_PROM) V7
 WHERE P.IND_DELIVERY = 'N' --JCORTEZ 16.10.09 solo para locales
   AND P.COD_GRUPO_CIA = cCodGrupoCia_in
   AND P.ESTADO = 'A'
   AND P.FEC_PROMOCION_INICIO <= TRUNC(SYSDATE)
   AND P.FEC_PROMOCION_FIN >= TRUNC(SYSDATE)
   AND (p.COD_PAQUETE_1 = C.COD_PAQUETE or p.COD_PAQUETE_2 = C.COD_PAQUETE)
   AND C.COD_PROD = vCodProd
   and P.cod_prom = v1.promo
   and P.cod_prom = v2.promo
   AND P.COD_PROM = V7.COD_PROM
   AND (P.IND_FID_USO = 'N' OR P.IND_FID_USO = vIsFidelizado_in);
         RETURN CURPROM ;
      END;

  /****************************************************************************/
  --LISTADO DE DETALLE DE PROMCIONES
    FUNCTION PROMOCIONES_DETALLE
    RETURN FARMACURSOR
    IS
        CURPROM FARMACURSOR;
      BEGIN
        OPEN CURPROM FOR
            SELECT COD_PAQUETE  || 'Ã' ||
                   COD_PROD || 'Ã' ||
                   CANTIDAD || 'Ã' ||
                   PORC_DCTO
            FROM  VTA_PROD_PAQUETE;
         RETURN CURPROM;
      END;

   /****************************************************************************/
  --LISTADO DE DETALLE DE PROMCIONES :Paquete1

       FUNCTION PROMOCIONES_LISTADO_PAQUETE1(cCodGrupoCia_in IN CHAR,
  		   				                             cCodLocal_in	 IN CHAR,
                                             vCodProm IN CHAR)
    RETURN FARMACURSOR
    IS
        CURPROM FARMACURSOR;
      BEGIN
        OPEN CURPROM FOR
    SELECT D.COD_PROD || 'Ã' ||
                 D.DESC_PROD || 'Ã' ||
   			   		   DECODE(F.IND_PROD_FRACCIONADO,'N',D.DESC_UNID_PRESENT,F.UNID_VTA) || 'Ã' ||
                 E.NOM_LAB || 'Ã' ||
                 TO_CHAR(F.VAL_PREC_VTA,'999,990.00')|| 'Ã' ||
                 --16/01/2008 DUBILLUZ MODIFICACION
                 --(C.CANTIDAD) * (F.VAL_FRAC_LOCAL) || 'Ã' ||
                 C.CANTIDAD * F.VAL_FRAC_LOCAL / C.VAL_FRAC || 'Ã' ||
                 --C.CANTIDAD || 'Ã' ||
                 --TO_CHAR((F.STK_FISICO),'999,990.00') || 'Ã' ||
                  --INI - ASOSA - 22/10/2014 - PANHD
                               TO_CHAR(
                               DECODE(GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                          cCodLocal_in,
                                                                                                          F.COD_PROD),0,F.STK_FISICO,GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                                                                                                                  cCodLocal_in,
                                                                                                                                                                                                  F.COD_PROD))      
                               , '999,990.00') || 'Ã' ||
                               --INI - ASOSA - 22/10/2014 - PANHD
                 TO_CHAR(((F.VAL_PREC_VTA)*(C.CANTIDAD * F.VAL_FRAC_LOCAL/ C.VAL_FRAC )*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)),'999,990.00') || 'Ã' ||
                 to_char(C.PORC_DCTO,'999,990.00') || 'Ã' ||
                 --TO_CHAR(v1.suma,'999,990.00')|| 'Ã' ||
                 TO_CHAR(v1.suma,'999,990.000')|| 'Ã' || --ASOSA, 20.05.2010
                 --implementa cantidad maxima de promociones, sin importar en que paquete este el dato
                 A.Cant_Max_Vta

           FROM  VTA_PROMOCION A,
                 VTA_PROD_PAQUETE C,
                 LGT_PROD_LOCAL F,
                 LGT_PROD D,
                 LGT_LAB E,
                 (  --SELECT SUM(((F.VAL_PREC_VTA)* (F.VAL_FRAC_LOCAL)*(C.CANTIDAD)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                      --SELECT SUM((round(F.VAL_PREC_VTA,2)* (F.VAL_FRAC_LOCAL)*(C.CANTIDAD/C.VAL_FRAC)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                      SELECT SUM((round(F.VAL_PREC_VTA,3)* (F.VAL_FRAC_LOCAL)*(C.CANTIDAD/C.VAL_FRAC)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA, --ASOSA, 20.05.2010
                           a.cod_prom promo
                    FROM   VTA_PROMOCION A,
                           VTA_PROD_PAQUETE C,
                           LGT_PROD_LOCAL F,
                           LGT_PROD D,
                           LGT_LAB E
                    /*   WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                                     TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')

                     */
                       WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                     AND
                           A.COD_PROM = vCodProm
                     AND   C.COD_PROD=F.COD_PROD
                     AND   C.COD_PROD=D.COD_PROD
                     AND   D.COD_LAB=E.COD_LAB
                    AND d.cod_grupo_cia=cCodGrupoCia_in
                    AND d.cod_grupo_cia=f.cod_grupo_cia
                    AND f.cod_local=cCodLocal_in
                    AND A.COD_PAQUETE_1(+)=C.COD_PAQUETE
                     group by a.cod_prom
                   )v1
         WHERE A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
        AND  A.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
        AND  A.COD_PROM = vCodProm
        AND  C.COD_PROD=F.COD_PROD
        AND  C.COD_PROD=D.COD_PROD
        AND  D.COD_LAB=E.COD_LAB
        AND  d.cod_grupo_cia=cCodGrupoCia_in
        AND  d.cod_grupo_cia=f.cod_grupo_cia
        AND  f.cod_local=cCodLocal_in
        AND  A.COD_PAQUETE_1(+)=C.COD_PAQUETE
        and   a.cod_prom = v1.promo(+);

      RETURN CURPROM ;
      END;

  /****************************************************************************/
  --LISTADO DE DETALLE DE PROMCIONES :Paquete2

      FUNCTION PROMOCIONES_LISTADO_PAQUETE2(cCodGrupoCia_in IN CHAR,
  		   				                            cCodLocal_in	 IN CHAR,
                                            vCodProm IN CHAR)
    RETURN FARMACURSOR
    IS
        CURPROM FARMACURSOR;
      BEGIN
        OPEN CURPROM FOR
          SELECT D.COD_PROD || 'Ã' ||
                 D.DESC_PROD || 'Ã' ||
   			   		   DECODE(F.IND_PROD_FRACCIONADO,'N',D.DESC_UNID_PRESENT,F.UNID_VTA) || 'Ã' ||
                 E.NOM_LAB || 'Ã' ||
                 TO_CHAR(F.VAL_PREC_VTA,'999,990.00')|| 'Ã' ||
                 --16/01/2008 DUBILLUZ MODIFICACION
                 --(C.CANTIDAD) * (F.VAL_FRAC_LOCAL) || 'Ã' ||
                 C.CANTIDAD * F.VAL_FRAC_LOCAL / C.VAL_FRAC || 'Ã' ||
                 --C.CANTIDAD || 'Ã' ||
                 --TO_CHAR((F.STK_FISICO),'999,990.00') || 'Ã' ||
                 --INI - ASOSA - 22/10/2014 - PANHD
                               TO_CHAR(
                               DECODE(GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                          cCodLocal_in,
                                                                                                          F.COD_PROD),0,F.STK_FISICO,GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                                                                                                                  cCodLocal_in,
                                                                                                                                                                                                  F.COD_PROD))      
                               , '999,990.00') || 'Ã' ||
                               --INI - ASOSA - 22/10/2014 - PANHD
                 TO_CHAR(((F.VAL_PREC_VTA)*(C.CANTIDAD * F.VAL_FRAC_LOCAL/ C.VAL_FRAC )*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)),'999,990.00') || 'Ã' ||
                 to_char(C.PORC_DCTO,'999,990.00') || 'Ã' ||
                 --TO_CHAR(v1.suma,'999,990.00')   || 'Ã' ||
                 TO_CHAR(v1.suma,'999,990.000')   || 'Ã' || --ASOSA, 20.05.2010
                  --implementa cantidad maxima de promociones, sin importar en que paquete este el dato
                 A.Cant_Max_Vta
           FROM  VTA_PROMOCION A,
                 VTA_PROD_PAQUETE C,
                 LGT_PROD_LOCAL F,
                 LGT_PROD D,
                 LGT_LAB E,
                 (  -- SELECT SUM(((F.VAL_PREC_VTA)*(F.VAL_FRAC_LOCAL)*(C.CANTIDAD)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                    --SELECT SUM((round(F.VAL_PREC_VTA,2)*(F.VAL_FRAC_LOCAL)*(C.CANTIDAD/C.VAL_FRAC)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA,
                    SELECT SUM((round(F.VAL_PREC_VTA,3)*(F.VAL_FRAC_LOCAL)*(C.CANTIDAD/C.VAL_FRAC)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)))SUMA, --ASOSA, 20.05.2010
                           a.cod_prom promo
                    FROM   VTA_PROMOCION A,
                           VTA_PROD_PAQUETE C,
                           LGT_PROD_LOCAL F,
                           LGT_PROD D,
                           LGT_LAB E
                    /*     WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                                     TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     */

                       WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)

                     AND   A.COD_PROM = vCodProm
                     AND   C.COD_PROD=F.COD_PROD
                     AND   C.COD_PROD=D.COD_PROD
                     AND   D.COD_LAB=E.COD_LAB
                    AND d.cod_grupo_cia=cCodGrupoCia_in
                    AND d.cod_grupo_cia=f.cod_grupo_cia
                    AND f.cod_local=cCodLocal_in
                    AND A.COD_PAQUETE_2(+)=C.COD_PAQUETE
                     group by a.cod_prom
                   )v1

         /* WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                       TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     */
           WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
               AND  A.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
         AND A.COD_PROM = vCodProm
         AND   C.COD_PROD=F.COD_PROD
         AND   C.COD_PROD=D.COD_PROD
         AND   D.COD_LAB=E.COD_LAB
          AND d.cod_grupo_cia=cCodGrupoCia_in
          AND d.cod_grupo_cia=f.cod_grupo_cia
          AND f.cod_local=cCodLocal_in
          AND A.COD_PAQUETE_2(+)=C.COD_PAQUETE
         and   a.cod_prom = v1.promo(+);
               RETURN CURPROM ;
      END;


  /****************************************************************************/
  --LISTADO DE DETALLE DE PROMCIONES
   FUNCTION PROMOCIONES_LISTADO_PAQUETES(cCodGrupoCia_in IN CHAR,
  		   				                         cCodLocal_in	 IN CHAR,
                                         vCodProm IN CHAR)
    RETURN FARMACURSOR
    IS
        CURPROM FARMACURSOR;
      BEGIN
      -- 10.- Actualiza porcentaje para Regalos con Precio Fijo
      ptoventa_promociones.sp_update_dscto_rega_prom(ccodgrupocia_in => ccodgrupocia_in,
                                                 ccodlocal_in => ccodlocal_in,
                                                 vcodprom => vcodprom);

     OPEN CURPROM FOR
     SELECT  PROD.COD_PROD || 'Ã' ||
			     PROD.DESC_PROD || 'Ã' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			     LAB.NOM_LAB || 'Ã' ||
			     --(PROD_LOCAL.STK_FISICO) || 'Ã' ||
           --INI - ASOSA - 22/10/2014 - PANHD
                               
                               DECODE(GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                          cCodLocal_in,
                                                                                                          PROD.COD_PROD),0,PROD_LOCAL.STK_FISICO,GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                                                                                                                  cCodLocal_in,
                                                                                                                                                                                                  PROD.COD_PROD))      
                                || 'Ã' ||
                               --INI - ASOSA - 22/10/2014 - PANHD
			   --  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
           --TO_CHAR(((PROD_LOCAL.VAL_PREC_VTA)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)),'999,990.00') || 'Ã' ||
           TO_CHAR(((PROD_LOCAL.VAL_PREC_VTA)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)),'999,990.000') || 'Ã' || --ASOSA, 18.05.2010
			     DECODE(PROD_LOCAL.ind_prod_cong ,'N',TO_CHAR(PROD.VAL_BONO_VIG,'990.00'),TO_CHAR((PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL),'990.00')) || 'Ã' ||
			     PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
           --16/01/2008 dubilluz modificacion
           --(C.CANTIDAD) * (PROD_LOCAL.VAL_FRAC_LOCAL) || 'Ã' ||--C.CANTIDAD  || 'Ã' ||
           C.CANTIDAD * PROD_LOCAL.VAL_FRAC_LOCAL / C.VAL_FRAC || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.00') || 'Ã' ||
			     TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			     PROD.IND_PROD_FARMA || 'Ã' ||
--         DECODE(NVL(PR_VRT.COD_PROD,C_INDICADOR_NO),C_INDICADOR_NO,C_INDICADOR_NO,C_INDICADOR_SI) ,
           NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
           PROD.IND_PROD_REFRIG || 'Ã' ||
           PROD.IND_TIPO_PROD || 'Ã' ||
           TO_CHAR(PROD.VAL_BONO_VIG,'990.00')   || 'Ã' ||
           --añadido Dubilluz
           TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'999,990.00')  || 'Ã' ||
           TO_CHAR(C.PORC_DCTO,'999,990.000') || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.00')  || 'Ã' ||
            TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100))-PROD_LOCAL.VAL_PREC_VTA),'999,990.000')|| 'Ã' ||-- JCHAVEZ 22102009  para ahorro de pack
           '6' -- JCHAVEZ 22102009  indicador de vta
           || 'Ã' ||'PREC_FIJ'      --16-AGO-13, TCT, Agrega Precio Fijo
           || 'Ã' ||NVL(A.IND_FID_USO,'N') -- 23
		FROM   LGT_PROD PROD,
			     LGT_PROD_LOCAL PROD_LOCAL,
			     LGT_LAB LAB,
			     PBL_IGV IGV,
           LGT_PROD_VIRTUAL PR_VRT,
    --tablas agregadas al from
           VTA_PROMOCION A,
           VTA_PAQUETE PAQ,
           VTA_PROD_PAQUETE C

 /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                                     TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     */

     WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
     AND   A.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
    AND    PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
    AND    A.COD_PROM =vCodProm
		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
		AND	   PROD.COD_LAB = LAB.COD_LAB
		AND	   PROD.COD_IGV = IGV.COD_IGV
		AND	   PROD.EST_PROD = 'A'
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    AND    A.COD_GRUPO_CIA = PAQ.COD_GRUPO_CIA
    AND    A.COD_PAQUETE_1 = PAQ.COD_PAQUETE                      --- Producto Emite
    AND    PAQ.COD_GRUPO_CIA = C.COD_GRUPO_CIA
    AND    PAQ.COD_PAQUETE = C.COD_PAQUETE
    AND    C.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
    AND    C.COD_PROD = PROD.COD_PROD
    AND    C.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
    AND    C.COD_PROD = PROD_LOCAL.COD_PROD
UNION
   SELECT  PROD.COD_PROD || 'Ã' ||                                -- 00
			     PROD.DESC_PROD || 'Ã' ||                               -- 01
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' || --02
			     LAB.NOM_LAB || 'Ã' ||                                  -- 03
			     --(PROD_LOCAL.STK_FISICO) || 'Ã' ||                      -- 04
           --INI - ASOSA - 22/10/2014 - PANHD
                               
                               DECODE(GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                          cCodLocal_in,
                                                                                                          PROD.COD_PROD),0,PROD_LOCAL.STK_FISICO,GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in,
                                                                                                                                                                                                  cCodLocal_in,
                                                                                                                                                                                                  PROD.COD_PROD))      
                                || 'Ã' ||
                               --INI - ASOSA - 22/10/2014 - PANHD
			    -- TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
--          TO_CHAR(((PROD_LOCAL.VAL_PREC_VTA)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)),'999,990.00') || 'Ã' ||
           TO_CHAR(((PROD_LOCAL.VAL_PREC_VTA)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100)),'999,990.000') || 'Ã' || -- 05 Val_Prec_Vta
			     DECODE(PROD_LOCAL.ind_prod_cong ,'N',TO_CHAR(PROD.VAL_BONO_VIG,'990.00'),TO_CHAR((PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL),'990.00')) || 'Ã' || --06 Bono
			     PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||                    --07
           PROD_LOCAL.IND_PROD_CONG || 'Ã' ||                     --08
           --16/01/2008 dubilluz modificacion
           --(C.CANTIDAD) * (PROD_LOCAL.VAL_FRAC_LOCAL) || 'Ã' ||--C.CANTIDAD  || 'Ã' ||
           C.CANTIDAD * PROD_LOCAL.VAL_FRAC_LOCAL / C.VAL_FRAC || 'Ã' ||    --09 Cantidad
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.00') || 'Ã' ||        -- 10 Val_Prec_Lista
			     TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			     PROD.IND_PROD_FARMA || 'Ã' ||
--           DECODE(NVL(PR_VRT.COD_PROD,C_INDICADOR_NO),C_INDICADOR_NO,C_INDICADOR_NO,C_INDICADOR_SI) ,
           NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
           PROD.IND_PROD_REFRIG || 'Ã' ||
           PROD.IND_TIPO_PROD || 'Ã' ||
           TO_CHAR(PROD.VAL_BONO_VIG,'990.00')|| 'Ã' ||
           --añadido
           TO_CHAR(PROD_LOCAL.PORC_DCTO_1,'999,990.00')  || 'Ã' ||
           TO_CHAR(C.PORC_DCTO,'999,990.000') || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.00') || 'Ã' ||
           TO_CHAR((PROD_LOCAL.VAL_PREC_VTA-((PROD_LOCAL.VAL_PREC_VTA)*DECODE(((100-C.PORC_DCTO)/100),1,1,(100-C.PORC_DCTO)/100))),'999,990.000') || 'Ã' || -- JCHAVEZ 22102009  para ahorro de pack
           ' ' -- JCHAVEZ 22102009  indicador de vta
           || 'Ã' ||TO_CHAR(nvl(C.Prec_Fijo,0),'999,990.000')      --16-AGO-13, TCT, Agrega Precio Fijo
           || 'Ã' ||NVL(A.IND_FID_USO,'N')      
		FROM   LGT_PROD PROD,
			     LGT_PROD_LOCAL PROD_LOCAL,
			     LGT_LAB LAB,
			     PBL_IGV IGV,
           LGT_PROD_VIRTUAL PR_VRT,

    --tablas agregadas al from
           VTA_PROMOCION A,
           VTA_PAQUETE PAQ,
           VTA_PROD_PAQUETE C

    /* WHERE (SELECT SYSDATE FROM DUAL)
            BETWEEN
             TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
         AND TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
              */
      WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
      AND  A.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
    AND    PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
    AND    A.COD_PROM = vCodProm
		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
		AND	   PROD.COD_LAB = LAB.COD_LAB
		AND	   PROD.COD_IGV = IGV.COD_IGV
		AND	   PROD.EST_PROD = 'A'
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    AND    A.COD_GRUPO_CIA = PAQ.COD_GRUPO_CIA
    AND    A.COD_PAQUETE_2 = PAQ.COD_PAQUETE                      --- Producto Regalo
    AND    PAQ.COD_GRUPO_CIA = C.COD_GRUPO_CIA
    AND    PAQ.COD_PAQUETE = C.COD_PAQUETE
    AND    C.COD_GRUPO_CIA = PROD.COD_GRUPO_CIA
    AND    C.COD_PROD = PROD.COD_PROD
    AND    C.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
    AND    C.COD_PROD = PROD_LOCAL.COD_PROD;

          RETURN CURPROM ;
      END;

  /****************************************************************************/
  FUNCTION PROMOCIONES_PERMITE_EN_LOCAL(cCodGrupoCia_in IN CHAR,
  		   				                        cCodLocal_in	  IN CHAR,
                                        vCodProm        IN CHAR)
  RETURN  CHAR
  IS
     cPermiteLocal         char(1);
     nCantIncorrectos  number(20);
  BEGIN

        SELECT COUNT(1)
        into   nCantIncorrectos
        FROM  (
        SELECT MOD(C.CANTIDAD * F.VAL_FRAC_LOCAL,C.VAL_FRAC) RESTO
        FROM   VTA_PROMOCION A,
               VTA_PROD_PAQUETE C,
               LGT_PROD_LOCAL F

       /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                      TO_DATE( TO_CHAR(A.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                  AND TO_DATE( TO_CHAR(A.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     */

           WHERE   A.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND A.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
           AND  A.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
        AND A.COD_PROM = vCodProm
        AND    F.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    F.COD_LOCAL = cCodLocal_in
        AND    C.COD_PROD=F.COD_PROD
        AND (   A.COD_PAQUETE_1 = C.COD_PAQUETE
          OR    A.COD_PAQUETE_2 = C.COD_PAQUETE)
        )V
        WHERE V.RESTO <> 0;

        if nCantIncorrectos = 0 then
            cPermiteLocal := 'S';
        else
            cPermiteLocal := 'N';
        end if;

        return cPermiteLocal;
  END;

 /* **************************************************************************** */
  PROCEDURE PROCESO_PROM_REGALO(cCodGrupoCia_in IN CHAR,
  		   				                cCodLocal_in	  IN CHAR,
                                cNumPedVta_in   IN CHAR,
                                cSecUsu_in      IN CHAR,
                                cLogin_in       IN CHAR,
                                cIp_in          IN CHAR)
   IS

 CURSOR cur IS
    --ERIOS 17.11.2014 Cambios de JLUNA 
    SELECT DISTINCT P.COD_PROM
    FROM   VTA_PROMOCION P,
           VTA_PROD_PAQUETE Q
      WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
    AND  P.ESTADO = 'A'
    AND    P.COD_PAQUETE_2 IN (SELECT V.COD_PAQUETE
                               FROM (
                               SELECT T.COD_PAQUETE,COUNT(1)
                               FROM   VTA_PROD_PAQUETE T
                               WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
                               AND T.PORC_DCTO = 100
                               GROUP BY T.COD_PAQUETE
                               HAVING  COUNT(1) =
                                       (SELECT COUNT(1) FROM VTA_PROD_PAQUETE X 
                                        WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in
                                        AND X.COD_PAQUETE = T.COD_PAQUETE)
                                     )V
                               )
    AND    P.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
    AND    P.COD_PAQUETE_1 = Q.COD_PAQUETE
    AND    Q.COD_PROD IN (select d.cod_prod
                          from   vta_pedido_vta_det d
                          where  d.cod_grupo_cia = cCodGrupoCia_in
                          and    d.cod_local     = cCodLocal_in
                          and    d.num_ped_vta   = cNumPedVta_in);


    CURSOR curPromMultiplo(cCodProm_in IN CHAR,
                      cCodCia_in  IN CHAR,
                      cLocal_in   IN CHAR,
                      cNumPed_in  IN CHAR) IS
     SELECT R1.COD_PROM,MIN(R1.MULTIPLO) FACTOR--, R1.cant_atendida,R1.sec_ped_vta_det  --JCHAVEZ 16102009 se agregó R1.cant_atendida,R1.sec_ped_vta_det
      FROM   (
              SELECT P.COD_PROM,P.COD_PAQUETE_1,
                     Q.COD_PROD,Q.CANTIDAD * PROD_LOCAL.VAL_FRAC_LOCAL / Q.VAL_FRAC,
                     DET_PEDIDO.cant_atendida*DET_PEDIDO.val_frac_local/DET_PEDIDO.val_frac,
                     TRUNC((DET_PEDIDO.cant_atendida*DET_PEDIDO.val_frac_local/DET_PEDIDO.val_frac)/(Q.CANTIDAD * PROD_LOCAL.VAL_FRAC_LOCAL / Q.VAL_FRAC)) MULTIPLO--,
                  --   DET_PEDIDO.cant_atendida, --JCHAVEZ 16102009 se agregó DET_PEDIDO.cant_atendida
                  --   DET_PEDIDO.sec_ped_vta_det  --JCHAVEZ 16102009 se agregó DET_PEDIDO.sec_ped_vta_det

              FROM   VTA_PROMOCION P,
                     VTA_PROD_PAQUETE Q,
                     LGT_PROD_LOCAL PROD_LOCAL,
                     (
                        select d.cod_prod,d.cant_atendida,d.val_frac,d.cod_prom,d.val_frac_local, d.sec_ped_vta_det --JCHAVEZ 16102009 se agregó d.sec_ped_vta_det
                        from   vta_pedido_vta_det d
                        where  d.cod_grupo_cia = cCodCia_in
                        and    d.cod_local     = cLocal_in
                        and    d.num_ped_vta   = cNumPed_in
                        and    d.val_prec_vta  != 0
                        and    d.val_prec_total != 0
                        and    d.cod_prom is null --JCHAVEZ 19102009
                     )DET_PEDIDO,
                     (
                      SELECT Q.COD_PAQUETE,COUNT(1) CANTIDAD
                      FROM   VTA_PROD_PAQUETE Q
                      GROUP BY Q.COD_PAQUETE
                      ) TOT_PAQUETE_PROD

           /*   WHERE (SELECT SYSDATE FROM DUAL)
              BETWEEN
               TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
                     */
                WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
              AND P.COD_PROM = cCodProm_in
              AND    P.ESTADO   = 'A'
              AND    P.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
              AND    P.COD_PAQUETE_1 = Q.COD_PAQUETE
              AND    Q.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
              AND    Q.COD_PROD = PROD_LOCAL.COD_PROD
              AND    PROD_LOCAL.COD_LOCAL = cLocal_in
              AND    DET_PEDIDO.COD_PROD  = Q.COD_PROD
              AND    TOT_PAQUETE_PROD.COD_PAQUETE = Q.COD_PAQUETE
              AND    TOT_PAQUETE_PROD.CANTIDAD = (SELECT COUNT(1)
                                                  FROM   VTA_PROD_PAQUETE   X,
                                                         VTA_PEDIDO_VTA_DET T
                                                  WHERE  X.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
                                                  AND    X.COD_PAQUETE = Q.COD_PAQUETE
                                                  AND    X.COD_PROD = T.COD_PROD
                                                  AND    T.COD_GRUPO_CIA = cCodCia_in
                                                  AND    T.COD_LOCAL = cLocal_in
                                                  AND    T.NUM_PED_VTA = cNumPed_in
                                                  AND    T.VAL_PREC_VTA  != 0
                                                  AND    T.VAL_PREC_TOTAL  != 0)
             )R1
      GROUP  BY R1.COD_PROM--, R1.cant_atendida,R1.sec_ped_vta_det --JCHAVEZ 16102009 se agregó  R1.cant_atendida,R1.sec_ped_vta_det
      ;


   CURSOR cCurProdRegalo(cCodCia_in IN CHAR,cCodProm_in IN CHAR) is
    SELECT PROM.COD_PROM,
           PAK.COD_PROD,PL.VAL_FRAC_LOCAL,
           PAK.CANTIDAD * PL.VAL_FRAC_LOCAL / PAK.VAL_FRAC CANTIDAD
    FROM   VTA_PROD_PAQUETE PAK,
           VTA_PROMOCION PROM,
           LGT_PROD_LOCAL PL

   /*WHERE (SELECT SYSDATE FROM DUAL)
            BETWEEN

             TO_DATE( TO_CHAR(PROM.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                                 AND TO_DATE( TO_CHAR(PROM.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')

*/

  WHERE   PROM.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND PROM.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
   AND

    PAK.COD_GRUPO_CIA = cCodCia_in
    AND    PROM.COD_PROM     = cCodProm_in
    AND    PAK.PORC_DCTO     = 100
    AND    PROM.COD_GRUPO_CIA = PAK.COD_GRUPO_CIA
    AND    PROM.COD_PAQUETE_2 = PAK.COD_PAQUETE
    AND    PL.COD_GRUPO_CIA   = PAK.COD_GRUPO_CIA
    AND    PL.COD_PROD        = PAK.COD_PROD
    AND    PL.COD_LOCAL = cCodLocal_in                --JCHAVEZ 19102009
    AND    PAK.COD_PROD NOT IN (
                                select d.cod_prod
                                from   vta_pedido_vta_det d
                                where  d.cod_grupo_cia = cCodGrupoCia_in
                                and    d.cod_local     = cCodLocal_in
                                and    d.num_ped_vta   = cNumPedVta_in
                                and    d.val_prec_vta = 0
                                and    d.val_prec_total = 0
                                );

    nCantItemPed number;
    nStockProd   number;
    nCantNueva   number;
    v_nCanAtendidaProm number; --JCHAVEZ 19102009
    v_nPorcDscto VTA_PROD_PAQUETE.Porc_Dcto%type; --JCHAVEZ 19102009
   BEGIN

      SELECT MAX(D.SEC_PED_VTA_DET)
      into   nCantItemPed
      FROM   VTA_PEDIDO_VTA_DET D
      WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    D.COD_LOCAL     = cCodLocal_in
      AND    D.NUM_PED_VTA   = cNumPedVta_in;

      FOR cursor_prom IN cur
      LOOP
       DBMS_OUTPUT.put_line(''||cursor_prom.cod_prom);

       FOR cPromFactor IN curPromMultiplo(cursor_prom.cod_prom,cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in)
       LOOP
           DBMS_OUTPUT.put_line(''||cPromFactor.Cod_Prom||' - '||cPromFactor.Factor);

         --  VTA_PEDIDO_VTA_DET
         for cProdRegalo in cCurProdRegalo(cCodGrupoCia_in,cPromFactor.Cod_Prom)
         loop
              DBMS_OUTPUT.put_line('cProdRega '||cProdRegalo.Cod_Prod ||' '|| cProdRegalo.Cantidad );
           nCantNueva := 0;

           SELECT PL.STK_FISICO
           INTO   nStockProd
           FROM   LGT_PROD_LOCAL PL
           WHERE  PL.COD_GRUPO_CIA = cCodGrupoCia_in
           AND    PL.COD_LOCAL     = cCodLocal_in
           AND    PL.COD_PROD      = cProdRegalo.Cod_Prod;

           IF nStockProd > 0 THEN
              DBMS_OUTPUT.put_line('cProdRegalo.Cantidad '||cProdRegalo.Cantidad);
              DBMS_OUTPUT.put_line('cPromFactor.Factor '||cPromFactor.Factor);


               nCantNueva := cProdRegalo.Cantidad*cPromFactor.Factor;
               DBMS_OUTPUT.put_line('nCantNueva_1 '||nCantNueva);

               DBMS_OUTPUT.put_line('nStockProd '||nStockProd);
               if nCantNueva > 0 then
                --JCHAVEZ 20102009
                UPDATE VTA_PEDIDO_VTA_DET A
                SET A.COD_PROM = cursor_prom.Cod_Prom, A.IND_PROM_AUTOMATICO = 'S'
                WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in AND
                A.COD_LOCAL = cCodLocal_in AND
                A.NUM_PED_VTA = cNumPedVta_in AND
                A.COD_PROD IN (
                select a.cod_prod
                from vta_prod_paquete a
                where a.cod_grupo_cia =  cCodGrupoCia_in
                and a.cod_paquete in ( select b.cod_paquete_1
                                       from vta_promocion b
                                       where b.cod_grupo_cia = cCodGrupoCia_in
                                       and b.cod_prom = cProdRegalo.Cod_Prom
                                     ) );

                select min(a.cant_atendida) into v_nCanAtendidaProm
                from vta_pedido_vta_det a
                where a.cod_grupo_cia = cCodGrupoCia_in
                and a.cod_local = cCodLocal_in
                and a.num_ped_vta = cNumPedVta_in
                and a.cod_prom =  cProdRegalo.Cod_Prom;

                INSERT INTO VTA_PEDIDO_PACK(COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_PROM,CANTIDAD,USU_CREA_PROMOCION,FEC_CREA_PROMOCION,IND_PROM_AUTOMATICO)
                VALUES(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in, cursor_prom.Cod_Prom,v_nCanAtendidaProm,cLogin_in,SYSDATE,'S');

                SELECT A.PORC_DCTO INTO v_nPorcDscto
                FROM VTA_PROD_PAQUETE A
                WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                AND A.COD_PAQUETE IN ( SELECT B.COD_PAQUETE_2 FROM VTA_PROMOCION B WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in AND B.COD_PROM = cProdRegalo.Cod_Prom)
                AND A.COD_PROD = cProdRegalo.Cod_Prod;
                --JCHAVEZ 20102009

                   nCantItemPed := nCantItemPed + 1;

                   IF  nCantNueva >= nStockProd  THEN
                       nCantNueva := nStockProd;
                   END IF;
                      DBMS_OUTPUT.put_line('nCantNueva '||nCantNueva);
                   UPDATE LGT_PROD_LOCAL
                   SET    USU_MOD_PROD_LOCAL = cLogin_in,
                          FEC_MOD_PROD_LOCAL = SYSDATE
        	         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        	         AND    COD_LOCAL     = cCodLocal_in
        	         AND    COD_PROD      = cProdRegalo.Cod_Prod;


                     -- insertando los productos regalo que tiene el paquete 2 de la promocion
                            INSERT INTO VTA_PEDIDO_VTA_DET
                            (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,SEC_PED_VTA_DET,COD_PROD,CANT_ATENDIDA,
                            VAL_PREC_VTA,VAL_PREC_TOTAL,PORC_DCTO_1,PORC_DCTO_2,PORC_DCTO_3,
                            PORC_DCTO_TOTAL,VAL_TOTAL_BONO,VAL_FRAC,
                            SEC_USU_LOCAL,USU_CREA_PED_VTA_DET,VAL_PREC_LISTA,
                            VAL_IGV,UNID_VTA,IND_EXONERADO_IGV,
                            VAL_PREC_PUBLIC,IND_ORIGEN_PROD,
                            VAL_FRAC_LOCAL,
                            CANT_FRAC_LOCAL,
                            COD_PROM,
                            IND_PROM_AUTOMATICO,  --JCHAVEZ 19102009
                            AHORRO_PACK, --JCHAVEZ 19102009
                            PORC_DCTO_CALC_PACK --JCHAVEZ 19102009
                            )
                            SELECT   cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,nCantItemPed,
                                     cProdRegalo.Cod_Prod,nCantNueva,
                                     0,0,
                                     PROD_LOCAL.PORC_DCTO_1,
                                     PROD_LOCAL.PORC_DCTO_2,
                                     PROD_LOCAL.PORC_DCTO_3,
                                     PROD_LOCAL.PORC_DCTO_1+ PROD_LOCAL.PORC_DCTO_2 +
                                     PROD_LOCAL.PORC_DCTO_3,
                                     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.VAL_BONO_VIG,(PROD.VAL_BONO_VIG / PROD_LOCAL.VAL_FRAC_LOCAL)),
                            			   PROD_LOCAL.VAL_FRAC_LOCAL,
                                     cSecUsu_in,cLogin_in,
                                     0,
                                     IGV.PORC_IGV,/* PROD.DESC_UNID_PRESENT*/
                                     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.Unid_Vta),--21.05.2010 dubilluz
                                     DECODE(IGV.PORC_IGV,0,'S','N'),
                                     PROD_LOCAL.VAL_PREC_VTA,
                                     null,
                                     PROD_LOCAL.VAL_FRAC_LOCAL,
                                     nCantNueva,--
                                     cProdRegalo.Cod_Prom, --
                                     'S',  --JCHAVEZ 19102009
                                     --(PROD_LOCAL.VAL_PREC_VTA*nCantNueva), --JCHAVEZ 19102009
                                     (((PROD_LOCAL.VAL_PREC_VTA)*DECODE(((100-v_nPorcDscto)/100),1,1,(100-v_nPorcDscto)/100))*nCantNueva),--JCHAVEZ 19102009
                                     v_nPorcDscto --JCHAVEZ 19102009

                            FROM     LGT_PROD PROD,
                            	       LGT_PROD_LOCAL PROD_LOCAL,
                                     pbl_igv igv
                            WHERE    PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
                            AND	     PROD_LOCAL.COD_LOCAL = cCodLocal_in
                            AND	     PROD_LOCAL.COD_PROD = cProdRegalo.Cod_Prod
                            AND	     PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
                            AND	     PROD.COD_PROD = PROD_LOCAL.COD_PROD
                            AND      PROD.COD_IGV = IGV.COD_IGV;


             end if;
             -- fin de inssert prod regalo

             END IF;
         end loop;

       END LOOP;

      END LOOP;

   NULL;
   END;


 /* *********************************************************** */
   PROCEDURE VTA_P_GRABA_PROM_NO_AUTOMAT(cCodGrupoCia_in 	 	  IN CHAR,
                                      cCodLocal_in    	 	  IN CHAR,
                            				  cNumPedVta_in   	 	  IN CHAR,
                                      cCodProm                	  IN CHAR,
                            				  nCantAtendida_in	 	  IN NUMBER,
                            				  cUsuCreaPedVtaDet_in	IN CHAR,
                                      -- KMONCADA 2016.01.22 GENERA PROFORMA [LOCAL M]
                                      cGeneraProforma_in  IN  CHAR DEFAULT 'N'
                                      )
  AS
    CURSOR curProdPqt2(cCodGrupoCia_in IN CHAR,codPaquete_in IN CHAR) IS
      SELECT A.COD_PROD
      FROM VTA_PROD_PAQUETE A
      WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND A.COD_PAQUETE = codPaquete_in;

    v_vCodPaquete VTA_PROMOCION.COD_PAQUETE_2%TYPE;
    v_vCodProd VTA_PROD_PAQUETE.Cod_Prod%TYPE;
    v_nCantAtendida VTA_PEDIDO_VTA_DET.CANT_ATENDIDA%TYPE;
    v_nPorcDscto VTA_PROD_PAQUETE.PORC_DCTO%TYPE;
  BEGIN
    IF cGeneraProforma_in = 'S' THEN
        INSERT INTO TMP_VTA_PEDIDO_PACK(COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_PROM,CANTIDAD,USU_CREA_PROMOCION,FEC_CREA_PROMOCION)
                VALUES(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cCodProm,nCantAtendida_in,cUsuCreaPedVtaDet_in,SYSDATE);      
    ELSE
        INSERT INTO VTA_PEDIDO_PACK(COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,COD_PROM,CANTIDAD,USU_CREA_PROMOCION,FEC_CREA_PROMOCION)
                VALUES(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cCodProm,nCantAtendida_in,cUsuCreaPedVtaDet_in,SYSDATE);
    END IF;
  END;
/*------------------------------------------------------------------------------------------------------------------
GOAL : Actualizar el Porcentaje de Descuento para Aquellos Regalos que aplica PRECIO FIJO
Ammedments:
When          Who      What
15-AGO-13     TCT      Create
--------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_UPDATE_DSCTO_REGA_PROM(cCodGrupoCia_in IN CHAR,
  		   				                         cCodLocal_in	 IN CHAR,
                                         vCodProm IN CHAR)
AS
 v_cCod_Paq_2 VARCHAR(6);
BEGIN
 -- 10 .- Obtener el codigo del paquete de Regalo
 SELECT vp.cod_paquete_2
 INTO v_cCod_Paq_2
 FROM vta_promocion vp
 WHERE vp.cod_grupo_cia = cCodGrupoCia_in
   AND vp.cod_prom      = vCodProm;
 -- 20.- Update Paquete con regalos
 UPDATE vta_prod_paquete pp
 SET pp.porc_dcto = (1-(pp.prec_fijo/(
                                   SELECT pl.val_prec_vta
                                   FROM LGT_PROD_LOCAL PL
                                   WHERE pl.cod_grupo_cia = cCodGrupoCia_in
                                     AND pl.cod_local     = cCodLocal_in
                                     AND pl.cod_prod      = pp.cod_prod
                                  )))*100,
      pp.fec_mod_prod_paquete = SYSDATE,
      pp.usu_mod_prod_paquete = 'SP_UPD_DSCTO'
WHERE pp.cod_paquete = v_cCod_Paq_2
  AND pp.flg_modo    = '2'                    -- Precio Fijo
  AND pp.flg_tipo_valor = '1'                 -- Importe
  AND NVL(pp.prec_fijo,0)>0;                  -- PRECIO FIJO > 0

END;
/*------------------------------------------------------------------------------------------------------------------
GOAL : Actualizar el Porcentaje de Descuento para Aquellos Regalos que aplica PRECIO FIJO x Producto
Ammedments:
When          Who      What
22-AGO-13     TCT      Create
--------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_UPD_DSCTO_REGA_PROD(cCodGrupoCia_in IN CHAR,
  		   				                         cCodLocal_in	 IN CHAR,
                                         vCodProd IN CHAR)
AS
 -- 05 .- Cursor con Promociones, Paquetes que tienen Regalos para Producto Emite
 CURSOR cr_Proms(ac_CodProd CHAR) IS
 SELECT vp.*
  FROM vta_promocion vp
  WHERE vp.cod_paquete_1 IN (
                             SELECT pp.cod_paquete
                             FROM vta_prod_paquete pp
                             WHERE pp.cod_prod      = ac_CodProd--'125144'
                               AND pp.cod_grupo_cia = '001'
                            )
   AND vp.estado='A'
   AND trunc(vp.fec_promocion_fin)>=trunc(SYSDATE);
-- Variables
 reg_Prom cr_Proms%ROWTYPE;




BEGIN
  FOR reg_Prom IN cr_Proms(vCodProd) LOOP
       -- 20.- Update Paquete con regalos
       UPDATE vta_prod_paquete pp
       SET pp.porc_dcto = (1-(pp.prec_fijo/(
                                         SELECT pl.val_prec_vta
                                         FROM LGT_PROD_LOCAL PL
                                         WHERE pl.cod_grupo_cia = cCodGrupoCia_in
                                           AND pl.cod_local     = cCodLocal_in
                                           AND pl.cod_prod      = pp.cod_prod
                                        )))*100,
            pp.fec_mod_prod_paquete = SYSDATE,
            pp.usu_mod_prod_paquete = 'SP_UPD_DSCTO'
      WHERE pp.cod_paquete = reg_Prom.Cod_Paquete_2
        AND pp.flg_modo    = '2'                    -- Precio Fijo
        AND pp.flg_tipo_valor = '1'                 -- Importe
        AND NVL(pp.prec_fijo,0)>0;                  -- PRECIO FIJO > 0
  END LOOP;

END;
/****************************************************************************************************************/

FUNCTION GET_MAX_STK_PROD_FINAL(cCodGrupoCia_in IN CHAR,
                                                             cCodLocal_in IN CHAR,
                                                             cCodProd_in IN CHAR)
    RETURN NUMBER
           IS
          rpta  number(6) := 0;

     BEGIN

           SELECT NVL(MIN(MINIMO),0)
           INTO rpta
            FROM 
            (SELECT pl.cod_prod, 
                                   ((pl.stk_fisico / TT.VAL_FRAC) * pl.val_frac_local) AS CANTIDAD,
                                   tt.cant_receta,
                                   (((pl.stk_fisico / TT.VAL_FRAC) * pl.val_frac_local) / tt.cant_receta) as minimo
                  FROM LGT_PROD_LOCAL PL,
                                LGT_REL_PROD_TICO TT
                  WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND PL.COD_LOCAL = cCodLocal_in
                  AND PL.COD_GRUPO_CIA = TT.COD_GRUPO_CIA
                  AND PL.COD_PROD = TT.COD_PROD_HIJO
                  AND TT.COD_PROD_PADRE = cCodProd_in) X ;
            
    RETURN rpta;
  END;

/****************************************************************************************/

FUNCTION GET_IND_PROD_IN_PACK(cCodGrupoCia_in IN CHAR,
                                                             cCodLocal_in IN CHAR,
                                                             cCodProd_in IN CHAR)
    RETURN CHAR
           IS
          cantidad  number(6) := 0;
          flag char(1) := 'N';

     BEGIN
    --ERIOS 17.11.2014 Cambios de JLUNA
        SELECT COUNT(1)
        INTO CANTIDAD
        FROM(
                  SELECT DISTINCT P.COD_PROM
                      FROM   VTA_PROMOCION P,
                             VTA_PROD_PAQUETE Q
                        WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND  P.ESTADO = 'A'
                      AND    P.COD_PAQUETE_2 IN (SELECT V.COD_PAQUETE
                                                 FROM (
                                                 SELECT T.COD_PAQUETE,COUNT(1)
                                                 FROM   VTA_PROD_PAQUETE T
                                                 WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
                                                 GROUP BY T.COD_PAQUETE
                                                 HAVING  COUNT(1) =
                                                         (SELECT COUNT(1) FROM VTA_PROD_PAQUETE X 
                                                          WHERE X.COD_GRUPO_CIA = cCodGrupoCia_in
                                                          AND X.COD_PAQUETE = T.COD_PAQUETE)
                                                       )V
                                                 )
                      AND    P.COD_GRUPO_CIA = Q.COD_GRUPO_CIA
                      AND    (P.COD_PAQUETE_1 = Q.COD_PAQUETE OR P.COD_PAQUETE_2 = Q.COD_PAQUETE)
                      AND    Q.COD_PROD = cCodProd_in
                      AND   Q.COD_GRUPO_CIA = cCodGrupoCia_in
            );
            
            IF cantidad > 0 THEN
                        flag := 'S';
            END IF;

    RETURN flag;
  END;
/****************************************************************************************/

END;
/
