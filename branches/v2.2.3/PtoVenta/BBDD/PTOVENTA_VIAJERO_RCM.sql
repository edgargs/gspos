--------------------------------------------------------
--  DDL for Package PTOVENTA_VIAJERO_RCM
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_VIAJERO_RCM" AS
  
    g_vIdUsu VARCHAR2(15) := 'VIAJERO_RCM';
    
    --Descripcion: Actualiza Unidad de Medida.
    --Fecha       Usuario	Comentario
    --29/05/2013  ERIOS   Creacion
    PROCEDURE VIAJ_ACTUALIZA_UNIDAD_MEDIDA(cCodLocal_in IN CHAR);

    --Descripcion: Actualiza Forma Farmaceutica.
    --Fecha       Usuario	Comentario
    --29/05/2013  ERIOS   Creacion
    PROCEDURE VIAJ_ACTUALIZA_FORMA_FARMAC(cCodLocal_in IN CHAR);
    
    --Descripcion: Actualiza Insuom.
    --Fecha       Usuario	Comentario
    --29/05/2013  ERIOS   Creacion
    PROCEDURE VIAJ_ACTUALIZA_INSUMO(cCodLocal_in IN CHAR);

    --Descripcion: Inserta o Actualiza Conversion unidades recibido
    --Fecha       Usuario	Comentario
    --20/06/2013  LLEIVA  Creacion
    PROCEDURE VIAJ_ACTUALIZA_CONVERSION_UNID(cCodLocal_in IN CHAR);

    --Descripcion: Inserta o Actualiza Orden Preparado recibido
    --Fecha       Usuario	Comentario
    --20/06/2013  LLEIVA  Creacion
    PROCEDURE VIAJ_ACTUALIZA_ORDEN_PREPARADO(cCodLocal_in IN CHAR);
END PTOVENTA_VIAJERO_RCM;

/
