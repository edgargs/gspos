--------------------------------------------------------
--  DDL for Package PTOVENTA_PSICOTROPICOS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_PSICOTROPICOS" AS

    TYPE FarmaCursor IS REF CURSOR;
    
    PROCEDURE VTA_GRABA_PEDIDO(pCodGruCia    IN CHAR,
                              pCodLocal     IN CHAR,
                              pNumPedVta    IN CHAR,
                              pDni          IN VARCHAR2,
                              pNomPaciente  IN VARCHAR2,
                              pFechaReceta  IN CHAR,
                              pCmp          IN VARCHAR2,
                              pNomMedico    IN VARCHAR2,
                              pIdUsu        IN VARCHAR2
                                      );

    FUNCTION F_GET_VENTA_RESTRINGIDA (pCodGruCia  IN CHAR,
                                      pCodLocal	  IN CHAR,
                                      pNumPedVta  IN CHAR )
    RETURN CHAR;

  
    --Descripcion: Retorna listado de reporte de psicotropicos entre pFecInicial y pFecFinal
    --Fecha        Usuario		    Comentario
    --23/Sep/2013  Luis Leiva     Creación
    FUNCTION REPORTE_PSICOTROPICOS (pCodGruCia_in  IN CHAR,
                                    pCodCia_in     IN CHAR,
                                    pCodLocal_in	 IN CHAR,
                                    pFecInicial_in IN CHAR,
                                    pFecFinal_in   IN CHAR,
                                    pCodProd_in    IN CHAR )
    RETURN FarmaCursor;
    
    --Descripcion: Retorna detalle de registro de psicotropicos seleccionado
    --Fecha        Usuario		    Comentario
    --24/Sep/2013  Luis Leiva     Creación
    FUNCTION DETALLE_PSICOTROPICOS (pSecuencia_in  IN CHAR)
    RETURN FarmaCursor;
    
    --Descripcion: Verifica si el producto es un Psicotropico
    --Fecha        Usuario		    Comentario
    --22/Oct/2013  Luis Leiva     Creación
    FUNCTION VERIFICA_PROD_PSICOTROPICO (pCodProd_in  IN CHAR)
    RETURN VARCHAR2;
END;

/
