--------------------------------------------------------
--  DDL for Package PTOVENTA_VIAJERO
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_VIAJERO" AS

    TYPE FarmaCursor IS REF CURSOR;

  COD_NUMERA_SEC_KARDEX PBL_NUMERA.COD_NUMERA%TYPE := '016';
  g_vCodMotKardexFraccion LGT_KARDEX.COD_MOT_KARDEX%TYPE := '100';
  g_vTipDocKardexFraccion LGT_KARDEX.TIP_DOC_KARDEX%TYPE := '00';
  g_vNumDocKardexFraccion LGT_KARDEX.NUM_TIP_DOC%TYPE := '0000000000';

  v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';

  v_gCantRegistrosActualizados NUMBER(7);
  v_gCantProdLocal NUMBER(7);
  v_gCantProd NUMBER(7);

  g_vIdUsu VARCHAR(30);
  g_dFechaInicio DATE;

  g_cCodCia PBL_CIA.COD_CIA%TYPE := '001';

  --Descripcion: Procesa los datos enviados de Adm Central.
  --Fecha       Usuario		Comentario
  --28/03/2006  ERIOS    	Creación
  --22/06/2006  ERIOS           Modificación: vIdUsu_in
  --27/06/2006  ERIOS     Modificación: LGT_GRUPO_QS, LGT_COD_BARRA
  PROCEDURE VIAJ_PROCESAR_VIAJERO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cIndDelIvery_in IN CHAR DEFAULT NULL,vIdUsu_in IN VARCHAR2 DEFAULT 'VIAJERO');

  --Descripcion: Procesa los proveedores.
  --Fecha       Usuario		Comentario
  --28/03/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_PROV(cCodLocal_in IN CHAR);

  --Descripcion: Procesa los laboratorios.
  --Fecha       Usuario		Comentario
  --28/03/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_LAB(cCodLocal_in IN CHAR);

  --Descripcion: Procesa los productos.
  --Fecha       Usuario		Comentario
  --28/03/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIndDelIvery_in IN CHAR DEFAULT NULL);

  --Descripcion: Procesa los precios y fraccionamiento.
  --Fecha       Usuario		Comentario
  --28/03/2006  ERIOS    	Creación
  --04/07/2006  ERIOS    	Modificación
  PROCEDURE VIAJ_ACTUALIZA_PROD_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cIndDelIvery_in IN CHAR DEFAULT NULL);

  --Descripcion: Genera Kardex por Cambio de Fraccion.
  --Fecha       Usuario		Comentario
  --02/05/2006  ERIOS    	Creación
  PROCEDURE INV_GRABAR_KARDEX_FRACCION(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        cCodProd_in		   IN CHAR,
                                        nStkAnteriorProd_in  IN NUMBER,
                                        nStkFinalProd_in  IN NUMBER,
                                        nValFrac_in		   IN NUMBER,
                                        cDescUnidVta_in   IN CHAR,
                                        cUsuCreaKardex_in	   IN CHAR
                                        );

  --Descripcion: Agrega producto local
  --Fecha       Usuario		Comentario
  --19/05/2006  ERIOS		Creación
  PROCEDURE AGREGA_PROD_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cCodProd_in IN CHAR,nValPrecVtaVig_in IN NUMBER,vDescUnid_in IN VARCHAR2);

  --Descripcion: Agrega Log
  --Fecha       Usuario		Comentario
  --19/05/2006  ERIOS		Creación
  PROCEDURE GRABA_LOG_VIAJERO(cCodLocal_in IN CHAR,cCodProd_in IN CHAR,vIdProceso_in IN VARCHAR2,vMensaje_in IN VARCHAR2);

  --Descripcion: Envia correo al generar error.
  --Fecha       Usuario		Comentario
  --16/05/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ENVIA_CORREO_ALERTA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

  --Descripcion: Realiza Backup de la tabla LGT_PROD_LOCAL para verificar cambios.
  --Fecha       Usuario		Comentario
  --14/06/2006  ERIOS    	Creación
  PROCEDURE VIAJ_BCK_PROD_LOCAL;

  --Descripcion: Envia informacion de los camios realizados en el local.
  --Fecha       Usuario		Comentario
  --14/06/2006  ERIOS    	Creación
  --04/07/2006  ERIOS    	Modificación: Se agregó la tabla Cambio Fraccion
  PROCEDURE VIAJ_ENVIA_CORREO_CAMBIOS(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

   FUNCTION VIAJ_LISTAR_PRECIOS_CAMBIADOS(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor;

  FUNCTION V_LIST_PREC_CAMB_X_FECHA_PROD(cCodGrupoCia_in IN CHAR,
                                                    cCodLocal_in	  	IN CHAR,
                                                    cFechaInicio      IN CHAR,
                                                    cFechaFin         IN CHAR,
                                                    cDescProd         IN CHAR)
  RETURN FarmaCursor;

  FUNCTION V_LIST_PREC_CAMB_FALTANTE(cCodGrupoCia_in IN CHAR, cCodLocal_in	  	IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Activa el producto si tiene stock.
  --Fecha       Usuario		Comentario
  --16/06/2006  ERIOS    	Creación
  PROCEDURE ACTIVAR_PRODUCTO(cCodGrupoCia_in IN CHAR,cCodProd_in IN CHAR,cIndEstProd_in IN CHAR);

  --Descripcion: Actualizar la talba Grupo_QS.
  --Fecha       Usuario		Comentario
  --27/06/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_GRUPO_QS(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza la tabla Cod_Barra.
  --Fecha       Usuario		Comentario
  --27/06/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_COD_BARRA(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Local
  --Fecha       Usuario		Comentario
  --06/07/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_LOCAL(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Grupo Reposicion Local
  --Fecha       Usuario		Comentario
  --06/07/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_GRUPO_REP_LOCAL(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Accion Terapeutica Producto
  --Fecha       Usuario		Comentario
  --05/07/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_ACC_TERAP_PROD(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Principio Activo Producto
  --Fecha       Usuario		Comentario
  --05/07/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_PRINC_ACT_PROD(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Tipo Cambio
  --Fecha       Usuario		Comentario
  --11/07/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_TIP_CAMBIO(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Tipo Cambio Sap
  --Fecha       Usuario		Comentario
  --12/12/2008  JCORTEZ    	Creación
  PROCEDURE VIAJ_ACTUALIZA_TIP_CAMBIO_SAP(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Maestro de Accion Terapeutica
  --Fecha       Usuario		Comentario
  --31/07/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_ACC_TERAP(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Maestro de Principio Activo
  --Fecha       Usuario		Comentario
  --31/07/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_PRINC_ACT(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Maestro de Cargos
  --Fecha       Usuario		Comentario
  --18/10/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_CARGO(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Maestro de Trabajadores
  --Fecha       Usuario		Comentario
  --18/10/2006  ERIOS    	Creación
  PROCEDURE VIAJ_ACTUALIZA_MAE_TRAB(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Maestro de Cambio Productos
  --Fecha       Usuario		Comentario
  --01/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_CAMBIO_PROD(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Campos Formulario
  --Fecha       Usuario		Comentario
  --15/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_CAMPOS_FORM(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza
  --Fecha       Usuario		Comentario
  --15/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_CON_LISTA(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Maestro de Empresas
  --Fecha       Usuario		Comentario
  --15/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_MAE_EMPRESA(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Maestro de Convenios
  --Fecha       Usuario		Comentario
  --15/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_MAE_CONV(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Campos por Convenios
  --Fecha       Usuario		Comentario
  --15/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_CAMPOS_CONV(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Listas por Convenios
  --Fecha       Usuario		Comentario
  --15/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_LISTA_CONV(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Laboratorios por Listas
  --Fecha       Usuario		Comentario
  --15/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_LAB_LISTA(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Productos por Listas
  --Fecha       Usuario		Comentario
  --15/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_PROD_LISTA(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Locales por Convenios
  --Fecha       Usuario		Comentario
  --15/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_LOCAL_CONV(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Maestro de Clientes
  --Fecha       Usuario		Comentario
  --15/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_MAE_CLIENTE(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Clientes por Convenios
  --Fecha       Usuario		Comentario
  --15/05/2007  ERIOS    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_CLI_CONV(cCodLocal_in IN CHAR);

  --Descripcion: Actualiza Codigos de Proveedores de Sap
  --Fecha       Usuario		Comentario
  --10/09/2007  pameghino    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_MAE_PROV_SAP(cCodLocal_in IN CHAR)  ;

  --Descripcion: Actualiza el maestro de servicios
  --Fecha       Usuario		Comentario
  --10/09/2007  pameghino    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_MAE_SERVICIO(cCodLocal_in IN CHAR)  ;

  --Descripcion: Actualiza el maestro de PROVEEDORES
  --Fecha       Usuario		Comentario
  --10/09/2007  pameghino    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_MAE_PROVEEDORES(cCodLocal_in IN CHAR)  ;

  --Descripcion: Actualiza la relacion de servicion con proveedor
  --Fecha       Usuario		Comentario
  --10/09/2007  pameghino    	Creacion
  PROCEDURE VIAJ_ACTUALIZA_REL_PROV_SERV(cCodLocal_in IN CHAR)  ;

  --Descripcion: Envia correo de tipo de cambios
  --Fecha       Usuario		Comentario
  --19/12/2013  ERIOS    	Creacion
  PROCEDURE ENVIA_CORREO_TIPOCAMBIO(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR);
END;

/
