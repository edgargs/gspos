--------------------------------------------------------
--  DDL for Package PTOVENTA_TOMA_INV_AUX
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_TOMA_INV_AUX" AS

TYPE FarmaCursor IS REF CURSOR;

	COD_MOT_KARDEX_AJUSTE_POS	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '107';
	COD_MOT_KARDEX_AJUSTE_NEG	  LGT_MOT_KARDEX.COD_MOT_KARDEX%TYPE := '009';
	TIP_DOC_KARDEX_TOMA_INV	      LGT_KARDEX.TIP_DOC_KARDEX%TYPE 	 := '03';
	COD_NUMERA_KARDEX             PBL_NUMERA.COD_NUMERA%TYPE 		 := '016';
	COD_NUMERA_TOMA_INV           PBL_NUMERA.COD_NUMERA%TYPE 		 := '013';
  ID_TAB_GRAL                   PBL_TAB_GRAL.ID_TAB_GRAL%TYPE  := '46';
	ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N';
	POS_INICIO		      CHAR(1):='I';

	EST_TOMA_INV_PROCESO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'P';
	EST_TOMA_INV_EMITIDO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'E';
	EST_TOMA_INV_CARGADO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'C';
	EST_TOMA_INV_ANULADO             LGT_TOMA_INV_CAB.EST_TOMA_INV%TYPE := 'N';

  --ESTADOS DE LAB_TOMA
  --07/01/2008  DUBILLUZ  CREACION
	EST_LAB_TOMA_EMITIDO               LGT_TOMA_INV_LAB.EST_TOMA_INV_LAB%TYPE := 'E';
  EST_LAB_TOMA_PROCESO               LGT_TOMA_INV_LAB.EST_TOMA_INV_LAB%TYPE := 'P';
	EST_LAB_TOMA_TERMINADO             LGT_TOMA_INV_LAB.EST_TOMA_INV_LAB%TYPE := 'T';

  --23/01/2008 DUBILLUZ CREACION
  TIPO_ROL_ADMINISTRADOR_LOCAL  CHAR(3):='011';
  TIPO_ROL_VENDEDOR             CHAR(3):='010';
  --LISTA LABORATORIOS


   procedure ti_p_actualiza_indices;

END;

/