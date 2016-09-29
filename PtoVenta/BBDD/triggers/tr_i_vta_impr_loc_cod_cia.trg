CREATE OR REPLACE TRIGGER PTOVENTA.TR_I_VTA_IMPR_LOC_COD_CIA BEFORE insert ON VTA_IMPR_LOCAL
FOR EACH ROW
DECLARE
C_COD_CIA char(3);
begin
        select
                cod_cia into C_COD_CIA
        from
                pbl_local
        where   cod_grupo_cia= :new.cod_grupo_cia
                and COD_LOCAL= :new.COD_LOCAL;
        :NEW.COD_CIA := C_COD_CIA;
end TR_I_VTA_IMPR_LOC_COD_CIA;
/

