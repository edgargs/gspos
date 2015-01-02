CREATE OR REPLACE PACKAGE PTOVENTA."DBA_SESSION" IS
FUNCTION FP_COUNT_SESSION(pnom_machine CHAR,pnom_usuario CHAR) return number ;
FUNCTION FP_last_activity(pnom_machine CHAR,pnom_usuario CHAR) return date ;
procedure sp_kill_session(pnom_machine CHAR) ;
end;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."DBA_SESSION" IS

FUNCTION FP_COUNT_SESSION(pnom_machine CHAR,pnom_usuario CHAR) return number is
xcount number(3);
begin
 select COUNT(1)
 into   xcount
 from   v$session s
 where  s.username=trim(upper(pnom_usuario))
 AND    UPPER(machine)=UPPER(pnom_machine)
 AND    UPPER(MODULE) LIKE '%THIN%'
   AND   S.STATUS IN('ACTIVE','INACTIVE') ;
 return xcount;
end;

FUNCTION FP_last_activity(pnom_machine CHAR,pnom_usuario CHAR) return date is
xlast_activity date;
begin
 select sysdate-s.last_call_et/24/60/60
 into   xlast_activity
 from   v$session s
 where  s.username=trim(upper(pnom_usuario))
 AND    UPPER(machine)=UPPER(pnom_machine)
 AND    UPPER(MODULE) LIKE '%THIN%';
RETURN xlast_activity ;
end;

procedure sp_kill_session(pnom_machine CHAR) is
cserial V$session.Serial#%type;
csid    v$session.sid%type;
cursor cr1 is
  SELECT sid, serial#
  FROM   v$session s
  WHERE  username = 'IDUPVENTA'
   AND   UPPER(machine)=UPPER(pnom_machine)
   AND   UPPER(MODULE) LIKE '%THIN%'
   AND   S.STATUS IN('ACTIVE','INACTIVE');
BEGIN
  open cr1;
  loop
    fetch cr1 into    csid, cserial   ;
    exit when cr1%notfound;
    EXECUTE IMMEDIATE  'ALTER SYSTEM KILL SESSION '''||csid||','||cserial||''' ';
  end loop;
  close cr1;
END ;
end DBA_SESSION;
/

