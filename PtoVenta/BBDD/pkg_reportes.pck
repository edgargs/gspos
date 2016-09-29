CREATE OR REPLACE PACKAGE PTOVENTA."PKG_REPORTES" IS

  FUNCTION FN_LISTA_KARDEX(A_FCH_INICIO VARCHAR2, A_FCH_FIN VARCHAR2) RETURN SYS_REFCURSOR;
  PROCEDURE SP_CARGA_KARDEX(A_FCH_INICIO VARCHAR2, A_FCH_FIN VARCHAR2);
  PROCEDURE ENVIA_LOCAL_KARDEX_EMAIL(cFecha_ini IN DATE DEFAULT to_char(trunc(SYSDATE-30,'mm'),'dd/MM/yyyy'),
                                     cFecha_fin IN DATE DEFAULT last_day(trunc(sysdate)-30));
END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PKG_REPORTES" is
  FUNCTION FN_LISTA_KARDEX(A_FCH_INICIO VARCHAR2, A_FCH_FIN VARCHAR2) RETURN SYS_REFCURSOR IS
    C_CURSOR SYS_REFCURSOR;
  BEGIN
    SP_CARGA_KARDEX(A_FCH_INICIO , A_FCH_FIN );
    OPEN C_CURSOR FOR
    SELECT * FROM TT_REPO_KARDEX_PROD
    ORDER BY 2 ASC;
--    COMMIT;
    RETURN C_CURSOR;

  END;
PROCEDURE SP_CARGA_KARDEX(A_FCH_INICIO VARCHAR2, A_FCH_FIN VARCHAR2)
  AS

CURSOR C_DATOS IS
--Jhon Ayala 08/06/2015 Se genera cursor con tabla de productos restringidos
SELECT R.COD_PROD FROM VTA_PROD_RESTRINGIDOS R WHERE R.COD_GRUPO_CIA = '001'
AND EXISTS (SELECT 1 FROM LGT_PROD P WHERE P.COD_GRUPO_CIA = R.COD_GRUPO_CIA AND R.COD_PROD = P.COD_PROD);
v_cCodGrupoCia CHAR(3);
v_cCodLocal CHAR(3);
BEGIN
	--ERIOS 04.12.2015 El reporte se ejecuta en el local. En Matriz da error.
	SELECT COD_GRUPO_CIA,COD_LOCAL
		INTO v_cCodGrupoCia,v_cCodLocal
	FROM PTOVENTA.PBL_NUMERA
	WHERE COD_NUMERA = '001';
	--ERIOS 04.12.2015 Se muestra el stock al corte del mes, de los productos sin movimientos.
    FOR REG IN C_DATOS LOOP
INSERT INTO TT_REPO_KARDEX_PROD
SELECT RR.COD_PROD,
       RR.DESC_PROD||' '||rr.desc_unid_present producto,
       stk_inicial,
       INGRESO,
       SALIDA,
       ((stk_inicial+INGRESO)-SALIDA) SaldoActual
FROM (
select COD_PROD,
       MAX(decode(rownum,1,stk_anterioR_prod,0)) stk_inicial,
        SUM(CASE WHEN CANT_MOV_PROD>0 THEN CANT_MOV_PROD ELSE 0 END) INGRESO,
        SUM(CASE WHEN CANT_MOV_PROD<0 THEN CANT_MOV_PROD*-1 ELSE 0 END) SALIDA

from (
select *
from lgt_kardex
where fec_kardex>= to_date(A_FCH_INICIO,'dd/mm/yyyy')
and  fec_kardex< to_date( A_FCH_FIN,'dd/mm/yyyy')+1
and cod_prod=REG.COD_PROD
order by cod_prod, fec_kardex
) ff
GROUP BY COD_PROD
having SUM(CANT_MOV_PROD)<>0 OR count(*) > 1
) KK, LGT_PROD RR
WHERE KK.COD_PROD = RR.COD_PROD
UNION
	SELECT RR.COD_PROD,
		   RR.DESC_PROD||' '||rr.desc_unid_present producto,
		   PL.STK_FISICO - nvl(((stk_inicial+INGRESO)-SALIDA),0) stk_inicial,
		   0 INGRESO,
		   0 SALIDA,
		   PL.STK_FISICO - nvl(((stk_inicial+INGRESO)-SALIDA),0) SaldoActual       
	FROM (
		select COD_PROD,
			   0 stk_inicial,
				SUM(CASE WHEN CANT_MOV_PROD>0 THEN CANT_MOV_PROD ELSE 0 END) INGRESO,
				SUM(CASE WHEN CANT_MOV_PROD<0 THEN CANT_MOV_PROD*-1 ELSE 0 END) SALIDA
		from lgt_kardex
		where fec_kardex>= to_date( A_FCH_FIN,'dd/mm/yyyy')+1
		and  fec_kardex< trunc(sysdate)+1
		and cod_prod=REG.COD_PROD
		GROUP BY COD_PROD
	) KK, LGT_PROD RR,
	PTOVENTA.LGT_PROD_LOCAL PL
	WHERE KK.COD_PROD(+) = RR.COD_PROD
	AND PL.COD_GRUPO_CIA = v_cCodGrupoCia
	AND PL.COD_LOCAL = v_cCodLocal
	AND PL.COD_PROD = REG.COD_PROD
	AND PL.COD_GRUPO_CIA = RR.COD_GRUPO_CIA
	AND PL.COD_PROD = RR.COD_PROD
	AND PL.STK_FISICO - nvl(((stk_inicial+INGRESO)-SALIDA),0) > 0
	AND NOT EXISTS (         
				 SELECT 1
				 FROM LGT_KARDEX K
				WHERE K.COD_GRUPO_CIA = v_cCodGrupoCia AND
						K.COD_LOCAL = v_cCodLocal AND				
						K.FEC_KARDEX BETWEEN TO_DATE(A_FCH_INICIO || ' 00:00:00','dd/MM/yyyy HH24:mi:ss') AND
											 TO_DATE(A_FCH_FIN || ' 23:59:59','dd/MM/yyyy HH24:mi:ss')
						AND K.COD_PROD = REG.COD_PROD
			  )
	;          
END LOOP;
END;
--
PROCEDURE ENVIA_LOCAL_KARDEX_EMAIL(cFecha_ini IN DATE DEFAULT to_char(trunc(SYSDATE-30,'mm'),'dd/MM/yyyy'),
                                   cFecha_fin IN DATE DEFAULT last_day(trunc(sysdate)-30))
IS
--
v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
v_vNombreArchivo   VARCHAR2(100) := '';

mesg_body_cab               VARCHAR2(32760) := '';
mesg_body                   VARCHAR2(32760);
msg                         VARCHAR2(32767);
--
v_refcursor    sys_refcursor;
vCOD_PROD      char(6);
vPRODUCTO      VARCHAR2(151);
vSTK_INICIAL   number;
vINGRESO       number;
vSALIDA        number;
vSALDOACTUAL   number;
--
   CURSOR curZona IS
        --
        SELECT Z.COD_LOCAL,Z.DESC_CORTA_LOCAL,Z.MAIL_LOCAL,V.EMAIL_JEFE_ZONA
        FROM   PBL_LOCAL Z, VTA_ZONA_VTA V, VTA_LOCAL_X_ZONA L
        WHERE  V.COD_GRUPO_CIA = L.COD_GRUPO_CIA AND V.COD_ZONA_VTA = L.COD_ZONA_VTA
          AND  Z.COD_GRUPO_CIA= L.COD_GRUPO_CIA AND Z.COD_LOCAL = L.COD_LOCAL;

        --

MES_ACT CHAR(30) :='';

BEGIN
     FOR v_CurZona IN curZona
     LOOP
         BEGIN
             SELECT TO_CHAR(ADD_MONTHS(cFecha_ini,0),'MONTH')
                      INTO MES_ACT
               FROM DUAL;
               --
               mesg_body := mesg_body||'Reporte de Psicotropicos del mes de '|| TO_CHAR(ADD_MONTHS(cFecha_ini,0),'MONTH')||'Local '||v_CurZona.cod_local||'-'||v_CurZona.Desc_Corta_local;
               mesg_body := mesg_body||'<br>';
               mesg_body := mesg_body||'<br>';
               --
               -- PREPARA INFORMACIÓN DE KARDEX POR LOCAL
               mesg_body_cab := mesg_body;

                --NOM ARCHIVO
                v_vNombreArchivo := 'REPORTE_DE_PSICOTROPICOS_MES_DE_'||TO_CHAR(ADD_MONTHS(cFecha_ini,0),'MONTH')||'LOCAL '||v_CurZona.cod_local||'-'||v_CurZona.Desc_Corta_local||'.xls';
                --INICIO ARCHIVO
                ARCHIVO_TEXTO:=UTL_FILE.FOPEN(v_gNombreDiretorio,TRIM(v_vNombreArchivo),'W');

                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<html>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'<head>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <meta content="text/html; charset=ISO-8859-1"');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,' http-equiv="content-type">');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'</head>');

                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <table style="text-align: left; width: 65%;" border="1"');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  cellpadding="2" cellspacing="1">');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <tbody>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <tr>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'        <th colspan="6" align="CENTER">REPORTE DE PSICOTROPICOS DEL MES DE '|| TO_CHAR(ADD_MONTHS(cFecha_ini,0),'MONTH')||'  </th>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      </tr>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      <tr>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'        <th><font size="1">COD_PROD</th>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'        <th><font size="1">PRODUCTO</th>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'        <th><font size="1">STK_INICIAL</th>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'        <th><font size="1">INGRESO</th>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'        <th><font size="1">SALIDA</th>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'        <th><font size="1">SALDO ACTUAL </th>');
                UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'      </tr>');

-- ACA VA EL CUERPO
   DELETE FROM TT_REPO_KARDEX_PROD;
    commit;
   v_refcursor := ptoventa.pkg_reportes.fn_lista_kardex(to_char(cFecha_ini,'dd/mm/yyyy'),to_char(cFecha_fin,'dd/mm/yyyy'));
   --
    FETCH v_refcursor
    INTO vCOD_PROD , vPRODUCTO , vSTK_INICIAL , vINGRESO , vSALIDA , vSALDOACTUAL;
     WHILE v_refcursor%FOUND LOOP

                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'     <tr>');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <td>'|| vCOD_PROD||'</td>');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <td>'|| vPRODUCTO ||'</td>');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <td>'|| vSTK_INICIAL ||'</td>');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <td>'|| vINGRESO ||'</td>');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <td>'|| vSALIDA ||'</td>');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <td>'|| vSALDOACTUAL ||'</td>');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'     </tr>');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
                      UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  ');
       FETCH v_refcursor
       INTO vCOD_PROD , vPRODUCTO , vSTK_INICIAL , vINGRESO , vSALIDA , vSALDOACTUAL;
       END LOOP;
       commit;
--Se cierra el cursor que fue abierto al momento de realizar la llamada al package
     CLOSE v_refcursor;

          UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'    </tbody>');
          UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  </table>');
          UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <br>');
          UTL_FILE.PUT_LINE(ARCHIVO_TEXTO,'  <br>');

          UTL_FILE.FCLOSE(ARCHIVO_TEXTO);
          DBMS_OUTPUT.PUT_LINE('GRABO ARCHIVO DE CAMBIOS');

--CERRAR TBODY
     --
     FARMA_EMAIL.ENVIA_CORREO_ATTACH(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                                     v_CurZona.Mail_Local||','||v_CurZona.EMAIL_JEFE_ZONA||', jayala',
                                     'REPORTE DE PSICOTROPICOS MES DE '||TO_CHAR(ADD_MONTHS(cFecha_ini,0),'MONTH')||'LOCAL '||v_CurZona.cod_local||'-'||v_CurZona.Desc_Corta_local,
                                     'REPORTE DE PSICOTROPICOS MES DE'||TO_CHAR(ADD_MONTHS(cFecha_ini,0),'MONTH')||'LOCAL '||v_CurZona.cod_local||'-'||v_CurZona.Desc_Corta_local,
                                     '<BR>'||mesg_body_cab||'</BR>',
                                     TRIM(v_vNombreArchivo),
                                     1,
                                     'mgallo',
                                     FARMA_EMAIL.GET_EMAIL_SERVER);
     --

       mesg_body := '';
       --
       INSERT INTO log_envia_mail(fech_load, proceso,descripcion,cod_local)
       VALUES (SYSDATE,'ENVIA_LOCAL_KARDEX_EMAIL','OK, Envío email kardex: '|| v_CurZona.cod_local,v_CurZona.cod_local);
       COMMIT;
       --
       EXCEPTION
         WHEN OTHERS THEN
           ROLLBACK;
           msg := substr(SQLERRM,1,100);
             INSERT INTO log_envia_mail(fech_load, proceso,descripcion,cod_local)
             VALUES (SYSDATE,'ENVIA_LOCAL_KARDEX_EMAIL','ERROR, Envío email kardex: '|| v_CurZona.cod_local||msg,v_CurZona.cod_local);
             COMMIT;
       END;
     END LOOP;
END;
--
END;
/

