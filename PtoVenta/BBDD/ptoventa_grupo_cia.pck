CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_GRUPO_CIA" AS

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Busca cliente juridico por RUC o por Razon Social
  --Fecha       Usuario		Comentario
  --16/08/2010  jquispe     Creación
  FUNCTION CIA_GET_COD_GRUPO_CIA
	RETURN CHAR;


  FUNCTION VTA_F_VERIFICAR_PED_COMP(vCodGrupoCia_in IN CHAR,
                                    vNumPedVta_in   IN VARCHAR2,
                                    vCodLocal_in    IN CHAR)
  RETURN CHAR;

  END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_GRUPO_CIA" AS

  FUNCTION CIA_GET_COD_GRUPO_CIA
  RETURN CHAR
  IS
    CONT INTEGER;
    COD_GRUPO_CIA CHAR(3);
  BEGIN

  SELECT COUNT(DISTINCT I.COD_GRUPO_CIA) --,I.COD_LOCAL
  INTO CONT
  FROM  VTA_IMPR_LOCAL I;

      IF CONT = 1 THEN
          SELECT DISTINCT I.COD_GRUPO_CIA
          INTO   COD_GRUPO_CIA
          FROM  VTA_IMPR_LOCAL I;
      ELSE
           COD_GRUPO_CIA := '001';  --ENVIO POR DEFECTO EL VALOR COD GRUPO CIA = 001
      END IF;

  RETURN COD_GRUPO_CIA;
  END;


  FUNCTION VTA_F_VERIFICAR_PED_COMP(vCodGrupoCia_in IN CHAR,
                                    vNumPedVta_in   IN VARCHAR2,
                                    vCodLocal_in    IN CHAR)
  RETURN CHAR
  IS
  --CursVtas FarmaCursor;
  vFlag number;
  vRpta char(1);
  BEGIN
 --VERIFICO EL TIPO DE COMPROBANTE
  select count(1) into vFlag
  from
  (
      (select v.tip_comp_pago
       from vta_comp_pago v
       where  v.cod_grupo_cia =  vCodGrupoCia_in and
              v.cod_local     =  vCodLocal_in    and
              v.num_ped_vta   =  vNumPedVta_in
             -- and
             -- v.num_comp_pago != v.sec_comp_pago
      ) minus
      (
       select tc.tip_comp
       from   vta_tip_comp tc
       where  tc.cod_grupo_cia = vCodGrupoCia_in
      )

  );



  /*OPEN CursVtas FOR
  SELECT  FROM VTA_COMP_PAGO C WHERE C.COD_GRUPO_CIA=vCodGrupoCia_in
                     AND C.COD_LOCAL=vCodLocal_in
                     AND C.NUM_PED_VTA = vNumPedVta_in;


  SELECT  FROM VTA_COMP_PAGO C WHERE C.COD_GRUPO_CIA=vCodGrupoCia_in
                     AND C.COD_LOCAL=vCodLocal_in
                     AND C.NUM_PED_VTA = vNumPedVta_in;


  */
  --vFlag :=1;

  if vFlag >0 then
     vRpta := 'N';
  else
    --VERIFICO SI SE ACTUALIZO EL NUM COMPROBANTE
     select count(1) into vFlag
     from
     vta_comp_pago v
     where  v.cod_grupo_cia =  vCodGrupoCia_in and
              v.cod_local     =  vCodLocal_in  and
              v.num_ped_vta   =  vNumPedVta_in and
              v.num_comp_pago = v.sec_comp_pago;

     if vFlag >0 then
        vRpta := 'N';
     else
        vRpta := 'S';
     end if;
  end if;

  return vRpta;

  END;


  END;
/

