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
SELECT * FROM LGT_PROD
WHERE COD_PROD IN (
'100012','100016','102661','103092','110550','111685','111692','112248','114665',
'115047','115048','115685','115763','116764','116765','116766','116767','116853',
'117474','119247','119248','119283','119856','120163','120165','120365','120366',
'120367','120459','120774','120778','121070','121134','121135','121136','121143',
'121148','123246','123247','123600','123823','123955','124026','124218','124221',
'126042','126051','126059','126066','126086','126293','130116','130118','130120',
'130121','130157','130236','130305','130314','130317','130320','130323','130326',
'130329','130331','130394','130396','130398','130400','130402','130526','130664',
'131021','131047','131053','131226','131228','131257','131383','131386','131752',
'132254','132261','132363','132474','132509','132667','132669','132671','132679',
'132684','132690','132693','132765','133072','133075','133256','133402','133403',
'133404','133407','133570','133763','134000','134006','134211','134216','134270',
'134272','134418','134589','134591','134663','134664','134665','135051','135055',
'135134','135137','135141','135146','135262','135265','135270','135272','135290',
'135295','135308','135311','135342','135344','135447','135450','135453','135497',
'135500','135503','135507','135512','135553','135630','135637','135646','135787',
'135840','135845','135848','135856','135858','135862','135878','135890','135909',
'135911','135912','135919','135925','136028','136031','136032','136070','136074',
'136077','136080','136084','136106','136135','136145','136155','136442','136537',
'136736','136763','136948','136964','137030','137033','137035','137581','137582',
'137723','137725','138046','138054','138055','138410','138411','138602','138810',
'138814','138975','138977','139616','139617','139618','139619','139722','140362',
'140412','140609','140658','140659','140702','140703','140704','141035','141073',
'141269','141286','141326','141331','141334','141339','141504','141507','141583',
'141584','142066','153459','155112','155113','156920','157721','158628','161179',
'163575','163578','163579','163590','163608','164242','166485','167951','169922',
'171780','171884','172871','174893','175651','175652','177664','178212','178213',
'180007','184243','184244','185137','187460','187481','190578','200911','200913',
'200914','201496','203626','206777','504225','504326','510164','510179','510203',
'510240','510241','510244','510279','510655','510905','510906','511146','511171',
'511283','511313','511316','511383','511467','511468','511542','511599','511721',
'511722','511723','511725','511926','511935','511936','511945','512501','512502',
'512549','512677','512716','513105','513107','513108','513109','513115','513116',
'513234','513393','513472','513825','513826','513838','513839','513885','513943',
'514055','514056','514057','514114','514138','514367','514368','514621','514622',
'514864','514865','514928','515155','515159','515160','515185','515186','515517',
'515525','515528','515644','515679','515805','534431','534446','537340','537341',
'570018','570031','570060','570070','570122','570292','570419','570577','570641',
'570751','571056','571235','571503','571639','571698','571699','572014','572051',
'572053','572068','572070','572345','572346','572610','572623','572624','572629',
'572636','572637','572644','572655','572656','572663','572668','572669','572700',
'573613','573657','574211','574222','574322','574503','577372','578044','578049',
'578050','578053'
);
BEGIN
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
WHERE KK.COD_PROD = RR.COD_PROD;
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
                                     --'jayala',
                                     'REPORTE DE PSICOTROPICOS MES DE '||TO_CHAR(ADD_MONTHS(cFecha_ini,0),'MONTH')||'LOCAL '||v_CurZona.cod_local||'-'||v_CurZona.Desc_Corta_local,
                                     'REPORTE DE PSICOTROPICOS MES DE'||TO_CHAR(ADD_MONTHS(cFecha_ini,0),'MONTH')||'LOCAL '||v_CurZona.cod_local||'-'||v_CurZona.Desc_Corta_local,
                                     '<BR>'||mesg_body_cab||'</BR>',
                                     TRIM(v_vNombreArchivo),
                                     1,
                                     'mgallo',
                                     --'jayala',
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

