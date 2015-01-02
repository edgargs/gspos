create or replace package ptoventa.PKG_OPERACIONES_CONTA as
procedure SP_VALIDA_VB (cFecha char, message OUT VARCHAR2, message_error OUT VARCHAR2);
procedure SP_RETIRO_VB (cCodLocal char, cFecha char);
procedure SP_VALIDA_SOBRE (cFecha char, message OUT VARCHAR2, message_error OUT VARCHAR2);
procedure SP_VALIDA_SOBRE_2 (cSobre varchar2, message OUT VARCHAR2, motivo OUT VARCHAR2);
procedure SP_ELIMINA_SOBRE (CodSobre VARCHAR2,CodLocal char);
procedure SP_VALIDA_REMITO (cFecha char, message OUT VARCHAR2, message_error OUT VARCHAR2, motivo OUT VARCHAR2);
procedure SP_ELIMINA_REMITO (CodLocal CHAR,CodRemito VARCHAR2);
end PKG_OPERACIONES_CONTA;
/

create or replace package body ptoventa.PKG_OPERACIONES_CONTA as
procedure SP_VALIDA_VB (cFecha char, message OUT VARCHAR2, message_error OUT VARCHAR2) is
BEGIN
DECLARE
v_Fecha char(10):=cFecha;
v_Cod_local varchar2(30);
v_Fec_Cierre date;
v_Ind_Dia char(1);
v_Ind_Conta char(1);
Dia number(1);
BEGIN

-- EMAQUERA -- Se valida que el cierre de dia este creado y que se encuentre dentro del rango de 60 atras
  SELECT COUNT(*) INTO Dia FROM CE_CIERRE_DIA_VENTA
  WHERE FEC_CIERRE_DIA_VTA=v_Fecha AND FEC_CIERRE_DIA_VTA>=SYSDATE-60;

  IF Dia > 0 THEN
-- EMAQUERA -- Se verifica que tenga VB dia y/o contable
    SELECT COD_LOCAL, FEC_CIERRE_DIA_VTA, IND_VB_CIERRE_DIA, IND_VB_CONTABLE
    INTO v_Cod_local, v_Fec_Cierre, v_Ind_Dia, v_Ind_Conta
    FROM CE_CIERRE_DIA_VENTA
    WHERE FEC_CIERRE_DIA_VTA=v_Fecha;

    IF v_Ind_Dia = 'S' THEN

      IF v_Ind_Conta = 'S' THEN
         message:='VBCONTABLE';
      ELSE
         message:='VBDIA';
      END IF;

    ELSE
        message:='<B>LA FECHA '||v_Fecha||' NO TIENE VB DE DIA.</B>';
    END IF;

  ELSE
    message:='<B>LA FECHA NO EXISTE O NO SE ENCUENTRA DENTRO DEL RANGO.</B>';
  END IF;
EXCEPTION
WHEN OTHERS THEN
  message_error:='<B>PROBLEMA DE CONEXION. FAVOR DE INTENTARLO EN UNOS MINUTOS.</B>';
  DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
COMMIT;
END;
--***** FIN DE SP_VALIDA_VB **********************************************************************************************
--************************************************************************************************************************
procedure SP_RETIRO_VB (cCodLocal char, cFecha char) is
BEGIN
DECLARE
cursor cr1 is
 select *
 from   ce_cierre_dia_venta c
 where c.cod_grupo_cia='001'
 and c.cod_local=cCodLocal
 and c.fec_cierre_dia_vta=cFecha;

 R1  cr1%ROWTYPE;
-- SQLT VARCHAR2(32000);
 XCANT NUMBER;
 INDICADOR_NO char(1):='N';
 INDICADOR_SI char(1):='S';
-- email_local pbl_local.mail_local%type;
 xsec_mov_caja ce_mov_caja.sec_mov_caja%type;
-- excepciones
time_out EXCEPTION;
PRAGMA EXCEPTION_INIT(time_out, -12170);

bloqueo EXCEPTION;
PRAGMA EXCEPTION_INIT(bloqueo, -02049);

deadlock_detected EXCEPTION;
PRAGMA EXCEPTION_INIT(deadlock_detected , -00060);
BEGIN
open cr1;
loop
  fetch cr1 into r1;
  exit when cr1%notfound;
--  select l.mail_local into email_local from pbl_local l where cod_local=r1.cod_local;
dbms_output.put_line('Cursor');
    begin
      select count(1) into xcant
      from CE_CIERRE_DIA_VENTA
      where cod_grupo_cia='001'
      and   cod_local=r1.cod_local
      and   fec_cierre_dia_vta=r1.fec_cierre_dia_vta
      and   IND_VB_CONTABLE  ='S'
      and   ind_vb_cierre_dia='S';

      if xcant=1 then

             UPDATE CE_CIERRE_DIA_VENTA C
             SET C.IND_VB_CONTABLE = INDICADOR_NO,
                 C.IND_ENVIO_LOCAL = INDICADOR_NO,
                 C.FEC_VB_CONTABLE = NULL,
                 C.USU_MOD_CIERRE_DIA = 'SQL_VB_CONT'
             WHERE  C.COD_GRUPO_CIA  = '001'
             AND    C.COD_LOCAL = r1.cod_local
             AND    C.FEC_CIERRE_DIA_VTA =r1.fec_cierre_dia_vta;

      end if;
    end;
    begin
      select count(1) into xcant
      from CE_CIERRE_DIA_VENTA
      where cod_grupo_cia='001'
      and   cod_local=r1.cod_local
      and   fec_cierre_dia_vta=r1.fec_cierre_dia_vta
      and   IND_VB_CONTABLE  ='N'
      and   ind_vb_cierre_dia='S';

      if (xcant=1) then
          BEGIN PTOVENTA_CE_LMR.CE_ACTUALIZA_VB_CIERRE_DIA('001',r1.cod_local,TO_CHAR(r1.fec_cierre_dia_vta,'DD/MM/YYYY'),'N',' ',' ','CONTABILIDAD'); END;
          DELETE FROM CE_CUADRATURA_CIERRE_DIA CD
      	  WHERE CD.COD_GRUPO_CIA = '001'
      	  AND   CD.COD_LOCAL = r1.cod_local
      	  AND   CD.FEC_CIERRE_DIA_VTA= r1.fec_cierre_dia_vta
      	  AND   CD.COD_CUADRATURA= '022';

      end if;
    end;

end loop;
close cr1;
EXCEPTION
WHEN time_out THEN
    DBMS_OUTPUT.PUT_LINE('TIMEOUT A BD. ESPERE UNOS MINUTOS...');
--    message_error:='TIMEOUT A BD. ESPERE UNOS MINUTOS...';
    rollback;
WHEN bloqueo THEN
    DBMS_OUTPUT.PUT_LINE('RECURSO OCUPADO. ESPERE UNOS MINUTOS...');
--    message_error:='RECURSO OCUPADO. ESPERE UNOS MINUTOS EN CASO CONTINUE ESTE PROBLEMA REPORTAR AL OPERADOR';
    rollback;
WHEN deadlock_detected THEN
    DBMS_OUTPUT.PUT_LINE('RECURSO OCUPADO. ESPERE UNOS MINUTOS...');
--    message_error:='RECURSO OCUPADO. ESPERE UNOS MINUTOS...';
    rollback;
WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('ORA'||SQLCODE||'-'||SQLERRM);
--    message_error:='ORA'||SQLCODE||'-'||SQLERRM;
    rollback;
END;
END;
--***** FIN DE SP_RETIRO_VB **********************************************************************************************
--************************************************************************************************************************
procedure SP_VALIDA_SOBRE (cFecha char, message OUT VARCHAR2, message_error OUT VARCHAR2) is
BEGIN
DECLARE
v_Fecha char(10):=cFecha;
v_Cod_local char(3);
Ind_Sobre number(1);
Ind_Fecha number(3);
Cant number(3);
BEGIN
   SELECT DISTINCT COD_LOCAL INTO v_Cod_local FROM VTA_IMPR_LOCAL;
-- EMAQUERA -- Se valida que el local genere sobre
  SELECT COUNT(*) INTO Ind_Sobre FROM PBL_TAB_GRAL A
  WHERE A.ID_TAB_GRAL=317 AND A.LLAVE_TAB_GRAL='S';

  IF Ind_Sobre > 0 THEN
-- EMAQUERA -- Se verifica que tenga VB dia y/o contable
    SELECT COUNT(DISTINCT FEC_DIA_VTA) INTO Ind_Fecha FROM CE_MOV_CAJA
    WHERE FEC_DIA_VTA=v_Fecha AND FEC_DIA_VTA>SYSDATE-10;

    IF Ind_Fecha > 0 THEN
       SELECT COUNT(*) INTO Cant FROM
      (SELECT DISTINCT FEC_DIA_VTA FROM CE_SOBRE
       WHERE FEC_DIA_VTA>=SYSDATE-10
       UNION
       SELECT DISTINCT FEC_DIA_VTA FROM CE_SOBRE_TMP
       WHERE FEC_DIA_VTA>=SYSDATE-10) N
       WHERE N.FEC_DIA_VTA=v_Fecha;

       IF Cant > 0 THEN
          message:='OK';
       ELSE
          message:='<B>EL LOCAL NO HA GENERADO SOBRES PARA LA FECHA '||v_Fecha||'</B>';
       END IF;

--       DBMS_OUTPUT.PUT_LINE('DATOS VALIDADOS');

    ELSE
        message:='<B>LA FECHA '||v_Fecha||' NO ENCUENTRA DENTRO DEL RANGO DE 10 ATRAS.</B>';
    END IF;

  ELSE
    message:='<B>NO ESTA ACTIVA LA OPCION DE SOBRES EN LOCAL '||v_Cod_local||'.</B>';
  END IF;
EXCEPTION
WHEN OTHERS THEN
  message_error:='<B>PROBLEMA DE CONEXION. FAVOR DE INTENTARLO EN UNOS MINUTOS.</B>';
  DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
COMMIT;
END;
--***** FIN DE SP_VALIDA_SOBRE *******************************************************************************************
--************************************************************************************************************************
procedure SP_VALIDA_SOBRE_2 (cSobre varchar2, message OUT VARCHAR2, motivo OUT VARCHAR2) is
BEGIN
DECLARE
v_Sobre char(12):=cSobre;
v_Cod_local char(3);
Remito varchar2(12);
v_Fecha varchar2(20);
v_Cod_sobre varchar2(15);
v_Forma_Pago varchar2(10);
v_Monto varchar2(8);
v_Usu varchar2(20);
BEGIN
   SELECT DISTINCT COD_LOCAL INTO v_Cod_local FROM VTA_IMPR_LOCAL;
-- EMAQUERA -- Se valida que el sobre no este asociado a un remito
  SELECT A.COD_REMITO INTO Remito FROM
  (SELECT COD_SOBRE, COD_REMITO FROM CE_SOBRE
   WHERE FEC_DIA_VTA>=SYSDATE-10
   UNION
   SELECT COD_SOBRE, COD_REMITO FROM CE_SOBRE_TMP
   WHERE FEC_DIA_VTA>=SYSDATE-10
  ) A
  WHERE A.COD_SOBRE=cSobre;

    IF Remito IS NULL THEN

      SELECT TO_CHAR(N.FEC_DIA_VTA,'DD/MM/YYYY'), N.COD_SOBRE, N.FORMA_PAGO, N.MONTO, N.USU_CREA_SOBRE
      INTO v_Fecha, v_Cod_sobre, v_Forma_Pago, v_Monto, v_Usu  FROM (
      SELECT A.FEC_DIA_VTA, A.COD_SOBRE ,
             DECODE(B.TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES') FORMA_PAGO,
             TRIM(TO_CHAR(NVL(CASE
                           WHEN B.TIP_MONEDA = '01' THEN
                            B.MON_ENTREGA_TOTAL
                           WHEN B.TIP_MONEDA = '02' THEN
                            B.MON_ENTREGA
                         END,
                         0),
                     '999,999,990.00')) MONTO,
             NVL(B.USU_CREA_FORMA_PAGO_ENT,' ') USU_CREA_SOBRE
        FROM CE_SOBRE A, CE_FORMA_PAGO_ENTREGA B
         WHERE A.COD_GRUPO_CIA = '001'
         AND A.COD_LOCAL = v_Cod_local
         AND A.FEC_DIA_VTA >SYSDATE-10
         AND A.COD_GRUPO_CIA = B.COD_GRUPO_CIA
         AND A.COD_LOCAL = B.COD_LOCAL
         AND A.SEC_MOV_CAJA = B.SEC_MOV_CAJA
         AND A.SEC_FORMA_PAGO_ENTREGA = B.SEC_FORMA_PAGO_ENTREGA
         UNION
         SELECT FEC_DIA_VTA, COD_SOBRE, DECODE(TIP_MONEDA, '01', 'SOLES', '02', 'DOLARES'),
                TRIM(TO_CHAR(NVL(CASE
                     WHEN TIP_MONEDA = '01' THEN
                      MON_ENTREGA_TOTAL
                     WHEN TIP_MONEDA = '02' THEN
                      MON_ENTREGA
                   END,
                   0),
               '999,999,990.00')),
               USU_CREA_SOBRE
         FROM CE_SOBRE_TMP
         WHERE COD_GRUPO_CIA = '001'
         AND COD_LOCAL = v_Cod_local
         AND FEC_DIA_VTA >SYSDATE-10 ) N
         WHERE N.COD_SOBRE=cSobre;

       message:='ANULAR';
       motivo:='<br>Fecha Sobre : '||v_Fecha||'<br>Nro Sobre : '||v_Cod_sobre||'<br>Forma Pago : '||v_Forma_Pago||'<br>Monto : '||v_Monto||'<br>Usuario : '||v_Usu;
       DBMS_OUTPUT.PUT_LINE('DATOS VALIDADOS 2');

    ELSE
        message:='<B>EL SOBRE '||cSobre||' SE ENCUENTRA ASOCIADO AL REMITO NRO '||Remito||'.</B>';
    END IF;

EXCEPTION
WHEN OTHERS THEN
  message:='<B>PROBLEMA DE CONEXION. FAVOR DE INTENTARLO EN UNOS MINUTOS.</B>';
  DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
COMMIT;
END;
--***** FIN DE SP_VALIDA_SOBRE *******************************************************************************************
--************************************************************************************************************************
PROCEDURE SP_ELIMINA_SOBRE (CodSobre VARCHAR2,CodLocal char)
IS
BEGIN
declare
nCodSobre  VARCHAR2(12) := CodSobre;
cCodLocal_in char(3)    := CodLocal;
-- variables para local
nCantSobres_tmp number;
nCantSobres number;

nCodCia char(3);
nLocal  char(3);
nSecMovCaja CHAR(10);
nSecFormaPago NUMBER(10);

-- excepciones
time_out EXCEPTION;
PRAGMA EXCEPTION_INIT(time_out, -12170);

deadlock_detected EXCEPTION;
PRAGMA EXCEPTION_INIT(deadlock_detected , -00060);
--
V_SQL varchar2(32000);

vSobreLocal number;

begin

		select count(1)
		into   nCantSobres
		from   ce_sobre T
		WHERE  trim(T.COD_SOBRE) = trim(nCodSobre)
		and    t.cod_local = cCodLocal_in
		and    t.cod_remito is null;

		select count(1)
		into   nCantSobres_tmp
		from   ce_sobre_tmp T
		WHERE  trim(T.COD_SOBRE) = trim(nCodSobre)
		and    t.cod_local = cCodLocal_in
		and    t.cod_remito is null;

--    message:='ELIMINACION DE SOBRE EN MATRIZ.<br><hr>';
--		message:=message||'Cant_Sobres = '||nCantSobres||'<br>';
--		message:=message||'Cant_Sobres_Parciales = '||nCantSobres_tmp||'<br>';

		if nCantSobres_tmp > 0 then
--		   message:=message||'Eliminando Sobre En Temporal'||'<br>';
		   delete ce_sobre_tmp WHERE  COD_SOBRE = nCodSobre;
--       message:=message||'Fin de Eliminar en Temporal'||'<br>';
		end if;

		if nCantSobres > 0 then

--		   message:=message||'Eliminando sobre en turno y en ce_sobre - Matriz'||'<br>';
		   select t.cod_grupo_cia,t.cod_local,t.sec_mov_caja,t.sec_forma_pago_entrega
		   into   nCodCia,nLocal,nSecMovCaja,nSecFormaPago
		   from   ce_sobre T
		   WHERE  trim(T.COD_SOBRE) = trim(nCodSobre)
		   and    t.cod_local = cCodLocal_in
		   and    t.cod_remito is null;

		   delete ce_sobre t
		   WHERE  trim(T.COD_SOBRE) = trim(nCodSobre)
		   and    t.cod_local = cCodLocal_in
		   and    t.cod_remito is null;

		   delete ce_forma_pago_entrega  t
		   where  t.cod_grupo_cia = nCodCia
		   and    t.cod_local     = nLocal
		   and    t.sec_mov_caja  = nSecMovCaja
		   and    t.sec_forma_pago_entrega = nSecFormaPago;

       DBMS_OUTPUT.put_line('El proceso de ELIMINA SOBRE EN MATRIZ acabo correctamente');
-- 		   message:=message||'Eliminando sobre en turno y en ce_sobre - Matriz'||'<br>';

--		   commit;

		end if;

--		message:=message||'El proceso de ELIMINA SOBRE EN MATRIZ acabo correctamente';

--	commit;

--DBMS_OUTPUT.put_line(message);
EXCEPTION
WHEN time_out THEN
    DBMS_OUTPUT.PUT_LINE('TIMEOUT A BD. ESPERE UNOS MINUTOS...');
--    message_error:='TIMEOUT A BD. ESPERE UNOS MINUTOS...';
    rollback;
WHEN deadlock_detected THEN
    DBMS_OUTPUT.PUT_LINE('RECURSO OCUPADO. ESPERE UNOS MINUTOS...');
--    message_error:='RECURSO OCUPADO. ESPERE UNOS MINUTOS...';
    rollback;
WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE(SQLERRM);
--    message_error:='ORA'||SQLCODE||'-'||SQLERRM;
    rollback;
end;
END;
--***** FIN DE SP_ELIMINA_SOBRE ********************************************************************************************
--**************************************************************************************************************************
procedure SP_VALIDA_REMITO (cFecha char, message OUT VARCHAR2, message_error OUT VARCHAR2, motivo OUT VARCHAR2) is
BEGIN
DECLARE
v_Fecha char(10):=cFecha;
v_Cod_local char(3);
Ind_Remito number(1);
Max_Remito varchar2(10);
Remito varchar2(10);
Ind_Fecha number(3);
v_Codigo_Remito varchar2(10);

Fech_Crea varchar2(30);
Nro_Remito varchar2(15);
Monto_Soles varchar2(15);
Monto_Dolares varchar2(15);
Monto_Total varchar2(15);
Cant_Sobres number(3);
BEGIN
   SELECT DISTINCT COD_LOCAL INTO v_Cod_local FROM VTA_IMPR_LOCAL;
-- EMAQUERA -- Se verifica la fecha este dentro del rango
    SELECT COUNT(DISTINCT FEC_DIA_VTA) INTO Ind_Fecha FROM CE_MOV_CAJA
    WHERE FEC_DIA_VTA=v_Fecha AND FEC_DIA_VTA>=SYSDATE-10;

  IF Ind_Fecha > 0 THEN

-- EMAQUERA -- Se valida que exista el remito en el dia indicado
  SELECT COUNT(*) INTO Ind_Remito FROM CE_REMITO A
  WHERE TRUNC(A.FEC_CREA_REMITO)=v_Fecha
  AND FEC_CREA_REMITO>=SYSDATE-10;

    IF Ind_Remito > 0 THEN
      SELECT TO_CHAR(MAX(FEC_CREA_REMITO),'DDMMYYYY') INTO Max_Remito FROM CE_REMITO
      WHERE FEC_CREA_REMITO>=SYSDATE-10;

      SELECT TO_CHAR(MAX(FEC_CREA_REMITO),'DDMMYYYY') INTO Remito FROM CE_REMITO
      WHERE TRUNC(FEC_CREA_REMITO)=v_Fecha
      AND FEC_CREA_REMITO>=SYSDATE-10;

       IF Max_Remito = Remito THEN

          SELECT MAX(COD_REMITO) INTO v_Codigo_Remito FROM CE_REMITO
           WHERE FEC_CREA_REMITO>=SYSDATE-10;
          -----
          SELECT  distinct (TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS AM')) , CODIGO ,
                           TRIM(TO_CHAR(SUM(SOLES), '999,999,990.00')) ,
                           TRIM(TO_CHAR(SUM(DOLARES), '999,999,990.00')),
                           TRIM(TO_CHAR(SUM(TOTAL), '999,999,990.00')) ,
                           SUM(CANT) INTO Fech_Crea, Nro_Remito, Monto_Soles, Monto_Dolares, Monto_Total, Cant_Sobres
                    FROM (SELECT V1.FECHA,
                                 V1.CODIGO,
                                 V1.SOLES,
                                 V1.DOLARES,
                                 V1.TOTAL,
                                 V1.CANT
                            FROM (SELECT B.fec_crea_remito FECHA,
                                         NVL(B.COD_REMITO, ' ') CODIGO,
                                         B.USU_CREA_REMITO USU,
                                         NVL(SUM(CASE
                                                   WHEN SOBRES.TIP_MONEDA = '01' THEN
                                                    SOBRES.MON_ENTREGA_TOTAL
                                                 END),
                                             0) SOLES,
                                         NVL(SUM(CASE
                                                   WHEN SOBRES.TIP_MONEDA = '02' THEN
                                                    SOBRES.MON_ENTREGA
                                                 END),
                                             0) DOLARES,
                                         NVL(SUM(SOBRES.MON_ENTREGA_TOTAL), 0) TOTAL,
                                         SUM(CASE
                                               WHEN SOBRES.COD_SOBRE IS NOT NULL THEN
                                                1
                                             END) CANT
                                    FROM CE_REMITO             B,
                                         (
                                            select C.COD_GRUPO_CIA,C.COD_LOCAL,C.COD_REMITO,C.COD_SOBRE,C.FEC_DIA_VTA,D.TIP_MONEDA,D.MON_ENTREGA,D.MON_ENTREGA_TOTAL
                                            from   CE_SOBRE C,
                                                   CE_FORMA_PAGO_ENTREGA D
                                            where  C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                            AND    C.COD_LOCAL = D.COD_LOCAL
                                            AND    C.SEC_MOV_CAJA = D.SEC_MOV_CAJA
                                            AND    C.ESTADO = 'A'
                                            AND    C.SEC_FORMA_PAGO_ENTREGA = D.SEC_FORMA_PAGO_ENTREGA
                                            UNION
                                            select T.COD_GRUPO_CIA,T.COD_LOCAL,T.COD_REMITO,T.COD_SOBRE,T.FEC_DIA_VTA,T.TIP_MONEDA,T.MON_ENTREGA,T.MON_ENTREGA_TOTAL
                                            from   CE_SOBRE_TMP T
                                            WHERE  T.ESTADO = 'A'
                                            AND    NOT EXISTS (
                                                              SELECT 1
                                                              FROM   CE_SOBRE G
                                                              WHERE  G.COD_GRUPO_CIA = T.COD_GRUPO_CIA
                                                              AND    G.COD_LOCAL = T.COD_LOCAL
                                                              AND    G.COD_SOBRE = T.COD_SOBRE
                                                              )
                                         )SOBRES
                                   WHERE B.COD_GRUPO_CIA = '001'
                                     AND B.COD_LOCAL = v_Cod_local
                                     AND B.fec_crea_remito
                                         BETWEEN
                                         TO_DATE(to_char(trunc(SYSDATE - 30),
                                                         'dd/mm/yyyy') || ' 00:00:00',
                                                 'dd/MM/yyyy HH24:mi:ss') AND
                                         TO_DATE(to_char(trunc(SYSDATE), 'dd/mm/yyyy') ||
                                                 ' 23:59:59',
                                                 'dd/MM/yyyy HH24:mi:ss')
                                     AND B.COD_GRUPO_CIA = SOBRES.COD_GRUPO_CIA
                                     AND B.COD_LOCAL = SOBRES.COD_LOCAL
                                     AND B.COD_REMITO = SOBRES.COD_REMITO
                                     AND B.COD_REMITO= v_Codigo_Remito
                                   GROUP BY B.fec_crea_remito,
                                            B.COD_REMITO,
                                            B.USU_CREA_REMITO) V1)
                   GROUP BY TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS AM'),
                            CODIGO,
                            TO_CHAR(FECHA, 'YYYYMMDDHHMMSS');
          -----
          message:='OK';
          motivo :='<br>Fecha Creacion: '||Fech_Crea||'<br> Nro Remito : '||Nro_Remito||'<br> Monto Soles: '||Monto_Soles||'<br> Monto Dolares: '||Monto_Dolares||'<br> Monto Total (S/.): '||Monto_Total||'<br> Cant Sobres: '||Cant_Sobres;

       ELSE
          message:='<B>NO SE PUEDE ANULAR EL REMITO DE LA FECHA '||v_Fecha||' PORQUE NO ES EL ULTIMO REMITO GENERADO.</B>';
       END IF;

    ELSE
        message:='<B>EL LOCAL NO HA GENERADO REMITO(S) PARA LA FECHA '||v_Fecha||'.</B>';
    END IF;

  ELSE
    message:='<B>LA FECHA '||v_Fecha||' NO SE ENCUENTRA DENTRO DE LOS ULTIMOS 10 DIAS.</B>';
  END IF;
EXCEPTION
WHEN OTHERS THEN
  message_error:='<B>PROBLEMA DE CONEXION. FAVOR DE INTENTARLO EN UNOS MINUTOS.</B>';
  DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
COMMIT;
END;
--***** FIN DE SP_VALIDA_REMITO ******************************************************************************************
--************************************************************************************************************************
PROCEDURE SP_ELIMINA_REMITO (CodLocal CHAR,CodRemito VARCHAR2)
IS
BEGIN
-- ELIMINA SOBRE LOCAL DBLINK
DECLARE
nCodLocal char(3) := CodLocal;
nCodRemito VARCHAR2(10) := CodRemito;
nExisteInterface number;
vRemitoLocal number;

V_SQL varchar2(32000);

-- excepciones
time_out EXCEPTION;
PRAGMA EXCEPTION_INIT(time_out, -12170);

deadlock_detected EXCEPTION;
PRAGMA EXCEPTION_INIT(deadlock_detected , -00060);

BEGIN

		update ce_sobre s
		set s.cod_remito = null
		where  s.cod_grupo_cia = '001'
		and    s.cod_local = nCodLocal
		and    s.cod_remito = nCodRemito;

		update ce_sobre_tmp s
		set s.cod_remito = null
		where  s.cod_grupo_cia = '001'
		and    s.cod_local = nCodLocal
		and    s.cod_remito = nCodRemito;

    dbms_output.put_line('liberando sobres de remito');

		delete int_ce_remito
		where  cod_grupo_cia = '001'
		and    cod_local = nCodLocal
		and    cod_remito = nCodRemito;

    dbms_output.put_line('eliminando posible interface');

		delete ce_tots_remi_sd
		where  cod_grupo_cia = '001'
		and    cod_local = nCodLocal
		and    cod_remito = nCodRemito;

		delete ce_sobre_remi_sd
		where  cod_grupo_cia = '001'
		and    cod_local = nCodLocal
		and    cod_remito = nCodRemito;

    dbms_output.put_line('eliminando calculo de historico del remito');

		delete ce_remito r
		where  cod_grupo_cia = '001'
		and    cod_local = nCodLocal
		and    cod_remito = nCodRemito;

    dbms_output.put_line('eliminando el remito..por fin..!!!!');

--	commit;
--  message_matriz:='ELIMINACION DE REMITO EN MATRIZ.<br><hr>';
--	message_matriz:=message_matriz||'SE ELIMINO EL REMITO '||nCodRemito||' EN EL LOCAL '||nCodLocal||'. <br>';

EXCEPTION
    WHEN time_out THEN
--        message_error := 'EN ESTOS MOMENTOS HAY PROBLEMAS DE CONEXION CON EL LOCAL.';
        dbms_output.put_line('EN ESTOS MOMENTOS HAY PROBLEMAS DE CONEXION CON EL LOCAL.');
        rollback;

    WHEN deadlock_detected THEN
--        message_error := 'EL OBJETO YA ESTA SIENDO UTILIZADO.';
        dbms_output.put_line('EL OBJETO YA ESTA SIENDO UTILIZADO.');
        rollback;

    WHEN others THEN
--        message_error := 'FALLO AL EJECUTAR EL PROCESO DE ELIMINAR SOBRE. <br>'||SQLERRM;
        dbms_output.put_line('FALLO AL EJECUTAR EL PROCESO DE ELIMINAR SOBRE.'||SQLERRM);
        rollback;

END;
END;
--***** FIN DE SP_ELIMINA_REMITO *****************************************************************************************
--************************************************************************************************************************
end PKG_OPERACIONES_CONTA;
/

