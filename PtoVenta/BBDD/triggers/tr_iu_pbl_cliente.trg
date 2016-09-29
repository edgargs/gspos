CREATE OR REPLACE TRIGGER PTOVENTA.TR_IU_PBL_CLIENTE before INSERT or update  ON PBL_CLIENTE
FOR EACH ROW
declare
   --autor jluna 20081022
   --creado para llenar el codigo de local de origen cuando el registro se ingresa desde el propio local
   vcod_local_origen pbl_cliente.cod_local_origen%type;
begin
    if (:NEW.cod_local_origen is null) then
      select distinct cod_local
      into   vcod_local_origen
      from   ptoventa.vta_impr_local;
      :NEW.cod_local_origen:=vcod_local_origen ;
    end if;
    EXCEPTION
    WHEN OTHERS THEN
         NULL;
end TR_IU_PBL_CLIENTE ;
/

