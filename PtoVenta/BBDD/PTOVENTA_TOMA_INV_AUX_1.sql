--------------------------------------------------------
--  DDL for Package Body PTOVENTA_TOMA_INV_AUX
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_TOMA_INV_AUX" AS

   /* ******************************************************************* */
   procedure ti_p_actualiza_indices is
     cursor cr1 is
       select a.index_name,
              'alter index PTOVENTA.' || A.index_name ||
              ' REBUILD TABLESPACE TS_PTOVENTA_IDX storage (initial 64K minextents 1 maxextents unlimited)' comando
         from all_indexes a
        where a.owner = 'PTOVENTA'
          and a.table_name in
              ('LGT_TOMA_INV_CAB', 'LGT_TOMA_INV_LAB',
               'LGT_TOMA_INV_LAB_PROD', 'AUX_LGT_PROD_TOMA_CONTEO',
               'LGT_TOMA_COD_BARRA_NO_FOUND');
     x cr1%rowtype;
   begin
     open cr1;
     loop
       fetch cr1
         into x;
       exit when cr1%notfound;
       execute immediate x.comando;
     end loop;
     close cr1;
   end;

END;

/
