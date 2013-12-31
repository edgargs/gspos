--------------------------------------------------------
--  DDL for Package Body PTOVENTA_TICKETERA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_TICKETERA" AS

   FUNCTION CAMP_F_VAR_MSJ_ANULACION(
                                   cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in 	IN CHAR,
                                   cajero_in    	IN CHAR,
                                   turno_in   	IN CHAR,
                                   numpedido_in IN CHAR,
                                   cod_igv_in IN CHAR ,
                                   cIndReimpresion_in in CHAR)

  RETURN VARCHAR2
  IS

  vMsg_out varchar2(32767);

  vMonto       NUMBER(9,3);
  vUsuario     varchar2(28);
  vLocalDes     varchar2(2800);
  vNroTicket     varchar2(20);
  vMoneda     varchar2(2);
  vFechaVenta varchar2(100);
  vNumComPago char(10);--JCORTEZ 17.07.09
  vMotivoAnulacion varchar2(2000);--JQUISPE 25.03.2010

  BEGIN

  begin
  -- se obtiene el monto afcecto o inafecto segun sea el caso
  select SUM(D.VAL_PREC_VTA * d.cant_atendida),
         c.usu_crea_ped_vta_cab,
         C.TIP_PED_VTA,
         c.fec_ped_vta,
         A.NUM_COMP_PAGO,
         c.motivo_anulacion--JQUISPE 25.03.2010
    into vMonto, vUsuario, vMoneda, vFechaVenta,vNumComPago,vMotivoAnulacion
    from vta_pedido_vta_cab c, lgt_prod P, vta_pedido_vta_det D,VTA_COMP_PAGO A
   where c.cod_grupo_cia = cCodGrupoCia_in
     and c.cod_local = cCodLocal_in
     and c.num_ped_vta = numpedido_in
     and c.est_ped_vta = 'C'
     and c.cod_grupo_cia = d.cod_grupo_cia
     and c.cod_local = d.cod_local
     and c.num_ped_vta = d.num_ped_vta
     and d.cod_grupo_cia = p.cod_grupo_cia
     and D.COD_PROD = P.COD_PROD
     and c.tip_comp_pago = '05'
     and P.COD_IGV = cod_igv_in
     --JCORTEZ 17.07.09 Se obtiene numero de comprobante
     AND C.COD_GRUPO_CIA=A.COD_GRUPO_CIA
     AND C.NUM_PED_VTA=A.NUM_PED_VTA
     AND C.COD_LOCAL=A.COD_LOCAL
       AND D.SEC_COMP_PAGO=A.SEC_COMP_PAGO
   group by c.usu_crea_ped_vta_cab, C.TIP_PED_VTA, c.fec_ped_vta,A.NUM_COMP_PAGO,C.MOTIVO_ANULACION;

  --se obtiene la descripcion del local

  select l.desc_local
  into  vLocalDes
  from   pbl_local l
  where  cod_local = cCodLocal_in;


  --se obtiene el numero de ticket asociado al comprobante de pago

  select DISTINCT(SUBSTR(c.num_comp_pago,1,3) || '-' ||  SUBSTR(c.num_comp_pago,4,10))
  into  vNroTicket
  from  vta_comp_pago c,
        VTA_PEDIDO_VTA_DET D,
        LGT_PROD A
  where D.cod_grupo_cia = cCodGrupoCia_in
  and   D.cod_local = cCodLocal_in
  and   D.num_ped_vta = numpedido_in
  AND   C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
  AND   C.COD_LOCAL = D.COD_LOCAL
  AND   C.NUM_PED_VTA = D.NUM_PED_VTA
  AND   C.SEC_COMP_PAGO = D.SEC_COMP_PAGO
  AND   D.COD_PROD=A.COD_PROD
  AND   A.COD_IGV=cod_igv_in;
  --and  c.val_neto_comp_pago = vMonto ;




  if vMoneda='01' then
     vMoneda:= 's/' ;
  else
     vMoneda:= '$/' ;
  end if;

              IF(vMonto>0) THEN

                    vMsg_out :=

                       cCodLocal_in  || ' ' ||  vLocalDes   || 'Ã'

                      ||  vNroTicket || 'Ã'

                      ||  to_char(sysdate, 'dd/mm/yyyy hh24:mi:ss')  || 'Ã'

                      ||  cajero_in  || 'Ã'

                      || turno_in    || 'Ã'

                      ||  vUsuario   || 'Ã'

                      ||  vMoneda || to_char(vMonto,'999990.00')  || 'Ã'

                      ||  vFechaVenta|| 'Ã' --to_char(vFechaVenta, 'dd/mm/rrrr hh24:mi:ss') ;

                      || vNumComPago || 'Ã' --JCORTEZ 17.07.09

                      || vMotivoAnulacion;  --JQUISPE 25.03.2010
               ELSE
                      vMsg_out:='N';
               END IF;

               if cIndReimpresion_in = 'S' then

               INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in,
                                            cCodLocal_in,
                                            'REIMPRESION TICKET ANULADO',
                                            'AVISO DE CONTROL',
                                            'Aviso se esta reimprimiendo la anulacion del ticket.<br>'||
                                            cCodLocal_in  || ' ' ||  vLocalDes || '<br>'
                                            ||  vNroTicket || '<br>'
                                            ||  to_char(sysdate, 'dd/mm/yyyy hh24:mi:ss')  || '<br>'
                                            ||  cajero_in  || '<br>'
                                            || turno_in    || '<br>'
                                            ||  vUsuario   || '<br>'
                                            ||  vMoneda || to_char(vMonto,'999990.00')  || '<br>'
                                            ||  vFechaVenta || '<br>'
                                            ||  vMotivoAnulacion
                                            );
                end if;


     exception
     when no_data_found then
      vMsg_out:='N';
     end;

     RETURN vMsg_out;

  END;

PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR)
  AS

    ReceiverAddress VARCHAR2(30);
    CCReceiverAddress VARCHAR2(120) := NULL;
    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN

    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    select t.llave_tab_gral
    into   ReceiverAddress
    from   pbl_tab_gral t
    where  t.id_tab_gral = 277;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             CCReceiverAddress,
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;
END;

/
