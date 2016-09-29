create or replace trigger ptoventa."TRG_I_DET_PED_E"
    before insert on ptoventa.vta_pedido_vta_det
    for each row
declare
    v_flg_exonerado varchar2(1) := 'N';
    v_cant_exonerado number(2) := 0;
    v_cod_cia char(3) := '001';
    v_cant_cod_cia number(2) := 0;
begin
    --ERIOS 12.05.2016 Todas las ventas son exoneradas por CIA. Solicitado por DJARA.
    begin
        select nvl(x.flg_exonerado, 'N')
          into v_flg_exonerado
          from ptoventa.pbl_cia x
         where x.cod_cia = (select cod_cia
                              from ptoventa.pbl_local
                             where cod_grupo_cia = :new.cod_grupo_cia
                               and cod_local = :new.cod_local);
    exception
        when others then
            v_flg_exonerado := 'N';
    end;

    --INI ASOSA - 13/07/2015 - IGVELECTRAZNIA
    /*select count(1)
    into v_cant_cod_cia
    from ptoventa.pbl_local
    where cod_grupo_cia = :new.cod_grupo_cia
    and cod_local = :new.cod_local;

    if v_cant_cod_cia = 1 then
       select nvl(cod_cia,'001')
       into v_cod_cia
       from ptoventa.pbl_local
       where cod_grupo_cia = :new.cod_grupo_cia
       and cod_local = :new.cod_local;
    end if;*/
    --FIN ASOSA - 13/07/2015 - IGVELECTRAZNIA

    -- Tipo de afectacion a igv
    -- Obtener de la tabla maestro_detalle.valor1 (tipo afectacion igv)
    -- Se define si el item es gravado(10), inafecto(30), exonerado(20) y gratuito(31)
    if :new.val_prec_vta = 0 then
        -- Es regalo
        :new.cod_tip_afec_igv_e := '31';
    else
        if :new.ind_exonerado_igv = 'S' and :new.val_igv = 0 then

           --INI ASOSA - 13/07/2015 - IGVELECTRAZNIA
           /*SELECT COUNT(1)
           INTO v_cant_exonerado
           FROM REL_PROD_CIA REL
           WHERE REL.COD_GRUPO_CIA = :new.cod_grupo_cia
           AND REL.COD_CIA = v_cod_cia
           AND REL.COD_PROD = :new.Cod_Prod;

           IF v_cant_exonerado > 0 THEN
               v_flg_exonerado := 'S';
           END IF;*/
           --FIN ASOSA - 13/07/2015 - IGVELECTRAZNIA

            if v_flg_exonerado = 'S' then
                -- Empresa exonerada
                :new.cod_tip_afec_igv_e := '20';
            else
                -- Es inafecto
                :new.cod_tip_afec_igv_e := '30';
            end if;
        else
            -- Es afecto
            :new.cod_tip_afec_igv_e := '10';
        end if;
    end if;

    -- Tipo precio de venta,
    -- obtener de la tabla mestro_detalle.valor1 (tipo precio de venta).
    -- precio unitario tiene igv (01), cuando es gratuito (02), y los exonerados e inafectos son nulos
    if :new.val_prec_vta = 0 then
        :new.cod_tip_prec_vta_e := '02';
    else
        :new.cod_tip_prec_vta_e := '01';
    end if;
/*
    -- Valor de precio venta unitario, precio unitario del item
    :new.val_prec_vta_unit_e := (((:new.val_prec_total + :new.ahorro) / :new.cant_atendida) * :new.val_frac);

    -- Valor de venta unitario por item
    -- es el vta_pedido_vta_det.val_prec_vta_unit_e menos el igv
    if :new.val_igv = 0 then
        :new.val_vta_unit_item_e := :new.val_prec_vta_unit_e;
    else
        :new.val_vta_unit_item_e := trunc(:new.val_prec_vta_unit_e / (1 + (:new.val_igv / 100)), 2);
    end if;

    -- Cantidad de unidades vendidas del item expresado en relacion a la unidad
    :new.cant_unid_vdd_e := trunc((:new.cant_atendida / :new.val_frac), 2);

    -- Valor de venta por item,
    -- Es el calculo: (VALOR DE VENTA - DESCUENTO) * CANTIDAD
    -- :new.val_vta_item_e := (:new.val_vta_unit_item_e * (1 - (:new.porc_dcto_calc / 100))) * :new.cant_unid_vdd_e;
    :new.val_vta_item_e := trunc(:new.val_prec_total / (1 + (:new.val_igv / 100)), 2);

    -- Valor total del igv por item, el igv del monto total del item
    :new.val_total_igv_item_e := trunc(:new.val_vta_item_e * (:new.val_igv / 100), 2);

    -- Valor total descuento por item, el descuento del monto total por item
    --:new.val_total_desc_item_e := trunc((:new.val_vta_unit_item_e * (:new.porc_dcto_calc / 100)), 2);
    :new.val_total_desc_item_e := trunc((:new.ahorro / (1 + (:new.val_igv / 100))), 2);
*/
    -- Descripcion del item, se usa para enviar a la sunat no para la impresion
    :new.desc_item_e := null;

end;
/

