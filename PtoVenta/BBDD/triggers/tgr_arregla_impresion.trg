CREATE OR REPLACE TRIGGER PTOVENTA.tgr_arregla_impresion
                      before insert on vta_pedido_vta_det
                      for each row

begin
                      :new.sec_grupo_impr:=nvl(:new.sec_grupo_impr,1);
end;
/

