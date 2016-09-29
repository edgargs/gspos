CREATE OR REPLACE TRIGGER PTOVENTA."TR_I_AUX_PBL_ROL_USU" BEFORE INSERT  ON AUX_PBL_ROL_USU
FOR EACH ROW
declare
  -- local variables here
  nCantidad number := 0;
begin

   IF INSERTING  THEN
      IF :NEW.COD_ROL = '011' THEN
      SELECT COUNT(1)
      INTO  nCantidad
      FROM   PBL_USU_LOCAL U
      WHERE  U.COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
      AND    U.COD_LOCAL = :NEW.COD_LOCAL
      AND    U.SEC_USU_LOCAL = :NEW.SEC_USU_LOCAL
      AND    U.COD_TRAB_RRHH NOT IN (SELECT M.COD_TRAB_RRHH
                                     FROM   CE_MAE_TRAB M
--                                     WHERE  M.COD_CARGO IN ('57','53')
--                                     WHERE  M.COD_CARGO = '57' -- SÓLO QF REGENTE
-- JOLIVA 2008-08-22 (permitirá al cargo 74 Y 73 ser administrador de local también)
-- JOLIVA 2009-09-30 (permitirá además al cargo 84  ser administrador de local también)
-- 2014-05-26 JOLIVA: SE AGREGAN LOS CARGOS NUEVOS IDENTIFICADOS PARA PERSONAL DE BTL
-- 2016-04-07 JOLIVA: SE EMPIEZA A USAR EL CAMPO IND_ADM_LOCAL AGREGADO EN EL MAESTRO DE CARGOS
--                                     WHERE  (M.COD_CARGO IN ('29','30','57','74', '73', '84', '115', '116', '204', '205', '213', '262', '270','457')
                                     WHERE  (M.COD_CARGO IN (SELECT COD_CARGO FROM CE_CARGO WHERE IND_ADM_LOCAL = 'S')
                                            OR M.COD_TRAB = '00024') -- VERONICA DEL CARPIO
                                     AND    COD_TRAB_RRHH IS NOT NULL
                                     );


      IF nCantidad > 0  THEN
                        RAISE_APPLICATION_ERROR(-20122, 'ERROR EN VERIFICACION DE ROL ADMINISTRADOR');
                    END IF;
     END IF;
   END IF;

end TR_I_AUX_PBL_ROL_USU;
/

