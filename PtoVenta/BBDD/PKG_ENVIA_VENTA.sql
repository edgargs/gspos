--------------------------------------------------------
--  DDL for Package PKG_ENVIA_VENTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PKG_ENVIA_VENTA" is

  -- Author  : TCANCHES
  -- Created : 05/02/2014 01:36:38 p.m.
  -- Purpose : Cargar las Ventas de Local  a Matriz

  TYPE TYP_VTA_PEDIDO_VTA_CAB IS TABLE OF VTA_PEDIDO_VTA_CAB%ROWTYPE;
  TYPE TYP_VTA_PEDIDO_VTA_DET IS TABLE OF VTA_PEDIDO_VTA_DET%ROWTYPE;
  TYPE TYP_VTA_COMP_PAGO IS TABLE OF VTA_COMP_PAGO%ROWTYPE;
  TYPE TYP_FID_TARJETA_PEDIDO IS TABLE OF FID_TARJETA_PEDIDO%ROWTYPE;

/******************************************************************************************************************************************/
/*-----------------------------------------------------------------------------------------------------------------------------------------
GOAL : Generar Temporales de Ventas
-----------------------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_GENE_TEMP_VTA(
                            ac_Fec_Ini IN CHAR DEFAULT to_char(SYSDATE-1,'dd/MM/yyyy'),
                            ac_Fec_Fin IN CHAR DEFAULT to_char(SYSDATE-1,'dd/MM/yyyy'),
                            ac_Cod_Local IN CHAR DEFAULT NULL
                           );
/*-------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_VTA_PEDIDO_VTA_CAB(
                                   ac_Fec_Ini IN CHAR,
                                   ac_Fec_Fin IN CHAR,
                                   ac_Cod_Local IN CHAR
                                   )
RETURN TYP_VTA_PEDIDO_VTA_CAB;

/******************************************************************************************************************************************/

end PKG_ENVIA_VENTA;

/
