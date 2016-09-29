CREATE OR REPLACE TRIGGER PTOVENTA.tgr_valida_beneficiario
    BEFORE INSERT ON ptoventa.vta_pedido_vta_cab
    FOR EACH ROW
when ( not NEW.COD_CONVENIO IS NULL )
DECLARE
v_flg_beneficiarios   mae_convenio.flg_beneficiarios%type;
v_cod_convenio        mae_convenio.cod_convenio%type;
BEGIN
     if :new.cod_convenio='0000000019' then
        :new.cod_convenio:='0000000843';
     end if;
  begin
        select nvl(flg_beneficiarios,0)
        into v_flg_beneficiarios
        from mae_convenio
        where cod_convenio= trim(:new.cod_convenio);
              EXCEPTION
        WHEN OTHERS THEN
            raise_application_error(-20000,'El codigo de convenio no existe *'||:new.cod_convenio||'*');
   END;


        v_cod_convenio:= :new.COD_CONVENIO;
        IF :NEW.cod_cli_conv='0000000000' THEN
            IF v_flg_beneficiarios='1' THEN
              raise_application_error(-20000,'El beneficiario no puede ser el codigo 0000000000');
            ELSE
                 :NEW.cod_cli_conv:= NULL;
            END IF;
        END IF;
end;
/

