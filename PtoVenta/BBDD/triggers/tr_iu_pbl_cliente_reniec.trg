create or replace trigger ptoventa.TR_IU_PBL_CLIENTE_RENIEC before INSERT or update  ON PBL_CLIENTE
FOR EACH ROW
declare
   --autor dubilluz 22.10.2009

    v_vReceiverAddress    VARCHAR2(3000);
  v_vCCReceiverAddress  VARCHAR2(120) := NULL;
  mesg_body             VARCHAR2(32767);
  vAsunto               VARCHAR2(500);
  vTitulo               VARCHAR2(50);
  vMensaje              VARCHAR2(32767);
  v_vDescLocal          VARCHAR2(200);

  vCodLocal char(3);
    v_vIP                 VARCHAR2(15);
begin

     select distinct cod_local
     into   vCodLocal
     from   vta_impr_local
     where rownum = 1;

        SELECT DESC_LOCAL
          INTO v_vDescLocal
          FROM PBL_LOCAL
         WHERE COD_GRUPO_CIA = '001'
           AND COD_LOCAL = vCodLocal;


        SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS') INTO v_vIP FROM DUAL;

   if UPDATING  then

      if :old.FEC_NAC_CLI is not null  then

         if :new.FEC_NAC_CLI is null then

         vMensaje := 'ALERTA EN LA ACTUALIZACIÓN FEC_NACIMIENTO CLIENTE :' ||
                    TO_CHAR(SYSDATE, 'dd/MM/yyyy HH24:mi:ss') || '</B>' ||
                    '<BR> <I>ALERTA : </I> <BR>' || '<BR>' ||
                    'En el local ' || vCodLocal || ' - ' ||
                    v_vDescLocal || '<BR>' || '<BR>' || 'En el IP  ' ||
                    v_vIP || '<BR>' ||
                    '<BR>' ||
                    'Se actualizó el cliente DNI : ' ||
                    :OLD.DNI_CLI || ' - '||
                    :old.NOM_CLI || ' Fecha Nacimiento:' ||
                    :old.FEC_NAC_CLI||
                    '<BR>' ||
                    '<BR>' ||
                    'Por la fecha de Nacimiento siguiente :' || :new.FEC_NAC_CLI || '<BR>' ||
                    '<BR>' || '(' || 'UPDATE' || ')' || '.<BR>';

        mesg_body := '<L><B>' || vMensaje || '</B></L>';
        vAsunto   := 'ALERTA POR FECHA NACIMIENTO NULL EN CLIENTE TR_IU_PBL_CLIENTE_RENIEC';
        vTitulo   := 'ALERTA';




        v_vReceiverAddress := 'dubilluz;joliva;jcortez';

        FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                 v_vReceiverAddress,
                                 vAsunto,
                                 vTitulo,
                                 mesg_body,
                                 v_vCCReceiverAddress,
                                 FARMA_EMAIL.GET_EMAIL_SERVER,
                                 true);

         end if;
      end if;

      IF NVL(:new.FEC_NAC_CLI,TRUNC(SYSDATE+1)) != NVL(:old.FEC_NAC_CLI,TRUNC(SYSDATE+1)) then
          INSERT INTO PBL_CLIENTE_BK
          (
           DNI_CLI,NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,FONO_CLI,SEXO_CLI,DIR_CLI,
           FEC_NAC_CLI,FEC_CREA_CLIENTE,USU_CREA_CLIENTE,FEC_MOD_CLIENTE,USU_MOD_CLIENTE,IND_ESTADO,
           EMAIL,COD_LOCAL_ORIGEN,CELL_CLI,COD_TIP_DOCUMENTO,ID_USU_CONFIR,
           Fecha_creacion,COD_LOCAL_CONFIR,IP_CONFIR
          )
          VALUES
          (
           :old.DNI_CLI,:old.NOM_CLI,:old.APE_PAT_CLI,:old.APE_MAT_CLI,:old.FONO_CLI,:old.SEXO_CLI,:old.DIR_CLI,
           :old.FEC_NAC_CLI,:old.FEC_CREA_CLIENTE,:old.USU_CREA_CLIENTE,:old.FEC_MOD_CLIENTE,:old.USU_MOD_CLIENTE,:old.IND_ESTADO,
           :old.EMAIL,:old.COD_LOCAL_ORIGEN,:old.CELL_CLI,:old.COD_TIP_DOCUMENTO,:old.ID_USU_CONFIR,
           sysdate,
           :OLD.COD_LOCAL_CONFIR,:OLD.IP_CONFIR
          );
      end if;

   elsif inserting then

      INSERT INTO PBL_CLIENTE_bk
      (
       DNI_CLI,NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,FONO_CLI,SEXO_CLI,DIR_CLI,
       FEC_NAC_CLI,FEC_CREA_CLIENTE,USU_CREA_CLIENTE,FEC_MOD_CLIENTE,USU_MOD_CLIENTE,IND_ESTADO,
       EMAIL,COD_LOCAL_ORIGEN,CELL_CLI,COD_TIP_DOCUMENTO,ID_USU_CONFIR,
       Fecha_creacion,COD_LOCAL_CONFIR,IP_CONFIR
      )
      VALUES
      (
       :New.DNI_CLI,:New.NOM_CLI,:New.APE_PAT_CLI,:New.APE_MAT_CLI,:New.FONO_CLI,:New.SEXO_CLI,:New.DIR_CLI,
       :New.FEC_NAC_CLI,:New.FEC_CREA_CLIENTE,:New.USU_CREA_CLIENTE,:New.FEC_MOD_CLIENTE,:New.USU_MOD_CLIENTE,:New.IND_ESTADO,
       :New.EMAIL,:New.COD_LOCAL_ORIGEN,:New.CELL_CLI,:New.COD_TIP_DOCUMENTO,:New.ID_USU_CONFIR,
       sysdate,
        :NEW.COD_LOCAL_CONFIR,:NEW.IP_CONFIR
      );

   end if;


end TR_IU_PBL_CLIENTE ;
/

