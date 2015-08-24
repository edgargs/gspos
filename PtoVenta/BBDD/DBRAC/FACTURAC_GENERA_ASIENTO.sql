-- No se ha podido presentar el DDL TRIGGER para el objeto MEDCO.FACTURAC_GENERA_ASIENTO mientras que DBMS_METADATA intenta utilizar el generador interno.
CREATE TRIGGER MEDCO.FACTURAC_GENERA_ASIENTO 
 BEFORE
  INSERT OR UPDATE
 ON medco.facturac
REFERENCING NEW AS NEW OLD AS OLD
 FOR EACH ROW
declare
  -- local variables here
  nCnt        number(5);
  nItem       number(4);
  vPeriodo    varchar2(4) := to_char(:new.fec_emi, 'yymm');
  vPeriodoAnt varchar2(4) := to_char(:old.fec_emi, 'yymm');
  vCompro     medco.facturac.compro%type;
  vCuenta     varchar2(7);
  vCONT       Number;
  vPASA       Boolean;
  vDebe       number(12,3);
  vHaber      number(12,3);
  vDebMn      number(12,3);
  vHabMn      number(12,3);
  vDebMe      number(12,3);
  vHabMe      number(12,3);
  vImporte    number(12,3);
  vTotal      number(12,3);
  vDescuento  number(12,3);
  vIgv        number(12,3);
  vIngreso    number(12,3);
  vOtros      number(12,3);
  vDiferencia number(12,2);
  vTipcam     Number;
  vGasto      varchar2(1);
  vIndOpe     varchar2(1);
  vCobrador   varchar2(11) := null;
  vIndCtaCte  varchar2(1);
  vError      varchar2(1000) := null;
  --vFacturable Varchar2(1);
  vGlosa      Varchar2(100) := ' Mot: '||:new.motivo||' - '||substr(:new.glosa1, 1, 30);
  vABONO      Number(12,2);

begin
   ---------------------------------------------------------
   -- Validando NOTAS DE ABONO Y CARGO
     if :new.tip_doc='NA' and :new.imp_total>0 then
         :new.inafecto :=ABS(:new.inafecto)*-1;
         :new.imp_total:=ABS(:new.imp_total)*-1;
         :new.imp_bruto:=ABS(:new.imp_bruto)*-1;
         :new.imp_desg :=ABS(:new.imp_desg)*-1;
         :new.imp_desb :=ABS(:new.imp_desb)*-1;
     elsif :new.tip_doc='NC' and :new.imp_total<0   THEN
         :new.inafecto:=ABS(:new.inafecto);
         :new.imp_total:=ABS(:new.imp_total);
         :new.imp_bruto:=ABS(:new.imp_bruto);
         :new.imp_desg :=ABS(:new.imp_desg);
         :new.imp_desb :=ABS(:new.imp_desb);
     end if;
    -----------------------------------------------------
       -- graba Ubigeo en Facturac
    -----------------------------------------------------
    if :new.ubigeo is null then

       if nvl(:new.anexo,'000')='000' then
         begin
              select ubigeo
              into :new.ubigeo
              from medco.cta_cte c
              where c.codigo=:new.ruc ;
              exception

              when others then
              :new.ubigeo:=null;
         end;
       else
         begin
            select ubigeo
            into :new.ubigeo
            from medco.cta_cted c
            where c.codigo=:new.ruc
            and   c.anexo = :new.anexo;
            exception

         when others then
             :new.ubigeo:=null;
         end;
       end if;
    end if;
    --------------------------------------------------------------------
    if nvl(:new.por_igv,0) not in (0.19,0.18,0) and :new.estado<>'A'  then
       RAISE_APPLICATION_ERROR(-20001,'Porcentaje de Igv no Valido, Revise Documento');
       return;
    end if;
    -- KMONCADA 21.10.2014 APLIQUE PARA TODAS LAS CIAS
    if medco.pack_cta_cte.jala_codigo_sap(:new.ruc) is null and :new.cia <> '11' then
       RAISE_APPLICATION_ERROR(-20001, chr(13)||chr(13)||'Cliente sin codigo SAP, Revise Cliente'||chr(13)||
                                       'RUC/DNI: '||:new.ruc||chr(13)||
                                       'RAZ.SOC: '||medco.pack_cta_cte.jala_cliente(:new.ruc)||chr(13)||chr(13));
       return;
    end if;
    
    --------------------------------------------------------------------
     :new.periodo:=to_char(:new.fec_emi,'YYMM');
    --------------------------------------------------------------------
    If Updating and nvl(:old.estado,' ')<> nvl(:new.estado,' ') Then
      :new.usuario:= USER;
      :new.fecha  := SYSDATE;
    end if;
    --------------------------------------------------------------------
    if :new.estado='A' then
     Begin
        Select sum( nvl(abono,0) )
        into   vAbono
        from medco.saldos_cte
        where cia=:new.cia
        and   cta_cte=:new.ruc
        and   tip_doc=:new.tip_doc
        and   nro_doc=:new.nro_doc;
      -------
      if vAbono<>0 then
         RAISE_APPLICATION_ERROR(-20001,'Documento con Abonos No se puede Anular : '||vABONO);
         return;
      end if;
       ---------------------------------------------------------
        Exception
        When Others Then
           vERROR := SQLERRM;
           RAISE_APPLICATION_ERROR(-20001,vERROR);
         RETURN;
       end;
      ---------------------------------------------------------
      :new.inafecto:=0;
      :new.imp_total:=0;
      :new.imp_bruto:=0;
      :new.imp_desg:=0;
      :new.imp_igv:=0;
      :new.imp_desb:=0;
      :new.glosa2  :='DOCUMENTO ANULADO POR '||USER;

   end if;
   ---------------------------------------------------------

  if vError is not null then
    RAISE_APPLICATION_ERROR(-20001,vError);
  return;
  end if;

  --- Revisando tipos de Cambio /// Paul
   Begin
     select tco_venta
     into vTipcam
     from medco.tipcam
     where fecha=:new.fec_emi;
   Exception
     When Others Then
      vTipcam:=0;
   End;
   if nvl(vTipcam,0)=0 then
      :new.tip_cam := 1 ;
   else
      :new.tip_cam:=vTipcam;
   end if;
 --- Revisando Cuadre de Base Imponible // Paul
/*   Begin
      select Facturable into vFacturable
      from medco.motivos
      where motivo=:new.motivo;
   Exception
     When Others Then
        vFacturable:='N';
   end;
   ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   vImp_igv:= round( (:new.imp_bruto - :new.imp_desg - :new.imp_desb-
                       nvl(:new.inafecto,0) + nvl(:new.imp_fle,0)+
                       nvl(:new.imp_seg,0) ) * :new.por_igv,2);
   vDiferencia:= ABS( vImp_igv - round(:new.imp_igv,2) )     ;
   -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   if vDiferencia > 0.06 and vFacturable='S' And :new.estado<>'A' Then
      RAISE_APPLICATION_ERROR(-20001,'Base Imponible No Cuadra con el Igv, Avisar a Sistemas '||:NEW.TIP_DOC||'-'||:NEW.NRO_DOC||' Monto de Diferencia: '|| vDiferencia );
      return;
   end if ;*/
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- si cambia el compro entonces hay que eliminar el asiento antiguo
  if :old.compro is not null then

    begin
      delete medco.asientosd
    where  cia     = :old.cia
    and    periodo = vPeriodoAnt
    and    compro  = :old.compro;
    exception when others then vError := 'DEL-asientosd:'||sqlerrm;
    end;

    if vError is not null then
      RAISE_APPLICATION_ERROR(-20001,vError);
    return;
    end if;

    if (:new.compro is null) or (:new.compro <> :old.compro) then
      begin
        update medco.asientosc
         set    estado  = 'A',
        usuario = substr(user,1,10),
        fecha   = sysdate
      where  cia     = :old.cia
      and    periodo = vPeriodoAnt
      and    compro  = :old.compro;
      exception when others then vError := 'UPD-asientosc:'||sqlerrm;
      end;
  end if;

    if vError is not null then
      RAISE_APPLICATION_ERROR(-20001,vError);
    return;
    end if;

  end if;
  -- si cambia el compro entonces hay que eliminar el asiento antiguo
  -- si es nueva factura -> asignar compro
  --- a modo de prueba vamos a hacer 3 intentos como maximo ... paul 16/03/2006
 vCONT:=0;
 vPASA:=FALSE;

 Loop

    if inserting and :new.compro is null then

        PACK_NUMERA.NUMERACION(:new.cia, '08', vPeriodo, vCompro);
        if vCompro is null then
           vError := 'No se logro obtener el correlativo para el asiento. Avise a Sistemas'||:new.cia||'|'||'08'||'|'||vPeriodo||'|'||vCompro;
        else
           :new.compro := '08'||LPAD(vCompro,6,'0');
           vCompro := lpad(vCompro+1, 6, '0');
           PACK_NUMERA.ACTUALIZA_NRO_REF(:new.cia, '08', vPeriodo, vCompro);
        end if;

     end if;

     -- verificamos que no se le haya asignado un compro usado por otro documento
     nCnt := 0;
     begin
        select count(*)
        into   nCnt
        from   medco.asientosc
        where  cia     = :new.cia
        and    periodo = vPeriodo
        and    compro  = :new.compro
        and not (cia     = :new.cia
        and      contabi = :new.tip_doc
        and      ncheque = :new.nro_doc);
     exception when others then null;
     end;


      -- si encontramos una factura con el mismo compro -> detenemos la grabacion
      if nvl(nCnt, 0) <> 0 then
         vCONT:=vCONT+1;
         :new.compro := Null;
         vPASA:= FALSE;
      else
         vPASA := TRUE;
       end if;
      -- verificamos que no se le haya asignado un compro usado por otro documento
      EXIT WHEN vPASA= TRUE OR VCONT=3 ;
 END LOOP;
 ------
 If vCONT=3 Then
    RAISE_APPLICATION_ERROR(-20001,'Se intento asignar un correlativo 3 Vecesa , pero el  asiento esta usado por otro documento. Avise a sistemas.'||:new.cia||'|'||'08'||'|'||vPeriodo||'|'||vCompro);
    return;
 end if;
  -- eliminamos el asiento antiguo
  begin
    delete medco.asientosd
  where  cia     = :new.cia
  and    periodo = vPeriodo
  and    compro  = :new.compro;
  exception when others then vError := 'DEL-asientosd:'||sqlerrm;
  end;

  if vError is not null then
    RAISE_APPLICATION_ERROR(-20001,vError);
  return;
  end if;

  if :new.estado = 'A' then
    begin
      update medco.asientosc
    set    estado  = 'A',
           usuario = substr(user,1,10),
       fecha   = sysdate
    where  cia     = :new.cia
    and    periodo = vPeriodo
    and    compro  = :new.compro;
    exception when others then vError := 'UPD-asientosc:'||sqlerrm;
    end;
  else
    begin
      delete medco.asientosc
    where  cia     = :new.cia
    and    periodo = vPeriodo
    and    compro  = :new.compro;
    exception when others then vError := 'DEL-asientosc:'||sqlerrm;
    end;
  end if;

  if vError is not null then
    RAISE_APPLICATION_ERROR(-20001,vError);
  return;
  end if;
  -- eliminamos el asiento antiguo

  -- volvemos a crear el asiento
  if :new.imp_total <> 0 and :new.estado <> 'A' then

/*    if nvl(:new.tip_cam, 0) <= 0 then
      vError := 'Tipo de Cambio no ha sido ingresado';
      RAISE_APPLICATION_ERROR(-20001,'PKG-graba_asientosc:'||vError);
      return;
    end if;*/

  -- grabamos asientos cabecera
  pack_asientos.graba_asientosc(
                                :new.cia, vPeriodo, :new.compro, 'T',
                                :new.motivo||' - ASIENTO VENTAS '||:new.tip_doc||'-'||:new.nro_doc,
                    :new.fec_emi, :new.tip_cam,
                  ( :new.imp_total + :new.imp_desg + :new.imp_desb ),
                  ( :new.imp_bruto + :new.imp_igv  + nvl(:new.imp_fle,0) ),
                  ( :new.imp_total + :new.imp_igv  + :new.imp_desg + :new.imp_desb )/:new.tip_cam,
                  ( :new.imp_bruto + :new.imp_igv  + nvl(:new.imp_fle,0) )/:new.tip_cam,
                  :new.nro_doc, '1', :new.tip_doc, :new.usuario, sysdate, vError
                 );

  if vError is not null then
      RAISE_APPLICATION_ERROR(-20001,'PKG-graba_asientosc:'||vError);
    return;
  end if;

    -- grabamos asientos detalle
  nItem := 0;
  for i in 1..5 loop
    --dbms_output.put_line(i);
    vGlosa := ' Mot: '||:new.motivo||' - '||substr(:new.glosa1, 1, 30);
    -------------------------------------------------------------------------------------------------
    if i = 1 then -- PARA CUENTA CORRIENTE
    --dbms_output.put_line(to_char(i) || 'PARA CUENTA CORRIENTE');
    vTotal   := round(:new.imp_total,2);
    vImporte := vTotal;
    vCuenta  := pack_asientos.cuenta('08', :new.motivo, 'MN');
    if :new.moneda <> 'S' then
      vTotal  := vTotal*:new.tip_cam;
      vCuenta := pack_asientos.cuenta('08', :new.motivo, 'ME');
    end if;
    end if;
      --------------------------------------------------------------------------------------------------
    if i = 2 then -- PARA DESCUENTOS
    --dbms_output.put_line(to_char(i) || 'PARA DESCUENTOS');
    vDescuento := round(:new.imp_desg + :new.imp_desb, 2);
    vImporte   := vDescuento;
    vCuenta    := pack_asientos.cuenta('08', :new.motivo, 'DS');
    if :new.moneda <> 'S' then
      vDescuento := vDescuento*:new.tip_cam;
    end if;
    end if;
      --------------------------------------------------------------------------------------------------
    if i = 3 then --- PARA IGV
    --dbms_output.put_line(to_char(i) || 'PARA IGV');
       vIgv     := round(:new.imp_igv, 2);
      vImporte := (-1)*vIgv;
      vCuenta  := pack_asientos.cuenta('08', :new.motivo, 'IM');
      if :new.moneda <> 'S' then
        vIgv := vIgv*:new.tip_cam;
       end if;
    end if;
      --------------------------------------------------------------------------------------------------
      --- AQUI HAY UNA DIFERENCIA 70101 AFECTO 70102 INAFECTO --PAUL
      ---
    if i = 5 then --- PARA INGRESO
    --dbms_output.put_line(to_char(i) || 'PARA INGRESO');

       vIngreso := round(:new.imp_bruto,2)+ nvl(:new.redondeo,0); --dbms_output.put_line(vIngreso);
      vImporte := (-1)*vIngreso; --dbms_output.put_line(vImporte);
      --- en esta parte o es inafecto o es afecto solo 1 de dos --pbc
      If abs(:new.inafecto)>0 then
         vCuenta  := pack_asientos.cuenta('08', :new.motivo, 'VI'); --dbms_output.put_line('VI'||vCuenta);
      else
         vCuenta  := pack_asientos.cuenta('08', :new.motivo, 'IN'); --dbms_output.put_line('IN'||vCuenta);
      end if;
      if :new.moneda <> 'S' then
        vIngreso := vIngreso*:new.tip_cam; --dbms_output.put_line(:new.moneda||vIngreso);
      end if;
      vDiferencia := (vTotal + vDescuento) - (vIgv + vIngreso + vOtros); --dbms_output.put_line('Diferencia'||vDiferencia);
      if abs(vDiferencia) < 0.05 then
         vImporte := vImporte - vDiferencia; --dbms_output.put_line('Importe'||vImporte);
      end if;
    end if;
      --------------------------------------------------------------------------------------------------
    if i = 4 then -- PARA FLETE
    --dbms_output.put_line(to_char(i) || 'PARA FLETE');
       vOtros := nvl(:new.imp_fle,0)  + nvl(:new.imp_seg,0);
      vImporte := (-1)*vOtros;
      vCuenta  := pack_asientos.cuenta('08', :new.motivo, 'VD');
      if :new.moneda <> 'S' then
        vOtros := vOtros*:new.tip_cam;
      end if;
      vGlosa := 'Flete y/o Seguro, Motivo  '||:new.Motivo;
    end if;
      --------------------------------------------------------------------------------------------------

    if vImporte <> 0 then

    vIndOpe := 'D';
    vDebe   := round(vImporte,2);
    vHaber  := 0;
    if vImporte < 0 then
      vIndOpe := 'H';
      vDebe   := 0;
      vHaber  := (-1)*round(vImporte,2);
    end if;

    vDebMn := vDebe;
    vHabMn := vHaber;
    vDebMe := 0;
    vHabMe := 0;
    if :new.moneda <> 'S' then
      vDebMn := vDebMn*:new.tip_cam;
      vHabMn := vHabMn*:new.tip_cam;
      vDebMe := vDebe;
      vHabMe := vHaber;
    end if;

    if vCuenta like '9%' then
      vGasto  := 'S';
    end if;


    -- validacion de la cuenta contable
    if vCuenta is null then
      vError := 'No se Asigno correctamente la Cuenta Contable en los asientos Tipo : Motivo '||:new.motivo ||'-'||i||' Avise a Contabilidad '||:new.inafecto;
          RAISE_APPLICATION_ERROR(-20001,'PKG-asientos-cuenta:'||vError);
        return;
    end if;
    -- validacion de la cuenta contable

    pack_ctactb.flag_analcta(vCuenta, vIndCtaCte, vError);

      if vError is not null then
          RAISE_APPLICATION_ERROR(-20001,'PKG-flag_analcta:'||vError);
        return;
      end if;

    nItem := nItem + 1;

    pack_asientos.graba_asientosdnn(
                                   :new.cia, vPeriodo, :new.compro, lpad(nItem,4,' '), vCuenta,
                     vGlosa,
                     :new.ruc, vGasto, vIndOpe,
                     vDebMn, vHabMn, vDebMe, vHabMe, 
                     :new.nro_doc, :new.fec_emi, :new.fec_ven, :new.nro_doc,
                     null, null, null,
                     :new.cencos, :new.tip_cam, :new.tip_doc, vIndCtaCte,
                     null, 'T', null, :new.moneda, null, lpad(nItem,4,' '),
                     :new.con_vta, null, :new.fec_emi, :new.tipo,
                     :new.motivo, vCobrador, null, '1', '1', :new.beneficiario,
                     :new.convenio,vError
                    );

      if vError is not null then
          RAISE_APPLICATION_ERROR(-20010,'PKG-graba_asientosdn:'||vError);
        return;
      end if;
      --dbms_output.put_line(to_char(i) || 'FIN');
    end if;

  end loop;

  if nItem < 2 then
    vError := 'Se intento generar un asiento con menos de 2 items. Avise a sistemas';
      RAISE_APPLICATION_ERROR(-20001,'TRG-asientos_ventas:'||vError);
    return;
  end if;

  -- ajustamos la diferencia
  PACK_genera_asientosd.ajuste_dif1(:NEW.CIA,vPeriodo, :new.compro, vError);

  if vError is not null then
      RAISE_APPLICATION_ERROR(-20001,'PKG-ajuste_dif:'||vError);
    return;
  end if;

  end if;
  -- volvemos a crear el asiento

end;
