CREATE OR REPLACE PACKAGE PTOVENTA."PKG_SOL_STOCK" is

  FUNCTION SP_GRABA_SOL(A_COD_GRUPO_CIA CAB_SOLICITUD_STOCK.COD_GRUPO_CIA%TYPE,
                         A_COD_LOCAL     CAB_SOLICITUD_STOCK.COD_LOCAL%TYPE,
                         A_USU_SOLICITUD CAB_SOLICITUD_STOCK.USU_SOLICITUD%TYPE,
                         A_QF_APROBADOR  CAB_SOLICITUD_STOCK.QF_APROBADOR%TYPE,
                         A_COD_PRODUCTO  DET_SOLICITUD_STOCK.COD_PRODUCTO%TYPE,
                         A_CANT_PRODUCTO DET_SOLICITUD_STOCK.CANT_PRODUCTO%TYPE,
                         A_VAL_FRACCION  DET_SOLICITUD_STOCK.VAL_FRACCION%TYPE,
                         A_ID_SOLICITUD  varchar2
                         ) RETURN VARCHAR2;

  FUNCTION FN_DEV_SEC RETURN CAB_SOLICITUD_STOCK.ID_SOLICITUD%TYPE;
  PROCEDURE INV_INGRESA_AJUSTE_KARDEX(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodProd_in      IN CHAR,
                                      cCodMotKardex_in IN CHAR,
                                      cNeoCant_in      IN CHAR,
                                      cGlosa_in        IN CHAR,
                                      cTipDoc_in       IN CHAR,
                                      cUsu_in          IN CHAR,
                                      cIndCorreoAjuste IN CHAR DEFAULT 'S');

PROCEDURE SP_APRUEBA_SOL(A_COD_GRUPO_CIA CAB_SOLICITUD_STOCK.COD_GRUPO_CIA%TYPE,
                           A_COD_LOCAL     CAB_SOLICITUD_STOCK.COD_LOCAL%TYPE,
                           --A_ID_SOLICITUD  CAB_SOLICITUD_STOCK.ID_SOLICITUD%TYPE,
                           A_NUM_PED_VTA   CAB_SOLICITUD_STOCK.NUM_PED_VTA%TYPE) ;

  FUNCTION SOL_F_PERMITE_VTA_NEGATIVA(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodProd_in      IN CHAR,
                                      cValFracVta_in IN NUMBER,
                                      cCtd_in        IN NUMBER)    RETURN VARCHAR2;

FUNCTION F_PERMITE_VTA_NEGATIVA  RETURN VARCHAR2;

end;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PKG_SOL_STOCK" IS

  FUNCTION SP_GRABA_SOL(A_COD_GRUPO_CIA CAB_SOLICITUD_STOCK.COD_GRUPO_CIA%TYPE,
                         A_COD_LOCAL     CAB_SOLICITUD_STOCK.COD_LOCAL%TYPE,
                         A_USU_SOLICITUD CAB_SOLICITUD_STOCK.USU_SOLICITUD%TYPE,
                         A_QF_APROBADOR  CAB_SOLICITUD_STOCK.QF_APROBADOR%TYPE,
                         A_COD_PRODUCTO  DET_SOLICITUD_STOCK.COD_PRODUCTO%TYPE,
                         A_CANT_PRODUCTO DET_SOLICITUD_STOCK.CANT_PRODUCTO%TYPE,
                         A_VAL_FRACCION  DET_SOLICITUD_STOCK.VAL_FRACCION%TYPE,
                         A_ID_SOLICITUD  varchar2
                         ) RETURN VARCHAR2 AS
    C_ID_SOLICITUD CAB_SOLICITUD_STOCK.ID_SOLICITUD%TYPE;
    nStkAdicional DET_SOLICITUD_STOCK.CANT_PRODUCTO%TYPE;
  BEGIN

   IF A_ID_SOLICITUD = 'X' THEN
   /*
      select NVL(MAX(ID_SOLICITUD),0) +1
      into   C_ID_SOLICITUD
      from   CAB_SOLICITUD_STOCK;
  */
      C_ID_SOLICITUD := FN_DEV_SEC;

    INSERT INTO PTOVENTA.CAB_SOLICITUD_STOCK
    (cod_local, id_solicitud, cod_estado, usu_solicitud, qf_aprobador,
     fch_solicitud, usu_regularizacion, fch_regularizacion, cod_grupo_cia, num_ped_vta)
    VALUES
      (A_COD_LOCAL,
       C_ID_SOLICITUD,
       'G',
       A_USU_SOLICITUD,
       A_QF_APROBADOR,
       SYSDATE,
       NULL,
       NULL,
       A_COD_GRUPO_CIA, NULL);

   ELSE
      C_ID_SOLICITUD := A_ID_SOLICITUD;
   END IF;

   select ((A_CANT_PRODUCTO*l.VAL_FRAC_LOCAL)/A_VAL_FRACCION)  - l.stk_fisico
   into   nStkAdicional
   from   lgt_prod_local l
   where  l.cod_grupo_cia  = A_COD_GRUPO_CIA
   and    l.cod_local = A_COD_LOCAL
   and    l.cod_prod = A_COD_PRODUCTO;

    INSERT INTO DET_SOLICITUD_STOCK
    (cod_grupo_cia, cod_local, id_solicitud, cod_producto, cant_producto,
    val_fraccion, tip_mot_kardex, num_tip_kardex, sec_kardex)
    VALUES
      (A_COD_GRUPO_CIA,
       A_COD_LOCAL,
       C_ID_SOLICITUD,
       A_COD_PRODUCTO,
       nStkAdicional,
       A_VAL_FRACCION,
       NULL,
       NULL,
       NULL
       );

    --COMMIT;
    RETURN C_ID_SOLICITUD;

  END;

  FUNCTION FN_DEV_SEC RETURN CAB_SOLICITUD_STOCK.ID_SOLICITUD%TYPE AS
    C_ID_SOLICITUD CAB_SOLICITUD_STOCK.ID_SOLICITUD%TYPE;
  BEGIN
    SELECT NVL(MAX(ID_SOLICITUD), 0) + 1
      INTO C_ID_SOLICITUD
      FROM PTOVENTA.CAB_SOLICITUD_STOCK;
    RETURN C_ID_SOLICITUD;
  END;

  FUNCTION FN_LISTA_PENDIENTE(A_FCH_INICIO VARCHAR2, A_FCH_FIN VARCHAR2)
    RETURN SYS_REFCURSOR AS
    C_CURSOR SYS_REFCURSOR;
  BEGIN
    OPEN C_CURSOR FOR
      SELECT *
        FROM PTOVENTA.CAB_SOLICITUD_STOCK
       WHERE FCH_SOLICITUD >= TO_DATE(A_FCH_INICIO, 'DD/MM/YYYY')
         AND FCH_SOLICITUD < TO_DATE(A_FCH_FIN, 'DD/MM/YYYY') + 1;
    RETURN C_CURSOR;
  END;

  PROCEDURE SP_APRUEBA_SOL(A_COD_GRUPO_CIA CAB_SOLICITUD_STOCK.COD_GRUPO_CIA%TYPE,
                           A_COD_LOCAL     CAB_SOLICITUD_STOCK.COD_LOCAL%TYPE,
                           --A_ID_SOLICITUD  CAB_SOLICITUD_STOCK.ID_SOLICITUD%TYPE,
                           A_NUM_PED_VTA   CAB_SOLICITUD_STOCK.NUM_PED_VTA%TYPE) AS
    CURSOR C_DATOS IS
      SELECT a.cod_producto,
             --a.cant_producto,a.val_fraccion
             --((a.cant_producto*PROD_LOCAL.VAL_FRAC_LOCAL)/a.val_fraccion)  as "CANT_PRODUCTO",
             a.cant_producto,
             B.QF_APROBADOR
        FROM DET_SOLICITUD_STOCK A, CAB_SOLICITUD_STOCK B,
             LGT_PROD_LOCAL PROD_LOCAL
       WHERE B.COD_GRUPO_CIA = A_COD_GRUPO_CIA
         AND B.COD_LOCAL = A_COD_LOCAL
         --AND B.ID_SOLICITUD = A_ID_SOLICITUD
         and  b.num_ped_vta = A_NUM_PED_VTA
       AND A.COD_GRUPO_CIA = b.COD_GRUPO_CIA
         AND A.COD_LOCAL = b.COD_LOCAL
         AND A.ID_SOLICITUD = b.id_solicitud
         and a.cod_grupo_cia = PROD_LOCAL.COD_GRUPO_CIA
         and a.cod_local = PROD_LOCAL.Cod_Local
         and a.cod_producto = PROD_LOCAL.COD_PROD;
  BEGIN
    begin
      FOR REG IN c_datos LOOP
        pkg_sol_stock.inv_ingresa_ajuste_kardex(ccodgrupocia_in  => A_COD_GRUPO_CIA,
                                                ccodlocal_in     => A_COD_LOCAL,
                                                ccodprod_in      => REG.COD_PRODUCTO,
                                                ccodmotkardex_in => '515',
                                                cneocant_in      => REG.CANT_PRODUCTO,
                                                cglosa_in        => 'AJUSTE VTA_NEGATIVA',
                                                ctipdoc_in       => '03',
                                                cusu_in          => REG.QF_APROBADOR,
                                                cindcorreoajuste => NULL);
      END LOOP;
    end;
  END;

  PROCEDURE INV_INGRESA_AJUSTE_KARDEX(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodProd_in      IN CHAR,
                                      cCodMotKardex_in IN CHAR,
                                      cNeoCant_in      IN CHAR,
                                      cGlosa_in        IN CHAR,
                                      cTipDoc_in       IN CHAR,
                                      cUsu_in          IN CHAR,
                                      cIndCorreoAjuste IN CHAR DEFAULT 'S') IS
    v_nStkFisico   NUMBER;
    v_nValFrac     NUMBER;
    v_vDescUnidVta VARCHAR2(30);
    vCantMov_in    NUMBER;
    v_nNeoCod      CHAR(10);
    v_cSecKardex   LGT_KARDEX.SEC_KARDEX%TYPE;
  BEGIN
    --Obtener STK Actual
    SELECT STK_FISICO, VAL_FRAC_LOCAL, UNID_VTA
      INTO v_nStkFisico, v_nValFrac, v_vDescUnidVta
      FROM LGT_PROD_LOCAL
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND COD_PROD = cCodProd_in;

    --vCantMov_in := cNeoCant_in - v_nStkFisico;
    vCantMov_in := cNeoCant_in;
    v_nNeoCod   := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,
                                                                                        cCodLocal_in,
                                                                                        Ptoventa_Inv.COD_NUMERA_SEC_MOV_AJUSTE_KARD),
                                                       10,
                                                       '0',
                                                       'I');
   dbms_output.put_line('><cCodProd_in>'||cCodProd_in);
   dbms_output.put_line('><Adicional >'||vCantMov_in);
   dbms_output.put_line('><cNeoCant_in>'||cNeoCant_in);
   dbms_output.put_line('><v_nStkFisico>'||v_nStkFisico);
    if vCantMov_in > 0 then
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               Ptoventa_Inv.COD_NUMERA_SEC_MOV_AJUSTE_KARD,
                                               cUsu_in);

    --Actualizar Stock de Prod Local
    UPDATE LGT_PROD_LOCAL
       SET USU_MOD_PROD_LOCAL = cUsu_in,
           FEC_MOD_PROD_LOCAL = SYSDATE,
           STK_FISICO         = TO_NUMBER(cNeoCant_in)+stk_fisico
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in
       AND COD_PROD = cCodProd_in;

    --INSERTAR KARDEX
    v_cSecKardex := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,
                                                                                         cCodLocal_in,
                                                                                         Ptoventa_Inv.COD_NUMERA_SEC_KARDEX),
                                                        10,
                                                        '0',
                                                        'I');
    INSERT INTO LGT_KARDEX
      (COD_GRUPO_CIA,
       COD_LOCAL,
       SEC_KARDEX,
       COD_PROD,
       COD_MOT_KARDEX,
       TIP_DOC_KARDEX,
       NUM_TIP_DOC,
       STK_ANTERIOR_PROD,
       CANT_MOV_PROD,
       STK_FINAL_PROD,
       VAL_FRACC_PROD,
       DESC_UNID_VTA,
       USU_CREA_KARDEX,
       DESC_GLOSA_AJUSTE)
    VALUES
      (cCodGrupoCia_in,
       cCodLocal_in,
       v_cSecKardex,
       cCodProd_in,
       cCodMotKardex_in,
       cTipDoc_in,
       v_nNeoCod,
       v_nStkFisico,
       vCantMov_in,
       (v_nStkFisico + vCantMov_in),
       v_nValFrac,
       v_vDescUnidVta,
       cUsu_in,
       cGlosa_in);
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               Ptoventa_Inv.COD_NUMERA_SEC_KARDEX,
                                               cUsu_in);
    --FIN INSERTAR KARDEX
   end if;
  END;

  /* ************************************************************************* */
  FUNCTION SOL_F_PERMITE_VTA_NEGATIVA(cCodGrupoCia_in  IN CHAR,
                                      cCodLocal_in     IN CHAR,
                                      cCodProd_in      IN CHAR,
                                      cValFracVta_in IN NUMBER,
                                      cCtd_in        IN NUMBER)    RETURN VARCHAR2 AS
    vResultado varchar2(20000) :='N';
    nStockLocal_in number(10,3);
    pPermiteVtaNegativa varchar2(2);
    pDescripProd lgt_prod.desc_prod%type;
    vUnidad varchar2(800);
  BEGIN

    SELECT (((PROD_LOCAL.STK_FISICO)*cValFracVta_in)/PROD_LOCAL.VAL_FRAC_LOCAL)
    into   nStockLocal_in
    FROM   LGT_PROD_LOCAL PROD_LOCAL
    WHERE  PROD_LOCAL.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     PROD_LOCAL.COD_LOCAL = cCodLocal_in
    AND     PROD_LOCAL.COD_PROD = cCodProd_in FOR UPDATE;


    if (nStockLocal_in - cCtd_in) < 0 then
      -- vta Negativa
        begin
        select llave_tab_gral
        into   pPermiteVtaNegativa
        from   pbl_tab_gral
        where  id_tab_gral = '501';
       exception
         when others then
           pPermiteVtaNegativa := 'N';
       end;
         vResultado := pPermiteVtaNegativa;

       ---------------------------------
       if pPermiteVtaNegativa = 'S' then
        select p.desc_prod
        into   pDescripProd
        from   lgt_prod p
        where  p.cod_prod = cCodProd_in
        and    p.cod_grupo_cia = cCodGrupoCia_in;

        select DECODE(l.IND_PROD_FRACCIONADO,'N',p.DESC_UNID_PRESENT,l.UNID_VTA)
        into   vUnidad
        from   lgt_prod p,
               lgt_prod_local l
        where p.cod_grupo_cia = l.cod_grupo_cia
        and   p.cod_prod = l.cod_prod
        and   l.cod_grupo_cia = cCodGrupoCia_in
        and   l.cod_local = cCodLocal_in
        and   l.cod_prod = cCodProd_in;

          vResultado := pDescripProd||' '||vUnidad||'@'|| -- 0
                        'Usted, desea vender '||cCtd_in|| ' unidades y su stock '||'@'||--1
                        'En Sistema es de '||nStockLocal_in||'@'||
                        '¿Desea Vender en Negativo?'||'@'||--2
                        'Consulte a su Jefe de Local'|| '@'||--3
                        -- diferencia de productos
                        abs(nStockLocal_in - cCtd_in);--4
       end if;
       ---------------------------------

    end if;

  return vResultado;

  END;

  /* *********************************************************** */
FUNCTION F_PERMITE_VTA_NEGATIVA  RETURN VARCHAR2 AS
 pPermiteVtaNegativa varchar2(10)   ;
begin
  begin
        select llave_tab_gral
        into   pPermiteVtaNegativa
        from   pbl_tab_gral
        where  id_tab_gral = '501';
       exception
         when others then
           pPermiteVtaNegativa := 'N';
       end;
   return pPermiteVtaNegativa;

end;
end;
/

