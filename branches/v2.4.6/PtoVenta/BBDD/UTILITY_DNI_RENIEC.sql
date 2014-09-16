--------------------------------------------------------
--  DDL for Package UTILITY_DNI_RENIEC
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."UTILITY_DNI_RENIEC" AS

  TYPE FarmaCursor IS REF CURSOR;

  function GET_EXISTE_DNI(cCodGrupocia_in in varchar2,
                          cCodLocal_in    in varchar2,
                          cDNI_in         in varchar2) return VARCHAR2;


  function GET_POS_DNI_ET(nDNI_in in number,
                          vTabla  in varchar2,
                          i       in integer,
                          j       in integer) return number;

  function getDNIPos(vTabla  in varchar2,pos in number) return number;

  function getDatosDNI(vTabla  in varchar2,pos in number) return VARCHAR2;

  function AUX_EXISTE_DNI_RENIEC(cCodGrupocia_in in varchar2,
                                 cCodLocal_in    in varchar2,
                                 cDNI_in         in varchar2) return VARCHAR2;

  function AUX_DATOS_EXISTE_DNI(cCodGrupocia_in in varchar2,
                                cCodLocal_in    in varchar2,
                                cDNI_in         in varchar2) return VARCHAR2;


END UTILITY_DNI_RENIEC;

/
