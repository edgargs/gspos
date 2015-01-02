--------------------------------------------------------
--  DDL for Package PTOVENTA_MENSAJES
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_MENSAJES" AS

  /**
  * Copyright (c) 2006 MiFarma Peru S.A.
  *
  * Entorno de Desarrollo : Oracle9i
  * Nombre del Paquete    : FARMA_UTILITY
  *
  * Histórico de Creación/Modificación
  * RCASTRO       15.01.2006   Creación
  *
  * @author Rolando Castro
  * @version 1.0
  *
  */

  TYPE FarmaCursor IS REF CURSOR;

  FUNCTION F_VAR2_GET_MENSAJE(cGrupoCia_in  IN CHAR,
                            cCod_Rol_in   IN CHAR )
  RETURN VARCHAR2;
    FUNCTION GET_MSG_FIDEICOMIZO(cCodGrupoCia in char,cCodLocal in char)
    RETURN VARCHAR2;
END PTOVENTA_MENSAJES;

/
