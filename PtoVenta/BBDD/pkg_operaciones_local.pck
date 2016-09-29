CREATE OR REPLACE PACKAGE PTOVENTA."PKG_OPERACIONES_LOCAL" AS

  TYPE FarmaCursor IS REF CURSOR;
  /* *************************************************************** */
  PROCEDURE P_EJECUTA_PEDIDO_LOCAL;
  /* *************************************************************** */

END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PKG_OPERACIONES_LOCAL" AS

/* ************************************************************************** */
  PROCEDURE P_EJECUTA_PEDIDO_LOCAL as
     xcod_local char(3);
     vCantItems   NUMBER:=1;
     vSumSuger    NUMBER:=1;
     cUserIdu_in VARCHAR2(8):='SISTEMAS';

	 CURSOR cur IS select distinct cod_local from vta_impr_local;
  BEGIN

  UPDATE LGT_PROD_LOCAL_REP A
  SET A.CANT_ROT=0
  WHERE A.CANT_ROT IS NULL;
  COMMIT;

  UPDATE LGT_PROD_LOCAL_REP A
  SET A.Val_Frac_Local=1
  WHERE A.Val_Frac_Local=0;
  COMMIT;



  /* ******************************************************************** */
  EXECUTE IMMEDIATE 'ALTER INDEX PTOVENTA.IX_VTA_RES_HIST_PROD_LOCAL REBUILD';
  EXECUTE IMMEDIATE 'ALTER INDEX PTOVENTA.PK_VTA_RES_HIST_ACUM_LOCAL REBUILD';
  DBMS_STATS.gather_table_stats('PTOVENTA','VTA_RES_HIST_PROD_LOCAL',CASCADE => TRUE);

  /* ******************************************************************** */
    --ERIOS 2.4.5 Cambios proyecto Conveniencia
	FOR fila IN cur
	LOOP
		xcod_local := fila.COD_LOCAL;
        Ptoventa_Int_Rep.PROCESA_PED_REP('001', xcod_local, 'SISTEMAS');
        update pbl_local set ind_ped_rep = 'N' where cod_local = xcod_local;
        commit;
        rep_3_cadenas_mifarma.p_opera_algoritmo_mf(xcod_local);
        commit;
        SELECT COUNT(*)
          INTO vCantItems
          FROM lgt_prod_local_rep
         WHERE COD_LOCAL = xcod_local
           AND cod_grupo_cia = '001'
           AND CANT_SUG > 0;
        SELECT SUM(CANT_SUG)
          INTO vSumSuger
          FROM lgt_prod_local_rep
         WHERE COD_LOCAL = xcod_local
           AND cod_grupo_cia = '001'
           AND CANT_SUG > 0;
        --Genera el pedido de reposicion
        PTOVENTA_REP.INV_GENERAR_PED_REP('001',
                                         xcod_local,
                                         vCantItems,
                                         vSumSuger,
                                         cUserIdu_in);
        commit;
	END LOOP;
  END;
/* ************************************************************************** */

END;
/

