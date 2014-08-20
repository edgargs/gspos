--------------------------------------------------------
--  DDL for Package PKG_DOCUMENTO_MF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "BTLPROD"."PKG_DOCUMENTO_MF" is

  type typ_arr_varchar is table of varchar2(100) index by pls_integer;

  cons_cia_mifarma char(3) := '99';
  cons_cia_fasa    char(3) := '98';
  cons_cia_btl     char(3) := '10';

  function sp_graba_tablas_tmp_rac(pcodgrupo_cia_in char,
                                   pcod_local_in    char,
                                   pnum_ped_vta_in  char,
                                   pindnotacred     char) return char;

  function sp_graba_documentos(pcodgrupo_cia_in char,
                               pcod_local_in    char,
                               pnum_ped_vta_in  char,
                               pindnotacred     char default 'N',
							   pIndGrabaPed     CHAR default 'N') return char;
  procedure sp_crea_cajas_liquidaciones(pcodgrupo_cia_in char,
                                        pcod_local_in    char,
                                        pnum_ped_vta_in  char);

  procedure sp_graba_datos_adicionales(pcodgrupo_cia_in char,
                                       pcod_local_in    char,
                                       pnum_ped_vta_in  char,
                                       pcodconvenio     cmr.mae_convenio.cod_convenio%type,
                                       pguiaexiste      char default 'N');

  procedure sp_graba_det_fpago(pcodgrupo_cia_in char,
                               pcod_local_in    char,
                               pcod_local_btl   char,
                               pnum_ped_vta_in  char);
  function sp_anula_documento(pcodtipodoc char,
                              pnumdoc     char,
                              pcodlocal   char default null) return char;
  procedure liberar_transaccion;

  procedure aceptar_transaccion;

  function fn_sec_caja return varchar2;

end;

/
