create or replace trigger ptoventa.TR_IU_LGT_KARDEX_001
  before UPDATE OR insert  on lgt_kardex
  FOR EACH ROW

when (NEW.COD_MOT_KARDEX = '522')
DECLARE
  -- 29-ABR-15  TCT    Validar la cantidad maxima de insumo a ingresar en kardex
  -- local variables here
   vn_Max_Ajus_Insu NUMBER;
BEGIN
 --- 10.- Carga Valor del Maximo ajuste de insumo
 BEGIN
  SELECT tg.Llave_Tab_Gral
  INTO vn_Max_Ajus_Insu
  FROM pbl_tab_gral tg
  WHERE TG.ID_TAB_GRAL = 699;
 EXCEPTION
  WHEN OTHERS THEN
    -- SI NO SE PUEDE LEER ASIGNAR VALOR ALTO
    vn_Max_Ajus_Insu:= 9999;
 END;

 ---
 IF  (:NEW.CANT_MOV_PROD/:NEW.val_fracc_prod) > vn_Max_Ajus_Insu THEN
  --ROLLBACK;
  RAISE_APPLICATION_ERROR(-20010,'La Cantidad Ingresada Supera el Valor Máximo de Ajuste para Insumos !!!');
 END IF;



end TR_IU_LGT_KARDEX_001;
/

