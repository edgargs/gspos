CREATE OR REPLACE PACKAGE PTOVENTA."UTILITY_DNI_RENIEC" AS

  TYPE FarmaCursor IS REF CURSOR;

  function GET_EXISTE_DNI(cCodGrupocia_in in varchar2,
                          cCodLocal_in    in varchar2,
                          cDNI_in         in varchar2) return VARCHAR2;


  function GET_POS_DNI_ET(nDNI_in in number,
                          vTabla  in varchar2,
                          i       in integer,
                          j       in integer) return number;

  function getDNIPos(vTabla  in varchar2,pos in number) return number;

  function getDatosDNI(vTabla  in varchar2,pos in number) return VARCHAR2;

  function AUX_EXISTE_DNI_RENIEC(cCodGrupocia_in in varchar2,
                                 cCodLocal_in    in varchar2,
                                 cDNI_in         in varchar2) return VARCHAR2;

  function AUX_DATOS_EXISTE_DNI(cCodGrupocia_in in varchar2,
                                cCodLocal_in    in varchar2,
                                cDNI_in         in varchar2) return VARCHAR2;


END UTILITY_DNI_RENIEC;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."UTILITY_DNI_RENIEC" AS

  /* ****************************************************************** */
  function GET_EXISTE_DNI(cCodGrupocia_in in varchar2,
                          cCodLocal_in    in varchar2,
                          cDNI_in         in varchar2) return VARCHAR2 is
    vDatosDNI varchar2(30000);
    nDNI number;
    vNombreTabla AUX_DNI_RENIEC_TABULADA.NAME_TABLE%TYPE;
    vPos  NUMBER;
    vCantFilasET number;
  begin

    dbms_output.put_line(cCodGrupocia_in||'-'||cCodLocal_in||'-'||cDNI_in);
    --- 0.- convierte el DNI ingresado a NUMERO para hacer la consulta y trabajo del mismo
    nDNI := trim(cDNI_in)*1;
    --- 1.- obtiene el nombre de la tabla externa donde se ubica
    begin
      SELECT T.NAME_TABLE,T.NUM_ITEMS
      into   vNombreTabla,vCantFilasET
      FROM   AUX_DNI_RENIEC_TABULADA T
      WHERE  nDNI >= T.INI AND nDNI <=T.FIN;
    exception
        when others then
        vNombreTabla := 'N';
    end;
    --- 2.- no existen en el rango que se tiene cargado
    if vNombreTabla = 'N' then
       dbms_output.put_line('DNI no existe en la tabulacion....');
       vDatosDNI := 'N';
    else
       dbms_output.put_line('DNI se encuentra en la tabla ...'||vNombreTabla);
       vPos := GET_POS_DNI_ET(nDNI,vNombreTabla,0,vCantFilasET);
       dbms_output.put_line('vPos:'||vPos);
       IF vPos = -1 THEN
         dbms_output.put_line('NOOO EXISTE EL DNI...');
         vDatosDNI := 'N';
       ELSE
         dbms_output.put_line('SI EXISTE EL DNI...');
         vDatosDNI := TRIM(TO_CHAR(vPos,'9999990'))||'&'||vNombreTabla;
       END IF;
    end if;

    return vDatosDNI;
  end;
  /****************************************************************************/
  /*
    Metodo Recursivo Binario para OBTENER la posicion indicada del DNI en busqueda
  */
  function GET_POS_DNI_ET(nDNI_in in number,
                          vTabla  in varchar2,
                          i       in integer,
                          j       in integer) return number is
    medio integer := 0;
    cod_prod_buscar number;
  begin
    if (i > j) then return -1; end if;

    medio := (i + j)/2;

    cod_prod_buscar :=  getDNIPos(vTabla,medio);

        if cod_prod_buscar < nDNI_in then
            return GET_POS_DNI_ET(nDNI_in,vTabla, medio + 1, j);
        else if cod_prod_buscar > nDNI_in then
                return GET_POS_DNI_ET(nDNI_in,vTabla, i, medio - 1);
             else
                return medio;
             end if;
       end if;

  end;
  /****************************************************************************/
  function getDNIPos(vTabla  in varchar2,pos in number) return number is
   vDNI_out number;
  begin

    execute immediate
    'SELECT e.dni_number FROM '|| vTabla ||' e where  e.orden = '||pos
    into vDNI_out;

    return vDNI_out;
  end;
  /****************************************************************************/
  function getDatosDNI(vTabla  in varchar2,pos in number) return VARCHAR2 is
   vDatosDNI VARCHAR2(30000);
  begin
    BEGIN
      EXECUTE IMMEDIATE
         'select E.TDNIAFIL'||'||'|| '''@''' ||'||'||'E.TNOMBAFIL'||'||'|| '''@''' ||'||'||
                'E.TAPELPATE'||'||'|| '''@''' ||'||'||'E.TAPELMATE'||'||'|| '''@''' ||'||'||
                'E.TSEXOAFIL'||'||'|| '''@''' ||'||'||'trim(E.TFECHNACI) '||
         'FROM   '||vTabla || ' E '||
         'WHERE  E.ORDEN = ' || pos
         INTO   vDatosDNI;
       dbms_output.put_line('>>> vDatosDNI >>> '||vDatosDNI);
    EXCEPTION
    WHEN OTHERS THEN
      vDatosDNI := 'N';
    END;
    return vDatosDNI;
  end;
/* *********************************************************************** */
 function AUX_EXISTE_DNI_RENIEC(cCodGrupocia_in in varchar2,
                               cCodLocal_in    in varchar2,
                               cDNI_in         in varchar2) return VARCHAR2 is
 BEGIN
    RETURN GET_EXISTE_DNI(cCodGrupocia_in,cCodLocal_in,cDNI_in);
 END;
/* *********************************************************************** */
  function AUX_DATOS_EXISTE_DNI(cCodGrupocia_in in varchar2,
                               cCodLocal_in    in varchar2,
                               cDNI_in         in varchar2) return VARCHAR2 is
 pValida varchar2(10000);
 vDatosDNI varchar2(30000);
    vTabla varchar2(1000);
    vPos   number;
 BEGIN
    pValida := GET_EXISTE_DNI(cCodGrupocia_in,cCodLocal_in,cDNI_in);

    if pValida = 'N' then
       vDatosDNI := 'N';
    else
       vPos   := TO_NUMBER(FARMA_UTILITY.split(pValida,1,'&'));
       vTabla := TRIM(FARMA_UTILITY.split(pValida,2,'&'));

       vDatosDNI := getDatosDNI(vTabla,vPos);
    end if;

    return vDatosDNI;
 END;
/* *********************************************************************** */


END UTILITY_DNI_RENIEC;
/

