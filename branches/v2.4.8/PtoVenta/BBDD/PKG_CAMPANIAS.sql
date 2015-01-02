--------------------------------------------------------
--  DDL for Package PKG_CAMPANIAS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PKG_CAMPANIAS" is

  -- Author  : TCANCHES
  -- Created : 11/04/2014 04:07:54 p.m.
  -- Purpose : MANEJO DE CAMPAÑAS
  
  /*-- Public type declarations
  type <TypeName> is <Datatype>;
  
  -- Public constant declarations
  <ConstantName> constant <Datatype> := <Value>;

  -- Public variable declarations
  <VariableName> <Datatype>;

  -- Public function and procedure declarations
  function <FunctionName>(<Parameter> <Datatype>) return <Datatype>;*/
/****************************************************************************************************************************************/
/*--------------------------------------------------------------------------------------------------------------------------------------------
GOAL : Devolver las Campañas Activas asociadas a un Producto
History : 10-ABR-14      Create
----------------------------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_CAMPA_X_PROD(ac_Cod_Prod IN CHAR) RETURN SYS_REFCURSOR;
/****************************************************************************************************************************************/

end PKG_CAMPANIAS;

/
