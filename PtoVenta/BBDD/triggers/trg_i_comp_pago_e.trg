CREATE OR REPLACE TRIGGER PTOVENTA."TRG_I_COMP_PAGO_E"
    before insert on ptoventa.vta_comp_pago
    for each row

begin
  --ERIOS 06.05.2015 No actualiza datos para NCR
  IF :new.tip_comp_pago = '04' THEN
    RETURN;
  END IF;
 DECLARE
   v_cTipPedVta CHAR(2);
   v_lais_chanka varchar2(1500);
    begin
    --OBTENER EL TIPO DE PEDIDO DE VENTA
      SELECT NVL(CAB.TIP_PED_VTA,'0')
      INTO v_cTipPedVta
      FROM VTA_PEDIDO_VTA_CAB CAB
      WHERE CAB.COD_GRUPO_CIA=:new.cod_grupo_cia
      AND CAB.COD_LOCAL=:new.cod_local
      AND CAB.NUM_PED_VTA=:new.num_ped_vta;

        IF :new.tip_clien_convenio = '2' THEN --EMPRESA
              IF v_cTipPedVta ='03' THEN -- VENTA MAYORISTA
                select substr(trim(CAB.NOM_CLI_PED_VTA), 1, 120),
                       trim(CAB.RUC_CLI_PED_VTA),
                       trim(CAB.DIR_CLI_PED_VTA)
                  into :new.nom_impr_comp,
                       :new.num_doc_impr,
                       :new.direc_impr_comp
                  from vta_pedido_vta_cab CAB
                WHERE CAB.COD_GRUPO_CIA=:new.cod_grupo_cia
                AND CAB.COD_LOCAL=:new.cod_local
                AND CAB.NUM_PED_VTA=:new.num_ped_vta;

              ELSE

                select substr(trim(institucion), 1, 120),
                       trim(ruc),
                       trim(direccion)
                  into :new.nom_impr_comp,
                       :new.num_doc_impr,
                       :new.direc_impr_comp
                  from mae_convenio v
                 inner join vta_pedido_vta_cab z
                    on z.cod_convenio = v.cod_convenio
                 where z.cod_grupo_cia = :new.cod_grupo_cia
                   and z.cod_local = :new.cod_local
                   and z.num_ped_vta = :new.num_ped_vta;
              END IF ;
          END IF;

          IF :new.tip_clien_convenio = '1' then --BENEFICIARIO
             /*BEGIN
             select tt.descripcion_campo,tt.cod_valor_in
              into  :new.nom_impr_comp, :new.num_doc_impr
              from   con_btl_mf_ped_vta tt
              where  cod_campo = 'D_000' --CODIGO DNI
              and    tt.cod_grupo_cia = :new.cod_grupo_cia
              and    tt.cod_local = :new.cod_local
              and    tt.num_ped_vta =  :new.num_ped_vta;
            EXCEPTION
              WHEN NO_DATA_FOUND THEN*/

                  IF :new.tip_comp_pago = '01' OR :new.tip_comp_pago='05' THEN
                    BEGIN
                      select CAB.RUC_CLI_PED_VTA, CAB.NOM_CLI_PED_VTA, CAB.DIR_CLI_PED_VTA
                      into   :new.num_doc_impr, :new.nom_impr_comp,:new.direc_impr_comp
                      from   VTA_PEDIDO_VTA_CAB CAB
                      WHERE  CAB.COD_GRUPO_CIA = :new.cod_grupo_cia
                      AND    CAB.COD_LOCAL = :new.cod_local
                      AND    CAB.NUM_PED_VTA = :new.num_ped_vta;
                    EXCEPTION
                      WHEN NO_DATA_FOUND THEN
                        select tt.descripcion_campo,tt.cod_valor_in
                        into  :new.nom_impr_comp, :new.num_doc_impr
                        from   con_btl_mf_ped_vta tt
                        where  cod_campo = 'D_000' --CODIGO DNI
                        and    tt.cod_grupo_cia = :new.cod_grupo_cia
                        and    tt.cod_local = :new.cod_local
                        and    tt.num_ped_vta =  :new.num_ped_vta;
                    END;
                  ELSE
                    select substr(trim(institucion), 1, 120),
                           trim(ruc),
                           trim(direccion)
                      into :new.nom_impr_comp,
                           :new.num_doc_impr,
                           :new.direc_impr_comp
                      from mae_convenio v
                     inner join vta_pedido_vta_cab z
                        on z.cod_convenio = v.cod_convenio
                     where z.cod_grupo_cia = :new.cod_grupo_cia
                       and z.cod_local = :new.cod_local
                       and z.num_ped_vta = :new.num_ped_vta;
                  END IF;

                --:new.nom_impr_comp := ' ';
                --:new.num_doc_impr := ' ';
--            END;
           END IF ;

           IF length(trim(:new.num_doc_impr))>0 THEN
             select decode(length(trim(:new.num_doc_impr)), 11, '6', 8, '1', '0')
             into :new.cod_tip_ident_recep_e
             from dual;
           ELSE
             :new.cod_tip_ident_recep_e := '1';
           END IF;

   exception
       when others then
           raise_application_error(-20000, sqlerrm||'-'||:new.num_doc_impr||'-'||:new.cod_tip_ident_recep_e||'-'||v_cTipPedVta||'-'||:new.tip_clien_convenio
           ||'-'||:new.num_ped_vta);
    end;
end;
/

