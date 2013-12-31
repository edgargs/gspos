--------------------------------------------------------
--  DDL for Package PKG_CAMP_PRECIO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PKG_CAMP_PRECIO" is

  -- Author  : TCANCHES
  -- Created : 04/12/2013 11:16:16 a.m.
  -- Purpose : PROCESAR CAMPAÑAS POR PRECIO
  
  -- Public type declarations
  TYPE FarmaCursor IS REF CURSOR;

  ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
	INDICADOR_NO		  CHAR(1):='N'; 
/****************************************************************************************************************************************/ 
/*--------------------------------------------------------------------------------------------------------------------------------------
GOAL: Devolver el Precio del Producto con La Mejor Campaña por Precio
Ammedmentes:
04-DIC-13   TCT   Create
--------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_MEJOR_PREC_PROD(
                                ac_CodGrupoCia IN CHAR DEFAULT '001',
                                ac_CodProd IN CHAR
                                ) RETURN FarmaCursor;
/****************************************************************************************************************************************/                                 

end PKG_CAMP_PRECIO;

/
