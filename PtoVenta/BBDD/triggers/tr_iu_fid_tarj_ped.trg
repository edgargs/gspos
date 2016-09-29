create or replace trigger ptoventa.TR_IU_FID_TARJ_PED before INSERT or update  ON fid_tarjeta_pedido
FOR EACH ROW
declare
begin
    if (:OLD.fec_recuperado is NOT null) then
       RAISE_APPLICATION_ERROR(-20160,'Acción Prohibida,El Pedido ya fue Recuperado.');
    end if;
end TR_IU_FID_TARJ_PED ;
/

