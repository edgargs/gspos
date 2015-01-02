CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_UTILITY_CONV" AS

  C_C_TIP_DOC_NATURAL VTA_CLI_LOCAL.TIP_DOC_IDENT%TYPE :='01';
  C_C_TIP_DOC_JURIDICO VTA_CLI_LOCAL.TIP_DOC_IDENT%TYPE :='02';
  C_C_ESTADO_ACTIVO VTA_CLI_LOCAL.EST_CLI_LOCAL%TYPE :='A';
  C_C_INDICADOR_NO  VTA_CLI_LOCAL.IND_CLI_JUR%TYPE :='N';

  TYPE FarmaCursor IS REF CURSOR;

    FUNCTION GET_IS_PED_VALIDA_RAC(
                  cCodGrupoCia_in	  IN CHAR,
  		   					cCodLocal_in	  	IN CHAR,
									cNumPedVta_in	  	IN CHAR)
	RETURN CHAR;



	END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_UTILITY_CONV" AS

/* ************************************************************************* */
  FUNCTION GET_IS_PED_VALIDA_RAC(
                  cCodGrupoCia_in	  IN CHAR,
  		   					cCodLocal_in	  	IN CHAR,
									cNumPedVta_in	  	IN CHAR)
	RETURN CHAR is
		v_cResultado	CHAR(1);
    vNeto_in vta_pedido_vta_cab.val_neto_ped_vta%type;
    vCodConv vta_pedido_vta_cab.cod_convenio%type;
    nMontoPagaEmpresa float;
    vIndValidaCredBenef char(1);
		BEGIN

    select c.val_neto_ped_vta,c.cod_convenio
    into   vNeto_in,vCodConv
    from   vta_pedido_vta_cab c
    where  cod_grupo_cia = cCodGrupoCia_in
    and    cod_local  = cCodLocal_in
    and    num_ped_vta = cNumPedVta_in;

    select decode(nvl(m.flg_valida_lincre_benef,0),1,'S','N')
    into   vIndValidaCredBenef
    from   mae_convenio m
    where  cod_convenio = vCodConv;

    if vIndValidaCredBenef = 'S' then
       v_cResultado := 'S';
    else

    --- monto que paga la empresa ---
    --- dubilluz 30.04.2012
    nMontoPagaEmpresa :=   ptoventa_conv_btlmf.BTLMF_FLOAT_OBT_MONTO_CREDITO(
         cCodGrupoCia_in,cCodLocal_in,
         vNeto_in,
         cNumPedVta_in,
         vCodConv);

         if nvl(nMontoPagaEmpresa,0) != 0 then
         -- tiene credito
            v_cResultado := 'S';
         else
             v_cResultado := 'N';
         end if;
    end if;

		   RETURN v_cResultado;
  END;

  /* ************************************************************************* */

  END;
/

