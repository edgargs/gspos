CREATE OR REPLACE TRIGGER PTOVENTA.TR_U_VTA_PEDIDO_VTA_CAB_BEF
AFTER UPDATE OF FEC_FIN_COBRO ON VTA_PEDIDO_VTA_CAB

FOR EACH ROW

DECLARE
    v_vReceiverAddress    VARCHAR2(3000);
    mesg_body             VARCHAR2(32767);
    vAsunto               VARCHAR2(500);
    vTitulo               VARCHAR2(50);
    vMensaje              VARCHAR2(32767);
    v_vDescLocal          VARCHAR2(200);
    v_vIP                 VARCHAR2(15);
    flag                  CHAR(1);
    cod_grupo_cia         CHAR(3);

BEGIN

   cod_grupo_cia := PTOVENTA_GRUPO_CIA.CIA_GET_COD_GRUPO_CIA;


   IF :OLD.EST_PED_VTA = 'C' AND  :NEW.EST_PED_VTA != 'N' THEN

   SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') INTO v_vIP FROM DUAL;

   SELECT DESC_LOCAL
     INTO v_vDescLocal
     FROM PBL_LOCAL
    WHERE COD_GRUPO_CIA = cod_grupo_cia
      AND COD_LOCAL = :NEW.COD_LOCAL;

    SELECT LLAVE_TAB_GRAL
      INTO v_vReceiverAddress
      FROM PBL_TAB_GRAL
     WHERE ID_TAB_GRAL = 298
       AND COD_GRUPO_CIA = cod_grupo_cia;

   flag := PTOVENTA_GRUPO_CIA.VTA_F_VERIFICAR_PED_COMP(cod_grupo_cia,:NEW.NUM_PED_VTA,:NEW.COD_LOCAL);

   IF flag = 'S' THEN

          IF :NEW.FEC_INI_COBRO IS NOT NULL AND :NEW.FEC_FIN_COBRO IS NOT NULL THEN
            dbms_output.put_line('ok');
          ELSE
            dbms_output.put_line('error');
            vMensaje := 'ALERTA ERROR AL GRABAR EL COMPROBANTE ' || '</B>' ||
                        'TIPO DE COMP. : ' || :OLD.TIP_COMP_PAGO || ' - ' || '</B>' ||
                        TO_CHAR(SYSDATE, 'dd/MM/yyyy HH24:mi:ss') || '</B>' ||
                        '<BR> <I>ALERTA : </I> <BR>' || '<BR>' ||
                        'En el local ' || :NEW.COD_LOCAL || ' - ' ||
                        v_vDescLocal || '<BR>' || '<BR>' || 'En el IP  ' ||
                        v_vIP || '<BR>' || '<BR>' ||
                        'Error , no se grabo las fechas de cobro del Pedido Nro.: ' ||
                        :NEW.NUM_PED_VTA || '<BR>' || '<BR>';

            mesg_body := '<L><B>' || vMensaje || '</B></L>';
            vAsunto   := 'ALERTA : - TR_U_VTA_PEDIDO_VTA_CAB_BEF';
            vTitulo   := 'ALERTA';
            FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                     v_vReceiverAddress,
                                     vAsunto,
                                     vTitulo,
                                     mesg_body,
                                     'jquispe',
                                     FARMA_EMAIL.GET_EMAIL_SERVER,
                                     true);

  RAISE_APPLICATION_ERROR (-20018, 'Error al actualizar la fecha de cobro.');


           END IF;


   ELSE
          vMensaje := 'ALERTA ERROR AL GRABAR EL COMPROBANTE ' || '</B>' ||
                        'TIPO DE COMP. : ' || :OLD.TIP_COMP_PAGO || ' - ' || '</B>' ||
                        TO_CHAR(SYSDATE, 'dd/MM/yyyy HH24:mi:ss') || '</B>' ||
                        '<BR> <I>ALERTA : </I> <BR>' || '<BR>' ||
                        'En el local ' || :NEW.COD_LOCAL || ' - ' ||
                        v_vDescLocal || '<BR>' || '<BR>' || 'En el IP  ' ||
                        v_vIP || '<BR>' || '<BR>' ||
                        'Error al actualizar el numero de comprobante en VTA_COMP_PAGO del Pedido Nro.: ' ||
                        :NEW.NUM_PED_VTA || '<BR>' || '<BR>';

            mesg_body := '<L><B>' || vMensaje || '</B></L>';
            vAsunto   := 'ALERTA : NO SE ACTUALIZO EL NUM. DE COMPROBANTE DEL PEDIDO A COBRAR - TR_U_VTA_PEDIDO_VTA_CAB_BEF';
            vTitulo   := 'ALERTA';
            FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                     v_vReceiverAddress,
                                     vAsunto,
                                     vTitulo,
                                     mesg_body,
                                     'jquispe',
                                     FARMA_EMAIL.GET_EMAIL_SERVER,
                                     true);
  RAISE_APPLICATION_ERROR (-20018, 'Error al actualizar el num. de comprobante del pedido.');
--           RAISE_APPLICATION_ERROR(-20018,'Serie de Comprobante Incorrecto:  '||nNumComPago);

   END IF;

   /*ELSE
   vMensaje := 'ALERTA ERROR AL GRABAR EL COMPROBANTE ' || '</B>' ||
                        'TIPO DE COMP. : ' || :OLD.TIP_COMP_PAGO || ' - ' || '</B>' ||
                        TO_CHAR(SYSDATE, 'dd/MM/yyyy HH24:mi:ss') || '</B>' ||
                        '<BR> <I>ALERTA : </I> <BR>' || '<BR>' ||
                        'En el local ' || :NEW.COD_LOCAL || ' - ' ||
                        v_vDescLocal || '<BR>' || '<BR>' || 'En el IP  ' ||
                        v_vIP || '<BR>' || '<BR>' ||
                        'Error al cambiar el estado del pedido de pendiente de impresion a cobrado, Pedido Nro.: ' ||
                        :NEW.NUM_PED_VTA || '<BR>' || '<BR>';

            mesg_body := '<L><B>' || vMensaje || '</B></L>';
            vAsunto   := 'ALERTA : ERROR AL CAMBIAR EL ESTADO DEL PEDIDO - TR_U_VTA_PEDIDO_VTA_CAB_BEF';
            vTitulo   := 'ALERTA';
            FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                     v_vReceiverAddress,
                                     vAsunto,
                                     vTitulo,
                                     mesg_body,
                                     'jquispe',
                                     FARMA_EMAIL.GET_EMAIL_SERVER,
                                     true);
   RAISE_APPLICATION_ERROR (-20018, 'Error al actualizar el estado del pedidio cobrado.');*/
   END IF;


END TR_U_VTA_PEDIDO_VTA_CAB_BEF;
/

