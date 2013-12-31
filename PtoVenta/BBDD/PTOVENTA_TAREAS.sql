--------------------------------------------------------
--  DDL for Package PTOVENTA_TAREAS
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_TAREAS" is

  --Descripcion: Graba el stock actual de los productso en el local
  --Fecha       Usuario	  Comentario
  --24/05/2006  ERIOS     Creacion
  PROCEDURE INV_GRABA_STOCK_ACTUAL_PRODS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsu_in IN CHAR);

  --Descripcion: Obtiene el numero de dias maximo de reposicion de un producto en un local.
  --Fecha       Usuario		Comentario
  --03/03/2006  ERIOS     Creacion
  --SE COPIO EL PROCEDIMEINTO DE PTOVENTA_INV.
  FUNCTION INV_GET_STK_TRANS_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR) RETURN NUMBER;

  --Descripcion: Guarda los dias de stock de los productos.
  --Fecha       Usuario	  Comentario
  --27/04/2007  ERIOS     Creacion
  PROCEDURE INV_GRABA_DIAS_STOCK_PRODS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIdUsu_in IN CHAR);

  --Descripcion: Se actualiza el precio venta del producto por convenio
  --Fecha       Usuario	  Comentario
  --27/04/2007  ERIOS     Creacion
  PROCEDURE UPDATE_PREC_VTA_LIST_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

  --Descripcion: Se actualiza la información de la tabla ZAN_LOCAL
  --Fecha       Usuario	  Comentario
  --09/06/2008  ERIOS     Creacion
  PROCEDURE UPDATE_PROD_ZAN_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

  --Descripcion: ACTUALIZACION DE LA TABLA rep_prod_sobrestock
  --Fecha       Usuario	  Comentario
  --16/07/2008  JLUNA     Creacion
  PROCEDURE UPDATE_rep_prod_sobrestock;

  PROCEDURE P_INACTIVA_CAMP_PRIMERA_COMPRA(
                                           cCodGrupoCia_in 	   IN CHAR,
                				    	             cCodLocal_in    	   IN CHAR,
                					                 cNumPedVta_in   	   IN CHAR
                                          );

  PROCEDURE UPDATE_PREC_VTA_LIST_PROD_2(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

    --Descripcion: INSERTA LA TABLA VTA_PED_DCTO_CLI_AUX EN VTA_PED_DCTO_CLI_BKP Y DEJA SOLO LO DE HACER 7 DIAS
  --Fecha       Usuario	  Comentario
  --16/12/2010  ASOSA     Creacion
  PROCEDURE PTOVTA_P_INS_DSCTO_AUX_BKP(cCodGrupoCia_in 	   IN CHAR);

  --jmiranda 12.07.2011
  --ACTUALIZA PRODUCTOS NUEVO CONVENIO LISTA 00019
  PROCEDURE UPDATE_PREC_VTA_LIST_PROD_19(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);
end;

/
