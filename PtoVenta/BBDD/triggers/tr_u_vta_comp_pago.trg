CREATE OR REPLACE TRIGGER PTOVENTA."TR_U_VTA_COMP_PAGO" AFTER UPDATE OF TIP_COMP_PAGO OR INSERT ON VTA_COMP_PAGO
 FOR EACH ROW

DECLARE
  v_vReceiverAddress    VARCHAR2(3000);
  v_vCCReceiverAddress  VARCHAR2(120) := NULL;
  mesg_body             VARCHAR2(32767);
  vAsunto               VARCHAR2(500);
  vTitulo               VARCHAR2(50);
  vMensaje              VARCHAR2(32767);
  v_vDescLocal          VARCHAR2(200);
  v_vTipCompOld         VARCHAR2(10) := ' ';
  v_vTipCompNew         VARCHAR2(10);
  v_vIP                 VARCHAR2(15);
  v_vTipComp            CHAR(2);
  v_vAccion             VARCHAR2(15);
  v_vTipCompCorresponde VARCHAR2(10);

  vIP_pc varchar2(20);
BEGIN

  SELECT Substrb(SYS_CONTEXT('USERENV', 'IP_ADDRESS'),0,5)
  into   vIP_pc
  FROM   DUAL;

  if vIP_pc = '10.10' then

      IF INSERTING THEN
        IF (:NEW.TIP_COMP_PAGO = '01' OR :NEW.TIP_COMP_PAGO = '05') AND :NEW.VAL_NETO_COMP_PAGO <0  THEN
          SELECT X.TIP_COMP
            INTO v_vTipComp
            FROM VTA_IMPR_IP X
           WHERE X.COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
             AND X.COD_LOCAL = :NEW.COD_LOCAL
             AND TRIM(X.IP) =
                 (SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') FROM DUAL);

          IF (v_vTipComp <> :NEW.TIP_COMP_PAGO) THEN
            v_vAccion := 'INSERTING';
            SELECT DESC_LOCAL
              INTO v_vDescLocal
              FROM PBL_LOCAL
             WHERE COD_GRUPO_CIA = :NEW.COD_GRUPO_CIA
               AND COD_LOCAL = :NEW.COD_LOCAL;
            SELECT DECODE(:NEW.TIP_COMP_PAGO,
                          01,
                          'BOLETA',
                          02,
                          'FACTURA',
                          05,
                          'TICKET')
              INTO v_vTipCompNew
              FROM DUAL;
            SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') INTO v_vIP FROM DUAL;
            SELECT DECODE(v_vTipComp, 01, 'BOLETA', 02, 'FACTURA', 05, 'TICKET')
              INTO v_vTipCompCorresponde
              FROM DUAL;

            vMensaje := 'ALERTA EN LA ACTUALIZACIÓN DE TIPO DE COMPROBANTE AL PROCESAR LA VENTA :' ||
                        TO_CHAR(SYSDATE, 'dd/MM/yyyy HH24:mi:ss') || '</B>' ||
                        '<BR> <I>ALERTA : </I> <BR>' || '<BR>' ||
                        'En el local ' || :NEW.COD_LOCAL || ' - ' ||
                        v_vDescLocal || '<BR>' || '<BR>' || 'En el IP  ' ||
                        v_vIP || '<BR>' || '<BR>' ||
                        'Se actualizó el tipo de comprobante del pedido Nro.: ' ||
                        :NEW.NUM_PED_VTA || '<BR>' || '<BR>' ||
                        'El tipo de comprobante que le corresponde al IP es ' ||
                        v_vTipCompCorresponde ||
                        ' y se le está asignando ' || v_vTipCompNew || '<BR>' ||
                        '<BR>' || '(' || v_vAccion || ')' || '.<BR>';

            mesg_body := '<L><B>' || vMensaje || '</B></L>';
            vAsunto   := 'ALERTA POR CAMBIO DEL TIPO DE COMPROBANTE EN LA VENTA: - TR_U_VTA_COMP_PAGO';
            vTitulo   := 'ALERTA';

            SELECT LLAVE_TAB_GRAL
              INTO v_vReceiverAddress
              FROM PBL_TAB_GRAL
             WHERE ID_TAB_GRAL = 298;

            FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                     v_vReceiverAddress,
                                     vAsunto,
                                     vTitulo,
                                     mesg_body,
                                     v_vCCReceiverAddress,
                                     FARMA_EMAIL.GET_EMAIL_SERVER,
                                     true);
          END IF;
        END IF;
      ELSIF UPDATING THEN
        IF (:NEW.TIP_COMP_PAGO = '01' OR :NEW.TIP_COMP_PAGO = '05') THEN
          SELECT X.TIP_COMP
            INTO v_vTipComp
            FROM VTA_IMPR_IP X
           WHERE X.COD_GRUPO_CIA = :OLD.COD_GRUPO_CIA
             AND X.COD_LOCAL = :OLD.COD_LOCAL
             AND TRIM(X.IP) =
                 (SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') FROM DUAL);

          IF (v_vTipComp <> :NEW.TIP_COMP_PAGO) THEN
            v_vAccion := 'UPDATING';
            SELECT DESC_LOCAL
              INTO v_vDescLocal
              FROM PBL_LOCAL
             WHERE COD_GRUPO_CIA = :OLD.COD_GRUPO_CIA
               AND COD_LOCAL = :OLD.COD_LOCAL;
            SELECT DECODE(:NEW.TIP_COMP_PAGO,
                          01,
                          'BOLETA',
                          02,
                          'FACTURA',
                          05,
                          'TICKET')
              INTO v_vTipCompNew
              FROM DUAL;
            SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') INTO v_vIP FROM DUAL;
            SELECT DECODE(v_vTipComp, 01, 'BOLETA', 02, 'FACTURA', 05, 'TICKET')
              INTO v_vTipCompCorresponde
              FROM DUAL;

            vMensaje := 'ALERTA EN LA ACTUALIZACIÓN DE TIPO DE COMPROBANTE AL PROCESAR LA VENTA :' ||
                        TO_CHAR(SYSDATE, 'dd/MM/yyyy HH24:mi:ss') || '</B>' ||
                        '<BR> <I>ALERTA : </I> <BR>' || '<BR>' ||
                        'En el local ' || :OLD.COD_LOCAL || ' - ' ||
                        v_vDescLocal || '<BR>' || '<BR>' || 'En el IP  ' ||
                        v_vIP || '<BR>' || '<BR>' ||
                        'Se actualizó el tipo de comprobante del pedido Nro.: ' ||
                        :OLD.NUM_PED_VTA || '<BR>' || '<BR>' ||
                        'El tipo de comprobante que le corresponde al IP es ' ||
                        v_vTipCompCorresponde ||
                        ' y se le está asignando ' || v_vTipCompNew || '<BR>' ||
                        '<BR>' || '(' || v_vAccion || ')' || '.<BR>';

            mesg_body := '<L><B>' || vMensaje || '</B></L>';
            vAsunto   := 'ALERTA POR CAMBIO DEL TIPO DE COMPROBANTE EN LA VENTA: - TR_U_VTA_COMP_PAGO';
            vTitulo   := 'ALERTA';

            SELECT LLAVE_TAB_GRAL
              INTO v_vReceiverAddress
              FROM PBL_TAB_GRAL
             WHERE ID_TAB_GRAL = 298;

            FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                     v_vReceiverAddress,
                                     vAsunto,
                                     vTitulo,
                                     mesg_body,
                                     v_vCCReceiverAddress,
                                     FARMA_EMAIL.GET_EMAIL_SERVER,
                                     true);
          END IF;
        END IF;
      END IF;
    end if;
END TR_U_VTA_COMP_PAGO;
/

