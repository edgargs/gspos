CREATE OR REPLACE TRIGGER PTOVENTA.tgr_cambia_precios
    BEFORE INSERT or UPDATE of val_prec_vta, val_frac_local  ON ptoventa.lgt_prod_local
    FOR EACH ROW
when ( ROUND(new.VAL_PREC_VTA * new.VAL_FRAC_LOCAL,2)<> ROUND(old.VAL_PREC_VTA * old.VAL_FRAC_LOCAL,2))
DECLARE
v_desc_prod     lgt_prod.desc_prod%type;
BEGIN
  select desc_prod
  into  v_desc_prod
  from lgt_prod
  where cod_prod= :new.cod_prod;

 INSERT INTO AUX_CAMBIO_PRECIO NOLOGGGING(COD_GRUPO_CIA,
                               COD_PROD,
                               DESCR_PROD,
                               COD_LOCAL,
                               PRECIOS,
                               IND_PROD_FRACCIONADO,
                               VAL_FRAC_LOCAL,
                               IND_IMPRESORA,
                               FECHA_CAMBIO,
                               CONT_IMPRESION)
values(:new.COD_GRUPO_CIA,
       :new.Cod_Prod,
       v_desc_prod,
       :new.cod_local,
            ROUND(:new.VAL_PREC_VTA * :new.VAL_FRAC_LOCAL,2),
            :new.Ind_Prod_Fraccionado,
            :new.VAL_FRAC_LOCAL,
            'N',
            --TO_CHAR(SYSDATE,'dd/MM/yyyy hh24:mi:ss')
            SYSDATE,
            0);
exception
  when DUP_VAL_ON_INDEX then
    UPDATE AUX_CAMBIO_PRECIO NOLOGGGING
    SET PRECIOS = ROUND(:new.VAL_PREC_VTA * :new.VAL_FRAC_LOCAL,2),
        FECHA_CAMBIO = sysdate
    WHERE COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
    AND COD_PROD = :NEW.COD_PROD
    and PRECIOS = ROUND(:new.VAL_PREC_VTA * :new.VAL_FRAC_LOCAL,2)
    AND COD_LOCAL= :NEW.COD_LOCAL;
end;
/

