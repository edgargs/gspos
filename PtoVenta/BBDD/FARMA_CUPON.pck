CREATE OR REPLACE PACKAGE "FARMA_CUPON" is

TYPE FarmaCursor IS REF CURSOR;
	ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
  C_ESTADO_ACTIVO CHAR(1) := 'A';
  C_ESTADO_EMITIDO CHAR(1) := 'E';
  
  C_TIPO_CLIENTE_TODO             CHAR(1) := 'T';
  C_TIPO_CLIENTE_FIDELIZADO       CHAR(1) := 'F';
  C_TIPO_CLIENTE_NO_FIDELIZADO    CHAR(1) := 'N';
  
PROCEDURE NVO_ALGORITMO_CUPON(cCodGrupoCia_in   IN CHAR,
                              cCodLocal_in    	IN CHAR,
										          cNumPedVta_in   	IN CHAR);

PROCEDURE VTA_GRABAR_PED_CUPON(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in       IN CHAR,
                                 cNumPedVta_in      IN CHAR,
                                 cCodCupon_in       IN CHAR,
                                 cCantidad_in       IN NUMBER,
                                 cLoginUsu_in       IN CHAR);
                                 
FUNCTION VTA_F_GET_IND_FID_EMI(cCodGrupoCia_in CHAR,
                                 cIndFidelizado_in CHAR,
                                 cCod_camp_cupon_in CHAR,
                                 cFechaNac_in DATE DEFAULT NULL,
                                 cSexo_in CHAR DEFAULT NULL)
  RETURN CHAR;


FUNCTION F_PERMITE_CAMAPANA(cCodGrupoCia_in  IN CHAR,
                             cCodLocal_in       IN CHAR,
                             cNumPedVta_in      IN CHAR,
                             cCodCupon_in       IN CHAR,
                             cTipo              IN CHAR,
                             nMontConsumoCamp_in IN CHAR) RETURN CHAR;
                             
PROCEDURE P_GEN_CUPONES(pCodGrupoCia_in     IN CHAR,
                        cCodLocal_in        IN CHAR,
                        cNumPedVta_in       IN CHAR,
                        cIdUsu_in           IN CHAR,
                        cDni_in             IN CHAR);                                   

FUNCTION GET_VERIFICA_PED_CAMP(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in     IN CHAR,
                                   cNumPedVta_in    IN CHAR)
  RETURN VARCHAR2;
  
  PROCEDURE P_GENERA_CUPON_CONV(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cNumPedVta_in   IN CHAR);
                               
  FUNCTION F_VALIDA_VTA_CENTRO_MEDICO(cCodGrupoCia_in      IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                      cCodLocal_in         IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                      cNumPedVta_in        IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                                      cCodCampCupon_in     IN VTA_CAMPANA_CUPON.COD_CAMP_CUPON%TYPE)
  RETURN CHAR;
  
END FARMA_CUPON;
/
CREATE OR REPLACE PACKAGE BODY "FARMA_CUPON" is

PROCEDURE NVO_ALGORITMO_CUPON(cCodGrupoCia_in 	 	     IN CHAR,
	                            cCodLocal_in    	 	     IN CHAR,
										          cNumPedVta_in   	 	     IN CHAR) IS
   cTipo            varCHAR2(10);
   pDniCli           varCHAR2(20); 
   cLoginUsu_in     varCHAR(1000);
  CURSOR curCampAplicables(numDia char, pDniCli CHAR,cSexo char,dFecNaci date, cCodConvenio CHAR) IS
    SELECT V.COD_CAMP_CUPON,ROWNUM SEC_ORDEN,PRIORIDAD
    FROM   (
      SELECT  DISTINCT C.COD_CAMP_CUPON,
              PRIORIDAD,
              TIP_CUPON,
              VALOR_CUPON
      FROM   VTA_CAMPANA_CUPON C,
             VTA_CAMPANA_PROD P,
             VTA_PEDIDO_VTA_DET D,
             vta_pedido_vta_cab cab
      WHERE  C.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    C.TIP_CAMPANA   = cTipo
      AND    C.ESTADO = 'A'
      AND    TRUNC(SYSDATE) BETWEEN C.FECH_INICIO AND  C.FECH_FIN
      AND    C.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND    C.COD_CAMP_CUPON = P.COD_CAMP_CUPON
      AND    cab.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    cab.COD_LOCAL = cCodLocal_in
      AND    cab.NUM_PED_VTA = cNumPedVta_in
      --- filtra campana por modalidad de venta
      --  dubilluz 17.04.2015
      --  JULIO INDICO QUE EL VIAJERO NO FALLARA 
      --  vta_campana_tipo_vta
      and   cab.cod_grupo_cia = D.COD_GRUPO_CIA
      and   cab.cod_local = D.COD_LOCAL
      and   cab.num_ped_vta =D.NUM_PED_VTA
      and   exists (
                     select 1
                     from   vta_campana_tipo_vta a
                     where  a.cod_grupo_cia = c.cod_grupo_cia
                     and    a.cod_camp_cupon = c.cod_camp_cupon
                     and    a.tip_ped_vta = cab.tip_ped_vta
                   )
      -- dubilluz 17.04.2015
      --------------------------------------------
      AND    P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
      AND    P.COD_PROD = D.COD_PROD
      AND    VTA_F_GET_IND_FID_EMI(cCodGrupoCia_in,
                                nvl2(trim(pDniCli),'S','N'),
                                  C.COD_CAMP_CUPON,
                                 dFecNaci,
                                 cSexo--,
                                 --c.Ind_Fid_Emi
                                 ) = 'S'
      --fin de filtro de los campos de Sexo y Edad
      AND    C.COD_CAMP_CUPON IN (
                                      SELECT *
                                        FROM   (
                                               SELECT X.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON X
                                               WHERE X.COD_GRUPO_CIA='001'
                                               AND X.TIP_CAMPANA=cTipo
                                               AND X.ESTADO='A'
                                               AND X.IND_CADENA='S'
                                               UNION
                                               SELECT Y.COD_CAMP_CUPON
                                               FROM VTA_CAMPANA_CUPON Y
                                               WHERE Y.COD_GRUPO_CIA='001'
                                               AND Y.TIP_CAMPANA=cTipo
                                               AND Y.ESTADO='A'
                                               AND Y.IND_CADENA='N'
                                               AND Y.COD_CAMP_CUPON IN (SELECT COD_CAMP_CUPON
                                                                        FROM   VTA_CAMP_X_LOCAL Z
                                                                        WHERE  Z.COD_GRUPO_CIA =cCodGrupoCia_in
                                                                        AND    Z.COD_LOCAL = cCodLocal_in
                                                                        AND    Z.ESTADO = 'A')

                                               )
                                            )
                AND  C.COD_CAMP_CUPON IN (
                                          SELECT *
                                          FROM
                                              (
                                                SELECT *
                                                FROM
                                                (
                                                  SELECT COD_CAMP_CUPON
                                                  FROM   VTA_CAMPANA_CUPON
                                                  MINUS
                                                  SELECT H.COD_CAMP_CUPON
                                                  FROM   VTA_CAMP_HORA H
                                                )
                                                UNION
                                                SELECT H.COD_CAMP_CUPON
                                                FROM   VTA_CAMP_HORA H
                                                WHERE  TRIM(TO_CHAR(SYSDATE,'HH24')) BETWEEN H.HORA_INICIO  AND H.HORA_FIN
                                              ))
                AND  DECODE(C.DIA_SEMANA,NULL,'S',
                              DECODE(C.DIA_SEMANA,REGEXP_REPLACE(C.DIA_SEMANA,numDia,'S'),'N','S')
                              ) = 'S'
                -- KMONCADA 09.05.2016 EMISION DE CUPONES PARA CONVENIO
                AND C.COD_CAMP_CUPON IN
                                        (SELECT C.COD_CAMP_CUPON
                                         FROM DUAL
                                         WHERE 0 = (SELECT COUNT(1)
                                                    FROM REL_VTA_CAMPANA_CONVENIO
                                                    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                                    AND COD_CAMP_CUPON = C.COD_CAMP_CUPON)
                                         UNION ALL
                                         SELECT COD_CAMP_CUPON
                                         FROM REL_VTA_CAMPANA_CONVENIO
                                         WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                         AND COD_CONVENIO = cCodConvenio)
            
            ORDER BY PRIORIDAD ASC ,
                     DECODE(C.TIP_CUPON,'P',1,'M',2) DESC,
                     VALOR_CUPON DESC
         )V;

  CURSOR curProdCampana(cCodGrupoCia_in  IN CHAR,
                        cCodLocal_in      IN CHAR,
                        cNumPedVta_in    IN CHAR,
                        cCodCampana_in IN CHAR) IS
    SELECT D.COD_PROD,D.VAL_PREC_TOTAL,D.COD_GRUPO_REP_EDMUNDO
    FROM   VTA_CAMPANA_PROD P,
           VTA_PEDIDO_VTA_DET D
    WHERE  P.COD_GRUPO_CIA = D.COD_GRUPO_CIA
    AND    P.COD_CAMP_CUPON = cCodCampana_in
    AND    P.COD_PROD = D.COD_PROD
    AND    D.COD_GRUPO_CIA  = cCodGrupoCia_in
    AND    D.COD_LOCAL      = cCodLocal_in
    AND    D.NUM_PED_VTA    = cNumPedVta_in
    AND    P.COD_PROD NOT IN (SELECT E.COD_PROD
                              FROM   TT_CAMPANA_PROD_PEDIDO E
                              WHERE  E.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND    E.COD_LOCAL = cCodLocal_in
                              AND    E.NUM_PED_VTA = cNumPedVta_in
                              AND    E.COD_CAMP_CUPON != cCodCampana_in);

  CURSOR curMontoCampanaPedido(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in      IN CHAR,
                               cNumPedVta_in    IN CHAR) is
  select distinct g.*
  from   (                               
  SELECT T.COD_GRUPO_CIA,T.COD_LOCAL,T.NUM_PED_VTA,
         T.COD_CAMP_CUPON,--t.cod_grupo_rep_edmundo,
         t.num_cupon as "CTD_CUPONES",
         /*(
         case 
           when  T.Cod_Grupo_Rep_Edmundo = '002'  then 1 -- leches
           when  T.Cod_Grupo_Rep_Edmundo = '004' or T.Cod_Grupo_Rep_Edmundo = '006' then 2 -- pañales
           when  T.Cod_Grupo_Rep_Edmundo = '001'then 3 -- farma
           else 4
         end 
         )orden,*/
         SUM(T.VAL_PREC_TOTAL) monto
  FROM   TT_PED_CAMP_PROD T
  WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    T.COD_LOCAL     = cCodLocal_in
  AND    T.NUM_PED_VTA   = cNumPedVta_in
  and    t.ind_emite = 'S'
  GROUP  BY T.COD_GRUPO_CIA,T.COD_LOCAL,T.NUM_PED_VTA,
            T.COD_CAMP_CUPON,
            /*t.cod_grupo_rep_edmundo,
            (
         case 
           when  T.Cod_Grupo_Rep_Edmundo = '002'  then 1
           when  T.Cod_Grupo_Rep_Edmundo = '004' or T.Cod_Grupo_Rep_Edmundo = '006' then 2
           when  T.Cod_Grupo_Rep_Edmundo = '001'then 3  
           else 4
         end 
         ),*/
         t.num_cupon
        )g;
  --ORDER BY g.orden ASC;

 CURSOR curVerificaCampPrevia(cCodGrupoCia_in  IN CHAR,
                              cCodLocal_in      IN CHAR,
                              cNumPedVta_in    IN CHAR) is
  SELECT T.COD_GRUPO_CIA,T.COD_LOCAL,T.NUM_PED_VTA,
         T.COD_CAMP_CUPON,SUM(T.VAL_PREC_TOTAL) monto
  FROM   TT_PED_CAMP_PROD T
  WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
  AND    T.COD_LOCAL     = cCodLocal_in
  AND    T.NUM_PED_VTA   = cNumPedVta_in
  GROUP  BY T.COD_GRUPO_CIA,T.COD_LOCAL,T.NUM_PED_VTA,
            T.COD_CAMP_CUPON,T.ORD_CREACION
  ORDER BY T.ORD_CREACION ASC;  
  
  
 CURSOR curGrupoEd(cCodGrupoCia_in  IN CHAR,
                   cCodLocal_in      IN CHAR,
                   cNumPedVta_in    IN CHAR) is
  select distinct vv.orden 
  from   (                 
          SELECT T.Cod_Grupo_Rep_Edmundo,
                 case 
                   when  T.Cod_Grupo_Rep_Edmundo = '002'  then 1
                   when  T.Cod_Grupo_Rep_Edmundo = '004' or T.Cod_Grupo_Rep_Edmundo = '006' then 2
                   when  T.Cod_Grupo_Rep_Edmundo = '001'then 3  
                   else 4
                 end orden
          FROM   TT_PED_CAMP_PROD T
          WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    T.COD_LOCAL     = cCodLocal_in
          AND    T.NUM_PED_VTA   = cNumPedVta_in
          ) vv
          order by vv.orden asc;
    

  nNumDia       VARCHAR(2);
  
  nMaxCampanaPedido   NUMBER;
  nCantCampPedido number;
  /**VARIABLE DE DATOS DE CLIENTE*/
  cSexo    char(1);
  dFecNaci date;
  /**FIN*/
    nListaNegraDNI number;
    
    nMaxPrioridad number;
    nCtdCampPermitidas number;
    nPosSeleccionada number;
    vPermite CHAR(1);
    nVersionD integer;
    
    vIndManual varchar2(10);
    vIndDLVCall varchar2(10);
    
    vCodConvenio MAE_CONVENIO.COD_CONVENIO%TYPE;
    vContinua CHAR(1);
 BEGIN
  
      update vta_pedido_vta_det d
      set    d.cod_grupo_rep_edmundo = (
                                       select p.cod_grupo_Rep_edmundo
                                       from   lgt_prod p
                                       where  p.cod_grupo_cia = cCodGrupoCia_in
                                       and     p.cod_prod = d.cod_prod
                                       )
      where  d.cod_grupo_cia = cCodGrupoCia_in
      and   d.cod_local = cCodLocal_in
      and   d.num_ped_vta = cNumPedVta_in
      and   d.cod_grupo_rep_edmundo is null
      and   exists (
                                       select 1
                                       from   lgt_prod p
                                       where  p.cod_grupo_cia = cCodGrupoCia_in
                                       and     p.cod_prod = d.cod_prod
                                       ); 
 
    select nvl(c.ind_comp_manual,'N'),
           nvl(c.ind_deliv_automatico,'N')
    into   vIndManual,vIndDLVCall
    from   vta_pedido_vta_cab c
    where  c.COD_GRUPO_CIA  = cCodGrupoCia_in
    AND    c.COD_LOCAL      = cCodLocal_in
    AND    c.NUM_PED_VTA    = cNumPedVta_in;

   
   if vIndManual = 'N' and vIndDLVCall = 'N'  then
  -------------------------------------
  -- para corregir error de Oracle 10g 
  -------------------------------------   
  begin
    select count(1)
      Into nVersionD
      from v$version
     where upper(banner) like upper('%Oracle Database 10g %');
    if nVersionD > 0 then
      execute immediate 'alter session set "_optimizer_filter_pred_pullup"=false';
    end if;
  exception
    when others then
      null;
  end;
  -------------------------------------    
  -------------------------------------    

  
 
   select 'C',c.dni_cli,c.usu_crea_ped_vta_cab, TRIM(c.cod_convenio)
   into   cTipo,pDniCli,cLoginUsu_in, vCodConvenio
   from   vta_pedido_vta_cab c
   where  c.cod_grupo_cia = cCodGrupoCia_in
   and    c.cod_local = cCodLocal_in
   and    c.num_ped_vta = cNumPedVta_in;
   
   select count(1)
   into   nListaNegraDNI
   from   fid_dni_nulos f
   where  f.dni_cli  = pDniCli
   and    f.estado = 'A';

   IF nListaNegraDNI = 0 then

   nNumDia := FARMA_UTILITY.OBTIEN_NUM_DIA(SYSDATE);
   /*****************************************************************************/
   --OBTENIENDO EL SEXO Y LA FECHA DE NACIMIENTO DEL CLIENTE
   IF pDniCli is not null then
      SELECT CL.SEXO_CLI, trunc(CL.FEC_NAC_CLI) INTO cSexo, dFecNaci
      FROM PBL_CLIENTE CL
      WHERE CL.DNI_CLI = pDniCli;

      --dbms_output.put_line('datos del cliente : sexo:'||cSexo||', fecha_nac:'||dFecNaci);
   end if;
    -- fin de agregado
    /*****************************************************************************/
   SELECT TO_NUMBER(T.LLAVE_TAB_GRAL,'999999')
   INTO   nMaxCampanaPedido
   FROM   PBL_TAB_GRAL T
   WHERE  T.ID_TAB_GRAL = 211;

   
      -- 1 ANALIZA CADA CAMPANA PERMITIDA AL PEDIDO E INSERTA EN TABLA DE PRODUCTO,CAMPANA    
      FOR vCampanas IN curCampAplicables(nNumDia, pDniCli, cSexo, dFecNaci, vCodConvenio)
      LOOP

         FOR vProdCamp IN curProdCampana(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,
                                         vCampanas.Cod_Camp_Cupon)
         LOOP
           
            IF vCodConvenio IS NOT NULL THEN
              vContinua := 'S';
              /* APLICARA PARA TODA VENTA DE CONVENIO
              vContinua := F_VALIDA_VTA_CENTRO_MEDICO(cCodGrupoCia_in => cCodGrupoCia_in,
                                                      cCodLocal_in => cCodLocal_in,
                                                      cNumPedVta_in => cNumPedVta_in,
                                                      cCodCampCupon_in => vCampanas.Cod_Camp_Cupon);
              */
              /*-- KMONCADA 09.05.2016 EVALUA SI ES PRIMERA COMPRA DEL BENIFICIARIO
              SELECT DECODE(COUNT(1), 0, 'S', 'N')
              INTO vContinua
              FROM VTA_CAMP_PEDIDO_CUPON C
              WHERE (C.COD_GRUPO_CIA, C.COD_LOCAL, C.NUM_PED_VTA) IN (
                      SELECT CAB_ANT.COD_GRUPO_CIA, CAB_ANT.COD_LOCAL, CAB_ANT.NUM_PED_VTA
                      FROM VTA_PEDIDO_VTA_CAB CAB,
                           CON_BTL_MF_PED_VTA DA,
                           CON_BTL_MF_PED_VTA DA_ANT,
                           VTA_PEDIDO_VTA_CAB CAB_ANT
                      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
                      AND CAB.COD_LOCAL = cCodLocal_in
                      AND CAB.NUM_PED_VTA = cNumPedVta_in
                      AND CAB.COD_GRUPO_CIA = DA.COD_GRUPO_CIA
                      AND CAB.COD_LOCAL = DA.COD_LOCAL
                      AND CAB.NUM_PED_VTA = DA.NUM_PED_VTA
                      AND DA.COD_CAMPO = 'D_000'
                      AND DA_ANT.COD_GRUPO_CIA = DA.COD_GRUPO_CIA
                      AND DA_ANT.COD_LOCAL = DA.COD_LOCAL
                      AND DA_ANT.NUM_PED_VTA <> DA.NUM_PED_VTA
                      AND DA_ANT.COD_CAMPO = DA.COD_CAMPO
                      AND DA_ANT.COD_CONVENIO = DA.COD_CONVENIO
                      AND TRIM(DA_ANT.COD_VALOR_IN) = TRIM(DA.COD_VALOR_IN)
                      AND DA_ANT.COD_GRUPO_CIA = CAB_ANT.COD_GRUPO_CIA
                      AND DA_ANT.COD_LOCAL = CAB_ANT.COD_LOCAL
                      AND DA_ANT.NUM_PED_VTA = CAB_ANT.NUM_PED_VTA
                      AND CAB_ANT.EST_PED_VTA = 'C'
               )
               AND C.ESTADO = 'E'
               AND C.COD_CAMP_CUPON = vCampanas.Cod_Camp_Cupon
               AND C.IND_IMPR = 'S';*/
            ELSE
              vContinua := 'S';
            END IF;
            IF vContinua = 'S' THEN
             INSERT INTO TT_PED_CAMP_PROD
                         (COD_GRUPO_CIA,COD_LOCAL,
                          NUM_PED_VTA,ORD_CREACION,
                          COD_CAMP_CUPON,COD_PROD,
                          VAL_PREC_TOTAL,
                          prioridad,
                          cod_grupo_rep_edmundo
                          )
             VALUES
             (cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,vCampanas.Sec_Orden,
              vCampanas.Cod_Camp_Cupon,vProdCamp.Cod_Prod,vProdCamp.Val_Prec_Total,
              vCampanas.PRIORIDAD,vProdCamp.Cod_Grupo_Rep_Edmundo);
           END IF;
         END LOOP;
      END LOOP;
      
      update     TT_PED_CAMP_PROD T
      set  t.orden_grupo  = (
                            case 
                   when  T.Cod_Grupo_Rep_Edmundo = '002'  then 1
                   when  T.Cod_Grupo_Rep_Edmundo = '004' or T.Cod_Grupo_Rep_Edmundo = '006' then 2
                   when  T.Cod_Grupo_Rep_Edmundo = '001'then 3  
                   else 4
                 end
                            )
          WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    T.COD_LOCAL     = cCodLocal_in
          AND    T.NUM_PED_VTA   = cNumPedVta_in;
      
      --2 QUITA LAS QUE SE VERIFICA QUE CALCULARA CUPONES
      FOR vCampaPedido IN curVerificaCampPrevia(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in)
      LOOP
          vPermite := F_PERMITE_CAMAPANA(vCampaPedido.Cod_Grupo_Cia,vCampaPedido.Cod_Local,
                             vCampaPedido.Num_Ped_Vta,vCampaPedido.Cod_Camp_Cupon,
                             cTipo,trim(to_char(vCampaPedido.Monto,'9999999999.99')));
         if vPermite = 'N' then
            delete TT_PED_CAMP_PROD T
            WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in 
            AND    T.COD_LOCAL     = cCodLocal_in    
            AND    T.NUM_PED_VTA   = cNumPedVta_in   
            AND    T.COD_CAMP_CUPON = vCampaPedido.Cod_Grupo_Cia;
         end if;
      END LOOP;      
      
      -- 3 evalua por grupo y selecciona la que va quedar
      for vCurGrupEd in curGrupoEd(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in)
      loop
        
          select max(t.prioridad)
          into   nMaxPrioridad
          from   TT_PED_CAMP_PROD t
          WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    T.COD_LOCAL     = cCodLocal_in
          AND    T.NUM_PED_VTA   = cNumPedVta_in          
          and    t.orden_grupo =  vCurGrupEd.Orden;
          
          select count(1)
          into   nCtdCampPermitidas
          from   TT_PED_CAMP_PROD t
          WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
          AND    T.COD_LOCAL     = cCodLocal_in
          AND    T.NUM_PED_VTA   = cNumPedVta_in          
          and    t.orden_grupo =  vCurGrupEd.Orden
          and    t.prioridad = nMaxPrioridad;
          
          if nCtdCampPermitidas = 1 then
            update TT_PED_CAMP_PROD t
            set    t.ind_emite = 'S'
            WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    T.COD_LOCAL     = cCodLocal_in
            AND    T.NUM_PED_VTA   = cNumPedVta_in          
          and    t.orden_grupo =  vCurGrupEd.Orden
            and    t.prioridad = nMaxPrioridad
             and    not exists (
                              select 1
                              from   TT_PED_CAMP_PROD v
                              WHERE  v.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND    v.COD_LOCAL     = cCodLocal_in
                              AND    v.NUM_PED_VTA   = cNumPedVta_in          
                              and    v.cod_camp_cupon = t.cod_camp_cupon
                              and    v.ind_emite = 'S'
                              );
          else
            nPosSeleccionada := trunc(DBMS_RANDOM.value(1,nCtdCampPermitidas));
            
            update TT_PED_CAMP_PROD t
            set    t.ind_emite = 'S'
            WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
            AND    T.COD_LOCAL     = cCodLocal_in
            AND    T.NUM_PED_VTA   = cNumPedVta_in          
            and    t.orden_grupo =  vCurGrupEd.Orden
            and    t.prioridad = nMaxPrioridad
            and    not exists (
                              select 1
                              from   TT_PED_CAMP_PROD v
                              WHERE  v.COD_GRUPO_CIA = cCodGrupoCia_in
                              AND    v.COD_LOCAL     = cCodLocal_in
                              AND    v.NUM_PED_VTA   = cNumPedVta_in          
                              and    v.cod_camp_cupon = t.cod_camp_cupon
                              and    v.ind_emite = 'S'
                              )
            and    t.cod_camp_cupon = 
                                    (
                                    select v.cod_camp_cupon
                                    from   (
                                            select t.cod_camp_cupon,t.prioridad,rownum pos
                                            from   TT_PED_CAMP_PROD t 
                                            WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND    T.COD_LOCAL     = cCodLocal_in
                                            AND    T.NUM_PED_VTA   = cNumPedVta_in          
                                            and    t.orden_grupo =  vCurGrupEd.Orden
                                            and    t.prioridad = nMaxPrioridad
                                           )v
                                     where v.pos = nPosSeleccionada 
                                     );
          end if;
          
      end loop;
      
      
      -- 2 ANALIZA
      FOR vCampaPedido IN curMontoCampanaPedido(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in)
      LOOP

       SELECT COUNT(1)
       INTO   nCantCampPedido
       FROM   VTA_PEDIDO_CUPON C,
              vta_campana_cupon camp
       WHERE  C.COD_GRUPO_CIA = vCampaPedido.Cod_Grupo_Cia
       AND    C.COD_LOCAL     = vCampaPedido.Cod_Local
       AND    C.NUM_PED_VTA   = vCampaPedido.Num_Ped_Vta
       and    c.cod_grupo_cia = camp.cod_grupo_cia
       and    c.cod_camp_cupon = camp.cod_camp_cupon
       and    camp.tip_campana = 'C';

       if nMaxCampanaPedido > nCantCampPedido then
         null;
          VTA_GRABAR_PED_CUPON(vCampaPedido.Cod_Grupo_Cia,vCampaPedido.Cod_Local,
                               vCampaPedido.Num_Ped_Vta,vCampaPedido.Cod_Camp_Cupon,
                               vCampaPedido.CTD_CUPONES ,cLoginUsu_in);
       end if;

      END LOOP;
      
  end if;
  
    IF GET_VERIFICA_PED_CAMP(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in)= 'S'THEN
      P_GEN_CUPONES(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,cLoginUsu_in,pDniCli);
    END IF;
   end if;
 END ;
 
PROCEDURE VTA_GRABAR_PED_CUPON(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in       IN CHAR,
                                 cNumPedVta_in      IN CHAR,
                                 cCodCupon_in       IN CHAR,
                                 cCantidad_in       IN NUMBER,
                                 cLoginUsu_in       IN CHAR)
  IS


  nCantidadNuevaCupones number := 0;
  
  BEGIN
   nCantidadNuevaCupones := cCantidad_in;

     if nCantidadNuevaCupones > 0 then
         INSERT INTO Vta_Pedido_Cupon
          (COD_GRUPO_CIA,COD_LOCAL,NUM_PED_VTA,
           COD_CAMP_CUPON,CANTIDAD,USU_CREA_PED_CUPON,ind_nvo_alg,ind_impresion)
          VALUES
          (cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,
           cCodCupon_in,nCantidadNuevaCupones,cLoginUsu_in,'S','S');
     end if;

  END;


FUNCTION VTA_F_GET_IND_FID_EMI(cCodGrupoCia_in CHAR,
                                 cIndFidelizado_in CHAR,
                                 cCod_camp_cupon_in CHAR,
                                 cFechaNac_in DATE DEFAULT NULL,
                                 cSexo_in CHAR DEFAULT NULL)
  RETURN CHAR
  IS
   cantCamp NUMBER(6) := 0;
   totalAcum NUMBER(6) := 0;
   rpta CHAR(1) := 'N';
   cIndFidEmi CHAR(1);
  BEGIN
       SELECT A.IND_FID_EMI INTO cIndFidEmi
         FROM VTA_CAMPANA_CUPON A
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
          AND A.COD_CAMP_CUPON = cCod_camp_cupon_in;
     BEGIN
       IF (cIndFidelizado_in = 'S') THEN
         IF (cIndFidEmi = 'S') THEN
           SELECT COUNT(*) INTO cantCamp
             FROM vta_campana_cupon c
            WHERE c.cod_grupo_cia = cCodGrupoCia_in
              AND c.cod_camp_cupon = cCod_camp_cupon_in
              AND c.ind_fid_emi = cIndFidEmi --obligado
              AND (C.TIPO_SEXO_E IS NULL OR C.TIPO_SEXO_E = cSexo_in)
              AND (C.FEC_NAC_INICIO_E IS NULL OR C.FEC_NAC_INICIO_E <=  cFechaNac_in)
              AND (C.FEC_NAC_FIN_E IS NULL OR C.FEC_NAC_FIN_E >= cFechaNac_in )
              AND (trunc(SYSDATE) BETWEEN c.fech_inicio AND c.fech_fin)
-- KMONCADA 30.10.2015 VALIDACION DE EMISION DE CUPONES PARA CLIENTES FIDELIZADOS O NO
              AND C.IND_EMI_TIPO_CLIENTE IN (C_TIPO_CLIENTE_TODO, C_TIPO_CLIENTE_FIDELIZADO)              
              ;
              IF cantCamp > 0 THEN
                 --tiene campaña
                 totalAcum := totalAcum + cantCamp;
              END IF;
          ELSE
         SELECT COUNT(*) INTO cantCamp
           FROM vta_campana_cupon cc
          WHERE cc.cod_grupo_cia = cCodGrupoCia_in
            AND cc.cod_camp_cupon = cCod_camp_cupon_in
            AND ind_fid_emi = cIndFidEmi
           -- VALIDA FECHA NI SEXO
            AND (cc.tipo_sexo_e IS NULL OR CC.TIPO_SEXO_E  = cSexo_in)
            AND (Cc.FEC_NAC_INICIO_E IS NULL OR Cc.FEC_NAC_INICIO_E <= cFechaNac_in )
            AND (Cc.FEC_NAC_FIN_E IS NULL OR cC.FEC_NAC_FIN_E >= cFechaNac_in )
            AND (trunc(SYSDATE) BETWEEN cc.fech_inicio AND cc.fech_fin)
-- KMONCADA 30.10.2015 VALIDACION DE EMISION DE CUPONES PARA CLIENTES FIDELIZADOS O NO
            AND CC.IND_EMI_TIPO_CLIENTE IN (C_TIPO_CLIENTE_TODO, C_TIPO_CLIENTE_FIDELIZADO)
            ;
            IF cantCamp > 0 THEN
               totalAcum := totalAcum + cantCamp;
            END IF;
          END IF;
       ELSE
       --NO FIDELIZADO
         SELECT COUNT(*) INTO cantCamp
           FROM vta_campana_cupon cc
          WHERE cc.cod_grupo_cia = cCodGrupoCia_in
            AND cc.cod_camp_cupon = cCod_camp_cupon_in
            AND ind_fid_emi = 'N'
           -- NO VALIDA FECHA NI SEXO
            AND (trunc(SYSDATE) BETWEEN cc.fech_inicio AND cc.fech_fin)
-- KMONCADA 30.10.2015 VALIDACION DE EMISION DE CUPONES PARA CLIENTES FIDELIZADOS O NO
            AND CC.IND_EMI_TIPO_CLIENTE IN (C_TIPO_CLIENTE_TODO, C_TIPO_CLIENTE_NO_FIDELIZADO)
            ;
            IF cantCamp > 0 THEN
               --tiene campaña
              -- rpta := 'S';
               totalAcum := totalAcum + cantCamp;
            --ELSE
              -- rpta := 'N';
            END IF;
       --dbms_output.put_line('no es fidelizado: '||cantCamp);
       END IF;
       IF (totalAcum > 0 ) THEN
         rpta := 'S';
       ELSE
         rpta := 'N';
       END IF;
       --dbms_output.put_line('rpta: '||rpta);
     END;
     RETURN rpta;
  END;

FUNCTION F_PERMITE_CAMAPANA(cCodGrupoCia_in  IN CHAR,
                            cCodLocal_in       IN CHAR,
                            cNumPedVta_in      IN CHAR,
                            cCodCupon_in       IN CHAR,
                            cTipo              IN CHAR,
                            nMontConsumoCamp_in IN CHAR) RETURN CHAR
  IS


  nUnidPedido     number;

  nMontoMin       number;
  nUnidMin        number;
  nNumCuponesRegalo number;
  vIndMultiplo_Mont char(1);
  vIndMultiplo_Unid char(1);
  nMaxCupones number;

  nCantidadNuevaCupones number := 0;
  nCantCupon_Monto number := 0;
  nCantCupon_Unidad number := 0;

  nMontConsumoCamp NUMBER := 0;
  BEGIN



    IF cTipo = 'C' THEN

      SELECT TO_NUMBER(nMontConsumoCamp_in,'9999999999.999')
      INTO   nMontConsumoCamp
      FROM DUAL;

      SELECT nvl(MONT_MIN,0),nvl(C.UNID_MIN,0),nvl(NUM_CUPON,1),
             IND_MULTIPLO_MONT,IND_MULTIPLO_UNIDAD,nvl(MAX_CUPONES,9999)
      INTO   nMontoMin,nUnidMin,nNumCuponesRegalo,
             vIndMultiplo_Mont,vIndMultiplo_Unid,
             nMaxCupones
      FROM   VTA_CAMPANA_CUPON C
      WHERE  C.COD_GRUPO_CIA  = cCodGrupoCia_in
      AND    C.COD_CAMP_CUPON = cCodCupon_in;

      SELECT SUM(D.CANT_ATENDIDA/D.VAL_FRAC)
      into   nUnidPedido
      FROM   VTA_PEDIDO_VTA_DET D,
             VTA_CAMPANA_PROD CD
      WHERE  D.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    D.COD_LOCAL = cCodLocal_in
      AND    D.NUM_PED_VTA = cNumPedVta_in
      AND    CD.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    CD.COD_CAMP_CUPON = cCodCupon_in
      AND    CD.COD_PROD = D.COD_PROD;

     if nMontoMin != 0 then
        if vIndMultiplo_Mont = 'S' then
           nCantCupon_Monto := trunc(nMontConsumoCamp/nMontoMin);
        else
           if trunc(nMontConsumoCamp/nMontoMin) > 0 then
              nCantCupon_Monto := 1;
           end if;
        end if;
     elsif nMontoMin = 0 then
           if vIndMultiplo_Mont = 'S' then
           nCantCupon_Monto := nMontConsumoCamp;
           end if;
     end if;

     if nUnidMin != 0 then
        if vIndMultiplo_Unid = 'S' then
             nCantCupon_Unidad := trunc(nUnidPedido/nUnidMin);
        else
           if trunc(nUnidPedido/nUnidMin) > 0 then
              nCantCupon_Unidad := 1;
           end if;
        end if;
     end if;

     if nCantCupon_Monto != 0 and nCantCupon_Unidad !=0 then
        if nCantCupon_Monto >= nCantCupon_Unidad then
           nCantidadNuevaCupones :=  nCantCupon_Unidad;
        else
             nCantidadNuevaCupones :=  nCantCupon_Monto;
        end if;
      else
        if nCantCupon_Monto >= nCantCupon_Unidad then
           nCantidadNuevaCupones :=  nCantCupon_Monto;
        else
             nCantidadNuevaCupones :=  nCantCupon_Unidad;
        end if;
      end if;

    nCantidadNuevaCupones := nCantidadNuevaCupones*nNumCuponesRegalo;

     if nCantidadNuevaCupones > nMaxCupones then
        nCantidadNuevaCupones := nMaxCupones;
     end if;

     DBMS_OUTPUT.put_line(''||nCantidadNuevaCupones);
     if nCantidadNuevaCupones > 0 then
        update TT_PED_CAMP_PROD t
        set    t.num_cupon  = nCantidadNuevaCupones
        WHERE  t.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    t.COD_LOCAL     = cCodLocal_in
        AND    T.NUM_PED_VTA   = cNumPedVta_in 
        and    t.cod_camp_cupon = cCodCupon_in;
         RETURN 'S';
     end if;
   end if;
   
   RETURN 'N';
  END;

 /* ******************************************************************************* */
PROCEDURE P_GEN_CUPONES(pCodGrupoCia_in     IN CHAR,
                        cCodLocal_in        IN CHAR,
                        cNumPedVta_in       IN CHAR,
                        cIdUsu_in           IN CHAR,
                        cDni_in             IN CHAR)
  IS
  --v_rVtaPedidoVtaCab VTA_PEDIDO_VTA_CAB%ROWTYPE;
  v_SecEan  NUMBER;
  c_SecEan  CHAR(8);
  v_CodCupon   VARCHAR2(20);
  n_cant    NUMBER(8);
  --n_canteExist NUMBER;
  i number:=0;
  FEC_INI_AUX DATE;
  FEC_FIN_AUX DATE;
  v_ip        VARCHAR2(20);

  cod_cupon VARCHAR2(20);

  CURSOR CURCAMP IS
 SELECT  DISTINCT X.COD_GRUPO_CIA,
          X.COD_LOCAL,
          X.COD_CAMP_CUPON,
          X.NUM_PED_VTA,
          X.CANTIDAD,
          NVL(Z.NUM_DIAS_INI,0) AS NUM_DIAS_INI,
          NVL(Z.NUM_DIAS_VIG,0) AS NUM_DIAS_VIG,
          Z.FECH_INICIO_USO,
          Z.FECH_FIN_USO,
          Z.IND_MULTIUSO,
          Z.COD_CAMP_IMP_CUP  --- 18-JUL-14 TCT ADD
  FROM VTA_PEDIDO_CUPON X,
       VTA_PEDIDO_VTA_CAB Y,
       VTA_CAMPANA_CUPON Z,
       VTA_CAMPANA_PROD_USO P,
       LGT_PROD_LOCAL L
  WHERE X.COD_GRUPO_CIA=pCodGrupoCia_in
  AND X.COD_LOCAL=cCodLocal_in
  AND Y.NUM_PED_VTA=cNumPedVta_in
  AND X.IND_IMPRESION='S'
  AND Z.TIP_CAMPANA='C'
  AND L.COD_GRUPO_CIA = pCodGrupoCia_in
  AND L.COD_LOCAL = cCodLocal_in
  AND L.STK_FISICO> 0
  AND L.COD_GRUPO_CIA = X.COD_GRUPO_CIA
  AND L.COD_LOCAL = X.COD_LOCAL
  AND P.COD_GRUPO_CIA = Z.COD_GRUPO_CIA
  AND P.COD_CAMP_CUPON = Z.COD_CAMP_CUPON
  AND P.COD_GRUPO_CIA = L.COD_GRUPO_CIA
  AND P.COD_PROD = L.COD_PROD
  --AND Y.EST_PED_VTA NOT IN ('N','C')
  AND X.COD_GRUPO_CIA=Y.COD_GRUPO_CIA
  AND X.COD_LOCAL=Y.COD_LOCAL
  AND X.NUM_PED_VTA=Y.NUM_PED_VTA
  AND X.COD_GRUPO_CIA=Z.COD_GRUPO_CIA
  AND X.COD_CAMP_CUPON=Z.COD_CAMP_CUPON;


  ROWCURCAMP CURCAMP%ROWTYPE;



  BEGIN
    --- TCT DEBUG
    --- sp_graba_log(ac_descrip => 'PASO POR :CAJ_GENERA_CUPON');
    ---

    FOR ROWCURCAMP IN CURCAMP
    LOOP
       BEGIN

         IF(ROWCURCAMP.IND_MULTIUSO='N')THEN

          n_cant:=ROWCURCAMP.CANTIDAD;
          dbms_output.put_line('INGRESO CURSOR -->'||n_cant);
          FOR i IN 1..n_cant
          LOOP
           
           v_SecEan := PTOVENTA_CAJ.OBTENER_NUMERACION(pCodGrupoCia_in, cCodLocal_in,ROWCURCAMP.COD_CAMP_CUPON);

		   --ERIOS 16.02.2016 El correlativo puede tener 8 digitos
           IF v_SecEan <= 99999999 THEN
             --RAISE_APPLICATION_ERROR(-20022,'Se ha superado el límite de cupones a imprimir.');

             c_SecEan := Farma_Utility.COMPLETAR_CON_SIMBOLO(TO_CHAR(v_SecEan),8,'0','I');
             --- 100.-  Veirfica Formato de Imprimir Cupones
             IF ROWCURCAMP.COD_CAMP_IMP_CUP IS  NULL THEN
               -- Antiguo modo de imprimir cupones
               v_CodCupon:=TRIM(ROWCURCAMP.COD_CAMP_CUPON);

             ELSIF ROWCURCAMP.COD_CAMP_IMP_CUP IS NOT NULL THEN
               -- Nuevo modo de imprimir cupones               
               v_CodCupon:='00'||TRIM(ROWCURCAMP.COD_CAMP_IMP_CUP);

             END IF;
             
			 --ERIOS 16.02.2016 Nuevo formato de cupones
             v_CodCupon := 'C'||v_CodCupon||ROWCURCAMP.COD_LOCAL||c_SecEan;

             --Se asume que la vigenci del cupon debe estar entre las fechas de uso de la campaña
             IF(ROWCURCAMP.NUM_DIAS_INI=0)THEN
                 FEC_INI_AUX:=TRUNC(SYSDATE);
                 IF(FEC_INI_AUX<ROWCURCAMP.FECH_INICIO_USO)THEN
                   FEC_INI_AUX:=ROWCURCAMP.FECH_INICIO_USO;
                 END IF;
             ELSIF (ROWCURCAMP.NUM_DIAS_INI>0) THEN
                 FEC_INI_AUX:=TRUNC(SYSDATE+ROWCURCAMP.NUM_DIAS_INI);
                 IF(FEC_INI_AUX<ROWCURCAMP.FECH_INICIO_USO)THEN
                   FEC_INI_AUX:=ROWCURCAMP.FECH_INICIO_USO;
                 END IF;
             END IF;

             IF(ROWCURCAMP.NUM_DIAS_VIG=0)THEN
                 FEC_FIN_AUX:=ROWCURCAMP.FECH_FIN_USO;
             ELSIF (ROWCURCAMP.NUM_DIAS_VIG>0)THEN
                 FEC_FIN_AUX:= TRUNC(FEC_INI_AUX + ROWCURCAMP.NUM_DIAS_VIG);
                 IF(FEC_FIN_AUX>ROWCURCAMP.FECH_FIN_USO)THEN
                   FEC_FIN_AUX:=ROWCURCAMP.FECH_FIN_USO;
                 END IF;
             END IF;


            SELECT substr(sys_context('USERENV','IP_ADDRESS'),1,50) INTO v_ip
            FROM DUAL;

             INSERT INTO VTA_CUPON(COD_GRUPO_CIA
                                  ,COD_LOCAL
                                  ,COD_CUPON
                                  ,ESTADO
                                  ,USU_CREA_CUP_CAB
                                  ,USU_MOD_CUP_CAB
                                  ,FEC_MOD_CUP_CAB
                                  ,COD_CAMPANA
                                  ,SEC_CUPON
                                  ,FEC_INI
                                  ,FEC_FIN,
                                  IP,NUM_DOC_IDENT)--JCORTEZ 17.08.09 nuevos campos
             VALUES (ROWCURCAMP.COD_GRUPO_CIA,
                    ROWCURCAMP.COD_LOCAL,
                    v_CodCupon,
                    C_ESTADO_ACTIVO,
                    cIdUsu_in,
                    NULL,NULL,ROWCURCAMP.COD_CAMP_CUPON,c_SecEan,FEC_INI_AUX,FEC_FIN_AUX,v_ip,cDni_in);
                    --DECODE(ROWCURCAMP.NUM_DIAS_INI,0,ROWCURCAMP.FECH_INICIO_USO,TRUNC(SYSDATE+ROWCURCAMP.NUM_DIAS_INI)),
                    --DECODE(ROWCURCAMP.NUM_DIAS_VIG,0,ROWCURCAMP.FECH_FIN_USO,TRUNC((SYSDATE+ROWCURCAMP.NUM_DIAS_INI)+ROWCURCAMP.NUM_DIAS_VIG)));

            INSERT INTO VTA_CAMP_PEDIDO_CUPON(COD_GRUPO_CIA
                                        ,COD_LOCAL
                                        ,COD_CUPON
                                        ,NUM_PED_VTA
                                        ,ESTADO
                                        ,USU_CREA_CUPON_PED
                                        ,USU_MOD_CUPON_PED
                                        ,FEC_MOD_CUPON_PED
                                        ,COD_CAMP_CUPON)
             VALUES(ROWCURCAMP.COD_GRUPO_CIA,
                    ROWCURCAMP.COD_LOCAL,
                    v_CodCupon,ROWCURCAMP.NUM_PED_VTA,C_ESTADO_EMITIDO,cIdUsu_in,NULL,NULL,ROWCURCAMP.COD_CAMP_CUPON);

              --Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(pCodGrupoCia_in,cCodLocal_in,C_C_COD_EAN_CUPON,cIdUsu_in);

              UPDATE VTA_NUMERA_CUPON X
              SET X.SEC_CUPON=v_SecEan
              WHERE X.COD_GRUPO_CIA=ROWCURCAMP.COD_GRUPO_CIA
              AND X.COD_LOCAL=ROWCURCAMP.COD_LOCAL
              AND X.COD_CAMP_CUPON=ROWCURCAMP.COD_CAMP_CUPON;
            END IF;
          END
          LOOP;
      /*EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK;  */

          ELSE --JCORTEZ 15/08/2008 se genera pedido cupon multiuso
           n_cant:=ROWCURCAMP.CANTIDAD;

          /*FOR i IN 1..n_cant
          LOOP*/

          SELECT A.COD_CUPON INTO cod_cupon
          FROM VTA_CUPON A
          WHERE A.COD_GRUPO_CIA=ROWCURCAMP.COD_GRUPO_CIA
          AND A.COD_CAMPANA=ROWCURCAMP.COD_CAMP_CUPON;

           INSERT INTO VTA_CAMP_PEDIDO_CUPON(COD_GRUPO_CIA
                                        ,COD_LOCAL
                                        ,COD_CUPON
                                        ,NUM_PED_VTA
                                        ,ESTADO
                                        ,USU_CREA_CUPON_PED
                                        ,USU_MOD_CUPON_PED
                                        ,FEC_MOD_CUPON_PED
                                        ,COD_CAMP_CUPON)
             VALUES(ROWCURCAMP.COD_GRUPO_CIA,
                    ROWCURCAMP.COD_LOCAL,
                    cod_cupon,ROWCURCAMP.NUM_PED_VTA,C_ESTADO_EMITIDO,cIdUsu_in,NULL,NULL,ROWCURCAMP.COD_CAMP_CUPON);
         /*  END
           LOOP;*/

          END IF;
       END;
    END
    LOOP;
  END;   
/* ********************************************************************** */
FUNCTION GET_VERIFICA_PED_CAMP(cCodGrupoCia_in   IN CHAR,
                                 cCodLocal_in     IN CHAR,
                                   cNumPedVta_in    IN CHAR)
  RETURN VARCHAR2
  IS
  vResultado varchar2(14000):= '';
  cant number:=0;
  
  BEGIN

    SELECT COUNT(*) INTO cant
    FROM VTA_PEDIDO_CUPON A
         --VTA_CAMPANA_CUPON B
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.NUM_PED_VTA=cNumPedVta_in;

     IF(cant>0)THEN
       vResultado:='S';
     ELSE
       vResultado:='N';
     END IF;

   RETURN vResultado;
   END;
  
  PROCEDURE P_GENERA_CUPON_CONV(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cNumPedVta_in   IN CHAR)
  IS
    CURSOR curCupones IS
      SELECT CAB.COD_GRUPO_CIA,
             CAB.COD_LOCAL,
             CAB.NUM_PED_VTA,
             CONV.COD_CAMP_CUPON,
             1 CTD_CUPONES,
             CAB.COD_CONVENIO,
             CAB.USU_CREA_PED_VTA_CAB USUARIO
      FROM VTA_PEDIDO_VTA_CAB CAB,
           VTA_PEDIDO_VTA_DET DET,
           REL_VTA_CAMPANA_CONVENIO CONV,
           VTA_CAMPANA_PROD C_PROD
      WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND CAB.COD_LOCAL = cCodLocal_in
      AND CAB.NUM_PED_VTA = cNumPedVta_in
      AND CONV.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
      AND CONV.COD_CONVENIO = CAB.COD_CONVENIO
      AND CAB.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
      AND CAB.COD_LOCAL = DET.COD_LOCAL
      AND CAB.NUM_PED_VTA = DET.NUM_PED_VTA
      AND C_PROD.COD_GRUPO_CIA = DET.COD_GRUPO_CIA
      AND C_PROD.COD_CAMP_CUPON = CONV.COD_CAMP_CUPON
      AND C_PROD.COD_PROD = DET.COD_PROD;
    filaCupones curCupones%ROWTYPE;
    vPrimeraCompra CHAR(1);
    vDni VARCHAR2(20);
  BEGIN
    OPEN curCupones;
    LOOP
      FETCH curCupones INTO filaCupones;
      EXIT WHEN curCupones%NOTFOUND;
        SELECT A.COD_VALOR_IN
        INTO vDni
        FROM CON_BTL_MF_PED_VTA A
        WHERE A.COD_GRUPO_CIA = filaCupones.Cod_Grupo_Cia
        AND A.COD_LOCAL = filaCupones.Cod_Local
        AND A.NUM_PED_VTA = filaCupones.Num_Ped_Vta
        AND A.COD_CAMPO = 'D_000';
        
        SELECT DECODE(COUNT(1),0,'S','N')
        INTO vPrimeraCompra
        FROM VTA_PEDIDO_VTA_CAB CAB,
             CON_BTL_MF_PED_VTA DA
        WHERE CAB.COD_GRUPO_CIA = DA.COD_GRUPO_CIA
        AND CAB.COD_LOCAL = DA.COD_LOCAL
        AND CAB.NUM_PED_VTA = DA.NUM_PED_VTA
        AND DA.COD_GRUPO_CIA = filaCupones.Cod_Grupo_Cia
        AND DA.COD_LOCAL = filaCupones.Cod_Local
        AND DA.NUM_PED_VTA != filaCupones.Num_Ped_Vta
        AND DA.COD_CAMPO = 'D_000'
        AND DA.COD_VALOR_IN = vDni
        AND DA.COD_CONVENIO = filaCupones.Cod_Convenio
        AND CAB.EST_PED_VTA = 'C';
        
        IF vPrimeraCompra = 'S' THEN
          VTA_GRABAR_PED_CUPON(filaCupones.Cod_Grupo_Cia,filaCupones.Cod_Local,
                               filaCupones.Num_Ped_Vta,filaCupones.Cod_Camp_Cupon,
                               filaCupones.CTD_CUPONES ,filaCupones.USUARIO);
        END IF;
    END LOOP;
    CLOSE curCupones;
    
    IF GET_VERIFICA_PED_CAMP(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in)= 'S' THEN
      P_GEN_CUPONES(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in,filaCupones.Usuario,'');
    END IF;
  END;
  
  FUNCTION F_VALIDA_VTA_CENTRO_MEDICO(cCodGrupoCia_in      IN VTA_PEDIDO_VTA_CAB.COD_GRUPO_CIA%TYPE,
                                      cCodLocal_in         IN VTA_PEDIDO_VTA_CAB.COD_LOCAL%TYPE,
                                      cNumPedVta_in        IN VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE,
                                      cCodCampCupon_in     IN VTA_CAMPANA_CUPON.COD_CAMP_CUPON%TYPE)
    RETURN CHAR IS
    vContinua CHAR(1);
    vDocBeneficiario CON_BTL_MF_PED_VTA.COD_VALOR_IN%TYPE;
    vCodConvenio MAE_CONVENIO.COD_CONVENIO%TYPE;
  BEGIN
    SELECT DECODE(COUNT(1),0,'S','N')
    INTO vContinua
    FROM REL_VTA_CAMPANA_CONVENIO
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_CAMP_CUPON = cCodCampCupon_in;
    
    SELECT TRIM(CONV.COD_VALOR_IN), CAB.COD_CONVENIO
    INTO vDocBeneficiario, vCodConvenio
    FROM VTA_PEDIDO_VTA_CAB CAB,
         CON_BTL_MF_PED_VTA CONV
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
    AND CAB.COD_LOCAL = cCodLocal_in
    AND CAB.NUM_PED_VTA = cNumPedVta_in
    AND CAB.COD_GRUPO_CIA = CONV.COD_GRUPO_CIA
    AND CAB.COD_LOCAL = CONV.COD_LOCAL
    AND CAB.NUM_PED_VTA = CONV.NUM_PED_VTA
    AND CONV.COD_CAMPO = 'D_000';
    
    IF vContinua = 'N' THEN
      SELECT DECODE(COUNT(1), 0, 'S', 'N')
      INTO vContinua
      FROM VTA_CAMP_PEDIDO_CUPON C
      WHERE (C.COD_GRUPO_CIA, C.COD_LOCAL, C.NUM_PED_VTA) IN (
              SELECT CAB.COD_GRUPO_CIA, CAB.COD_LOCAL, CAB.NUM_PED_VTA
              FROM VTA_PEDIDO_VTA_CAB CAB
                LEFT OUTER JOIN VTA_PEDIDO_VTA_CAB CAB_ANU
                  ON CAB.COD_GRUPO_CIA = CAB_ANU.COD_GRUPO_CIA
                 AND CAB.COD_LOCAL = CAB_ANU.COD_LOCAL
                 AND CAB.NUM_PED_VTA = CAB_ANU.NUM_PED_VTA_ORIGEN
               INNER JOIN CON_BTL_MF_PED_VTA CONV
                  ON CAB.COD_GRUPO_CIA = CONV.COD_GRUPO_CIA
                 AND CAB.COD_LOCAL = CONV.COD_LOCAL
                 AND CAB.NUM_PED_VTA = CONV.NUM_PED_VTA
              WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
              AND CAB.COD_LOCAL = cCodLocal_in
              AND CAB.NUM_PED_VTA != cNumPedVta_in
              AND CAB.EST_PED_VTA = 'C'
              AND TRUNC(CAB.FEC_PED_VTA ) = TRUNC(SYSDATE) -- NO EXISTA VTA EN EL DIA
              AND CAB.COD_CONVENIO = vCodConvenio
              AND CONV.COD_CAMPO = 'D_000'
              AND TRIM(CONV.COD_VALOR_IN) = TRIM(vDocBeneficiario)
              AND CAB_ANU.NUM_PED_VTA IS NULL -- QUE NO ESTE ANULADA
       )
       AND C.ESTADO = 'E'
       AND C.COD_CAMP_CUPON = cCodCampCupon_in
       AND C.IND_IMPR = 'S';
    END IF;
    
    RETURN vContinua;
  END;

END FARMA_CUPON;
/
