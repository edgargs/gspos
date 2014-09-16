--------------------------------------------------------
--  DDL for Package Body PTOVENTA_VTA_LISTA_AS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_VTA_LISTA_AS" is

  FUNCTION VTA_LISTA_PROD_02_NEW(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             vCadena_in IN VARCHAR2,
                             cTipo_in IN VARCHAR2)
  RETURN FarmaCursor
  IS
     LISTA FarmaCursor;
     codDIAD char(5);
     codCINC char(5);
     porcDIAD number(8,2);
     maxDIAD number(8,2);
     porcCINC number(8,2);
     maxCINC number(8,2);
     cIndCosPromedioDia char(1);
     cIndCosPromedioGold char(1);
  BEGIN
     SELECT W.LLAVE_TAB_GRAL INTO codDIAD FROM PBL_TAB_GRAL W
     WHERE W.ID_TAB_GRAL='334';
     SELECT Y.LLAVE_TAB_GRAL INTO codCINC FROM PBL_TAB_GRAL Y
     WHERE Y.ID_TAB_GRAL='335';

     SELECT P.VALOR_CUPON, P.MONTO_MAX_DESCT,p.ind_val_costo_prom
     INTO porcDIAD, maxDIAD,cIndCosPromedioDia
     FROM VTA_CAMPANA_CUPON P
     WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
     AND P.COD_CAMP_CUPON=codDIAD;

     SELECT K.VALOR_CUPON, K.MONTO_MAX_DESCT,k.ind_val_costo_prom
     INTO porcCINC, maxCINC,cIndCosPromedioGold
     FROM VTA_CAMPANA_CUPON K
     WHERE K.COD_GRUPO_CIA=cCodGrupoCia_in
     AND K.COD_CAMP_CUPON=codCINC;

     IF cCodGrupoCia_in = COD_CIA_PERU THEN

       IF cTipo_in = '1' THEN

         OPEN LISTA FOR
         SELECT A.DESC_PROD || 'Ã' ||
                A.DESC_UNID_PRESENT || 'Ã' ||
                C.NOM_LAB || 'Ã' ||
                --TO_CHAR(A.VAL_PREC_PROV_VIG,'999,999.000') || 'Ã' || --VVF PRECIO DEL PROVEEDOR VIGENTE
                NVL(TO_CHAR(VTA_REDONDEO_MAS(B.VAL_PREC_VTA*B.Val_Frac_Local),'999,999.00'),'NO DISP') || 'Ã' || --PRECIO ENTERO LOCAL
                NVL(TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcDIAD,maxDIAD,a.cod_prod,codDIAD,cIndCosPromedioDia,VAL_PREC_PROM,decode(TRIM(a.cod_igv),'01',19,0)),'999,999.00'),'NO DISP') || 'Ã' ||--JSANTIVANEZ 02.09.2010
                NVL(TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcCINC,maxCINC,a.cod_prod,codCINC,cIndCosPromedioGold,VAL_PREC_PROM,decode(TRIM(a.cod_igv),'01',19,0)),'999,999.00'),'NO DISP') || 'Ã' ||--JSANTIVANEZ 02.09.2010
                B.COD_PROD
         FROM LGT_PROD A, LGT_PROD_LOCAL B, LGT_LAB C
         WHERE A.COD_PROD=B.COD_PROD
         AND A.COD_LAB=C.COD_LAB
         AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
         AND B.COD_GRUPO_CIA=cCodGrupoCia_in
         --JQUISPE 14.08.2010 CAMBIO COD GRUPO CIA
         --AND B.COD_GRUPO_CIA=C.COD_GRUPO_CIA
         AND B.COD_LOCAL=cCodLocal_in
         AND A.EST_PROD='A'
         AND B.STK_FISICO/*-B.STK_COMPROMETIDO*/>0
         AND A.DESC_PROD LIKE '%'||vCadena_in||'%'
         --ORDER BY INSTR(A.DESC_PROD,vCadena_in,1),A.DESC_PROD;
         ORDER BY SIGN(INSTR(A.DESC_PROD,vCadena_in,1)-1),DESC_PROD;

       ELSE

         OPEN LISTA FOR
         SELECT A.DESC_PROD || 'Ã' ||
                A.DESC_UNID_PRESENT || 'Ã' ||
                C.NOM_LAB || 'Ã' ||
                --TO_CHAR(A.VAL_PREC_PROV_VIG,'999,999.000') || 'Ã' || --VVF PRECIO DEL PROVEEDOR VIGENTE
                TO_CHAR(VTA_REDONDEO_MAS(B.VAL_PREC_VTA*B.Val_Frac_Local),'999,999.00') || 'Ã' || --PRECIO ENTERO LOCAL
                TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcDIAD,maxDIAD,a.cod_prod,codDIAD,cIndCosPromedioDia,VAL_PREC_PROM,decode(TRIM(a.cod_igv),'01',19,0)),'999,999.00') || 'Ã' ||--JSANTIVANEZ 02.09.2010
                TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcCINC,maxCINC,a.cod_prod,codCINC,cIndCosPromedioGold,VAL_PREC_PROM,decode(TRIM(a.cod_igv),'01',19,0)),'999,999.00')  || 'Ã' || --JSANTIVANEZ 02.09.2010
                B.COD_PROD
         FROM LGT_PROD A, LGT_PROD_LOCAL B, LGT_LAB C, lgt_princ_act D, lgt_princ_act_prod E
         WHERE A.COD_PROD=B.COD_PROD
         AND A.COD_LAB=C.COD_LAB
         AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
         AND B.COD_GRUPO_CIA=cCodGrupoCia_in
         --JQUISPE 14.08.2010 CAMBIO COD GRUPO CIA
--         AND B.COD_GRUPO_CIA=C.COD_GRUPO_CIA
         AND B.COD_LOCAL=cCodLocal_in
         AND A.EST_PROD='A'
         AND B.STK_FISICO/*-B.STK_COMPROMETIDO*/>0
         AND D.DESC_PRINC_ACT LIKE '%'||vCadena_in||'%'
         --AND D.COD_GRUPO_CIA=A.COD_GRUPO_CIA
        -- AND D.COD_GRUPO_CIA=E.COD_GRUPO_CIA
         AND TRIM(D.COD_PRINC_ACT)=TRIM(E.COD_PRINC_ACT)
         AND A.COD_PROD=E.COD_PROD
         --ORDER BY INSTR(A.DESC_PROD,vCadena_in,1),A.DESC_PROD;
         ORDER BY SIGN(INSTR(A.DESC_PROD,vCadena_in,1)-1),DESC_PROD;

       END IF;

     ELSIF cCodGrupoCia_in = COD_CIA_BOL THEN

       OPEN LISTA FOR
       SELECT A.DESC_PROD || 'Ã' ||
              A.DESC_UNID_PRESENT || 'Ã' ||
              C.NOM_LAB || 'Ã' ||
              --TO_CHAR(A.VAL_PREC_PROV_VIG,'999,999.000') || 'Ã' || --VVF PRECIO DEL PROVEEDOR VIGENTE
              TO_CHAR(VTA_REDONDEO_MAS(B.VAL_PREC_VTA*B.Val_Frac_Local),'999,999.00') || 'Ã' ||/* || 'Ã' || --PRECIO ENTERO LOCAL
              TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcDIAD,maxDIAD,a.cod_prod,codDIAD,cIndCosPromedioDia,VAL_PREC_PROM,decode(TRIM(a.cod_igv),'01',19,0)),'999,999.00') || 'Ã' ||--JSANTIVANEZ 02.09.2010
              TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcCINC,maxCINC,a.cod_prod,codCINC,cIndCosPromedioGold,VAL_PREC_PROM,decode(TRIM(a.cod_igv),'01',19,0)),'999,999.00')--JSANTIVANEZ 02.09.2010*/
              B.COD_PROD
       FROM LGT_PROD A, LGT_PROD_LOCAL B, LGT_LAB C
       WHERE A.COD_PROD=B.COD_PROD
       AND A.COD_LAB=C.COD_LAB
       AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
       AND B.COD_GRUPO_CIA=cCodGrupoCia_in
       --JQUISPE 14.08.2010 CAMBIO COD GRUPO CIA
       --AND B.COD_GRUPO_CIA=C.COD_GRUPO_CIA
       AND B.COD_LOCAL=cCodLocal_in
       AND A.EST_PROD='A'
       AND B.STK_FISICO/*-B.STK_COMPROMETIDO*/>0
       AND A.DESC_PROD LIKE '%'||vCadena_in||'%'
       --ORDER BY INSTR(A.DESC_PROD,vCadena_in,1),A.DESC_PROD;
       ORDER BY SIGN(INSTR(A.DESC_PROD,vCadena_in,1)-1),DESC_PROD;

     END IF;

     RETURN LISTA;
  END;



/*--------------------------------------------------------------------------------------------------------------------------------
GOAL : Calcular Descuento en Precio de Productos
History : 31-JUL-14  TCT  Modifica Lectura de Productos uso en Camp : A0012
---------------------------------------------------------------------------------------------------------------------------------*/
 FUNCTION VTA_CALCULAR_DSCTO(nPrecio_in IN NUMBER,
                              nPorc_in IN NUMBER,
                              nMax_in IN NUMBER,
                              nCodProd_in IN varchar2,
                              nCodCamp_in IN varchar2,
                              nValCostProm in char,
                              nValPrecProm in number,
                              nIgv number)
  RETURN NUMBER
  IS
  valorDscto number(8,2);
  valorFinal number(8,2);
  vAplicaDescuento number;
  maxDsctoDNI NUMBER(8,2); --ASOSA, 26.07.2010
  BEGIN

       SELECT t.llave_tab_gral --INI - ASOSA, 26.07.2010
       INTO maxDsctoDNI
       FROM pbl_tab_gral t
       WHERE t.id_tab_gral=274; --FIN - ASOSA, 26.07.2010
       
       --- < 31-JUL-14  TCT Modifica Lectura de Productos Uso >
/*       select count(1)
       into   vAplicaDescuento
       from   vta_campana_prod_uso p
       where  p.cod_grupo_cia = '001'
       and    p.cod_camp_cupon = nCodCamp_in
       and    p.cod_prod = nCodProd_in;*/
       
       select count(1)
       into   vAplicaDescuento
       from   vta_campana_prod_uso p
       where  p.cod_grupo_cia = '001'
       and    p.cod_camp_cupon = (
                                   SELECT nvl(cc.cod_camp_cupon_prod_uso,cc.cod_camp_cupon)
                                   FROM vta_campana_cupon cc
                                   WHERE cc.cod_grupo_cia  = '001'
                                     AND cc.cod_camp_cupon = nCodCamp_in
                                  )
       and    p.cod_prod = nCodProd_in;
       --- < /31-JUL-14  TCT Modifica Lectura de Productos Uso >
       

       if vAplicaDescuento > 0 then

         valorDscto:=nPrecio_in*(nPorc_in/100);
         IF valorDscto>nMax_in THEN
            valorDscto:=nMax_in;
         END IF;
         IF valorDscto > maxDsctoDNI THEN --INI - ASOSA, 26.07.2010
            valorDscto:=maxDsctoDNI;
         END IF;                    --FIN - ASOSA, 26.07.2010


         valorFinal:=nPrecio_in-valorDscto;
         -----------------------------------
         if trim(nValCostProm) = 'N' then

            if valorFinal < nValPrecProm*(1+nIgv/100) then
               valorFinal:= nValPrecProm*(1+nIgv/100);
            else
               valorFinal:=nPrecio_in-valorDscto;
            end if;
         else
            valorFinal:=nPrecio_in-valorDscto;
         end if;
         ------

       else
         valorFinal:=nPrecio_in;
         end if;

       --RETURN ptoventa_vta_lista_as.vta_redondeo_mas(valorFinal); --ASOSA, 15.10.2010 por indicacion de JOLIVA y autorizacion de RCASTRO
       RETURN valorFinal;
  END;

  /*****************************************************************************************************/
  FUNCTION VTA_REDONDEO_MAS(nPrecio_in IN NUMBER)
  RETURN NUMBER
  IS
  nEntera  number;
  nParteDecimal number;
  nDecimales number;
  nCentecima number;
  valorFinal number;
  BEGIN

       select trunc(nPrecio_in)
       into   nEntera
       from   dual;

       nParteDecimal := nPrecio_in -  nEntera;

       select trunc((nParteDecimal)*10)
       into   nDecimales
       from   dual;


       select trunc( ( (nParteDecimal)*10 - trunc((nParteDecimal)*10) ) *10 )
       into   nCentecima
       from   dual;

       /*dbms_output.put_line(nParteDecimal);
       dbms_output.put_line(nEntera);
       dbms_output.put_line(nDecimales);
       dbms_output.put_line(nCentecima);*/

       if nCentecima > 0 then
          nDecimales:= nDecimales + 1;

       end if;

       valorFinal := nEntera + nDecimales/10;
       RETURN valorFinal;
  END;

  /*****************************************************************************************************/

  FUNCTION VTA_RETORNAR_MENSAJE(vIP_LOCAL_in IN VARCHAR,cCodGrupoCia_in in char, cCodCia_in in char, cCodLocal_in IN CHAR)
  RETURN VARCHAR2
  IS
   vMsg_out varchar2(32767):= '';

  vFila_IMG_Cabecera_MF varchar2(2800):= '';

  vFila_Num_Ped      varchar2(2800):= '';
  vFila_Msg_01       varchar2(2800):= '';
  vFila_Msg_02       varchar2(2800):= '';
  vFila_Pie_Pagina   varchar2(2800):= '';

  vFila_Consejos     varchar2(22767):= '';
    v_vRuta VARCHAR2(500);
  BEGIN

    v_vRuta := PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ||'\'||PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES||'\'||PTOVENTA_GRAL.GET_IMG_LIST_DIGEMID||PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(cCodGrupoCia_in,cCodCia_in,cCodLocal_in);
       vFila_IMG_Cabecera_MF:= '<tr align="center"> '||
                         '<img src=file:'||
                         v_vRuta||
                         ' width="300" height="90">'||
                         '</tr> ';
       --vFila_Msg_01:='<tr><font sice="-2" face="Arial" >En cumplimiento al <b>DS  Nº 006-2009-PCM ¿ TEXTO ÚNICO ORDENADO DE LA LEY DE PROTECCION AL CONSUMIDOR, Artículo 17</b> ponemos a disposición de cualquier consumidor que lo solicite la lista de precios de Mifarma en este local. Dado que la lista entera no entra en una pantalla, para encontrar el producto solicitado basta con digitar un mínimo de tres letras del producto buscado y saldrán todas las alternativas de productos que tengan esas letras. El personal de Boticas Mifarma está dispuesto a ayudar en la digitación y la pantalla estará a la vista del consumidor que lo solicite expresamente.</font></tr>';
       --JMIRANDA 02.02.2010
       vFila_Msg_01:='<tr><font size="3" face="Arial" >En cumplimiento de la Ley 29571 ponemos a disposición del consumidor que lo solicite la siguiente consulta de precios de los productos comercializados en este local. Para acceder al precio de un producto el consumidor primero debe ingresar el nombre del producto a consultar o parte del nombre del mismo. Seguidamente aparecerá una primera lista de todos los productos que contienen las letras ingresadas por el consumidor y los precios de lista vigentes en el momento de la consulta. En caso de productos farmacéuticos el consumidor podrá elegir el producto específico que quiere consultar y saldrá una lista ordenada alfabéticamente por la Denominación Común Internacional de todos los productos que contengan la misma DCI.</font></tr>';
 vMsg_out:=C_INICIO_MSG||vFila_Msg_01||vFila_IMG_Cabecera_MF||C_FIN_MSG;
 return vMsg_out;
  END;

  /*****************************************************************************************************/


/*****************************************************************************************************/

    FUNCTION IMP_GET_NUM_CARACTERES
  RETURN VARCHAR2
  IS
  vResultado PBL_TAB_GRAL.LLAVE_TAB_GRAL%TYPE := ' ';
  BEGIN
      BEGIN
      SELECT TRIM(T.LLAVE_TAB_GRAL)
      INTO   vResultado
      FROM   PBL_TAB_GRAL T
      WHERE  T.ID_TAB_GRAL = 338;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      vResultado := '4';
      END;

   RETURN vResultado;
  END;

/*****************************************************************************************************/

FUNCTION VTA_LISTA_PROD_FILTRO(cCodGrupoCia_in IN CHAR,
  		   				  		           cCodLocal_in	   IN CHAR,
								                 cTipoFiltro_in  IN CHAR,
  		   						             cCodFiltro_in 	 IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
    vFecha_actual date;
  BEGIN
    SELECT TRUNC(SYSDATE)
    INTO   vFecha_actual
    FROM   DUAL;

  	 IF(cTipoFiltro_in = 1) THEN --principio activo
     	OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO/* - PROD_LOCAL.STK_COMPROMETIDO*/) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
              TO_CHAR(VTA_F_GET_PREC_CAMP(PROD_LOCAL.COD_GRUPO_CIA,PROD_LOCAL.COD_LOCAL,PROD_LOCAL.COD_PROD),'999,990.000') || 'Ã' ||
			   		  NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			   		  PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			   		  PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			        TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			   		  PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
			        DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)|| 'Ã' ||
              NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||-- JCORTEZ ind encarte
              IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
			 FROM   LGT_PROD PROD,
				   	  LGT_PROD_LOCAL PROD_LOCAL,
				   	  LGT_LAB LAB,
				   	  PBL_IGV IGV,
					    LGT_PRINC_ACT_PROD PRINC_ACT_PROD,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = '001'
                      ) V1,
                      VTA_PROMOCION    P

                   /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                 AND TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
                      WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                      AND  P.COD_GRUPO_CIA = 001
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
              ) Z,
             (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                    PROD_ENCARTE.COD_ENCARTE,
                    PROD_ENCARTE.COD_PROD
             FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                    VTA_ENCARTE ENCARTE
             WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
             AND    ENCARTE.ESTADO = 'A'
             AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
             AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
             )   V2
			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND	  PROD_LOCAL.COD_LOCAL = cCodLocal_in
			 AND	  PRINC_ACT_PROD.COD_PRINC_ACT = cCodFiltro_in
			 AND	  PROD.COD_PROD = PRINC_ACT_PROD.COD_PROD
			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD
			 AND	  PROD.COD_LAB = LAB.COD_LAB
			 AND	  PROD.COD_IGV = IGV.COD_IGV
			 AND	  PROD.EST_PROD = 'A'
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       AND    PROD.COD_PROD = Z.COD(+)
       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
       AND   PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
       ;
	ELSIF(cTipoFiltro_in = 2) THEN --accion terapeutica
		OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO /*- PROD_LOCAL.STK_COMPROMETIDO*/) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
              TO_CHAR(VTA_F_GET_PREC_CAMP(PROD_LOCAL.COD_GRUPO_CIA,PROD_LOCAL.COD_LOCAL,PROD_LOCAL.COD_PROD),'999,990.000') || 'Ã' ||
			   		  NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			   		  PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			   		  PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			        TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			   		  PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD  || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
	      	    DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)|| 'Ã' ||
              NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||-- JCORTEZ ind encarte
              IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
			 FROM   LGT_PROD PROD,
				   	  LGT_PROD_LOCAL PROD_LOCAL,
				   	  LGT_LAB LAB,
				   	  PBL_IGV IGV,
					    LGT_ACC_TERAP ACC_TER,
					    LGT_ACC_TERAP_PROD ACC_TERAP_PROD,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = '001'
                      ) V1,
                      VTA_PROMOCION    P

                 /*    WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                AND  TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */         WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND  P.COD_GRUPO_CIA = 001
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
              ) Z,
              (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                      PROD_ENCARTE.COD_ENCARTE,
                      PROD_ENCARTE.COD_PROD
               FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                      VTA_ENCARTE ENCARTE
               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
               AND    ENCARTE.ESTADO = 'A'
               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
               )   V2
			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND	  PROD_LOCAL.COD_LOCAL = cCodLocal_in
			 AND	  ACC_TER.COD_ACC_TERAP = cCodFiltro_in
			 AND	  PROD.COD_GRUPO_CIA = ACC_TERAP_PROD.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = ACC_TERAP_PROD.COD_PROD
			 AND	  ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP
			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD
			 AND	  PROD.COD_LAB = LAB.COD_LAB
			 AND	  PROD.COD_IGV = IGV.COD_IGV
			 AND	  PROD.EST_PROD = 'A'
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       AND    PROD.COD_PROD = Z.COD(+)
       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
       AND   PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
       ;
	ELSIF(cTipoFiltro_in = 3) THEN --laboratorio
		OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO /*- PROD_LOCAL.STK_COMPROMETIDO*/) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
              TO_CHAR(VTA_F_GET_PREC_CAMP(PROD_LOCAL.COD_GRUPO_CIA,PROD_LOCAL.COD_LOCAL,PROD_LOCAL.COD_PROD),'999,990.000') || 'Ã' ||
			   		  NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			   		  PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			   		  PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			        TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			   		  PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD  || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
	    		    DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)|| 'Ã' ||
              NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||-- JCORTEZ ind encarte
              IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
			 FROM   LGT_PROD PROD,
				   	  LGT_PROD_LOCAL PROD_LOCAL,
				   	  LGT_LAB LAB,
				   	  PBL_IGV IGV,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                      ) V1,
                      VTA_PROMOCION    P

                   /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                AND  TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
                      WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
              ) Z,
              (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                      PROD_ENCARTE.COD_ENCARTE,
                      PROD_ENCARTE.COD_PROD
               FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                      VTA_ENCARTE ENCARTE
               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
               AND    ENCARTE.ESTADO = 'A'
               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
               )   V2
			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND	  PROD_LOCAL.COD_LOCAL = cCodLocal_in
			 AND	  LAB.COD_LAB = cCodFiltro_in
			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD
			 AND	  PROD.COD_LAB = LAB.COD_LAB
			 AND	  PROD.COD_IGV = IGV.COD_IGV
			 AND	  PROD.EST_PROD = 'A'
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       AND    PROD.COD_PROD = Z.COD(+)
       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
       AND   PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
       ;
  ELSIF(trim(cTipoFiltro_in) = 4) THEN -- JCORTTEZ 17.04.08 productos de encarte
		OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO  /*-PROD_LOCAL.STK_COMPROMETIDO*/) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
              TO_CHAR(VTA_F_GET_PREC_CAMP(PROD_LOCAL.COD_GRUPO_CIA,PROD_LOCAL.COD_LOCAL,PROD_LOCAL.COD_PROD),'999,990.000') || 'Ã' ||
			   		  NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			   		  PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			   		  PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			        TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			   		  PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD  || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
	      	    DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)|| 'Ã' ||
              NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||-- JCORTEZ ind encarte
              IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
			 FROM   LGT_PROD PROD,
				   	  LGT_PROD_LOCAL PROD_LOCAL,
				   	  LGT_LAB LAB,
				   	  PBL_IGV IGV,
					    LGT_ACC_TERAP ACC_TER,
					    LGT_ACC_TERAP_PROD ACC_TERAP_PROD,
              LGT_PROD_VIRTUAL PR_VRT,
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                      ) V1,
                      VTA_PROMOCION    P

                    /* WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                      TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                  AND TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */
                      WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND   P.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))) Z,
              (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                      PROD_ENCARTE.COD_ENCARTE,
                      PROD_ENCARTE.COD_PROD
               FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                      VTA_ENCARTE ENCARTE
               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
               AND    ENCARTE.ESTADO = 'A'
               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
               )   V2
			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND	  PROD_LOCAL.COD_LOCAL = cCodLocal_in
			 --AND	  ACC_TER.COD_ACC_TERAP = cCodFiltro_in
			 AND	  PROD.COD_GRUPO_CIA = ACC_TERAP_PROD.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = ACC_TERAP_PROD.COD_PROD
			 AND	  ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP
			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD
			 AND	  PROD.COD_LAB = LAB.COD_LAB
			 AND	  PROD.COD_IGV = IGV.COD_IGV
			 AND	  PROD.EST_PROD = 'A'
       AND    V2.COD_ENCARTE = '00001' --preguntar a DIEGO
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       AND    PROD.COD_PROD = Z.COD(+)
       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
       AND    PROD.COD_PROD=V2.COD_PROD--JCORTEZ
       ;
	ELSIF(cTipoFiltro_in = 5) THEN -- JCORTTEZ 17.04.08 productos Cupon
		OPEN curVta FOR
			 SELECT PROD.COD_PROD || 'Ã' ||
			   		  PROD.DESC_PROD || 'Ã' ||
			   		  DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
			   		  LAB.NOM_LAB || 'Ã' ||
			   		  (PROD_LOCAL.STK_FISICO /*- PROD_LOCAL.STK_COMPROMETIDO*/) || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_VTA,'999,990.000') || 'Ã' ||
              TO_CHAR(VTA_F_GET_PREC_CAMP(PROD_LOCAL.COD_GRUPO_CIA,PROD_LOCAL.COD_LOCAL,PROD_LOCAL.COD_PROD),'999,990.000') || 'Ã' ||
			   		  NVL(PROD.IND_ZAN,' ') || 'Ã' ||
			   		  PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			   		  PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
			   		  TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
			        TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			   		  PROD.IND_PROD_FARMA || 'Ã' ||
              DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
              NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
              PROD.IND_PROD_REFRIG          || 'Ã' ||
              PROD.IND_TIPO_PROD  || 'Ã' ||
              DECODE(NVL(Z.COD,'N'),'N','N','S')|| 'Ã' ||
              PROD.DESC_PROD ||
	      	    DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA)|| 'Ã' ||
              NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||-- JCORTEZ ind encarte
              IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
			 FROM   LGT_PROD PROD,
				   	  LGT_PROD_LOCAL PROD_LOCAL,
				   	  LGT_LAB LAB,
				   	  PBL_IGV IGV,
					    LGT_ACC_TERAP ACC_TER,
					    LGT_ACC_TERAP_PROD ACC_TERAP_PROD,
              LGT_PROD_VIRTUAL PR_VRT,
              --VTA_PROD_PAQUETE  PROM
              (SELECT DISTINCT(V1.COD_PROD) COD
               FROM  (SELECT COD_PAQUETE,COD_PROD
                      FROM   VTA_PROD_PAQUETE
                      WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                      ) V1,
                      VTA_PROMOCION    P

                    /* WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN

                     TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')

             AND     TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
           */

             WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                      AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND    P.ESTADO  = 'A'
                      AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                             OR
                               P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
              ) Z,
              (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                      PROD_ENCARTE.COD_ENCARTE,
                      PROD_ENCARTE.COD_PROD
               FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                      VTA_ENCARTE ENCARTE
               WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
               AND    ENCARTE.ESTADO = 'A'
               AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
               AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
               )   V2,
                VTA_CAMPANA_PROD V3,
                VTA_CAMPANA_CUPON V4 -- DUBILLUZ 03/07/2008
			 WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
			 AND	  PROD_LOCAL.COD_LOCAL = cCodLocal_in
			-- AND	  ACC_TER.COD_ACC_TERAP = cCodFiltro_in
			 AND	  PROD.COD_GRUPO_CIA = ACC_TERAP_PROD.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = ACC_TERAP_PROD.COD_PROD
			 AND	  ACC_TERAP_PROD.COD_ACC_TERAP = ACC_TER.COD_ACC_TERAP
			 AND	  PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
			 AND	  PROD.COD_PROD = PROD_LOCAL.COD_PROD
			 AND	  PROD.COD_LAB = LAB.COD_LAB
			 AND	  PROD.COD_IGV = IGV.COD_IGV
			 AND	  PROD.EST_PROD = 'A'
       AND    V4.ESTADO = 'A'
       AND    vFecha_actual between V4.FECH_INICIO AND  V4.FECH_FIN
       and    v4.cod_grupo_cia = v3.cod_grupo_cia
       and    v4.cod_camp_cupon = v3.cod_camp_cupon
       AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
       AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
       AND    PROD.COD_PROD = Z.COD(+)
       AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
       AND    PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
       AND    PROD.COD_GRUPO_CIA=V3.COD_GRUPO_CIA(+)
       AND    PROD.COD_PROD=V3.COD_PROD
       ;
  	END IF;
  RETURN curVta;
  END;

/***************************************************************************************************************/

FUNCTION VTA_LISTA_PROD(cCodGrupoCia_in IN CHAR,
  		   				          cCodLocal_in	  IN CHAR)
    RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
	SELECT distinct(PROD.COD_PROD) || 'Ã' ||
			     PROD.DESC_PROD || 'Ã' ||
			     DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
           LAB.NOM_LAB || 'Ã' ||
			     (PROD_LOCAL.STK_FISICO /*- PROD_LOCAL.STK_COMPROMETIDO*/) || 'Ã' ||
           TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
           TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(VTA_F_GET_PREC_CAMP(PROD_LOCAL.Cod_Grupo_Cia,PROD_LOCAL.Cod_Local,PROD_LOCAL.Cod_Prod)),'999,990.000') || 'Ã' || --ASOSA, 10.05.2010. Precio campaña q se desee
           NVL(PROD.IND_ZAN,' ') || 'Ã' ||
           PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
			     PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
           TO_CHAR(PROD_LOCAL.VAL_PREC_LISTA,'999,990.000') || 'Ã' ||
           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
			     PROD.IND_PROD_FARMA || 'Ã' ||
           DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
           NVL(PR_VRT.TIP_PROD_VIRTUAL,' ')|| 'Ã' ||
           PROD.IND_PROD_REFRIG          || 'Ã' ||
           PROD.IND_TIPO_PROD          || 'Ã' ||
           DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
           PROD.DESC_PROD || DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
           NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--JCORTEZ ind encarte
           IND_ORIGEN_PROD --ERIOS 11/04/2008 Origen
           || 'Ã'
		FROM   LGT_PROD PROD,
			     LGT_PROD_LOCAL PROD_LOCAL,
			     LGT_LAB LAB,
			     PBL_IGV IGV,
           LGT_PROD_VIRTUAL PR_VRT,
           --21/11/2007 DUBILLUZ MODIFICADO
           (SELECT DISTINCT(V1.COD_PROD) COD
            FROM  (SELECT COD_PAQUETE,COD_PROD
                   FROM   VTA_PROD_PAQUETE
                   WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                   ) V1,
                   VTA_PROMOCION    P

                   /*  WHERE (SELECT SYSDATE FROM DUAL)
                     BETWEEN
                          TO_DATE( TO_CHAR(P.FEC_PROMOCION_INICIO,'dd/MM/YYYY') || ' 00:00:00','dd/MM/yyyy HH24:mi:ss')
                     AND TO_DATE( TO_CHAR(P.FEC_PROMOCION_FIN,'dd/MM/YYYY') || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')

                    */
                   WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
            AND  P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
            AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    P.ESTADO  = 'A'
            AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                    OR
                     P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
           ) Z,
           (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                  PROD_ENCARTE.COD_ENCARTE,
                  PROD_ENCARTE.COD_PROD
           FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                  VTA_ENCARTE ENCARTE
           WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
           AND    ENCARTE.ESTADO = 'A'
           AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
           AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
           )   V2
		WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
		AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
		AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
		AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
		AND	   PROD.COD_LAB = LAB.COD_LAB
		AND	   PROD.COD_IGV = IGV.COD_IGV
		AND	   PROD.EST_PROD = 'A'
    AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
    AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
    AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
    AND    PROD.COD_PROD = z.cod (+)
    AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
    AND   PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
    ;

    RETURN curVta;
  END;

/*******************************************************************************************************************/

FUNCTION VTA_F_GET_PREC_CAMP(CCODCIA_IN   IN CHAR,
                             CCODLOCAL_IN IN CHAR,
                             CCODPROD_IN  IN CHAR) RETURN NUMBER IS
  PREC_CAMP NUMBER(8, 3);
  PORC_DESC NUMBER(8, 3);
BEGIN
  SELECT A.VAL_PREC_VTA
    INTO PREC_CAMP
    FROM LGT_PROD_LOCAL A
   WHERE A.COD_GRUPO_CIA = CCODCIA_IN
     AND A.COD_LOCAL = CCODLOCAL_IN
     AND A.COD_PROD = CCODPROD_IN;

  SELECT B.VALOR_CUPON
    INTO PORC_DESC
    FROM VTA_CAMPANA_CUPON B
   WHERE B.COD_GRUPO_CIA = CCODCIA_IN
     AND B.COD_CAMP_CUPON =
         (SELECT C.LLAVE_TAB_GRAL
            FROM PBL_TAB_GRAL C
           WHERE C.ID_TAB_GRAL = '359');

  RETURN PREC_CAMP *(100 - PORC_DESC) / 100;
END;

/*******************************************************************************************************************/

    FUNCTION VTA_F_IND_LIST_PREC_CAMP(cCodCia_in IN CHAR DEFAULT '001')
    RETURN CHAR
    IS
    ind CHAR(1):='';
    BEGIN
      SELECT nvl(a.llave_tab_gral,'N') INTO ind
      FROM pbl_tab_gral a
      WHERE
      a.id_tab_gral='360';
      RETURN ind;
    EXCEPTION WHEN OTHERS THEN
              ind:='N';
              RETURN ind;
    END;

/*******************************************************************************************************************/

 FUNCTION VTA_LISTA_PROD_SUSTITUTOS(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR)

 RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
  	   OPEN curVta FOR --POR DEFECTO
SELECT
      CASE WHEN PROD_LOCAL.IND_PROD_FRACCIONADO = 'N'  THEN

                 PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
          		   DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,PROD_LOCAL.UNID_VTA) || 'Ã' ||
          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO /*- PROD_LOCAL.STK_COMPROMETIDO*/) || 'Ã' ||
          		   TO_CHAR(  ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
                 TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(VTA_F_GET_PREC_CAMP(PROD_LOCAL.Cod_Grupo_Cia,PROD_LOCAL.Cod_Local,PROD_LOCAL.Cod_Prod)),'999,990.000') || 'Ã' || --ASOSA, 10.05.2010. Precio campaña q se desee
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen

         WHEN PROD.VAL_FRAC_VTA_SUG IS NOT NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S'
              AND PROD.VAL_FRAC_VTA_SUG < PROD_LOCAL.VAL_FRAC_LOCAL     THEN

                 PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',' ',TRIM(PROD.desc_unid_vta_sug)) || 'Ã' ||

          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO /*- PROD_LOCAL.STK_COMPROMETIDO*/) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
                 TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(VTA_F_GET_PREC_CAMP(PROD_LOCAL.Cod_Grupo_Cia,PROD_LOCAL.Cod_Local,PROD_LOCAL.Cod_Prod)),'999,990.000') || 'Ã' || --ASOSA, 10.05.2010. Precio campaña q se desee
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen


             WHEN PROD.VAL_FRAC_VTA_SUG IS NOT NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S'
             AND PROD.VAL_FRAC_VTA_SUG =  PROD_LOCAL.VAL_FRAC_LOCAL  THEN

                 PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||
                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',' ',TRIM(PROD_LOCAL.UNID_VTA)) || 'Ã' ||

          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO/* - PROD_LOCAL.STK_COMPROMETIDO*/) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
                 TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(VTA_F_GET_PREC_CAMP(PROD_LOCAL.Cod_Grupo_Cia,PROD_LOCAL.Cod_Local,PROD_LOCAL.Cod_Prod)),'999,990.000') || 'Ã' || --ASOSA, 10.05.2010. Precio campaña q se desee
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' ||--JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen

            WHEN PROD.VAL_FRAC_VTA_SUG IS NULL AND PROD_LOCAL.IND_PROD_FRACCIONADO = 'S' THEN

                  PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||

                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,TRIM(PROD_LOCAL.UNID_VTA)) || 'Ã' ||


          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO /*- PROD_LOCAL.STK_COMPROMETIDO*/) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
                 TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(VTA_F_GET_PREC_CAMP(PROD_LOCAL.Cod_Grupo_Cia,PROD_LOCAL.Cod_Local,PROD_LOCAL.Cod_Prod)),'999,990.000') || 'Ã' || --ASOSA, 10.05.2010. Precio campaña q se desee
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen

         ELSE
                  PROD.COD_PROD || 'Ã' ||
          		   PROD.DESC_PROD || 'Ã' ||

                 DECODE(PROD_LOCAL.IND_PROD_FRACCIONADO,'N',PROD.DESC_UNID_PRESENT,TRIM(PROD_LOCAL.UNID_VTA)) || 'Ã' ||

          		   LAB.NOM_LAB || 'Ã' ||
          		   (PROD_LOCAL.STK_FISICO/* - PROD_LOCAL.STK_COMPROMETIDO*/) || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_VTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
                 TO_CHAR(ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(VTA_F_GET_PREC_CAMP(PROD_LOCAL.Cod_Grupo_Cia,PROD_LOCAL.Cod_Local,PROD_LOCAL.Cod_Prod)),'999,990.000') || 'Ã' || --ASOSA, 10.05.2010. Precio campaña q se desee
          		   NVL(PROD.IND_ZAN,' ') || 'Ã' ||
                 PROD_LOCAL.IND_PROD_CONG || 'Ã' ||
          		   PROD_LOCAL.VAL_FRAC_LOCAL || 'Ã' ||
          		   TO_CHAR( ptoventa_vta.VTA_F_CHAR_PREC_REDONDEADO(PROD_LOCAL.VAL_PREC_LISTA),'999,990.000') || 'Ã' || --JCHAVEZ 29102009  precio redondeado
			           TO_CHAR(IGV.PORC_IGV,'990.00') || 'Ã' ||
          	   	 PROD.IND_PROD_FARMA || 'Ã' ||
                 DECODE(NVL(PR_VRT.COD_PROD,'N'),'N','N','S') || 'Ã' ||
                 NVL(PR_VRT.TIP_PROD_VIRTUAL,' ') || 'Ã' ||
                 PROD.IND_PROD_REFRIG          || 'Ã' ||
                 PROD.IND_TIPO_PROD            || 'Ã' ||
                 DECODE(NVL(Z.COD,'N'),'N','N','S') || 'Ã' ||
                 ' ' || 'Ã' ||--NVL(to_char(prod.val_bono_vig,'990.00')||TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ')
                 NVL(trim(V2.COD_ENCARTE),' ')|| 'Ã' ||--ind encarte
                 IND_ORIGEN_SUST --ERIOS 11/04/2008 Origen

                 END



          FROM   LGT_PROD PROD,
          		   LGT_PROD_LOCAL PROD_LOCAL,
          		   LGT_LAB LAB,
          		   PBL_IGV IGV,
                 LGT_PROD_VIRTUAL PR_VRT,
                 --21/11/2007 DUBILLUZ MODIFICADO
                 (SELECT DISTINCT(V1.COD_PROD) COD
                  FROM  (SELECT COD_PAQUETE,COD_PROD
                         FROM   VTA_PROD_PAQUETE
                         WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
                         ) V1,
                         VTA_PROMOCION    P

                  WHERE   P.FEC_PROMOCION_INICIO<=TRUNC(SYSDATE) AND P.FEC_PROMOCION_FIN>=TRUNC(SYSDATE)
                  AND    P.IND_DELIVERY='N' --JCORTEZ 16.10.09 solo para locales
                  AND  P.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND    P.ESTADO  = 'A'
                  AND    ( P.COD_PAQUETE_1 = (V1.COD_PAQUETE)
                          OR
                           P.COD_PAQUETE_2 = (V1.COD_PAQUETE))
                ) Z,
                (SELECT PROD_ENCARTE.COD_GRUPO_CIA,
                        PROD_ENCARTE.COD_ENCARTE,
                        PROD_ENCARTE.COD_PROD
                 FROM   VTA_PROD_ENCARTE PROD_ENCARTE,
                        VTA_ENCARTE ENCARTE
                 WHERE  TRUNC(SYSDATE) BETWEEN ENCARTE.FECH_INICIO AND ENCARTE.FECH_FIN
                 AND    ENCARTE.ESTADO = 'A'
                 AND    PROD_ENCARTE.COD_GRUPO_CIA = ENCARTE.COD_GRUPO_CIA
                 AND    PROD_ENCARTE.COD_ENCARTE = ENCARTE.COD_ENCARTE
                 )   V2
          WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND	   PROD_LOCAL.COD_LOCAL = cCodLocal_in
          AND    (PROD_LOCAL.STK_FISICO/* - PROD_LOCAL.STK_COMPROMETIDO*/) > 0

          AND	   PROD_LOCAL.COD_PROD IN ((
                                        -- 1. obtener los productos con mismos principios activos
                                         select cod_prod
                                         from   lgt_prod pp
                                         where  COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND    cod_prod in (select cod_prod
                                                             from   lgt_princ_act_prod
                                                             where  COD_GRUPO_CIA = cCodGrupoCia_in

                                                             AND    (cod_prod > cCodProd_in or cod_prod < cCodProd_in)
                                                             and    cod_princ_act in (select cod_princ_act
                                                                                      from   lgt_princ_act_prod
                                                                                      where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                                      AND    cod_prod      = cCodProd_in)
                                                             group by cod_prod
                                                             having count(*) = (select count(*)
                                                                                from   lgt_princ_act_prod
                                                                                where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                                AND    cod_prod      =cCodProd_in))
                                         and cod_prod IN (select cod_prod
                                                          from   lgt_princ_act_prod
                                                          where  COD_GRUPO_CIA =cCodGrupoCia_in
--
                                                          AND    (cod_prod > cCodProd_in or cod_prod < cCodProd_in)
                                                          group by cod_prod
                                                          having count(*) = (select count(*)
                                                                             from   lgt_princ_act_prod
                                                                             where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                             AND    cod_prod = cCodProd_in)))
                                             UNION

                                             (
                                             select cod_prod
                                             from   lgt_prod PP
                                             where  COD_GRUPO_CIA =cCodGrupoCia_in
                                             AND    EXISTS       (SELECT 1
                                                                  FROM  LGT_CAT_SUST_CAB C,
                                                                        LGT_CAT_SUST_DET D
                                                                  WHERE C.COD_CAT_SUST = D.COD_CAT_SUST
                                                                  AND   D.cod_ind_cat  = PP.cod_prod
--
                                                                  AND  (D.cod_ind_cat > cCodProd_in or D.cod_ind_cat < cCodProd_in )
                                                                  AND   C.IND_CAT = 'PROD'
                                                                  AND   C.EST_CAT = 'A'
                                                                  and   EXISTS            (SELECT 1
                                                                                           FROM   LGT_CAT_SUST_CAB C1,
                                                                                                  LGT_CAT_SUST_DET D
                                                                                           WHERE  C1.COD_CAT_SUST = D.COD_CAT_SUST
                                                                                           AND    C1.COD_CAT_SUST = C.COD_CAT_SUST
                                                                                           AND    C1.IND_CAT      = 'PROD'
                                                                                           AND    C1.EST_CAT      = 'A'
                                                                                           AND    D.COD_IND_CAT   = cCodProd_in))
                                            )


                                               UNION
                                                (select cod_prod
                                                 from   lgt_prod pp
                                                 where  COD_GRUPO_CIA = cCodGrupoCia_in

                                                 and  exists (select  1
                                                                FROM  lgt_prod_grupo_similar p,lgt_grupo_similar g
                                                                where p.cod_grupo     = g.cod_grupo
                                                                and   p.cod_grupo_cia = cCodGrupoCia_in

                                                                and   (p.cod_prod     > cCodProd_in or p.cod_prod     < cCodProd_in)
                                                                and   p.est_prod_grupo_similar='A'
                                                                and   g.est_grupo_similar='A'
                                                                and   p.cod_prod=pp.cod_prod
                                                                and   p.cod_grupo IN (SELECT cod_grupo
                                                                                   FROM lgt_prod_grupo_similar
                                                                                   WHERE cod_prod = cCodProd_in)))

                           )


          AND	   PROD.COD_GRUPO_CIA = PROD_LOCAL.COD_GRUPO_CIA
          AND	   PROD.COD_PROD = PROD_LOCAL.COD_PROD
          AND	   PROD.COD_LAB = LAB.COD_LAB
          AND	   PROD.COD_IGV = IGV.COD_IGV
          AND	   PROD.EST_PROD = 'A'
          AND    PR_VRT.EST_PROD_VIRTUAL(+) = 'A'
          AND    PROD.COD_GRUPO_CIA = PR_VRT.COD_GRUPO_CIA(+)
          AND    PROD.COD_PROD = PR_VRT.COD_PROD(+)
          AND    PROD.COD_PROD = Z.COD (+)
          AND    PROD.COD_GRUPO_CIA=V2.COD_GRUPO_CIA(+) --JCORTEZ
          AND    PROD.COD_PROD=V2.COD_PROD(+)--JCORTEZ
          AND   exists( SELECT 1
                                      FROM LGT_PROD     PADRE,
                                           LGT_REL_UNID UPADRE,
                                           LGT_REL_UNID UHIJO,
                                           LGT_PROD     HIJO
                                      WHERE HIJO.EST_PROD ='A'
                                            AND PADRE.COD_PROD = cCodProd_in
                                            and HIJO.cod_prod=PROD.COD_PROD
                                            and HIJO.COD_GRUPO_CIA=cCodGrupoCia_in
                                            and PADRE.COD_GRUPO_CIA=cCodGrupoCia_in
                                            AND UPADRE.COD_UNID_MEDIDA = PADRE.COD_UNID_MIN_FRAC
                                            AND UHIJO.COD_REL = UPADRE.COD_REL
                                            AND HIJO.COD_UNID_MIN_FRAC = UHIJO.COD_UNID_MEDIDA
                                    ) --JCORTEZ 18.04.08 productos con misma unidad de medida
          ORDER BY NVL(PROD.IND_ZAN || TO_CHAR((((PROD_LOCAL.VAL_PREC_VTA*PROD_LOCAL.VAL_FRAC_LOCAL)/(1+IGV.PORC_IGV/100))-PROD.VAL_PREC_PROM)/PROD.VAL_MAX_FRAC,'999990.000'),' ') DESC
          ;

  	RETURN curVta;
  END;

/************************************************************************************************************/

FUNCTION VTA_F_GET_COD_DESC_CAMP(cCodCia_in IN CHAR DEFAULT '001')
RETURN VARCHAR2
IS
cadena VARCHAR2(200):='';
BEGIN
     SELECT nvl(TRIM(a.llave_tab_gral),'nada')||'ººº'||nvl(TRIM(a.desc_corta),'CLUB') INTO cadena
     FROM pbl_tab_gral a
     WHERE
     a.id_tab_gral='359';
     RETURN cadena;
END;

/************************************************************************************************************/
  FUNCTION VTA_F_GET_PRINC_ACT(cCodGrupoCia_in in char default '001',
                               cCodProd_in IN CHAR)
    RETURN FarmaCursor
    IS
    cur FarmaCursor;
    nCantProd NUMBER;
    BEGIN

     --JMIRANDA 04.10.2010
     --CONSULTAR SI EL PRODUCTO ES FARMA
      SELECT COUNT(1) INTO nCantProd
        FROM LGT_PROD P
       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND P.COD_PROD = cCodProd_in
         AND P.IND_PROD_FARMA = 'S';

    IF nCantProd > 0 THEN
        OPEN CUR FOR
        SELECT NVL(B.DESC_PRINC_ACT,' ') || 'Ã' ||
               B.COD_PRINC_ACT
        FROM LGT_PRINC_ACT_PROD A,
             LGT_PRINC_ACT B
        WHERE --A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
        --AND
        A.COD_PRINC_ACT=B.COD_PRINC_ACT
        AND A.COD_PROD=cCodProd_in
        AND A.COD_GRUPO_CIA=cCodGrupoCia_in;

    ELSE
    --   RAISE_APPLICATION_ERROR(-20090,'PRODUCTO NO FARMA');

       OPEN CUR FOR
        SELECT ' ' || 'Ã' ||
               ' '
        FROM DUAL;
    END IF;

     RETURN CUR;
    END;

/********************************************************************************************************/
 FUNCTION VTA_LISTA_PROD_DCI(cCodGrupoCia_in IN CHAR,
								 	   cCodLocal_in	   IN CHAR,
									   cCodProd_in     IN CHAR)

 RETURN FarmaCursor
  IS
    curVta FarmaCursor;

     codDIAD char(5);
     codCINC char(5);
     porcDIAD number(8,2);
     maxDIAD number(8,2);
     porcCINC number(8,2);
     maxCINC number(8,2);
     cIndCosPromedioDia char(1);
     cIndCosPromedioGold char(1);
     nCantProd NUMBER;
  BEGIN

     SELECT W.LLAVE_TAB_GRAL INTO codDIAD FROM PBL_TAB_GRAL W
     WHERE W.ID_TAB_GRAL='334';
     SELECT Y.LLAVE_TAB_GRAL INTO codCINC FROM PBL_TAB_GRAL Y
     WHERE Y.ID_TAB_GRAL='335';

     SELECT P.VALOR_CUPON, P.MONTO_MAX_DESCT,p.ind_val_costo_prom
     INTO porcDIAD, maxDIAD,cIndCosPromedioDia
     FROM VTA_CAMPANA_CUPON P
     WHERE P.COD_GRUPO_CIA=cCodGrupoCia_in
     AND P.COD_CAMP_CUPON=codDIAD;

     SELECT K.VALOR_CUPON, K.MONTO_MAX_DESCT,k.ind_val_costo_prom
     INTO porcCINC, maxCINC,cIndCosPromedioGold
     FROM VTA_CAMPANA_CUPON K
     WHERE K.COD_GRUPO_CIA=cCodGrupoCia_in
     AND K.COD_CAMP_CUPON=codCINC;

     --JMIRANDA 04.10.2010
     --CONSULTAR SI EL PRODUCTO ES FARMA
      SELECT COUNT(1) INTO nCantProd
        FROM LGT_PROD P
       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND P.COD_PROD = cCodProd_in
         AND P.IND_PROD_FARMA = 'S';

  IF nCantProd > 0 THEN
  	   OPEN curVta FOR
         SELECT A.DESC_PROD || 'Ã' ||
                A.DESC_UNID_PRESENT || 'Ã' ||
                C.NOM_LAB || 'Ã' ||
                --TO_CHAR(A.VAL_PREC_PROV_VIG,'999,999.000') || 'Ã' || --VVF PRECIO DEL PROVEEDOR VIGENTE
                TO_CHAR(VTA_REDONDEO_MAS(B.VAL_PREC_VTA*B.Val_Frac_Local),'999,999.00') || 'Ã' || --PRECIO ENTERO LOCAL
                TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcDIAD,maxDIAD,a.cod_prod,codDIAD,cIndCosPromedioDia,VAL_PREC_PROM,decode(TRIM(a.cod_igv),'01',19,0)),'999,999.00') || 'Ã' ||--JSANTIVANEZ 02.09.2010
                TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcCINC,maxCINC,a.cod_prod,codCINC,cIndCosPromedioGold,VAL_PREC_PROM,decode(TRIM(a.cod_igv),'01',19,0)),'999,999.00') || 'Ã' ||--JSANTIVANEZ 02.09.2010
                B.COD_PROD|| 'Ã' ||
                VTA_F_GET_PRINC_ACT_PROD(cCodGrupoCia_in,cCodProd_in)
         FROM LGT_PROD A, LGT_PROD_LOCAL B, LGT_LAB C
         WHERE A.COD_PROD=B.COD_PROD
         AND A.COD_LAB=C.COD_LAB
         AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
         AND B.COD_GRUPO_CIA=cCodGrupoCia_in
         --JQUISPE 14.08.2010 CAMBIO COD GRUPO CIA
         --AND B.COD_GRUPO_CIA=C.COD_GRUPO_CIA
         AND B.COD_LOCAL=cCodLocal_in
         AND A.EST_PROD='A'
         AND B.STK_FISICO/*-B.STK_COMPROMETIDO*/>0
         --JMIRANDA 02.10.2010
         AND B.COD_PROD IN
         --query para obtener principios activos
         ((
                                        -- 1. obtener los productos con mismos principios activos
                                         select cod_prod
                                         from   lgt_prod pp
                                         where  COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND    cod_prod in (select cod_prod
                                                             from   lgt_princ_act_prod
                                                             where  COD_GRUPO_CIA = cCodGrupoCia_in

                                                             AND    (cod_prod > cCodProd_in or cod_prod < cCodProd_in)
                                                             and    cod_princ_act in (select cod_princ_act
                                                                                      from   lgt_princ_act_prod
                                                                                      where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                                      AND    cod_prod      = cCodProd_in)
                                                             group by cod_prod
                                                             having count(*) = (select count(*)
                                                                                from   lgt_princ_act_prod
                                                                                where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                                AND    cod_prod      =cCodProd_in))
                                         and cod_prod IN (select cod_prod
                                                          from   lgt_princ_act_prod
                                                          where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                          AND    (cod_prod > cCodProd_in or cod_prod < cCodProd_in)
                                                          group by cod_prod
                                                          having count(*) = (select count(*)
                                                                             from   lgt_princ_act_prod
                                                                             where  COD_GRUPO_CIA =cCodGrupoCia_in
                                                                             AND    cod_prod = cCodProd_in)))
                                             UNION
                                             --asolis
                                              -- 2. obtener los productos que pertenecen al mismo grupo lgt_cab_sust_cab y lgt_cab_sust_det
                                             (
                                             select cod_prod
                                             from   lgt_prod PP
                                             where  COD_GRUPO_CIA =cCodGrupoCia_in
                                             AND    EXISTS       (SELECT 1
                                                                  FROM  LGT_CAT_SUST_CAB C,
                                                                        LGT_CAT_SUST_DET D
                                                                  WHERE C.COD_CAT_SUST = D.COD_CAT_SUST
                                                                  AND   D.cod_ind_cat  = PP.cod_prod
                                                                  AND  (D.cod_ind_cat > cCodProd_in or D.cod_ind_cat < cCodProd_in )
                                                                  AND   C.IND_CAT = 'PROD'
                                                                  AND   C.EST_CAT = 'A'
                                                                  and   EXISTS            (SELECT 1
                                                                                           FROM   LGT_CAT_SUST_CAB C1,
                                                                                                  LGT_CAT_SUST_DET D
                                                                                           WHERE  C1.COD_CAT_SUST = D.COD_CAT_SUST
                                                                                           AND    C1.COD_CAT_SUST = C.COD_CAT_SUST
                                                                                           AND    C1.IND_CAT      = 'PROD'
                                                                                           AND    C1.EST_CAT      = 'A'
                                                                                           AND    D.COD_IND_CAT   = cCodProd_in))
                                            )

                                           --asolis
                                           -- 3. obtener los productos que pertenecen al mismo grupo lgt_grupo_similar y lgt_prod_grupo_similar

                                               UNION
                                                (select cod_prod
                                                 from   lgt_prod pp
                                                 where  COD_GRUPO_CIA = cCodGrupoCia_in

                                                 and  exists (select  1
                                                                FROM  lgt_prod_grupo_similar p,lgt_grupo_similar g
                                                                where p.cod_grupo     = g.cod_grupo
                                                                and   p.cod_grupo_cia = cCodGrupoCia_in

                                                                and   (p.cod_prod     > cCodProd_in or p.cod_prod     < cCodProd_in)
                                                                and   p.est_prod_grupo_similar='A'
                                                                and   g.est_grupo_similar='A'
                                                                and   p.cod_prod=pp.cod_prod
                                                                and   p.cod_grupo IN (SELECT cod_grupo
                                                                                   FROM lgt_prod_grupo_similar
                                                                                   WHERE cod_prod = cCodProd_in)))

                           )
         ORDER BY DESC_PROD;

    ELSE
    RAISE_APPLICATION_ERROR(-20090,'El producto seleccionado es no Farmacéutico');
    /* OPEN curVta FOR
        SELECT ' ' || 'Ã' ||
               ' ' || 'Ã' ||
               ' ' || 'Ã' ||
               ' ' || 'Ã' || --PRECIO ENTERO LOCAL
               ' ' || 'Ã' ||--JSANTIVANEZ 02.09.2010
               ' ' || 'Ã' ||--JSANTIVANEZ 02.09.2010
               ' '
        FROM DUAL;       */
    END IF;

  	RETURN curVta;
  END;

/********************************************************************************************************/
  FUNCTION VTA_LISTA_PROD_02(cCodCia_in IN CHAR,
                             cCodLocal_in IN CHAR,
                             vCadena_in IN VARCHAR2)
  RETURN FarmaCursor
  IS
     LISTA FarmaCursor;
     codDIAD char(5);
     codCINC char(5);
     porcDIAD number(8,2);
     maxDIAD number(8,2);
     porcCINC number(8,2);
     maxCINC number(8,2);
     cIndCosPromedioDia char(1);
     cIndCosPromedioGold char(1);
  BEGIN
     SELECT W.LLAVE_TAB_GRAL INTO codDIAD FROM PBL_TAB_GRAL W
     WHERE W.ID_TAB_GRAL='334';
     SELECT Y.LLAVE_TAB_GRAL INTO codCINC FROM PBL_TAB_GRAL Y
     WHERE Y.ID_TAB_GRAL='335';

     SELECT P.VALOR_CUPON, P.MONTO_MAX_DESCT,p.ind_val_costo_prom
     INTO porcDIAD, maxDIAD,cIndCosPromedioDia
     FROM VTA_CAMPANA_CUPON P
     WHERE P.COD_GRUPO_CIA=cCodCia_in
     AND P.COD_CAMP_CUPON=codDIAD;

     SELECT K.VALOR_CUPON, K.MONTO_MAX_DESCT,k.ind_val_costo_prom
     INTO porcCINC, maxCINC,cIndCosPromedioGold
     FROM VTA_CAMPANA_CUPON K
     WHERE K.COD_GRUPO_CIA=cCodCia_in
     AND K.COD_CAMP_CUPON=codCINC;

     OPEN LISTA FOR
     SELECT --B.COD_PROD || 'Ã' ||
            A.DESC_PROD || 'Ã' ||
            A.DESC_UNID_PRESENT || 'Ã' ||
            C.NOM_LAB || 'Ã' ||
            --TO_CHAR(A.VAL_PREC_PROV_VIG,'999,999.000') || 'Ã' || --VVF PRECIO DEL PROVEEDOR VIGENTE
            TO_CHAR(VTA_REDONDEO_MAS(B.VAL_PREC_VTA*B.Val_Frac_Local),'999,999.00') || 'Ã' || --PRECIO ENTERO LOCAL
            TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcDIAD,maxDIAD,a.cod_prod,codDIAD,cIndCosPromedioDia,VAL_PREC_PROM,decode(TRIM(a.cod_igv),'01',19,0)),'999,999.00') || 'Ã' ||
            TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcCINC,maxCINC,a.cod_prod,codCINC,cIndCosPromedioGold,VAL_PREC_PROM,decode(TRIM(a.cod_igv),'01',19,0)),'999,999.00')
     FROM LGT_PROD A, LGT_PROD_LOCAL B, LGT_LAB C
     WHERE A.COD_PROD=B.COD_PROD
     AND A.COD_LAB=C.COD_LAB
     AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
     AND B.COD_GRUPO_CIA=cCodCia_in
     AND B.COD_LOCAL=cCodLocal_in
     AND A.EST_PROD='A'
     AND B.STK_FISICO/*-B.STK_COMPROMETIDO*/>0
     AND A.DESC_PROD LIKE '%'||vCadena_in||'%'
     --ORDER BY INSTR(A.DESC_PROD,vCadena_in,1),A.DESC_PROD;
     ORDER BY SIGN(INSTR(A.DESC_PROD,vCadena_in,1)-1),DESC_PROD;
     /*
     UNION ALL
     SELECT --B.COD_PROD || 'Ã' ||
            A.DESC_PROD || 'Ã' ||
            A.DESC_UNID_PRESENT || 'Ã' ||
            C.NOM_LAB || 'Ã' ||
            --TO_CHAR(A.VAL_PREC_PROV_VIG,'999,999.000') || 'Ã' || --VVF PRECIO DEL PROVEEDOR VIGENTE
            TO_CHAR(B.VAL_PREC_VTA*B.Val_Frac_Local,'999,999.000') || 'Ã' || --PRECIO ENTERO LOCAL
            TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcDIAD,maxDIAD),'999,999.000') || 'Ã' ||
            TO_CHAR(VTA_CALCULAR_DSCTO(B.VAL_PREC_VTA*B.Val_Frac_Local,porcCINC,maxCINC),'999,999.000')
     FROM LGT_PROD A, LGT_PROD_LOCAL B, LGT_LAB C
     WHERE A.COD_PROD=B.COD_PROD
     AND A.COD_LAB=C.COD_LAB
     AND A.COD_GRUPO_CIA=B.COD_GRUPO_CIA
     AND B.COD_GRUPO_CIA=cCodCia_in
     AND B.COD_LOCAL=cCodLocal_in
     AND A.EST_PROD='A'
     AND B.STK_FISICO-B.STK_COMPROMETIDO>0
     AND A.DESC_PROD LIKE '%'||vCadena_in||'%'
     AND A.DESC_PROD NOT LIKE vCadena_in||'%';
     */
     RETURN LISTA;
  END;

/********************************************************************************************************/

FUNCTION VTA_F_IND_PROD_FARMA(cCodGrupoCia_in IN CHAR,
                              cCodProd_in IN CHAR)
                              RETURN CHAR
IS
  vInd CHAR := 'N';
  nCantProd NUMBER := 0;
BEGIN
     --JMIRANDA 04.10.2010
     --CONSULTAR SI EL PRODUCTO ES FARMA
      SELECT COUNT(1) INTO nCantProd
        FROM LGT_PROD P
       WHERE P.COD_GRUPO_CIA = cCodGrupoCia_in
         AND P.COD_PROD = cCodProd_in
         AND P.IND_PROD_FARMA = 'S';

      IF (nCantProd > 0 ) THEN
         vInd := 'S';
      ELSE
         RAISE_APPLICATION_ERROR(-20090,'El producto seleccionado es no Farmacéutico');
      END IF;

RETURN vInd;
END;
/********************************************************************************************************/
FUNCTION VTA_F_GET_PRINC_ACT_PROD(cCodGrupoCia_in IN CHAR,
                              cCodProd_in IN CHAR)
                              RETURN VARCHAR2
IS
V_TEXT VARCHAR2(1000) := '';
vCantCar NUMBER := 0;
--cursor con todos los principios activos del producto
CURSOR CUR_PRIN IS
       SELECT A.DESC_PRINC_ACT
         FROM LGT_PRINC_ACT A, LGT_PRINC_ACT_PROD AP
        WHERE A.COD_PRINC_ACT = AP.COD_PRINC_ACT
          AND A.EST_PRINC_ACT_PROD = 'A'
          AND AP.COD_PROD = cCodProd_in;

BEGIN
    FOR CUR IN CUR_PRIN
    LOOP
    V_TEXT := V_TEXT||CUR.DESC_PRINC_ACT||', ';

    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('V_TEXT: '||V_TEXT);
    --QUITAR LA ÚLTIMA COMA
    vCantCar := LENGTH(TRIM(V_TEXT));
    --DBMS_OUTPUT.PUT_LINE(vCantCar);

    V_TEXT := SUBSTR(TRIM(V_TEXT),0,vCantCar-1);
    --DBMS_OUTPUT.PUT_LINE('V_TEXT: '||V_TEXT);
  RETURN V_TEXT;
END;

end;

/
