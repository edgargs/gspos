CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CE_SEGURIDAD" AS

  TYPE FarmaCursor IS REF CURSOR;
  COD_NUM_SEC_SOBRE PBL_NUMERA.COD_NUMERA%TYPE := '062';

  C_INICIO_MSG_1 VARCHAR2(1000) := '<html><head><style type="text/css">' ||
                                   '.titulo {font-size: 10;font-family:sans-serif;font-style: italic;}' ||
                                   '.cajero {font-size: 20;font-family: Arial, Helvetica, sans-serif;border-style: solid;} ' ||
                                   '.histcab {font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.historico{font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.msgfinal {font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.tip{font-size: 10;font-family: Arial, Helvetica, sans-serif;}' ||
                                   '.fila{border-style: solid;}' ||
                                   '</style>' || '</head>' || '<body>' ||
                                   '<table width="200" border="0">' ||
                                   '<tr>' || '<td>&nbsp;&nbsp;</td>' ||
                                   '<td>' ||
                                   '<table width="300"  border="1" cellspacing="0" cellpadding="5">';

  C_INICIO_MSG_2 VARCHAR2(20000) := ' <html>' ||
                                    ' <head>' ||
                                    ' <style type="text/css">' ||
                                    ' .style3 {font-family: Arial, Helvetica, sans-serif}' ||
                                    ' .style8 {font-size: 24; }' ||
                                    ' .style9 {font-size: larger}' ||
                                    ' .style12 {' ||
                                    ' font-family: Arial, Helvetica, sans-serif;' ||
                                    ' font-size: larger;' ||
                                    ' font-weight: bold;' ||
                                    ' }' ||
                                    ' </style>' ||
                                    ' </head>' ||
                                    ' <body>' ||
                                    ' <table width="510"border="0">' ||
                                    ' <tr>' ||
                                    ' <td width="487" align="center" valign="top"><h1>REMITO</h1></td>' ||
                                    ' </tr>' ||
                                    ' </table>' ||
                                    ' <table width="504" border="1">' ||
                                    ' <tr>' ||
                                    ' <td height="43" colspan="3"><h2>Deposito En Soles (CTA. CTE. N&deg;)</h2></td>' ||
                                    ' <td colspan="3"><h2>Deposito En Dolares (CTA. CTE. N&deg;)</h2> </td>' ||
                                    ' </tr>' ||
                                    ' <tr>' ||
                                    ' <td width="78" height="61"><strong>FECHA</strong></td>' ||
                                    ' <td width="50"><strong>N&deg; SOBRES </strong></td>' ||
                                    ' <td width="110"><strong>MONTO  S/.</strong></td>' ||
                                    ' <td width="78"><strong>FECHA</strong></td>' ||
                                    ' <td width="50"><strong>N&deg; SOBRES</strong></td>' ||
                                    ' <td width="110"><strong>MONTO  US$</strong></td>' ||
                                    ' </tr>';

  C_LINEA VARCHAR2(20000) := '<tr>' ||
                                ' <td height="25">&nbsp;</td>' ||
                                ' <td>&nbsp;</td>' ||
                                ' <td>&nbsp;</td>' ||
                                ' <td>&nbsp;</td>' ||
                                ' <td>&nbsp;</td>' ||
                                ' <td>&nbsp;</td>' ||
                               '</tr>';

  C_MSG_MED VARCHAR2(2000) := '<td></td>
                                 </tr>
                                 <tr>
                                 <td></td>
                                 </tr>
                                </tr>
                                </table>
                                <table width="570" height="450" border="0">
                                  <tr>
                                    <td width="4" >&nbsp;</td>
                                    <td width="4" >&nbsp;</td>
                                    <td>
                                ';

  C_FIN_MSG VARCHAR2(2000) := '</td>' ||
                                  '</tr>' ||
                                  '</table>' ||
                                  '</body>' ||
                                  '</html>';

  COD_NUM_SEC_SOBRE PBL_NUMERA.COD_NUMERA%TYPE := '062';

  ACC_INGRESO  CHAR(1) := 'I';
  ACC_MODIFICA CHAR(1) := 'M';
  ACC_ELIMINA  CHAR(1) := 'E';
  ACC_APRUEBA  CHAR(1) := 'A';

  ESTADO_PENDIENTE CE_SOBRE.ESTADO%TYPE := 'P';
  ESTADO_APROBADO  CE_SOBRE.ESTADO%TYPE := 'A';
  ESTADO_INACTIVO  CE_SOBRE.ESTADO%TYPE := 'I';


  COD_MONEDA_SOLES CHAR(1):= 'S';
  DES_MONEDA_SOLES CHAR(8):= 'SOLES';
  COD_MONEDA_DOLARES CHAR(1):= 'D';
  DES_MONEDA_DOLARES CHAR(8):= 'DOLARES';
  COD_EFECTIVO_SOLES CHAR(5):='00001';
  COD_EFECTIVO_DOLARES CHAR(5):='00002';


  FUNCTION SEG_F_CHAR_IND_SEGUR_LOCAL(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCodFormPago_in IN CHAR) RETURN CHAR;

  PROCEDURE SEG_P_VALIDA_SOBRE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodFormPago_in IN CHAR,
                               cSecCaja_in     IN CHAR);

  PROCEDURE SEG_P_INSERT_SOBRE(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in     IN CHAR,
                               cSecCaja_in      IN CHAR,
                               cSecFormaPago_in IN CHAR,
                               cIdUsuario_in    IN CHAR,
                               cCodSobre_in     IN CHAR);

  PROCEDURE SEG_P_ELIMINA_SOBRE(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cCodSobre_in    IN CHAR,
                                cCodUser        IN CHAR);

  FUNCTION SEG_F_VAR_IMP_HTML_SOBRES(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cMovCaja_in     IN CHAR,
                                     cCodSobre_in    IN CHAR) return varchar2;

  FUNCTION SEG_F_CUR_GET_SOBRES(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cMovCaja_in     IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CUR_REMITOS(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             FechaIni        IN CHAR,
                             FechaFin        IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CUR_FEC_REMITO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cCodRemito      IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CUR_DIA_SIN_REMITO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
    RETURN FarmaCursor;

  PROCEDURE SEG_P_AGREGA_REMITO(cCodGrupoCia_in IN CHAR,
                                cCodLocal       IN CHAR,
                                cIdUsu_in       IN CHAR,
                                cNumRemito      IN CHAR,
                                cFecha          IN CHAR);

  FUNCTION SEG_F_CUR_GET_SOBRE_FECHA(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cFecha          IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CUR_SOBRE_FECHA(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cFecha          IN CHAR) RETURN FarmaCursor;

 FUNCTION SEG_F_CUR_SOBRE_FECHA_DET(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cFecha          IN CHAR) RETURN FarmaCursor;

 FUNCTION SEG_F_CUR_SOBRE_REMITO(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cCodigoRemito   IN CHAR)

 RETURN FarmaCursor;

  FUNCTION SEG_F_VAR2_EXISTE_REMITO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumRemito      IN CHAR) RETURN VARCHAR2;

  FUNCTION SEG_F_VAR2_IMP_DATOS_VOUCHER(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodRemito      IN CHAR,
                                        cIpServ_in      IN CHAR)
    RETURN VARCHAR2;

  FUNCTION SEG_F_CUR_GET_DATA4(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CUR_GET_DATA3(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor;

  FUNCTION SEG_F_CHAR_IND_PROSEGUR(
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR
                                  ) RETURN CHAR;
  --OBTENER INDICADOR DE SI PUEDE O NO MODIFICAR EL LA FORMA DE DECLARAR EN CIERRE DE CAJA
  --JCALLO 02/02/2008
  FUNCTION SEG_F_CHAR_IND_CHCB_SOBRE
  RETURN CHAR;

  --OBTENER CANTIDAD DE INTENTOS DE MODIFICACION
  --JCALLO 02/02/2008
  FUNCTION SEG_F_CHAR_CANT_MOD_SOBRE
  RETURN CHAR;

  FUNCTION SEG_F_CHAR_SOBRES_ELI(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cFechaCierreDia IN CHAR,
                                 cSecMovCaja     IN CHAR)
  RETURN CHAR;

  --ENVIAR CORREO DE ALERTA
  --JCALLO 02/02/2008
  PROCEDURE SEG_P_ENVIAR_CORREO_ALERTA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in          IN CHAR,
                                 cFechaCierreDia       IN CHAR,
                                 cSecMovCaja           IN CHAR,
                                 cSecUsuLocal_in       IN CHAR,
                                 cTipMensaje_in        IN CHAR,
                                 cCodSobre_in          IN CHAR);

   /*********************************CAMBIO DE INGRESO DE SOBRES*************************************************************/

  --Descripcion: Se valida la asociacion del sobre con un remito
  --Fecha       Usuario		 Comentario
  --30/03/2010  JCORTEZ   Creación
   FUNCTION SEG_F_VALIDA_SOBRE_REMITO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cFechaCierreDia IN CHAR,
                                      cSecMovCaja     IN CHAR,
                                      cCodSobre       IN CHAR)
   RETURN CHAR;

   --Descripcion: Se listan los sobres para el nuevo remito
   --Fecha       Usuario		 Comentario
   --08/04/2010  JCORTEZ   Creación
   FUNCTION SEG_F_CUR_GET_SOBRES_APROBAR(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cFecIni_in      IN CHAR,
                                         cFecFin_in      IN CHAR)
   RETURN FarmaCursor;

    --Descripcion: Se valida el estado del sobre
    --Fecha       Usuario		 Comentario
    --08/04/2010  JCORTEZ   Creación
    PROCEDURE  SEG_P_VALIDA_EST_SOBRE(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cFecVta_in      IN CHAR,
                                      cCodSobre_in    IN CHAR);

    --Descripcion: Se aprueba el sobre seleccionado
    --Fecha       Usuario		 Comentario
    --08/04/2010  JCORTEZ   Creación
    PROCEDURE SEG_P_VALIDA_APRUEBA_SOBRE(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cIdUsu_in         IN CHAR,
                                         cFecVta_in      IN CHAR,
                                         cCodSobre_in    IN CHAR,
                                         cSecUsuQF  IN CHAR);

    --Descripcion: Se valida el rol de usuario que debe aprobar los sobres.
    --Fecha       Usuario		 Comentario
    --08/04/2010  JCORTEZ   Creación
   FUNCTION SEG_F_VERIFICA_ROL_USU(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   vSecUsu_in       IN CHAR,
                                   cCodRol_in       IN CHAR)
    RETURN CHAR;

    --Descripcion: Si el indicador de concepto de sobres esta en 'S' y la forma de pago puede ponerse en sobres entonces devuelve 'S' sino 'N'
    --Fecha       Usuario		 Comentario
    --31/05/2010  ASOSA   Creación
    FUNCTION SEG_F_CHAR_IND_SOBRES(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cCodFormPago_in IN CHAR)
    RETURN CHAR;



  FUNCTION SEG_F_ACCION_SOBRE_TMP(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cSec_in         IN CHAR,
                               cSecMovCaja_in  IN CHAR,
                               cIdUsu_in       IN CHAR,
                               cCodSobre_in    IN CHAR,

                               cCodFormaPago_in    IN CHAR,
                               cTipMoneda_in       IN CHAR,
                               cMonEntrega_in      IN NUMBER,
                               cMonEntregaTotal_in IN NUMBER,

                               cTipoAccion_in  IN CHAR)
  RETURN CHAR;

  FUNCTION SEG_F_BLOQUEO_ESTADO(cCodGrupoCia_in  IN CHAR,
                                cCodLocal_in     IN CHAR,
                                cSec_in          IN CHAR,
                                cSecMovCaja_in   IN CHAR,
                                cCodSobre_in     IN CHAR,
                                cTipoSobre_in    IN CHAR)
  RETURN CHAR;

  FUNCTION CAJ_F_OBTIENE_SECSOBRE(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in   IN CHAR,
                                        cSecMovCaja_in  IN CHAR,
                                        cFecDiaVta_in   IN DATE)
  RETURN NUMBER;

  --Descripcion: Graba el comprobante de un pedido
  --Fecha       Usuario		Comentario
  --11/02/2011  RHERRERA   Modificacion para obtener el sec mov caja
 FUNCTION CAJ_F_OBTIENE_SEC_MOV_CAJA(cCodGrupoCia_in IN CHAR,
                                 cCod_Local_in   IN CHAR,
                                 cSecUsuLocal_in  IN CHAR)
 return char;

 FUNCTION  SEG_F_GET_TIPO_CAMBIO(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cSecUsuLocal_in  IN CHAR)
 RETURN NUMBER;

   function SEG_F_CUR_DET_EFECTIVO_TURNO(cCodGrupoCia_in in char,
                                      cCodLocal_in in char,
                                      cCodTurno_in in char,
                                      cCodCaja_in in char,
                                      cFecha_in in char,
                                      cTipoEfectivo_in in char
                                     )
  return FarmaCursor;

  function SEG_F_CUR_DET_EFECTIVO_DIA(cCodGrupoCia_in in char,
                                      cCodLocal_in in char,
                                      cFecha_in in char,
                                      cTipoEfectivo_in in char
                                     )
  return FarmaCursor;

END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CE_SEGURIDAD" AS

  /******************************************************************************/
  FUNCTION SEG_F_CHAR_IND_SEGUR_LOCAL(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCodFormPago_in IN CHAR)
    RETURN char IS
    cRespta               char(1);
    nCantFormPagoEfectivo number;
        indPerm    CHAR(1);
    montoPerm  VARCHAR2(10);
  BEGIN


    begin
      SELECT nvl(L.IND_PROSEGUR, 'N')
        INTO cRespta
        FROM PBL_LOCAL L
       WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
         AND L.COD_LOCAL = cCodLocal_in;
    exception
      when no_data_found then
        cRespta := 'N';
    end;


    SELECT count(1)
      into nCantFormPagoEfectivo
      FROM VTA_FORMA_PAGO F
     WHERE F.IND_TARJ = 'N'
       AND F.IND_FORMA_PAGO_EFECTIVO = 'S'
       AND F.COD_CONVENIO IS NULL
       AND F.IND_FORMA_PAGO_CUADRATURA = 'S'
       AND F.COD_GRUPO_CIA = cCodGrupoCia_in
       AND F.COD_FORMA_PAGO = cCodFormPago_in;

    if cRespta = 'S' then
        if nCantFormPagoEfectivo > 0 then
          cRespta := 'S';
        else
          cRespta := 'N';
        end if;
    end if;

    RETURN cRespta;
  END;

  /******************************************************************************/
  PROCEDURE SEG_P_VALIDA_SOBRE(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodFormPago_in IN CHAR,
                               cSecCaja_in     IN CHAR) IS

    nDiaRemito   number;
    dFechaDiaVta date;
  BEGIN

    IF (SEG_F_CHAR_IND_SEGUR_LOCAL(cCodGrupoCia_in,
                                  cCodLocal_in,
                                  cCodFormPago_in) = 'N' AND
       SEG_F_CHAR_IND_SOBRES(cCodGrupoCia_in,
                             cCodLocal_in,
                             cCodFormPago_in) = 'N')
                                  THEN
      RAISE_APPLICATION_ERROR(-20001, 'No puede agregar un Sobre.');
    END IF;

    select c.fec_dia_vta
      into dFechaDiaVta
      from ce_mov_caja c
     where c.cod_grupo_cia = cCodGrupoCia_in
       and c.cod_local = cCodLocal_in
       and c.sec_mov_caja = cSecCaja_in;
    /*
    SELECT count(1)
      into nDiaRemito
      FROM CE_DIA_REMITO D
     WHERE D.COD_GRUPO_CIA = cCodGrupoCia_in
       AND D.COD_LOCAL = cCodLocal_in
       AND D.COD_REMITO IS NOT NULL
       AND D.FEC_DIA_VTA = dFechaDiaVta;

    IF nDiaRemito > 0 THEN
      RAISE_APPLICATION_ERROR(-20002,
                              'No Puede agregar sobres si el dia ya se asocio a un Remito.');
    END IF;
    */

  END;

  /******************************************************************************/
  PROCEDURE SEG_P_INSERT_SOBRE(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in     IN CHAR,
                               cSecCaja_in      IN CHAR,
                               cSecFormaPago_in IN CHAR,
                               cIdUsuario_in    IN CHAR,
                               cCodSobre_in     IN CHAR) IS
    nSecSobre     number;
    vCodigoSobre  varchar2(20);
    cFecDiaVta_in date;

    cSecuencialFormaPago number;
    nExiteRegistro       number;
    SecMovCaja CE_MOV_CAJA.SEC_MOV_CAJA%TYPE;

    cCodFormPago_in CHAR(5);

    cant1  NUMBER;
    cant2  NUMBER;
     cEstadoCreado VARCHAR2(1);

     cantidad NUMBER(3);

     indSobres pbl_tab_gral.llave_tab_gral%TYPE; --ASOSA, 26.07.2010
     indPROSEGUR pbl_local.ind_prosegur%TYPE; --ASOSA, 26.07.2010

  BEGIN


    SELECT F.COD_FORMA_PAGO
      INTO cCodFormPago_in
      FROM CE_FORMA_PAGO_ENTREGA F
     WHERE F.COD_GRUPO_CIA = cCodGrupoCia_in
       AND F.COD_LOCAL = cCodLocal_in
       AND F.SEC_MOV_CAJA = cSecCaja_in
       AND F.SEC_FORMA_PAGO_ENTREGA = cSecFormaPago_in;

    SEG_P_VALIDA_SOBRE(cCodGrupoCia_in,
                       cCodLocal_in,
                       cCodFormPago_in,
                       cSecCaja_in);

    select to_number(cSecFormaPago_in, '99999')
      into cSecuencialFormaPago
      from dual;

    select m.fec_dia_vta
      into cFecDiaVta_in
      from ce_mov_caja m
     where m.cod_grupo_cia = cCodGrupoCia_in
       and m.cod_local = cCodLocal_in
       and m.sec_mov_caja = cSecCaja_in;

    --nSecSobre := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, COD_NUM_SEC_SOBRE);

   /* select to_number(nvl(max(Substr(s.cod_sobre,10)),'0'),'999')+ 1
    into nSecSobre
    from ce_sobre s
    where s.cod_grupo_cia = cCodGrupoCia_in
       and s.cod_local = cCodLocal_in
       and s.fec_dia_vta = cFecDiaVta_in;*/

    nSecSobre:=PTOVENTA_CAJ.CAJ_F_OBTIENE_SECSOBRE(cCodGrupoCia_in,cCodLocal_in,cSecCaja_in,cFecDiaVta_in);

    vCodigoSobre :=  --cCodLocal_in ||'-'||
     to_char(cFecDiaVta_in, 'ddmmyyyy') || '-' ||
                    Farma_Utility.COMPLETAR_CON_SIMBOLO(nSecSobre,
                                                        3,
                                                        0,
                                                        'I');
   SELECT COUNT(*) INTO cantidad --INI ASOSA, 11.06.2010, validar que exista antes de hacer cualquier insert
   FROM ce_sobre x
   WHERE x.cod_grupo_cia=cCodGrupoCia_in
   AND x.cod_local=cCodLocal_in
   AND x.cod_sobre=vCodigoSobre
   AND x.estado IN ('P','A'); --FIN ASOSA, 11.06.2010
   --RAISE_APPLICATION_ERROR(-20033,'cCodLocal_in:'||cCodLocal_in||':d:'||vCodigoSobre||':d:');
   IF cantidad = 0 THEN

    select count(1)
      into nExiteRegistro
      from CE_DIA_REMITO
     where FEC_DIA_VTA = cFecDiaVta_in
       and COD_GRUPO_CIA = cCodGrupoCia_in
       and COD_LOCAL = cCodLocal_in;

    if nExiteRegistro = 0 then

      INSERT INTO CE_DIA_REMITO
        (FEC_DIA_VTA,
         COD_GRUPO_CIA,
         COD_LOCAL,
         ESTADO,
         USU_CREA_DIA_REMITO)
      VALUES
        (cFecDiaVta_in, cCodGrupoCia_in, cCodLocal_in, 'A', cIdUsuario_in);
    end if;

     DBMS_OUTPUT.put_line('cCodSobre_in: '||cCodSobre_in);
     IF(LENGTH(TRIM(cCodSobre_in))>0)THEN

           DBMS_OUTPUT.put_line('INSERT SELECT');
      --JCORTEZ 04.11.09 inserta los sobres ya creados
      INSERT INTO CE_SOBRE (COD_SOBRE,COD_GRUPO_CIA,COD_LOCAL,FEC_DIA_VTA,SEC_MOV_CAJA
      ,SEC_FORMA_PAGO_ENTREGA,ESTADO,USU_CREA_SOBRE,USU_MOD_SOBRE,FEC_MOD_SOBRE,
      COD_FORMA_PAGO,SEC_USU_QF,cod_remito,IND_ETV -- DUBILLUZ 27.07.2010
      )
      SELECT A.COD_SOBRE,A.COD_GRUPO_CIA,A.COD_LOCAL,A.FEC_DIA_VTA,cSecCaja_in,cSecuencialFormaPago,
             A.ESTADO,cIdUsuario_in,NULL,NULL--JCORTEZ 29.03.2010 se creara con estado que este
             ,COD_FORMA_PAGO,SEC_USU_QF,a.cod_remito, A.Ind_Etv -- DUBILLUZ 27.07.2010
      FROM CE_SOBRE_TMP A
      WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
      AND A.COD_LOCAL=cCodLocal_in
      AND A.COD_SOBRE=cCodSobre_in
      AND A.FEC_DIA_VTA=cFecDiaVta_in;



      ELSE

      --ASOSA, 02.06.2010
      /*SELECT TRIM(NVL(A.DESC_LARGA,'A')) INTO cEstadoCreado
       FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL=317;*/
       cEstadoCreado:='P'; --ASOSA, 10.06.2010

       SELECT tt.llave_tab_gral INTO indSobres
       FROM pbl_tab_gral tt
       WHERE tt.id_tab_gral='317';

       SELECT ff.ind_prosegur INTO indPROSEGUR
       FROM pbl_local ff
       WHERE ff.cod_grupo_cia=cCodGrupoCia_in
       AND ff.cod_local=cCodLocal_in;

       IF indPROSEGUR='S' AND indSobres='N' THEN
          cEstadoCreado:='A';
       END IF;


       --cEstadoCreado := 'A'; --ASOSA, 09.08.2010, en conclusion si exusten sobres todos deben de crearse APROBADOs segun lo indicado
       --vuelta a modificar, ASOSA, 11.08.2010

        DBMS_OUTPUT.put_line('INSERT INTO');
        INSERT INTO CE_SOBRE
          (COD_SOBRE,
           COD_GRUPO_CIA,
           COD_LOCAL,
           SEC_MOV_CAJA,
           SEC_FORMA_PAGO_ENTREGA,
           FEC_DIA_VTA,
           ESTADO,
           USU_CREA_SOBRE,COD_FORMA_PAGO, IND_ETV)
        VALUES
          (vCodigoSobre,
           cCodGrupoCia_in,
           cCodLocal_in,
           cSecCaja_in,
           cSecuencialFormaPago,
           cFecDiaVta_in,
           cEstadoCreado,--JCORTEZ 29.03.2010 se creara con estado C
           cIdUsuario_in,cCodFormPago_in, indPROSEGUR);

       END IF;

   /* Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               COD_NUM_SEC_SOBRE,
                                               cIdUsuario_in);*/
     ELSE
         RAISE_APPLICATION_ERROR(-20024,'El sobre ya esta registrado'); --ASOSA, 11.06.2010
     END IF;
   END;
  /******************************************************************************/
  PROCEDURE SEG_P_ELIMINA_SOBRE(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cCodSobre_in    IN CHAR,
                                cCodUser        IN CHAR) IS
    dFecDia    date;
    cantSobre  number;
    cCodRemito VARCHAR2(20);
    cCantSobreTemp NUMBER;
    cCodFormPago_in CHAR(5);
    cSecCaja_in     CHAR(10);
    cSecCaja_inTmp     CHAR(10);
    SecMovCaja      CHAR(10);
    fecDiaVta      DATE;
  BEGIN

    SELECT COUNT(*) INTO cCantSobreTemp
    FROM CE_SOBRE_TMP E
    WHERE E.COD_GRUPO_CIA=cCodGrupoCia_in
    AND E.COD_LOCAL=cCodLocal_in
    AND E.COD_SOBRE=cCodSobre_in;

    IF(cCantSobreTemp>0) THEN

       --actualizando el estado de sobre temp a INACTIVO
       --INICIO

        SELECT E.SEC_MOV_CAJA,TO_CHAR(E.FEC_DIA_VTA,'DD/MM/YYYY') INTO cSecCaja_inTmp,fecDiaVta
        FROM CE_SOBRE_TMP E
        WHERE E.COD_GRUPO_CIA=cCodGrupoCia_in
        AND E.COD_LOCAL=cCodLocal_in
        AND E.COD_SOBRE=cCodSobre_in;

        UPDATE CE_SOBRE_TMP A
        SET A.ESTADO = 'I',
               A.FEC_MOD_SOBRE = SYSDATE,
               A.USU_MOD_SOBRE = cCodUser
         WHERE A.COD_SOBRE = trim(cCodSobre_in)
           AND A.COD_GRUPO_CIA = cCodGrupoCia_in
           AND A.COD_LOCAL = cCodLocal_in
           AND A.SEC_MOV_CAJA=cSecCaja_inTmp
           AND A.FEC_DIA_VTA=fecDiaVta;
       --FIN
       --RHERRERA : 18.09.201 Inactiva sobre si o si
       UPDATE CE_SOBRE S
       SET S.ESTADO = 'I', S.FEC_MOD_SOBRE = SYSDATE,
           S.USU_MOD_SOBRE = cCodUser
           WHERE S.COD_SOBRE = cCodSobre_in
             AND S.COD_GRUPO_CIA = cCodGrupoCia_in
             AND S.COD_LOCAL = cCodLocal_in;


    ELSE

          SELECT F.Cod_Forma_Pago, f.sec_mov_caja
            INTO cCodFormPago_in, cSecCaja_in
            FROM CE_SOBRE S, CE_FORMA_PAGO_ENTREGA F
           WHERE S.COD_SOBRE = cCodSobre_in
             AND S.COD_GRUPO_CIA = cCodGrupoCia_in
             AND S.COD_LOCAL = cCodLocal_in
             AND S.COD_GRUPO_CIA = F.COD_GRUPO_CIA
             AND S.COD_LOCAL = F.COD_LOCAL
             --AND S.ESTADO IN ('A','C')--JCORTEZ 30.03.2010 Se consideraran creados y aprobados
             AND S.ESTADO IN ('A','P')--ASOSA, 15.06.2010
             AND S.SEC_MOV_CAJA = F.SEC_MOV_CAJA
             AND S.SEC_FORMA_PAGO_ENTREGA = F.SEC_FORMA_PAGO_ENTREGA;

          SEG_P_VALIDA_SOBRE(cCodGrupoCia_in,
                             cCodLocal_in,
                             cCodFormPago_in,
                             cSecCaja_in);

          SELECT S.FEC_DIA_VTA
            INTO dFecDia
            FROM CE_SOBRE S
           WHERE S.COD_SOBRE = cCodSobre_in
             AND S.COD_GRUPO_CIA = cCodGrupoCia_in
             AND S.COD_LOCAL = cCodLocal_in
            --AND S.ESTADO IN ('A','C');--JCORTEZ 30.03.2010 Se consideraran creados y aprobados
            AND S.ESTADO IN ('A','P');--ASOSA, 15.06.2010

          SELECT D.COD_REMITO
            INTO cCodRemito
            FROM ce_dia_remito d
           WHERE D.FEC_DIA_VTA = dFecDia
             AND D.COD_GRUPO_CIA = cCodGrupoCia_in
             AND D.COD_LOCAL = cCodLocal_in;

          IF cCodRemito IS NOT NULL THEN
            RAISE_APPLICATION_ERROR(-20004,'chispoteada de diego.');
          END IF;

          --actualizando el estado de sobre a INACTIVO
          UPDATE CE_SOBRE S SET S.ESTADO = 'I', S.FEC_MOD_SOBRE = SYSDATE, S.USU_MOD_SOBRE = cCodUser
           WHERE S.COD_SOBRE = cCodSobre_in
             AND S.COD_GRUPO_CIA = cCodGrupoCia_in
             AND S.COD_LOCAL = cCodLocal_in;


         -- JCORTEZ 04.11.09 actualizando el estado de sobre temp a INACTIVO
         --INICIO
         SELECT A.SEC_MOV_CAJA_ORIGEN
                 INTO SecMovCaja
          FROM CE_MOV_CAJA A
          WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
          AND A.COD_LOCAL=cCodLocal_in
          AND A.SEC_MOV_CAJA=trim(cSecCaja_in);--obtenermos apertura del turno

          UPDATE CE_SOBRE_TMP A
          SET    A.ESTADO = 'I',
                 A.FEC_MOD_SOBRE = SYSDATE,
                 A.USU_MOD_SOBRE = cCodUser
           WHERE A.COD_SOBRE = trim(cCodSobre_in)
             AND A.COD_GRUPO_CIA = cCodGrupoCia_in
             AND A.COD_LOCAL = cCodLocal_in
             AND A.SEC_MOV_CAJA=SecMovCaja;
         --FIN

          --cantidad de sobres activos
          SELECT COUNT(1)
            INTO cantSobre
            FROM CE_SOBRE S
           WHERE S.FEC_DIA_VTA = dFecDia
             AND S.COD_GRUPO_CIA = cCodGrupoCia_in
      --       AND S.ESTADO = 'A'
             AND S.COD_LOCAL = cCodLocal_in;
          --si no hay ningun sobre activo se elimina fisicamente
          --el sobre dia correspondiente, aunque creo que solo se deberia de cambiar de estado tb
          if cantSobre = 0 then
            DELETE CE_DIA_REMITO S
             WHERE S.FEC_DIA_VTA = dFecDia
               AND S.COD_GRUPO_CIA = cCodGrupoCia_in
               AND S.COD_LOCAL = cCodLocal_in;
          end if;

    IF cCodRemito IS NOT NULL THEN
      RAISE_APPLICATION_ERROR(-20004,'Error al eliminar sobres');
    END IF;

    --actualizando el estado de sobre a INACTIVO
    UPDATE CE_SOBRE S SET S.ESTADO = 'I', S.FEC_MOD_SOBRE = SYSDATE, S.USU_MOD_SOBRE = cCodUser
     WHERE S.COD_SOBRE = cCodSobre_in
       AND S.COD_GRUPO_CIA = cCodGrupoCia_in
       AND S.COD_LOCAL = cCodLocal_in;


   -- JCORTEZ 04.11.09 actualizando el estado de sobre temp a INACTIVO
   --INICIO
   /*
   SELECT A.SEC_MOV_CAJA
           INTO SecMovCaja
    FROM CE_MOV_CAJA A
    WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.SEC_MOV_CAJA=TO_NUMBER(trim(cSecCaja_in))-1 ;--obtenermos apertura del turno
    */
    UPDATE CE_SOBRE_TMP X SET X.ESTADO = 'I', X.FEC_MOD_SOBRE = SYSDATE, X.USU_MOD_SOBRE = cCodUser
     WHERE X.COD_SOBRE = trim(cCodSobre_in)
       AND X.COD_GRUPO_CIA = cCodGrupoCia_in
       AND X.COD_LOCAL = cCodLocal_in
       AND X.SEC_MOV_CAJA=SecMovCaja;
   --FIN

    --cantidad de sobres activos
    SELECT COUNT(1)
      INTO cantSobre
      FROM CE_SOBRE S
     WHERE S.FEC_DIA_VTA = dFecDia
       AND S.COD_GRUPO_CIA = cCodGrupoCia_in
--       AND S.ESTADO = 'A'
       AND S.COD_LOCAL = cCodLocal_in;
    --si no hay ningun sobre activo se elimina fisicamente
    --el sobre dia correspondiente, aunque creo que solo se deberia de cambiar de estado tb
    if cantSobre = 0 then
      DELETE CE_DIA_REMITO S
       WHERE S.FEC_DIA_VTA = dFecDia
         AND S.COD_GRUPO_CIA = cCodGrupoCia_in
         AND S.COD_LOCAL = cCodLocal_in;
    end if;

   END IF;
  END;
  /******************************************************************************/

  FUNCTION SEG_F_VAR_IMP_HTML_SOBRES(cCodGrupoCia_in IN CHAR,
                                     cCodLocal_in    IN CHAR,
                                     cMovCaja_in     IN CHAR,
                                     cCodSobre_in    IN CHAR) RETURN VARCHAR2 IS
    msgHTMLCab VARCHAR2(4000) := '';
    msgHTMLDet VARCHAR2(4000) := '';
    msgHTMLPie VARCHAR2(4000) := '';
    msgHTML    VARCHAR2(4000) := '';
    vCajero    VARCHAR2(300) := '';
    vTerminalista    VARCHAR2(300) := '';
    msgVbQuimico varchar2(500) := ''; --ASOSA - 08/08/2014

    --vFila_IMG_Cabecera_MF varchar2(800):= '';

    vSecUsuLocal PBL_USU_LOCAL.SEC_USU_LOCAL%TYPE;
    dFechaVenta  CE_MOV_CAJA.FEC_DIA_VTA%TYPE;

    vDescLocal varchar2(100) := '';
    ------------------------------------

    CURSOR curSobres IS
      SELECT S.COD_SOBRE,
             DECODE(FPE.TIP_MONEDA, '01', 'SOLES', 'DOLARES') AS MONEDA,
             TRIM(to_char(FPE.MON_ENTREGA, '999999999.00')) AS MONTO
        FROM CE_SOBRE S, CE_FORMA_PAGO_ENTREGA FPE
       WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
         AND S.COD_LOCAL = cCodLocal_in
         AND S.SEC_MOV_CAJA = cMovCaja_in
         AND S.COD_SOBRE = cCodSobre_in
         AND S.ESTADO in ('A','P')--dubilluz 27.07.2010
         AND FPE.COD_GRUPO_CIA = S.COD_GRUPO_CIA
         AND FPE.COD_LOCAL = S.COD_LOCAL
         AND FPE.SEC_MOV_CAJA = S.SEC_MOV_CAJA
         AND FPE.SEC_FORMA_PAGO_ENTREGA = S.SEC_FORMA_PAGO_ENTREGA;
    rSobre curSobres%ROWTYPE;

  BEGIN

    SELECT C.SEC_USU_LOCAL, C.FEC_DIA_VTA
      INTO vSecUsuLocal, dFechaVenta
      FROM CE_MOV_CAJA C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCodLocal_in
       AND C.SEC_MOV_CAJA = cMovCaja_in
       AND C.TIP_MOV_CAJA = 'C';

    SELECT trim(UL.COD_TRAB_RRHH) , trim(UL.NOM_USU) || ' ' ||
           trim(UL.APE_PAT) || ' ' || trim(UL.APE_MAT)
      into vTerminalista , vCajero
      FROM PBL_USU_LOCAL UL
     WHERE UL.COD_GRUPO_CIA = cCodGrupoCia_in
       AND UL.COD_LOCAL = cCodLocal_in
       AND UL.SEC_USU_LOCAL = vSecUsuLocal;

    SELECT COD_LOCAL || '-' || DESC_CORTA_LOCAL
      into vDescLocal
      FROM PBL_LOCAL
     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
       AND COD_LOCAL = cCodLocal_in;

    /*       vFila_IMG_Cabecera_MF:= '<tr> <td>'||
    '<img src=file://///'||
    cIpServ_in || CA_F_VAR_RUTA_IMG_CABECERA_2||
    ' width="300" height="90"></td>'||
    '</tr> ';*/
    --obteniedo la cabecera del mensaje
    msgHTMLCab := C_INICIO_MSG_1 ||
                  '<tr><td align="center" colspan="1" class="cajero">SOBRE DECLARADO</td></tr> ' ||
                  '<tr><td colspan="1" class="cajero"> Local: '||vDescLocal||'</td></tr>'||
                  '<tr><td colspan="1" class="cajero"> Dia: '||trim(TO_CHAR(dFechaVenta, 'dd/mm/yyyy')) || '</td></tr>' ||
                  '<tr><td colspan="1" class="cajero"> Terminalista: ' || TRIM(vTerminalista) ||'</td></tr>'||
                  '<tr><td colspan="1" class="cajero"> Cajero: ' || TRIM(vCajero) ||'</td></tr>';-- ||
--                  '<tr><td colspan="1" class="cajero"> Nro SOBRE: '||</td><td  class="cajero">MONEDA</td><td  class="cajero">MONTO</td></tr>';

    FOR rSobre IN curSobres LOOP
--      DBMS_OUTPUT.put_line('html:' || msgHTML);
                                        --rherrera: ingresar codigo del local en el número de sobre, informativo.
      msgHTMLDet := msgHTMLDet || '<tr><td class="cajero">Nro Sobre: '||trim(cCodLocal_in)||'-'||trim(rSobre.COD_SOBRE) ||'</td></tr>'
                    || '<tr><td class="cajero">Moneda: ' || trim(rSobre.MONEDA) || '</td></tr>'
                    || '<tr><td class="cajero">Monto : ' || trim(rSobre.MONTO) || '</td></tr>';
    END LOOP;

    --fin del html
    msgHTMLPie := '</table></td></tr></table>';

     msgVbQuimico :=  '<table  border="0" cellspacing="0" cellpadding="5">' ||--ASOSA - 08/08/2014
                                           '<tr><td class="cajero">V°B° Jefe Local:________________</td></tr></table>' ||
                                           '</html>';

    msgHTML := trim(msgHTMLCab) || trim(msgHTMLDet) || trim(msgHTMLPie) || trim(msgVbQuimico);

    return msgHTML;
  END;

  /******************************************************************************/
  FUNCTION SEG_F_CUR_GET_SOBRES(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cMovCaja_in     IN CHAR) RETURN FarmaCursor IS
    curSobresCajero FarmaCursor;
  begin

    OPEN curSobresCajero FOR
      SELECT S.COD_SOBRE
        FROM CE_SOBRE S, CE_FORMA_PAGO_ENTREGA FPE
       WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
         AND S.COD_LOCAL = cCodLocal_in
         AND S.SEC_MOV_CAJA = cMovCaja_in
         --AND S.ESTADO in ('P','A') -- dubilluz 27.07.2010
         AND S.ESTADO in ('P') -- ASOSA 04.08.2010
         AND FPE.COD_GRUPO_CIA = S.COD_GRUPO_CIA
         AND FPE.COD_LOCAL = S.COD_LOCAL
         AND FPE.SEC_MOV_CAJA = S.SEC_MOV_CAJA
         AND FPE.SEC_FORMA_PAGO_ENTREGA = S.SEC_FORMA_PAGO_ENTREGA
         AND S.COD_SOBRE NOT IN (
                                 SELECT T.COD_SOBRE
                                 FROM   ce_sobre_tmp t
                                 where  t.sec_mov_caja = (
                                                         SELECT M.SEC_MOV_CAJA_ORIGEN
                                                         FROM   CE_MOV_CAJA M
                                                         WHERE  M.COD_GRUPO_CIA = cCodGrupoCia_in
                                                         AND    M.COD_LOCAL     = cCodLocal_in
                                                         AND    M.SEC_MOV_CAJA  = cMovCaja_in
                                                         )
                                 AND    T.IND_IMP = 'S'
                                );

    return curSobresCajero;
  end;
  /* ********************************************************************************************** */
  FUNCTION SEG_F_CUR_REMITOS(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             FechaIni        IN CHAR,
                             FechaFin        IN CHAR) RETURN FarmaCursor IS
    curDet FarmaCursor;
  BEGIN

    IF (LENGTH(NVL(FechaIni, 0)) < 2 AND LENGTH(NVL(FechaFin, 0)) < 2) THEN
      OPEN curDet FOR
        SELECT --TO_CHAR(MIN (FECHA),'DD/MM/YYYY HH:MM:SS')|| 'Ã' ||
        distinct (TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS')) || 'Ã' || CODIGO || 'Ã' || USU || 'Ã' ||
                 TO_CHAR(SUM(SOLES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(DOLARES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(TOTAL), '999,999,990.00') || 'Ã' || SUM(CANT) || 'Ã' ||
                 RANGO || 'Ã' || TO_CHAR(FECHA, 'YYYYMMDDHHMMSS')
          FROM (SELECT V1.FECHA,
                       V1.CODIGO,
                       V1.USU,
                       V1.SOLES,
                       V1.DOLARES,
                       V1.TOTAL,
                       V1.CANT,
                       V1.RANGO
                  FROM (SELECT b.fec_crea_remito FECHA,
                               NVL(A.COD_REMITO, ' ') CODIGO,
                               B.USU_CREA_REMITO USU,
                               NVL(SUM(CASE
                                         WHEN D.TIP_MONEDA = '01' THEN
                                          D.MON_ENTREGA_TOTAL
                                       END),
                                   0) SOLES,
                               NVL(SUM(CASE
                                         WHEN D.TIP_MONEDA = '02' THEN
                                          D.MON_ENTREGA
                                       END),
                                   0) DOLARES,
                               NVL(SUM(D.MON_ENTREGA_TOTAL), 0) TOTAL,
                               SUM(CASE
                                     WHEN C.COD_SOBRE IS NOT NULL THEN
                                      1
                                   END) CANT,
                               TO_CHAR(SYSDATE - 30, 'DD/MM/YYYY') || '' ||
                               TO_CHAR(SYSDATE, 'DD/MM/YYYY') RANGO
                          FROM CE_DIA_REMITO         A,
                               CE_REMITO             B,
                               CE_SOBRE              C,
                               CE_FORMA_PAGO_ENTREGA D
                         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                           AND A.COD_LOCAL = cCodLocal_in
                           AND b.fec_crea_remito -- BETWEEN SYSDATE-30 AND SYSDATE
                               BETWEEN
                               TO_DATE(to_char(trunc(SYSDATE - 30),
                                               'dd/mm/yyyy') || ' 00:00:00',
                                       'dd/MM/yyyy HH24:mi:ss') AND
                               TO_DATE(to_char(trunc(SYSDATE), 'dd/mm/yyyy') ||
                                       ' 23:59:59',
                                       'dd/MM/yyyy HH24:mi:ss')
                                  and c.estado = 'A'
                           AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                           AND A.COD_LOCAL = B.COD_LOCAL
                           AND A.COD_REMITO = B.COD_REMITO
                           AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                           AND TRUNC(A.FEC_DIA_VTA) = TRUNC(C.FEC_DIA_VTA)
                           AND A.COD_LOCAL = C.COD_LOCAL
                           AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                           AND C.COD_LOCAL = D.COD_LOCAL
                           AND C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                           AND C.SEC_FORMA_PAGO_ENTREGA =
                               D.SEC_FORMA_PAGO_ENTREGA --
                         GROUP BY b.fec_crea_remito,
                                  A.COD_REMITO,
                                  B.USU_CREA_REMITO) V1)
        --GROUP BY CODIGO,USU,RANGO;
         GROUP BY TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS'),
                  CODIGO,
                  USU,
                  RANGO,
                  TO_CHAR(FECHA, 'YYYYMMDDHHMMSS');
    ELSE
      OPEN curDet FOR
        SELECT --TO_CHAR(MIN (FECHA),'DD/MM/YYYY HH:MM:SS')|| 'Ã' ||
        distinct (TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS')) || 'Ã' || CODIGO || 'Ã' || USU || 'Ã' ||
                 TO_CHAR(SUM(SOLES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(DOLARES), '999,999,990.00') || 'Ã' ||
                 TO_CHAR(SUM(TOTAL), '999,999,990.00') || 'Ã' || SUM(CANT) || 'Ã' ||
                 RANGO || 'Ã' || TO_CHAR(FECHA, 'YYYYMMDDHHMMSS')
          FROM (SELECT V1.FECHA,
                       V1.CODIGO,
                       V1.USU,
                       V1.SOLES,
                       V1.DOLARES,
                       V1.TOTAL,
                       V1.CANT,
                       V1.RANGO
                  FROM (SELECT b.fec_crea_remito FECHA,
                               NVL(A.COD_REMITO, ' ') CODIGO,
                               B.USU_CREA_REMITO USU,
                               NVL(SUM(CASE
                                         WHEN D.TIP_MONEDA = '01' THEN
                                          D.MON_ENTREGA_TOTAL
                                       END),
                                   0) SOLES,
                               NVL(SUM(CASE
                                         WHEN D.TIP_MONEDA = '02' THEN
                                          D.MON_ENTREGA
                                       END),
                                   0) DOLARES,
                               NVL(SUM(D.MON_ENTREGA_TOTAL), 0) TOTAL,
                               SUM(CASE
                                     WHEN C.COD_SOBRE IS NOT NULL THEN
                                      1
                                   END) CANT,
                               TO_CHAR(SYSDATE - 30, 'DD/MM/YYYY') || '' ||
                               TO_CHAR(SYSDATE, 'DD/MM/YYYY') RANGO
                          FROM CE_DIA_REMITO         A,
                               CE_REMITO             B,
                               CE_SOBRE              C,
                               CE_FORMA_PAGO_ENTREGA D
                         WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                           AND A.COD_LOCAL = cCodLocal_in
                           AND b.fec_crea_remito BETWEEN
                               TO_DATE(FechaIni || ' 00:00:00',
                                       'dd/MM/yyyy HH24:mi:ss') AND
                               TO_DATE(FechaFin || ' 23:59:59',
                                       'dd/MM/yyyy HH24:mi:ss')
                                              and c.estado = 'A'
                           AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                           AND A.COD_LOCAL = B.COD_LOCAL
                           AND A.COD_REMITO = B.COD_REMITO
                           AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                           AND TRUNC(A.FEC_DIA_VTA) = TRUNC(C.FEC_DIA_VTA)
                           AND A.COD_LOCAL = C.COD_LOCAL
                           AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                           AND C.COD_LOCAL = D.COD_LOCAL
                           AND C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                           AND C.SEC_FORMA_PAGO_ENTREGA =
                               D.SEC_FORMA_PAGO_ENTREGA --
                         GROUP BY b.fec_crea_remito,
                                  A.COD_REMITO,
                                  B.USU_CREA_REMITO) V1)
        -- GROUP BY CODIGO,USU,RANGO;
         GROUP BY TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS'),
                  CODIGO,
                  USU,
                  RANGO,
                  TO_CHAR(FECHA, 'YYYYMMDDHHMMSS');
    END IF;

    RETURN curDet;
  END;

  /* ********************************************************************************************** */
  FUNCTION SEG_F_CUR_FEC_REMITO(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cCodRemito      IN CHAR) RETURN FarmaCursor IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
      SELECT V2.FECHA || 'Ã' || V2.CANT_S || 'Ã' ||
             TO_CHAR(V2.SOLES, '999,999,990.00') || 'Ã' || V2.CANT_D || 'Ã' ||
             TO_CHAR(V2.DOLARES, '999,999,990.00') || 'Ã' || V2.CANT
        FROM (SELECT TO_CHAR(A.FEC_DIA_VTA, 'DD/MM/YYYY') FECHA,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '01' THEN
                                1
                             END),
                         0) CANT_S,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '01' THEN
                                C.MON_ENTREGA_TOTAL
                             END),
                         0) SOLES,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '02' THEN
                                1
                             END),
                         0) CANT_D,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '02' THEN
                                C.MON_ENTREGA
                             END),
                         0) DOLARES,
                     V1.CANT CANT
                FROM CE_DIA_REMITO A,
                     CE_SOBRE B,
                     CE_FORMA_PAGO_ENTREGA C,
                     (SELECT COUNT(*) CANT,
                             TRUNC(X.FEC_DIA_VTA) FEC,
                             X.COD_GRUPO_CIA,
                             X.COD_LOCAL
                        FROM CE_SOBRE X
                        where  x.estado = 'A'
                       GROUP BY TRUNC(X.FEC_DIA_VTA),
                                X.COD_GRUPO_CIA,
                                X.COD_LOCAL) V1,
                     CE_CIERRE_DIA_VENTA Y
                     --VTA_FORMA_PAGO Z
               WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND A.COD_LOCAL = cCodLocal_in
                 AND A.COD_REMITO = TRIM(cCodRemito) --
                 AND Y.IND_VB_CIERRE_DIA = 'S'
                        and b.estado = 'A'
                 AND A.COD_REMITO IS NOT NULL
                 AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                 AND A.COD_LOCAL = B.COD_LOCAL
                 AND A.FEC_DIA_VTA = B.FEC_DIA_VTA
                 AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND B.COD_LOCAL = C.COD_LOCAL
                 AND B.SEC_MOV_CAJA = C.SEC_MOV_CAJA
                 AND B.SEC_FORMA_PAGO_ENTREGA = C.SEC_FORMA_PAGO_ENTREGA --
                 AND TRUNC(A.FEC_DIA_VTA) = V1.FEC
                 AND A.COD_GRUPO_CIA = V1.COD_GRUPO_CIA
                 AND A.COD_LOCAL = V1.COD_LOCAL
                 AND A.COD_GRUPO_CIA = Y.COD_GRUPO_CIA
                 AND A.COD_LOCAL = Y.COD_LOCAL
                 AND A.FEC_DIA_VTA = Y.FEC_CIERRE_DIA_VTA --
                -- AND C.COD_GRUPO_CIA = Z.COD_GRUPO_CIA
                -- AND C.COD_FORMA_PAGO = Z.COD_FORMA_PAGO
               GROUP BY TO_CHAR(A.FEC_DIA_VTA, 'DD/MM/YYYY'), V1.CANT) V2;
    RETURN curDet;
  END;

  /* ********************************************************************************************** */
  FUNCTION SEG_F_CUR_DIA_SIN_REMITO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR)
    RETURN FarmaCursor IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
      SELECT TO_CHAR(V2.FECHA, 'DD/MM/YYYY') || 'Ã' ||  V2.CANT_S || 'Ã' ||
             TO_CHAR(V2.SOLES, '999,999,990.00') || 'Ã' ||V2.CANT_D || 'Ã' ||
             TO_CHAR(V2.DOLARES, '999,999,990.00') || 'Ã' || V2.CANT
        FROM (SELECT A.FEC_DIA_VTA FECHA,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '01' THEN
                                1
                             END),
                         0) CANT_S,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '01' THEN
                                NVL(C.MON_ENTREGA_TOTAL, 0)
                             END),
                         0) SOLES,
                          NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '02' THEN
                                1
                             END),
                         0) CANT_D,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '02' THEN
                                NVL(C.MON_ENTREGA, 0)
                             END),
                         0) DOLARES,
                     V1.CANT CANT
                FROM CE_DIA_REMITO A,
                     CE_SOBRE B,
                     CE_FORMA_PAGO_ENTREGA C,
                     (SELECT COUNT(*) CANT,
                             TRUNC(X.FEC_DIA_VTA) FEC,
                             X.COD_GRUPO_CIA,
                             X.COD_LOCAL
                        FROM CE_SOBRE X
                        where  x.estado = 'A'
                       GROUP BY TRUNC(X.FEC_DIA_VTA),
                                X.COD_GRUPO_CIA,
                                X.COD_LOCAL) V1,
                     CE_CIERRE_DIA_VENTA Y
               WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND A.COD_LOCAL = cCodLocal_in
                 AND A.COD_REMITO IS NULL
                 AND Y.IND_VB_CIERRE_DIA = 'S'
                        and b.estado = 'A'
                 AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                 AND A.COD_LOCAL = B.COD_LOCAL
                 AND A.FEC_DIA_VTA = TRUNC(B.FEC_DIA_VTA)
                 AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND B.COD_LOCAL = C.COD_LOCAL
                 AND B.SEC_MOV_CAJA = C.SEC_MOV_CAJA
                 AND B.SEC_FORMA_PAGO_ENTREGA = C.SEC_FORMA_PAGO_ENTREGA --
                 AND A.FEC_DIA_VTA = V1.FEC
                 AND A.COD_GRUPO_CIA = V1.COD_GRUPO_CIA
                 AND A.COD_LOCAL = V1.COD_LOCAL
                 AND A.COD_GRUPO_CIA = Y.COD_GRUPO_CIA
                 AND A.COD_LOCAL = Y.COD_LOCAL
                 AND A.FEC_DIA_VTA = Y.FEC_CIERRE_DIA_VTA --
               GROUP BY A.FEC_DIA_VTA, V1.CANT
               ORDER BY 1 DESC) V2;
    RETURN curDet;
  END;

  /**********************************************************************************/
  PROCEDURE SEG_P_AGREGA_REMITO(cCodGrupoCia_in IN CHAR,
                                cCodLocal       IN CHAR,
                                cIdUsu_in       IN CHAR,
                                cNumRemito      IN CHAR,
                                cFecha          IN CHAR)

   IS
    v_nCant  NUMBER;
    v_nCant2 NUMBER;

  BEGIN

    SELECT COUNT(*)
      INTO v_nCant
      FROM CE_DIA_REMITO A
     WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
       AND A.COD_LOCAL = cCodLocal
       AND TRUNC(A.FEC_DIA_VTA) = TRIM(cFecha);

    SELECT COUNT(*)
      INTO v_nCant2
      FROM CE_REMITO C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCodLocal
       AND TRIM(C.COD_REMITO) = TRIM(cNumRemito);

    IF (v_nCant2 = 0) THEN
      INSERT INTO CE_REMITO
        (COD_REMITO,
         COD_GRUPO_CIA,
         COD_LOCAL,
         USU_CREA_REMITO,
         USU_MOD_REMITO,
         FEC_MOD_REMITO,
         FEC_PROCESO_ARCHIVO,
         FEC_PROCESO_INT_CE)
      VALUES
        (cNumRemito,
         cCodGrupoCia_in,
         cCodLocal,
         cIdUsu_in,
         NULL,
         NULL,
         NULL,
         NULL);
    END IF;

    IF v_nCant > 0 THEN
      UPDATE CE_DIA_REMITO B
         SET B.COD_REMITO         = cNumRemito,
             B.USU_MOD_DIA_REMITO = cIdUsu_in,
             B.FEC_MOD_DIA_REMITO = SYSDATE
       WHERE B.COD_GRUPO_CIA = cCodGrupoCia_in
         AND B.COD_LOCAL = cCodLocal
         AND TRUNC(B.FEC_DIA_VTA) = TRIM(cFecha)
         AND B.COD_REMITO IS NULL;
    END IF;

  EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
      RAISE_APPLICATION_ERROR(-20002, 'Ya existe asignacion de remito');

  END;

  /* **********************************************************************************************Detallle*/
  FUNCTION SEG_F_CUR_GET_SOBRE_FECHA(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cFecha          IN CHAR) RETURN FarmaCursor IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
      SELECT A.COD_SOBRE || 'Ã' ||
             DECODE(B.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') || 'Ã' ||
             TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                     '999,999,990.00') || 'Ã' ||
             NVL(C.DESC_FORMA_PAGO, ' ')|| 'Ã' ||
             NVL(B.USU_CREA_FORMA_PAGO_ENT,' ')|| 'Ã' ||
             NVL(D.USU_CREA_CIERRE_DIA,' ')
        FROM CE_SOBRE A, CE_FORMA_PAGO_ENTREGA B, VTA_FORMA_PAGO C,CE_CIERRE_DIA_VENTA D
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.FEC_DIA_VTA = to_date(cFecha,'dd/mm/yyyy')
                and a.estado = 'A'
         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.SEC_MOV_CAJA = B.SEC_MOV_CAJA
         AND A.SEC_FORMA_PAGO_ENTREGA = B.SEC_FORMA_PAGO_ENTREGA
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         AND A.COD_GRUPO_CIA=D.COD_GRUPO_CIA
         AND A.FEC_DIA_VTA=TRUNC(D.FEC_CIERRE_DIA_VTA);
    RETURN curDet;
  END;

  /* ********************************************************************************************** Nuevo*/
  FUNCTION SEG_F_CUR_SOBRE_FECHA(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cFecha          IN CHAR) RETURN FarmaCursor IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
      SELECT to_char(E.FEC_DIA_VTA,'dd/mm/yyyy') || 'Ã' ||
             A.COD_SOBRE || 'Ã' ||
             DECODE(B.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') || 'Ã' ||
             TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                     '999,999,990.00') || 'Ã' ||
             NVL(C.DESC_FORMA_PAGO, ' ')|| 'Ã' ||
             NVL(B.USU_CREA_FORMA_PAGO_ENT,' ')|| 'Ã' ||
             NVL(D.USU_CREA_CIERRE_DIA,' ')|| 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                         NVL(CASE WHEN B.TIP_MONEDA = '01' THEN 1 END,0)|| 'Ã' ||
                         NVL(CASE WHEN B.TIP_MONEDA = '02' THEN 1 END,0)
        FROM CE_SOBRE A, CE_FORMA_PAGO_ENTREGA B, VTA_FORMA_PAGO C,CE_CIERRE_DIA_VENTA D,
             CE_DIA_REMITO E
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
          AND E.COD_REMITO IS NULL
                 and a.estado = 'A'
         --AND A.FEC_DIA_VTA = to_date(cFecha,'dd/mm/yyyy')
         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.SEC_MOV_CAJA = B.SEC_MOV_CAJA
         AND A.SEC_FORMA_PAGO_ENTREGA = B.SEC_FORMA_PAGO_ENTREGA
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         AND A.COD_GRUPO_CIA=D.COD_GRUPO_CIA
         AND A.FEC_DIA_VTA=TRUNC(D.FEC_CIERRE_DIA_VTA)
         AND A.COD_GRUPO_CIA=E.COD_GRUPO_CIA
         AND A.COD_LOCAL=E.COD_LOCAL(+)
         AND A.FEC_DIA_VTA=E.FEC_DIA_VTA(+);
    RETURN curDet;
  END;

  /*****************************************************************************************/
  FUNCTION SEG_F_CUR_SOBRE_FECHA_DET(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cFecha          IN CHAR) RETURN FarmaCursor IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
      SELECT A.COD_SOBRE || 'Ã' ||
             DECODE(B.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') || 'Ã' ||
             TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                     '999,999,990.00') || 'Ã' ||
             NVL(C.DESC_FORMA_PAGO, ' ')|| 'Ã' ||
             NVL(B.USU_CREA_FORMA_PAGO_ENT,' ')|| 'Ã' ||
             NVL(D.USU_CREA_CIERRE_DIA,' ')
        FROM CE_SOBRE A, CE_FORMA_PAGO_ENTREGA B, VTA_FORMA_PAGO C,CE_CIERRE_DIA_VENTA D
       WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
         AND A.COD_LOCAL = cCodLocal_in
         AND A.FEC_DIA_VTA = to_date(cFecha,'dd/mm/yyyy')
                and a.estado = 'A'
         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.SEC_MOV_CAJA = B.SEC_MOV_CAJA
         AND A.SEC_FORMA_PAGO_ENTREGA = B.SEC_FORMA_PAGO_ENTREGA
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         AND A.COD_GRUPO_CIA=D.COD_GRUPO_CIA
         AND A.FEC_DIA_VTA=TRUNC(D.FEC_CIERRE_DIA_VTA);
    RETURN curDet;
  END;
  /***************************************************************************************/
 FUNCTION SEG_F_CUR_SOBRE_REMITO(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cCodigoRemito   IN CHAR)
 RETURN FarmaCursor IS
    curDet FarmaCursor;
 BEGIN
    OPEN curDet FOR
     SELECT to_char(A.FEC_DIA_VTA,'dd/mm/yyyy') || 'Ã' ||
             A.COD_SOBRE || 'Ã' ||
             DECODE(B.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') || 'Ã' ||
             TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                     '999,999,990.00') || 'Ã' ||
             NVL(C.DESC_FORMA_PAGO, ' ')|| 'Ã' ||
             NVL(B.USU_CREA_FORMA_PAGO_ENT,' ')|| 'Ã' ||
             NVL(D.USU_CREA_CIERRE_DIA,' ')|| 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                    TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                         '999,999,990.00')|| 'Ã' ||
                         NVL(CASE WHEN B.TIP_MONEDA = '01' THEN 1 END,0)|| 'Ã' ||
                         NVL(CASE WHEN B.TIP_MONEDA = '02' THEN 1 END,0)
        FROM CE_SOBRE A, CE_FORMA_PAGO_ENTREGA B, VTA_FORMA_PAGO C,CE_CIERRE_DIA_VENTA D,
             CE_REMITO R,
             CE_DIA_REMITO DR
       WHERE R.COD_REMITO = cCodigoRemito
         AND R.COD_GRUPO_CIA = cCodGrupoCia_in
         AND R.COD_LOCAL = cCodLocal_in
         AND A.ESTADO = 'A'
         AND DR.COD_GRUPO_CIA = R.COD_GRUPO_CIA
         AND DR.COD_LOCAL     = R.COD_LOCAL
         AND DR.COD_REMITO    = R.COD_REMITO

         AND A.FEC_DIA_VTA   = DR.FEC_DIA_VTA
         AND A.COD_GRUPO_CIA = DR.COD_GRUPO_CIA
         AND A.COD_LOCAL     = DR.COD_LOCAL


         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.SEC_MOV_CAJA = B.SEC_MOV_CAJA
         AND A.SEC_FORMA_PAGO_ENTREGA = B.SEC_FORMA_PAGO_ENTREGA
         AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
         AND B.COD_FORMA_PAGO = C.COD_FORMA_PAGO
         AND A.COD_GRUPO_CIA=D.COD_GRUPO_CIA
         AND A.FEC_DIA_VTA=TRUNC(D.FEC_CIERRE_DIA_VTA);
    RETURN curDet;

  END ;
  /***************************************************************************************/
  FUNCTION SEG_F_VAR2_EXISTE_REMITO(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in    IN CHAR,
                                    cNumRemito      IN CHAR) RETURN VARCHAR2 IS
    v_nCant NUMBER;
    VALOR   CHAR(1);
  BEGIN

    SELECT COUNT(*)
      INTO v_nCant
      FROM CE_REMITO C
     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_LOCAL = cCodLocal_in
       AND TRIM(C.COD_REMITO) = TRIM(cNumRemito);

    IF (v_nCant > 0) THEN
      VALOR := 'S';
    ELSE
      VALOR := 'N';
    END IF;

    RETURN VALOR;
  END;

  /*********************************************************************************/
  FUNCTION SEG_F_VAR2_IMP_DATOS_VOUCHER(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cCodRemito      IN CHAR,
                                        cIpServ_in      IN CHAR)
    RETURN VARCHAR2 IS
    vMsg_out varchar2(32767) := '';
    --vFila_Consejos     varchar2(22767):= '';
    --vFila_1 VARCHAR2(32767):='';
    vFila_2  VARCHAR2(32767) := '';
    vFila_3  VARCHAR2(32767) := '';
    vFila_4  VARCHAR2(32767) := '';
    vFila_41 VARCHAR2(32767) := '';

    vFecha      varchar2(200);
    vNumSobresS varchar2(200);
    vMontoS     varchar2(200);
    vNumSobresD varchar2(200);
    vMontoD     varchar2(200);

    vMontotTotalS varchar2(200);
    vMontotTotalD varchar2(200);
    vUsuRem       varchar2(200);

    i NUMBER(7) := 0;
    --cursor1 FarmaCursor:=VTA_OBTENER_DATA1(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
    --cursor2 FarmaCursor:=VTA_OBTENER_DATA2(cCodGrupoCia_in,cCodLocal_in,cNumPedVta_in);
    cursor3 FarmaCursor := SEG_F_CUR_GET_DATA3(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cCodRemito);
    cursor4 FarmaCursor := SEG_F_CUR_GET_DATA4(cCodGrupoCia_in,
                                               cCodLocal_in,
                                               cCodRemito);

  BEGIN
    LOOP
      FETCH cursor4
        INTO vFecha, vNumSobresS, vMontoS, vNumSobresD, vMontoD;
      EXIT WHEN cursor4%NOTFOUND;
      IF (LENGTH(vFila_4) >= 32767 - 20) THEN
        i := i + 1;
        IF (i = 1) THEN
          vFila_41 := vFila_41 || vFila_4;
        END IF;
        vFila_41 := vFila_41 || ' <tr> ' || ' <td>' || vFecha || '</td> ' ||
                    ' <td>' || vNumSobresS || '</td> ' || ' <td><p>S/' ||
                    vMontoS || '</p></td> ' || ' <td>' || vFecha ||
                    '</td> ' || ' <td>' || vNumSobresD || '</td> ' ||
                    ' <td><p>US$' || vMontoD || '</p></td> ' || ' </tr> ';
      ELSE
        vFila_4 := vFila_4 || ' <tr> ' || ' <td>' || vFecha || '</td> ' ||
                   ' <td>' || vNumSobresS || '</td> ' || ' <td><p>S/' ||
                   vMontoS || '</p></td> ' || ' <td>' || vFecha || '</td> ' ||
                   ' <td>' || vNumSobresD || '</td> ' || ' <td><p>US$' ||
                   vMontoD || '</p></td> ' || ' </tr> ';

      END IF;
    END LOOP;
    -------------------------------------------------------------------------------
    LOOP
      FETCH cursor3
        INTO vUsuRem, vMontotTotalS, vMontotTotalD;
      EXIT WHEN cursor3%NOTFOUND;
      vFila_3 := vFila_3 || ' <tr>  ' ||
                 ' <td><strong>TOTAL</strong></td>  ' ||
                 ' <td>&nbsp;</td>  ' || ' <td><p><strong>S/.' ||
                 vMontotTotalS || '</strong></p></td>  ' ||
                 ' <td><p><strong>TOTAL</strong></p></td>  ' ||
                 ' <td>&nbsp;</td>  ' || ' <td><p><strong>US$' ||
                 vMontotTotalD || '</strong></p></td> ' || ' </tr>';

    END LOOP;

    -----------------------------------------------------
    vFila_2 := vFila_2 || '   <tr> ' ||
               ' <td height="68" colspan="3"><p>REMITO N&deg; :  <strong>' ||
               cCodRemito || '</strong></p> ' || ' <p><strong>' || vUsuRem ||
               '</strong></p></td> ' || ' <td colspan="3"><center>' ||
               cCodLocal_in || '  -  ' || TRUNC(SYSDATE) ||
               '</center></td> ' || ' </tr>';

    --dbms_output.put_line('CAN :'||vFila_4);
    vMsg_out := C_INICIO_MSG_2 || vFila_4 || vFila_41 ||
               -- C_LINEA ||
                vFila_3 || vFila_2 || C_FIN_MSG;

    --         dbms_output.put_line('CANT :'||LENGTH(vMsg_out));
    --       dbms_output.put_line(vMsg_out);

    RETURN vMsg_out;

  END;

  /*********************************************************************************************/
  FUNCTION SEG_F_CUR_GET_DATA4(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor IS
    curVta FarmaCursor;
    --int_total number;
    --vCod_local_origen char(3);
  BEGIN
    OPEN curVta FOR
      SELECT V2.FECHA,
             V2.CANT_S,
             TO_CHAR(V2.SOLES, '999,999,990.00'),
             V2.CANT_D,
             TO_CHAR(V2.DOLARES, '999,999,990.00')
        FROM (SELECT TO_CHAR(A.FEC_DIA_VTA, 'DD/MM/YYYY') FECHA,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '01' THEN
                                C.MON_ENTREGA_TOTAL
                             END),
                         0) SOLES,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '01' THEN
                                1
                             END),
                         0) CANT_S,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '02' THEN
                                C.MON_ENTREGA
                             END),
                         0) DOLARES,
                     NVL(SUM(CASE
                               WHEN C.TIP_MONEDA = '02' THEN
                                1
                             END),
                         0) CANT_D,
                     V1.CANT CANT
                FROM CE_DIA_REMITO A,
                     CE_SOBRE B,
                     CE_FORMA_PAGO_ENTREGA C,
                     (SELECT COUNT(*) CANT,
                             TRUNC(X.FEC_DIA_VTA) FEC,
                             X.COD_GRUPO_CIA,
                             X.COD_LOCAL
                        FROM CE_SOBRE X
                        where x.estado = 'A'
                       GROUP BY TRUNC(X.FEC_DIA_VTA),
                                X.COD_GRUPO_CIA,
                                X.COD_LOCAL) V1,
                     CE_CIERRE_DIA_VENTA Y
               WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND A.COD_LOCAL = cCodLocal_in
                 AND A.COD_REMITO = TRIM(cCodRemito) --
                 AND Y.IND_VB_CIERRE_DIA = 'S'
                        and b.estado = 'A'
                 AND A.COD_REMITO IS NOT NULL
                 AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                 AND A.COD_LOCAL = B.COD_LOCAL
                 AND A.FEC_DIA_VTA = B.FEC_DIA_VTA
                 AND B.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND B.COD_LOCAL = C.COD_LOCAL
                 AND B.SEC_MOV_CAJA = C.SEC_MOV_CAJA
                 AND B.SEC_FORMA_PAGO_ENTREGA = C.SEC_FORMA_PAGO_ENTREGA --
                 AND TRUNC(A.FEC_DIA_VTA) = V1.FEC
                 AND A.COD_GRUPO_CIA = V1.COD_GRUPO_CIA
                 AND A.COD_LOCAL = V1.COD_LOCAL
                 AND A.COD_GRUPO_CIA = Y.COD_GRUPO_CIA
                 AND A.COD_LOCAL = Y.COD_LOCAL
                 AND A.FEC_DIA_VTA = Y.FEC_CIERRE_DIA_VTA
               GROUP BY TO_CHAR(A.FEC_DIA_VTA, 'DD/MM/YYYY'), V1.CANT
               ORDER BY 1 ASC) V2;
    RETURN curVta;
  END;

  /* ********************************************************************************************** */
  FUNCTION SEG_F_CUR_GET_DATA3(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cCodRemito      IN CHAR) RETURN FarmaCursor IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
      SELECT V1.USU,
             TO_CHAR(V1.SOLES, '999,999,990.00'),
             TO_CHAR(V1.DOLARES, '999,999,990.00')
        FROM (SELECT B.USU_CREA_REMITO USU,
                     NVL(SUM(CASE
                               WHEN D.TIP_MONEDA = '01' THEN
                                D.MON_ENTREGA_TOTAL
                             END),
                         0) SOLES,
                     NVL(SUM(CASE
                               WHEN D.TIP_MONEDA = '02' THEN
                                D.MON_ENTREGA
                             END),
                         0) DOLARES
                FROM CE_DIA_REMITO         A,
                     CE_REMITO             B,
                     CE_SOBRE              C,
                     CE_FORMA_PAGO_ENTREGA D
               WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
                 AND A.COD_LOCAL = cCodLocal_in
                 AND A.COD_REMITO = TRIM(cCodRemito)
                        and c.estado = 'A'
                 AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
                 AND A.COD_LOCAL = B.COD_LOCAL
                 AND A.COD_REMITO = B.COD_REMITO
                 AND A.COD_GRUPO_CIA = C.COD_GRUPO_CIA
                 AND TRUNC(A.FEC_DIA_VTA) = TRUNC(C.FEC_DIA_VTA)
                 AND A.COD_LOCAL = C.COD_LOCAL
                 AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                 AND C.COD_LOCAL = D.COD_LOCAL
                 AND C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                 AND C.SEC_FORMA_PAGO_ENTREGA = D.SEC_FORMA_PAGO_ENTREGA
               GROUP BY B.USU_CREA_REMITO) V1;

    RETURN curDet;
  END;
  /* ************************************************************************ */
  FUNCTION SEG_F_CHAR_IND_PROSEGUR(
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR
                                  ) RETURN CHAR
  IS
   cRespta               char(1);
   nCantFormPagoEfectivo number;
  BEGIN

    begin
      SELECT nvl(L.IND_PROSEGUR, 'N')
        INTO cRespta
        FROM PBL_LOCAL L
       WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
         AND L.COD_LOCAL = cCodLocal_in;
    exception
      when no_data_found then
        cRespta := 'N';
    end;

    RETURN cRespta;
  END;
  /**********************************************************************************/
  FUNCTION SEG_F_CHAR_IND_CHCB_SOBRE
  RETURN CHAR
  IS
   cRespta               char(1);
  BEGIN

    begin
      SELECT nvl(P.LLAVE_TAB_GRAL, 'N')
        INTO cRespta
        FROM PBL_TAB_GRAL P
       WHERE P.ID_TAB_GRAL = 257;
    exception
      when no_data_found then
        cRespta := 'N';
    end;

    RETURN cRespta;
  END;
  /**********************************************************************************/
  FUNCTION SEG_F_CHAR_CANT_MOD_SOBRE
  RETURN CHAR
  IS
   cRespta               char(1);
  BEGIN

    begin
      SELECT nvl(P.LLAVE_TAB_GRAL, '3')
        INTO cRespta
        FROM PBL_TAB_GRAL P
       WHERE P.ID_TAB_GRAL = 258;
    exception
      when no_data_found then
        cRespta := '3';
    end;

    RETURN cRespta;
  END;

  FUNCTION SEG_F_CHAR_SOBRES_ELI(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 cFechaCierreDia IN CHAR,
                                 cSecMovCaja     IN CHAR)
  RETURN CHAR
  IS
   nCantidad number:=0;
   nCantVBContable number;
   dFechaValidacion date;
  BEGIN

      SELECT COUNT(1)
      INTO   nCantVBContable
      FROM   CE_HIST_VB_MOV_CAJA H
      WHERE  H.COD_GRUPO_CIA =  cCodGrupoCia_in
      AND    H.COD_LOCAL     =  cCodLocal_in
      AND    H.SEC_MOV_CAJA  =  cSecMovCaja
      AND    H.IND_VB_MOV_CAJA = 'N';

      IF nCantVBContable > 0 THEN

      SELECT min(H.FEC_VB_MOV_CAJA)
      INTO   dFechaValidacion
      FROM   CE_HIST_VB_MOV_CAJA H
      WHERE  H.COD_GRUPO_CIA =  cCodGrupoCia_in
      AND    H.COD_LOCAL     =  cCodLocal_in
      AND    H.SEC_MOV_CAJA  =  cSecMovCaja
      AND    H.IND_VB_MOV_CAJA = 'N';

      SELECT NVL(COUNT(1),0)
      INTO nCantidad
      FROM CE_SOBRE S
      WHERE S.COD_GRUPO_CIA = cCodGrupoCia_in
      AND   S.COD_LOCAL     = cCodLocal_in
      AND   S.FEC_DIA_VTA   = TO_DATE(cFechaCierreDia,'DD/MM/YYYY')
      AND   (
            S.FEC_CREA_SOBRE >=  dFechaValidacion
                                                 or
            S.FEC_MOD_SOBRE  >=  dFechaValidacion
            )
      AND   S.SEC_MOV_CAJA  = cSecMovCaja
      AND   S.ESTADO = 'I';

      END IF;

      dbms_output.put_line('nCantidad:'||nCantidad);



    RETURN ''||nCantidad;
  END;

  PROCEDURE SEG_P_ENVIAR_CORREO_ALERTA(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in          IN CHAR,
                                 cFechaCierreDia       IN CHAR,
                                 cSecMovCaja           IN CHAR,
                                 cSecUsuLocal_in       IN CHAR,
                                 cTipMensaje_in        IN CHAR,
                                 cCodSobre_in          IN CHAR)
  IS
   cEmailAlerta varchar(300):='';
   cEmailMens varchar(300):='';
   descLocal varchar(300):='';
   cajero varchar(300):='';
  BEGIN

      begin
        SELECT nvl(P.LLAVE_TAB_GRAL, 'DUBILLUZ')
          INTO cEmailAlerta
          FROM PBL_TAB_GRAL P
         WHERE P.ID_TAB_GRAL = 259;
      exception
        when no_data_found then
          cEmailAlerta := 'DUBILLUZ';
      end;


      select l.cod_local||'-'||l.desc_corta_local
      into descLocal
      from pbl_local l
      where l.cod_grupo_cia = cCodGrupoCia_in
      and   l.cod_local     = cCodLocal_in;

      select u.cod_trab_rrhh||'-'||u.nom_usu||' '||u.ape_pat||' '||u.ape_mat
      into cajero
      from pbl_usu_local u
      where u.cod_grupo_cia = cCodGrupoCia_in
      and   u.cod_local     = cCodLocal_in
      and   u.sec_usu_local = cSecUsuLocal_in;

      IF (cTipMensaje_in='01')THEN

        SELECT nvl(P.LLAVE_TAB_GRAL, 'SE ESTA INTENTANDO ELIMINAR SOBRE APROBADO')
          INTO cEmailMens
          FROM PBL_TAB_GRAL P
         WHERE P.ID_TAB_GRAL = 350;

      ELSIF  (cTipMensaje_in='02')THEN

      SELECT nvl(P.LLAVE_TAB_GRAL, 'SE PROCEDIO CON LA ELIMINACION DEL SOBRE')
          INTO cEmailMens
          FROM PBL_TAB_GRAL P
         WHERE P.ID_TAB_GRAL = 351;

      END IF;


      FARMA_UTILITY.ENVIA_CORREO(cCodGrupoCia_in,
                                 cCodLocal_in,
                                 cEmailAlerta,
                                 'Alerta: modificacion de sobres - prosegur',
                                 'Alerta:'||cEmailMens||' - '||cCodSobre_in,
                                 'Se esta modificando los sobres declarados en '
                               ||'<br><b>Local</b> : '||descLocal
                               ||'<br><b>Cajero</b> : '||cajero
                               ||'<br><b>Dia de venta</b> : '||cFechaCierreDia
                               ||'<br>',
                                 NULL);

  END;

  /*************************************************************************************************************/
   FUNCTION SEG_F_VALIDA_SOBRE_REMITO(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cFechaCierreDia IN CHAR,
                                      cSecMovCaja     IN CHAR,
                                      cCodSobre       IN CHAR)
  RETURN CHAR
  IS
   nCantidad  number:=0;
   nCantidad2 number:=0;
   nCantidad3 number:=0;
   nCantidad4 number:=0;
   cIndExist  CHAR:='N';
   mensaje    VARCHAR2(200);
   mensaje2   VARCHAR2(200);
       cIndicador VARCHAR2(1);
       indProsegur CHAR(1); --ASOSA, 02.06.2010
  BEGIN

       --SELECT TRIM(NVL(A.DESC_CORTA,' ')) INTO cIndicador
       SELECT TRIM(NVL(A.Llave_Tab_Gral,' ')) INTO cIndicador --ASOSA, 02.06.2010
       FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL=317;

       --ASOSA, 02.06.2010
       --LLEIVA 24-Ene-2014 Ya no se utilizara el indicador de Prosegur del local, sino uno propio en cada remito
       --SELECT nvl(x.ind_prosegur,'N') INTO indProsegur
       --FROM pbl_local x
       --WHERE x.cod_grupo_cia=cCodGrupoCia_in
       --AND x.cod_local=cCodLocal_in;

       IF(cIndicador='S'
          --OR indProsegur='S'
          )THEN

       --No se considera sobres temporales por que para cuando existe al remito ya solo deben existir ce_sobre
       SELECT COUNT(*) INTO nCantidad
       FROM CE_SOBRE A
       WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
       AND A.ESTADO='A'
       AND A.COD_LOCAL=cCodLocal_in
       AND A.FEC_DIA_VTA=cFechaCierreDia
       AND A.SEC_MOV_CAJA IN (cSecMovCaja)
       AND NVL(a.cod_remito,0) = 0--RHERRERA: LOS SOBRES SIN REMITO GENERADO
       AND A.COD_SOBRE=cCodSobre;

       SELECT COUNT(*) INTO nCantidad3
       FROM CE_SOBRE_TMP A
       WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
       AND A.ESTADO='A'
       AND A.COD_LOCAL=cCodLocal_in
       AND A.FEC_DIA_VTA=cFechaCierreDia
       AND A.SEC_MOV_CAJA IN (SELECT B.SEC_MOV_CAJA_ORIGEN
                              FROM CE_MOV_CAJA B
                              WHERE B.COD_GRUPO_CIA=A.COD_GRUPO_CIA
                              AND B.COD_LOCAL=A.COD_LOCAL
                              AND B.SEC_MOV_CAJA=cSecMovCaja)
       AND A.COD_SOBRE=cCodSobre;

       ---VALIDAR QUE EXITA EL SOBRE EM CE_SOBRE
       SELECT COUNT(*) INTO nCantidad4
       FROM CE_SOBRE A
       WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
       AND A.ESTADO='A'
       AND A.COD_LOCAL=cCodLocal_in
       AND A.FEC_DIA_VTA=cFechaCierreDia
       AND A.SEC_MOV_CAJA IN (cSecMovCaja)
       AND A.COD_SOBRE=cCodSobre;


       IF (nCantidad>0 OR nCantidad3>0 OR nCantidad4>0) THEN -- Se verifica la existencia del unico sobre

       --solo por locales con prosegur, ya que para ellos unicamente se crearan remitos


       SELECT nvl(P.LLAVE_TAB_GRAL, 'NO SE PUEDE ELIMINAR ESTE SOBRE YA CERRADO')
          INTO mensaje
          FROM PBL_TAB_GRAL P
         WHERE P.ID_TAB_GRAL = 352;

       SELECT nvl(P.LLAVE_TAB_GRAL, 'SE ESTA ELIMINANDO UN SOBRE YA CERRADO')
          INTO mensaje2
          FROM PBL_TAB_GRAL P
         WHERE P.ID_TAB_GRAL = 353;

         mensaje:=mensaje||', ASOCIADO A UN REMITO.
                                                  '||'.';--rango permitido 63 espac
         mensaje2:=mensaje2||'
                                                  '||'.';

        --IF nCantidad2>0 THEN --Se valida que el sobre no este asociado a un remito
        --RHERRERA : Validad si encuentra sobre continua.
        IF nCantidad=0 THEN --Se valida que el sobre no este asociado a un remito
           cIndExist:='S';
           RAISE_APPLICATION_ERROR(-20020,mensaje);
        ELSE
           cIndExist:='N';
           RAISE_APPLICATION_ERROR(-20021,TRIM(mensaje2));
        END IF;
        --RHERRERA 24.09.2014
        ELSE
        RAISE_APPLICATION_ERROR(-20010,'EL SOBRE '||cCodSobre ||' NO SE ENCUENTRA REGISTRADO.');

       END IF;

       ELSE
        RAISE_APPLICATION_ERROR(-20010,'EL SOBRE '||cCodSobre ||' NO SE ENCUENTRA REGISTRADO.');


      dbms_output.put_line('nCantidad:'||nCantidad);

      END IF;

    RETURN cIndExist;
  END;

    /******************************************************************************/
  FUNCTION SEG_F_CUR_GET_SOBRES_APROBAR(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cFecIni_in      IN CHAR,
                                        cFecFin_in      IN CHAR)
  RETURN FarmaCursor IS
    curSobresCajero FarmaCursor;
  begin
 -- no se considera movimiento, ya que tiene que mostrarse todos los sobres pendientes
    OPEN curSobresCajero FOR
      SELECT D.NOM_USU || 'Ã' ||
             A.NUM_CAJA_PAGO || 'Ã' ||
             A.NUM_TURNO_CAJA || 'Ã' ||
             V1.COD_SOBRE || 'Ã' ||
             TO_CHAR(A.FEC_DIA_VTA,'DD/MM/YYYY') || 'Ã' ||
             nvl(V1.TIP_MONEDA,' ')|| 'Ã' ||
             V1.MON_ENTREGA || 'Ã' ||
             V1.MON_ENTREGA_TOTAL || 'Ã' ||
             V1.ESTADO|| 'Ã' ||
             V1.SEC_MOV_CAJA
      FROM (SELECT B.COD_GRUPO_CIA,B.COD_LOCAL,B.COD_SOBRE,E.SEC_MOV_CAJA AS SEC_MOV_CAJA,DECODE(F.TIP_MONEDA,'01','SOLES','02','DOLARES') AS TIP_MONEDA,
                   TO_CHAR(F.MON_ENTREGA,'999,990.000') AS MON_ENTREGA,TO_CHAR(F.MON_ENTREGA_TOTAL,'999,990.000') AS MON_ENTREGA_TOTAL,
                   --DECODE(B.ESTADO,'C','CREADO','A','APROBADO','I','INACTIVO')AS ESTADO
                   B.ESTADO
                   FROM CE_SOBRE B,
                        CE_MOV_CAJA E,
                        CE_FORMA_PAGO_ENTREGA F
                    WHERE  B.COD_GRUPO_CIA=cCodGrupoCia_in
                    AND B.COD_LOCAL=cCodLocal_in
                    --AND B.ESTADO IN ('C','A')--creados y aprobados
                    AND B.ESTADO IN ('P','A')--ASOSA, 10.06.2010
                    AND TRUNC(B.FEC_DIA_VTA) BETWEEN  NVL(cFecIni_in,SYSDATE) AND  NVL(cFecFin_in,SYSDATE) --TRUNC(SYSDATE-10)--
                    AND B.COD_GRUPO_CIA=E.COD_GRUPO_CIA
                    AND B.COD_LOCAL=E.COD_LOCAL
                    AND B.FEC_DIA_VTA=E.FEC_DIA_VTA
                    AND B.SEC_MOV_CAJA=E.SEC_MOV_CAJA
                    AND B.COD_GRUPO_CIA=F.COD_GRUPO_CIA
                    AND B.COD_LOCAL=F.COD_LOCAL
                    AND E.SEC_MOV_CAJA=F.SEC_MOV_CAJA
                    AND B.SEC_FORMA_PAGO_ENTREGA=F.SEC_FORMA_PAGO_ENTREGA
            UNION
           SELECT C.COD_GRUPO_CIA,C.COD_LOCAL,C.COD_SOBRE,D.SEC_MOV_CAJA,DECODE(C.TIP_MONEDA,'01','SOLES','02','DOLARES') AS TIP_MONEDA,TO_CHAR(C.MON_ENTREGA,'999,990.000') AS MON_ENTREGA,
                  TO_CHAR(C.MON_ENTREGA_TOTAL,'999,990.000') AS MON_ENTREGA_TOTAL ,
                  --DECODE(C.ESTADO,'C','CREADO','A','APROBADO','I','INACTIVO') AS ESTADO
                  C.ESTADO
                   FROM CE_SOBRE_TMP C,
                        CE_MOV_CAJA D
                   WHERE C.COD_GRUPO_CIA=cCodGrupoCia_in
                   AND C.COD_LOCAL=cCodLocal_in
                   --AND C.ESTADO IN ('C','A')--creados y aprobados
                   AND C.ESTADO IN ('P','A')--ASOSA, 10.06.2010
                   AND  TRUNC(C.FEC_DIA_VTA) BETWEEN  NVL(cFecIni_in,SYSDATE) AND  NVL(cFecFin_in,SYSDATE) --TRUNC(SYSDATE-10)--
                   AND C.COD_GRUPO_CIA=D.COD_GRUPO_CIA
                   AND C.COD_LOCAL=D.COD_LOCAL
                   AND C.SEC_MOV_CAJA=D.SEC_MOV_CAJA
                   ) V1,
            CE_MOV_CAJA A,
            PBL_USU_LOCAL D
      WHERE V1.COD_GRUPO_CIA=A.COD_GRUPO_CIA
      AND V1.COD_LOCAL=A.COD_LOCAL
      AND V1.SEC_MOV_CAJA=A.SEC_MOV_CAJA
      AND A.COD_GRUPO_CIA=D.COD_GRUPO_CIA
      AND A.COD_LOCAL=D.COD_LOCAL
      AND A.SEC_USU_LOCAL=D.SEC_USU_LOCAL;
     /* AND A.FEC_DIA_VTA  IN (SELECT E.FEC_DIA_VTA
                               FROM CE_DIA_REMITO E
                               WHERE E.COD_GRUPO_CIA=A.COD_GRUPO_CIA
                               AND E.COD_LOCAL=A.COD_LOCAL);*/

    return curSobresCajero;
  end;


    /**********************************************************************************/
     PROCEDURE  SEG_P_VALIDA_EST_SOBRE(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cFecVta_in      IN CHAR,
                                       cCodSobre_in    IN CHAR)

    AS
        cEstado_in1  CE_SOBRE.ESTADO%TYPE;
        cant1        NUMBER;
        cEstado_in2  CE_SOBRE.ESTADO%TYPE;
        cant2        NUMBER;

      BEGIN
        SELECT COUNT(*) INTO cant1
        FROM CE_SOBRE A
        WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
        AND A.COD_LOCAL=cCodLocal_in
        AND A.FEC_DIA_VTA=cFecVta_in
        AND A.COD_SOBRE=cCodSobre_in;

        SELECT COUNT(*) INTO cant2
        FROM CE_SOBRE_TMP A
        WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
        AND A.COD_LOCAL=cCodLocal_in
        AND A.FEC_DIA_VTA=cFecVta_in
        AND A.COD_SOBRE=cCodSobre_in;

        IF(cant1>0)THEN

        SELECT A.ESTADO
        INTO cEstado_in1
        FROM CE_SOBRE A
        WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
        AND A.COD_LOCAL=cCodLocal_in
        AND A.FEC_DIA_VTA=cFecVta_in
        AND A.COD_SOBRE=cCodSobre_in;

         IF cEstado_in1 = 'A' THEN
             RAISE_APPLICATION_ERROR(-20001,'Este sobre ya ha sido aprobado.');
         ELSIF cEstado_in1= 'N' THEN
             RAISE_APPLICATION_ERROR(-20002,'Este sobre ya ha sido rechazado.¡No puede aprobar el sobre!');
         END IF;

        ELSIF (cant2>0)THEN

          SELECT A.ESTADO
          INTO cEstado_in2
          FROM CE_SOBRE_TMP A
          WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
          AND A.COD_LOCAL=cCodLocal_in
          AND A.FEC_DIA_VTA=cFecVta_in
          AND A.COD_SOBRE=cCodSobre_in;

           IF cEstado_in2 = 'A' THEN
               RAISE_APPLICATION_ERROR(-20001,'Este sobre ya ha sido aprobado.');
           ELSIF cEstado_in2= 'N' THEN
               RAISE_APPLICATION_ERROR(-20002,'Este sobre ya ha sido rechazado.¡No puede aprobar el sobre!');
           END IF;

         END IF;

     EXCEPTION
       WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20003,'El sobre no existe');
  END;

    /******************************************************************************************************************/
    PROCEDURE SEG_P_VALIDA_APRUEBA_SOBRE(cCodGrupoCia_in IN CHAR,
                                         cCodLocal_in    IN CHAR,
                                         cIdUsu_in         IN CHAR,
                                         cFecVta_in      IN CHAR,
                                         cCodSobre_in    IN CHAR,
                                         cSecUsuQF  IN CHAR)
   IS
  BEGIN

  --se aprueba finales
    UPDATE CE_SOBRE A
    SET    A.ESTADO='A',
           A.FEC_MOD_SOBRE=SYSDATE,
           A.USU_MOD_SOBRE=cIdUsu_in,
           A.SEC_USU_QF = cSecUsuQF -- DUBILLUZ 27.07.2010
    WHERE   A.COD_GRUPO_CIA=cCodGrupoCia_in
    AND A.COD_LOCAL=cCodLocal_in
    AND A.FEC_DIA_VTA =cFecVta_in
    AND A.COD_SOBRE=cCodSobre_in;

    --se aprueba temporales par no considerarlo en las posteriores aprobaciones
    UPDATE CE_SOBRE_TMP B
    SET    B.ESTADO='A',
           B.FEC_MOD_SOBRE=SYSDATE,
           B.USU_MOD_SOBRE=cIdUsu_in,
           B.SEC_USU_QF = cSecUsuQF -- DUBILLUZ 27.07.2010
    WHERE  B.COD_GRUPO_CIA=cCodGrupoCia_in
    AND B.COD_LOCAL=cCodLocal_in
    AND B.FEC_DIA_VTA =cFecVta_in
    AND B.COD_SOBRE=cCodSobre_in;

  END;

   /***************************************************************************/
   FUNCTION SEG_F_VERIFICA_ROL_USU(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   vSecUsu_in       IN CHAR,
                                   cCodRol_in       IN CHAR)
    RETURN CHAR
    IS
    vresultado  CHAR(1);
    vcontador   NUMBER;
    BEGIN

    BEGIN
    SELECT COUNT(*) INTO vcontador
    FROM  PBL_ROL_USU X
    WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
    AND X.COD_LOCAL=cCodLocal_in
    AND X.SEC_USU_LOCAL=vSecUsu_in
    AND X.COD_ROL=cCodRol_in;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
           vcontador :=0;
    END;

    IF vcontador > 0 THEN
      vresultado := 'S';
    ELSE
      vresultado := 'N';
    END IF;
    RETURN vresultado;

    END;

   /***************************************************************************/

  FUNCTION SEG_F_CHAR_IND_SOBRES(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cCodFormPago_in IN CHAR) RETURN char IS
    cRespta               char(1);
    nCantFormPagoEfectivo number;
    indPerm    CHAR(1);
    montoPerm  VARCHAR2(10);
  BEGIN
    begin
      SELECT nvl(L.Llave_Tab_Gral, 'N') --ASOSA, 02.06.2010
        INTO cRespta
        FROM PBL_TAB_GRAL L
       WHERE --L.COD_GRUPO_CIA = cCodGrupoCia_in
         --AND
         L.ID_TAB_GRAL='317';
    exception
      when no_data_found then
        cRespta := 'N';
    end;


    SELECT count(1)
      into nCantFormPagoEfectivo
      FROM VTA_FORMA_PAGO F
     WHERE F.IND_TARJ = 'N'
       AND F.IND_FORMA_PAGO_EFECTIVO = 'S'
       AND F.COD_CONVENIO IS NULL
       AND F.IND_FORMA_PAGO_CUADRATURA = 'S'
       AND F.COD_GRUPO_CIA = cCodGrupoCia_in
       AND F.COD_FORMA_PAGO = cCodFormPago_in;

    if cRespta = 'S' then
        if nCantFormPagoEfectivo > 0 then
          cRespta := 'S';
        else
          cRespta := 'N';
        end if;
    end if;

    RETURN cRespta;
  END;
/* ******************************************************************************* */

  /******************************************************************************/
  FUNCTION SEG_F_ACCION_SOBRE_TMP(cCodGrupoCia_in  IN CHAR,
                               cCodLocal_in    IN CHAR,
                               cSec_in         IN CHAR,
                               cSecMovCaja_in  IN CHAR,
                               cIdUsu_in       IN CHAR,
                               cCodSobre_in    IN CHAR,

                               cCodFormaPago_in    IN CHAR,
                               cTipMoneda_in       IN CHAR,
                               cMonEntrega_in      IN NUMBER,
                               cMonEntregaTotal_in IN NUMBER,

                               cTipoAccion_in  IN CHAR)
  RETURN CHAR IS
    cResultado char(1) := 'N';

   v_numSec NUMBER;
   cFecDiaVta_in CE_MOV_CAJA.FEC_DIA_VTA%TYPE;
   vCodigoSobre  varchar2(20);
   nSecSobre     number;
   cEstadoCreado VARCHAR2(1);

   cEstadoSobreActual ce_sobre_tmp.estado%type;
   nCantidadExisteSOBRE number;
   nCantVBTurno number;

   nIsVacioSobre number;
   indicador char(1);

   v_totalSobre number;
   v_totalVenta number;

   fecdiaVenta date;

   indProsegur ce_sobre_tmp.ind_etv%type; --

  BEGIN

    select count(1)
     into   nCantidadExisteSOBRE
     from   ce_sobre s
     where  s.cod_sobre = cCodSobre_in
     and    s.cod_grupo_cia = cCodGrupoCia_in
     and    s.cod_local = cCodLocal_in;

    select  count(1)
     into   nCantVBTurno
     from   ce_mov_caja r
     where  r.cod_grupo_cia = cCodGrupoCia_in
     and    r.cod_local = cCodLocal_in
     and    r.sec_mov_caja_origen = cSecMovCaja_in
     and    r.tip_mov_caja  = 'C'
     and    r.ind_vb_cajero = 'S';

    -- RAISE_APPLICATION_ERROR(-20020,'Comprobante Incorrecto:  '||nNumComPago);

    if nCantidadExisteSOBRE > 0 then
       RAISE_APPLICATION_ERROR(-20020,'No puede cambiar el sobre.Por que ya fue agregado al turno.');
    else
      if nCantVBTurno > 0 then
         RAISE_APPLICATION_ERROR(-20021,'El día ya cuenta con VB de Cajero no puede realizar la acción.');
      else
        if cTipoAccion_in = ACC_MODIFICA or cTipoAccion_in = ACC_ELIMINA or cTipoAccion_in = ACC_APRUEBA then
             select T.ESTADO
             INTO   cEstadoSobreActual
             from   CE_SOBRE_TMP T
             WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
             AND    T.COD_LOCAL = cCodLocal_in
             AND    T.SEC = cSec_in
             AND    T.SEC_MOV_CAJA = cSecMovCaja_in FOR UPDATE;


             if cEstadoSobreActual != ESTADO_PENDIENTE then
                RAISE_APPLICATION_ERROR(-20022,'El sobre no se encuentra pendiente.');
             end if;
        end if;
      end if;
    end if;
    if cTipoAccion_in = ACC_APRUEBA then

        UPDATE CE_SOBRE_TMP B
        SET    B.ESTADO = ESTADO_APROBADO,
               B.FEC_MOD_SOBRE = SYSDATE,
               B.USU_MOD_SOBRE = cIdUsu_in
        WHERE  B.COD_GRUPO_CIA = cCodGrupoCia_in
        AND    B.COD_LOCAL = cCodLocal_in
        AND    B.SEC = cSec_in
        AND    B.SEC_MOV_CAJA = cSecMovCaja_in;

    else
      if cTipoAccion_in = ACC_MODIFICA then

      UPDATE CE_SOBRE_TMP T
      SET    T.MON_ENTREGA = cMonEntrega_in,
             T.MON_ENTREGA_TOTAL = cMonEntregaTotal_in,
             T.USU_MOD_SOBRE = cIdUsu_in ,
             T.FEC_MOD_SOBRE = sysdate
      WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
      AND    T.COD_LOCAL = cCodLocal_in
      AND    T.SEC = cSec_in
      AND    T.SEC_MOV_CAJA = cSecMovCaja_in;

      else if cTipoAccion_in = ACC_ELIMINA then

              UPDATE CE_SOBRE_TMP T
              SET    T.ESTADO = ESTADO_INACTIVO,
                     T.USU_MOD_SOBRE = cIdUsu_in,
                     T.FEC_MOD_SOBRE = sysdate
              WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
              AND    T.COD_LOCAL = cCodLocal_in
              AND    T.SEC = cSec_in
              AND    T.SEC_MOV_CAJA = cSecMovCaja_in;

            elsif cTipoAccion_in = ACC_INGRESO then
                --crea un nuevo sobre
                select m.fec_dia_vta
                  into cFecDiaVta_in
                  from ce_mov_caja m
                 where m.cod_grupo_cia = cCodGrupoCia_in
                   and m.cod_local = cCodLocal_in
                   and m.sec_mov_caja = cSecMovCaja_in;

                --obtiene un secuencial de sobre
                nSecSobre:= CAJ_F_OBTIENE_SECSOBRE(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in,cFecDiaVta_in);

                --obtiene el codigo de sobre
                vCodigoSobre :=  to_char(cFecDiaVta_in, 'ddmmyyyy') || '-' ||
                                                Farma_Utility.COMPLETAR_CON_SIMBOLO(nSecSobre,
                                                                                    3,
                                                                                    0,

                                                                                     'I');


                   /*SELECT count(1)
                   into nIsVacioSobre
                   FROM   CE_SOBRE_TMP A
                   WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                   AND    A.COD_LOCAL = ccodlocal_in
                   AND    A.SEC_MOV_CAJA = Csecmovcaja_In ;

                   if nIsVacioSobre > 0 then

                       SELECT 1
                       INTO   v_numSec
                       FROM   CE_SOBRE_TMP A
                       WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                       AND    A.COD_LOCAL = ccodlocal_in
                       AND    A.SEC_MOV_CAJA = Csecmovcaja_In for update;
                   else*/
                       select t.ind_vb_cajero
                       into   indicador
                       from   ce_mov_caja t
                       WHERE  t.COD_GRUPO_CIA = ccodgrupocia_in
                       AND    t.COD_LOCAL = ccodlocal_in
                       AND    t.SEC_MOV_CAJA = Csecmovcaja_In for update;
                   --end if;

                   SELECT NVL(MAX(A.SEC),0) + 1
                   INTO   v_numSec
                   FROM   CE_SOBRE_TMP A
                   WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
                   AND    A.COD_LOCAL = ccodlocal_in
                   AND    A.SEC_MOV_CAJA = Csecmovcaja_In;

                    select t.fec_dia_vta
                       into   fecdiaVenta
                       from   ce_mov_caja t
                       WHERE  t.COD_GRUPO_CIA = ccodgrupocia_in
                       AND    t.COD_LOCAL = ccodlocal_in
                       AND    t.SEC_MOV_CAJA = Csecmovcaja_In;


                    select a.ind_prosegur
                    into indProsegur
                    from pbl_local a
                    where a.cod_grupo_cia=ccodgrupocia_in
                    and   a.cod_local=ccodlocal_in;

                INSERT INTO CE_SOBRE_TMP
                (COD_SOBRE,COD_GRUPO_CIA,COD_LOCAL,FEC_DIA_VTA,SEC_MOV_CAJA,SEC,COD_FORMA_PAGO,
                 TIP_MONEDA,MON_ENTREGA,MON_ENTREGA_TOTAL,USU_CREA_SOBRE,USU_MOD_SOBRE,FEC_MOD_SOBRE,ESTADO, IND_ETV)
                VALUES
                (vCodigoSobre,cCodGrupoCia_in,cCodLocal_in,fecdiaVenta,cSecMovCaja_in,v_numSec,cCodFormaPago_in,
                cTipMoneda_in,cMonEntrega_in,cMonEntregaTotal_in,cIdUsu_in,NULL,NULL,ESTADO_PENDIENTE, indProsegur);

           end if;
      end if;
    end if;

    SELECT SUM(X.VAL_NETO_PED_VTA)
    INTO v_totalVenta
      FROM VTA_PEDIDO_VTA_CAB X
      WHERE X.COD_GRUPO_CIA=cCodGrupoCia_in
      AND X.COD_LOCAL=cCodLocal_in
      AND X.EST_PED_VTA='C'
      --AND X.IND_PEDIDO_ANUL='N'
      AND X.SEC_MOV_CAJA=cSecMovCaja_in;

       SELECT sum(a.mon_entrega_total)
       INTO v_totalSobre
       FROM   CE_SOBRE_TMP A
       WHERE  A.COD_GRUPO_CIA = ccodgrupocia_in
       AND    A.COD_LOCAL = ccodlocal_in
       AND    A.SEC_MOV_CAJA = Csecmovcaja_In
       and    a.estado in ('P','A');

       if v_totalSobre > v_totalVenta then
          RAISE_APPLICATION_ERROR(-20025,'El monto total acumulado supera las ventas realizadas. Verifique!!!');
       end if;

    return cResultado;

  END;

  /******************************************************************************/
  FUNCTION SEG_F_BLOQUEO_ESTADO(cCodGrupoCia_in  IN CHAR,
                                cCodLocal_in     IN CHAR,
                                cSec_in          IN CHAR,
                                cSecMovCaja_in   IN CHAR,
                                cCodSobre_in     IN CHAR,
                                cTipoSobre_in    IN CHAR)
  RETURN CHAR IS
   cEstadoSobre char(1) := 'N';
  BEGIN

     IF TRIM(cTipoSobre_in) = 'T' THEN
         select T.ESTADO
         INTO   cEstadoSobre
         from   CE_SOBRE_TMP T
         WHERE  T.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    T.COD_LOCAL = cCodLocal_in
         AND    T.SEC = cSec_in
         AND    T.SEC_MOV_CAJA = cSecMovCaja_in FOR UPDATE;
     ELSE

         select Q.ESTADO
         INTO   cEstadoSobre
         from   CE_SOBRE q
         WHERE  Q.COD_GRUPO_CIA = cCodGrupoCia_in
         AND    Q.COD_LOCAL = cCodLocal_in
         AND    Q.COD_SOBRE = cCodSobre_in FOR UPDATE;

     END IF;

    return cEstadoSobre;
  END;
  /******************************************************************************/
 FUNCTION CAJ_F_OBTIENE_SECSOBRE(cCodGrupoCia_in IN CHAR,
                                        cCod_Local_in   IN CHAR,
                                        cSecMovCaja_in  IN CHAR,
                                        cFecDiaVta_in   IN DATE)
    RETURN NUMBER
    IS
  Sec CHAR(10);
  cSecMovOrigen CHAR(10);
  nSecSobre1 NUMBER;
  nSecSobre2 NUMBER;
  BEGIN



    select to_number(nvl(max(Substr(B.cod_sobre,10)),'0'),'999')+ 1
    into nSecSobre1
    from CE_SOBRE_TMP B
    where B.cod_grupo_cia = cCodGrupoCia_in
       and B.cod_local = cCod_Local_in
       and B.FEC_DIA_VTA = cFecDiaVta_in;


     select to_number(nvl(max(Substr(A.cod_sobre,10)),'0'),'999')+ 1
    into nSecSobre2
    from CE_SOBRE A
    where A.cod_grupo_cia = cCodGrupoCia_in
       and A.cod_local = cCod_Local_in
      and A.FEC_DIA_VTA = cFecDiaVta_in;



    IF(nSecSobre1>nSecSobre2)THEN
        Sec:=nSecSobre1;
    ELSE
        Sec:=nSecSobre2;
    END IF;


   RETURN Sec;
   END;
 /******************************************************************************/
 FUNCTION CAJ_F_OBTIENE_SEC_MOV_CAJA(cCodGrupoCia_in IN CHAR,
                                 cCod_Local_in   IN CHAR,
                                 cSecUsuLocal_in  IN CHAR)
 return char is

  vSecMovCaja ce_mov_caja.sec_mov_caja%type;
  vnFecAper             DATE;  -- variable para obtener utlima fecha apertura

 begin

    BEGIN

    SELECT MAX(FEC_DIA_VTA)
       INTO   vnFecAper
       FROM   CE_MOV_CAJA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in AND
              COD_LOCAL     = cCod_Local_in   AND
              SEC_USU_LOCAL = cSecUsuLocal_in      AND
              TIP_MOV_CAJA  = 'A'; -- TIPO APERTURA


    SELECT W.SEC_MOV_CAJA
    INTO   vSecMovCaja
    FROM   ce_mov_caja w
    where  w.cod_grupo_cia = cCodGrupoCia_in
    and    w.cod_local     = cCod_Local_in
    and    w.sec_usu_local = cSecUsuLocal_in
    AND    fec_dia_vta   = vnFecAper    -- validamos registros de la fecha ultima apertura
    AND    W.TIP_MOV_CAJA = 'A' -- TIPO APERTURA
    AND    NOT EXISTS (
                      SELECT 1
                      FROM   CE_MOV_CAJA M
                      WHERE  M.COD_GRUPO_CIA = W.COD_GRUPO_CIA
                      AND    M.COD_LOCAL = W.COD_LOCAL
                      AND    M.SEC_MOV_CAJA_ORIGEN = W.SEC_MOV_CAJA
                      AND    M.TIP_MOV_CAJA = 'C' -- TIPO CIERRE
                      );
    EXCEPTION
    WHEN OTHERS THEN
         vSecMovCaja := 'X'; -- NO SE OBTUVO EL SECUENCIAL DE CAJA.
    END;

    RETURN vSecMovCaja;

 end;

 /******************************************************************************/
  FUNCTION  SEG_F_GET_TIPO_CAMBIO(cCodGrupoCia_in IN CHAR,
             cCodLocal_in    IN CHAR,
             cSecUsuLocal_in  IN CHAR)
  RETURN NUMBER IS
  v_nValorTipCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE;
  fechadiaventa date;
  vSecMovCaja ce_mov_caja.sec_mov_caja%type;
  vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
  v_nValorTipCambio := 0.00;
    BEGIN
    SELECT W.SEC_MOV_CAJA
    INTO   vSecMovCaja
    FROM   ce_mov_caja w
    where  w.cod_grupo_cia = cCodGrupoCia_in
    and    w.cod_local     = cCodLocal_in
    and    w.sec_usu_local = cSecUsuLocal_in
    and    w.fec_dia_vta =  trunc(sysdate) -- FECHA DE VENTA DEL DÍA ACTUAL
    AND    W.TIP_MOV_CAJA = 'A' -- TIPO APERTURA
    AND    NOT EXISTS (
                      SELECT 1
                      FROM   CE_MOV_CAJA M
                      WHERE  M.COD_GRUPO_CIA = W.COD_GRUPO_CIA
                      AND    M.COD_LOCAL = W.COD_LOCAL
                      AND    M.SEC_MOV_CAJA_ORIGEN = W.SEC_MOV_CAJA
                      AND    M.TIP_MOV_CAJA = 'C' -- TIPO CIERRE
                      );

  select m.fec_dia_vta
  into   fechadiaventa
  from   ce_mov_caja m
  where  m.cod_grupo_cia = cCodGrupoCia_in
  and    m.cod_local     = cCodLocal_in
  and    m.sec_mov_caja  = vSecMovCaja;

  --ERIOS 19.12.2013 Se utiliza el utilitario
  SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
  v_nValorTipCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,TO_CHAR(fechadiaventa,'DD/MM/YYYY'),'V');

  exception
  when others then
  v_nValorTipCambio := 0;
  end;

  RETURN v_nValorTipCambio;
  END;
 /******************************************************************************/
 /*
SELECT VAL_TIPO_CAMBIO,t.fec_ini_vig,t.fec_fin_vig,
       RANK() OVER (ORDER BY t.fec_fin_vig desc) orden
  FROM CE_TIP_CAMBIO t
 WHERE COD_GRUPO_CIA = '001'
   AND FEC_INI_VIG <=
       DECODE('14/06/2010' ,
              NULL,
              SYSDATE,
              TO_DATE(('14/06/2010' || ' 23:59:59'),
                      'dd/MM/yyyy HH24:MI:SS'))
 ORDER BY FEC_INI_VIG DESC;
 */
/** ************************************************************** */

  function SEG_F_CUR_DET_EFECTIVO_TURNO(cCodGrupoCia_in in char,
                                        cCodLocal_in in char,
                                        cCodTurno_in in char,
                                        cCodCaja_in in char,
                                        cFecha_in in char,
                                        cTipoEfectivo_in in char
                                     )
  return FarmaCursor
  is
  cursFarma FarmaCursor;
  begin

        IF cTipoEfectivo_in = COD_MONEDA_SOLES THEN
          open cursFarma for
          select
          c.cod_sobre || 'Ã' ||
          TO_CHAR(cc.fec_dia_vta,'DD/MM/YYYY') || 'Ã' ||
          DES_MONEDA_SOLES || 'Ã' ||
          cc.mon_tot
          from
          ce_sobre c,
          ce_mov_caja cc,
          ce_forma_pago_entrega cf
          where
          c.sec_mov_caja=cc.sec_mov_caja
          and c.fec_dia_vta=cc.fec_dia_vta
          and c.cod_local=cc.cod_local
          and c.cod_grupo_cia=cc.cod_grupo_cia
          and c.cod_local=cCodLocal_in
          and c.estado='A'
          and cf.sec_mov_caja=cc.sec_mov_caja
          and cf.cod_forma_pago = COD_EFECTIVO_SOLES--,'00002')
          and cf.cod_local=c.cod_local
          and cf.sec_forma_pago_entrega=c.sec_forma_pago_entrega
          and c.cod_grupo_cia=cf.cod_grupo_cia
          and c.cod_grupo_cia=cCodGrupoCia_in
          and c.fec_dia_vta = to_date(cFecha_in)
          and cc.num_caja_pago = cCodCaja_in
          and cc.num_turno_caja = cCodTurno_in;
      ELSE
          IF  cTipoEfectivo_in = COD_MONEDA_DOLARES THEN
              open cursFarma for
              select c.cod_sobre || 'Ã' ||
              to_char(cc.fec_dia_vta,'DD/MM/YYYY') || 'Ã' ||
              DES_MONEDA_DOLARES || 'Ã' ||
              cc.mon_tot
              from
              ce_sobre c,
              ce_mov_caja cc,
              ce_forma_pago_entrega cf
              where
              c.sec_mov_caja=cc.sec_mov_caja
              and c.fec_dia_vta=cc.fec_dia_vta
              and c.cod_local=cc.cod_local
              and c.cod_grupo_cia=cc.cod_grupo_cia
              and c.cod_local=cCodLocal_in
              and c.estado='A'
              and cf.sec_mov_caja=cc.sec_mov_caja
              and cf.cod_forma_pago = COD_EFECTIVO_DOLARES--,'00002')
              and cf.cod_local=c.cod_local
              and cf.sec_forma_pago_entrega=c.sec_forma_pago_entrega
              and c.cod_grupo_cia=cf.cod_grupo_cia
              and c.cod_grupo_cia=cCodGrupoCia_in
              and c.fec_dia_vta = to_date(cFecha_in)
              and cc.num_caja_pago = cCodCaja_in
              and cc.num_turno_caja = cCodTurno_in;
          END IF;
      END IF;

    RETURN cursFarma;
  end;

  function SEG_F_CUR_DET_EFECTIVO_DIA(cCodGrupoCia_in in char,
                                      cCodLocal_in in char,
                                      cFecha_in in char,
                                      cTipoEfectivo_in in char
                                     )
  return FarmaCursor
  is
  cursFarma FarmaCursor;
  begin

        IF cTipoEfectivo_in = COD_MONEDA_SOLES THEN
          open cursFarma for
          select
          c.cod_sobre || 'Ã' ||
          TO_CHAR(cc.fec_dia_vta,'DD/MM/YYYY') || 'Ã' ||
          DES_MONEDA_SOLES || 'Ã' ||
          cc.mon_tot
          from
          ce_sobre c,
          ce_mov_caja cc,
          ce_forma_pago_entrega cf
          where
          c.sec_mov_caja=cc.sec_mov_caja
          and c.fec_dia_vta=cc.fec_dia_vta
          and c.cod_local=cc.cod_local
          and c.cod_grupo_cia=cc.cod_grupo_cia
          and c.cod_local=cCodLocal_in
          and c.estado='A'
          and cf.sec_mov_caja=cc.sec_mov_caja
          and cf.cod_forma_pago = COD_EFECTIVO_SOLES--,'00001')
          and cf.cod_local=c.cod_local
          and cf.sec_forma_pago_entrega=c.sec_forma_pago_entrega
          and c.cod_grupo_cia=cf.cod_grupo_cia
          and c.cod_grupo_cia=cCodGrupoCia_in
          and c.fec_dia_vta = to_date(cFecha_in);
      ELSE
          IF cTipoEfectivo_in = COD_MONEDA_DOLARES THEN

              open cursFarma for
              select c.cod_sobre || 'Ã' ||
              to_char(cc.fec_dia_vta,'DD/MM/YYYY') || 'Ã' ||
              DES_MONEDA_DOLARES || 'Ã' ||
              cc.mon_tot
              from
              ce_sobre c,
              ce_mov_caja cc,
              ce_forma_pago_entrega cf
              where
              c.sec_mov_caja=cc.sec_mov_caja
              and c.fec_dia_vta=cc.fec_dia_vta
              and c.cod_local=cc.cod_local
              and c.cod_grupo_cia=cc.cod_grupo_cia
              and c.cod_local=cCodLocal_in
              and c.estado='A'
              and cf.sec_mov_caja=cc.sec_mov_caja
              and cf.cod_forma_pago = COD_EFECTIVO_DOLARES--,'00002'
              and cf.cod_local=c.cod_local
              and cf.sec_forma_pago_entrega=c.sec_forma_pago_entrega
              and c.cod_grupo_cia=cf.cod_grupo_cia
              and c.cod_grupo_cia=cCodGrupoCia_in
              and c.fec_dia_vta = to_date(cFecha_in);
          END IF;
      END IF;

    RETURN cursFarma;

  end;


END;
/

