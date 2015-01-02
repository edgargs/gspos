--------------------------------------------------------
--  DDL for Package PTOVENTA_VIAJERO_MDIRECTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_VIAJERO_MDIRECTA" AS

  g_vIdUsu VARCHAR2(15) := 'VIAJERO_MDIRECTA';

    --Descripcion: Actualiza Orden Compra.
    --Fecha       Usuario	Comentario
    --16/08/2013  ERIOS   Creacion
    PROCEDURE VIAJ_ACTUALIZA_ORDEN_COMPRA(cCodLocal_in IN CHAR);

    --Descripcion: Actualiza Orden Compra - Detalle.
    --Fecha       Usuario	Comentario
    --16/08/2013  ERIOS   Creacion
    PROCEDURE VIAJ_ACTUALIZA_ORDEN_DET(cCodLocal_in IN CHAR);

END PTOVENTA_VIAJERO_MDIRECTA;

/
