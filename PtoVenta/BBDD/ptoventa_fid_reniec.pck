CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_FID_RENIEC" AS

  -- Author  : DVELIZ
  -- Created : 26/09/2008 11:08:41 a.m.
  -- Purpose :

  ESTADO_ACTIVO		  CHAR(1):='A';
	ESTADO_INACTIVO		  CHAR(1):='I';
	INDICADOR_SI		  CHAR(1):='S';
  INDICADOR_NO      CHAR(1):='N';

  COL_ET_RENIEC_DNI integer := 1;
  COL_ET_RENIEC_NOMBRE integer := 2;
  COL_ET_RENIEC_APE_PAT integer := 3;
  COL_ET_RENIEC_APE_MAT integer := 4;
  COL_ET_RENIEC_SEXO    integer := 5;
  COL_ET_RENIEC_FN    integer := 6  ;

  TYPE FarmaCursor IS REF CURSOR;
  CC_MOD_TAR_FID char(2):='TF';--TARJETAS DE FIFELIZACION
  CC_COD_NUMERA  CHAR(3):='070';


    --Descripcion: Se valida que exista en table RENIEC y sea menor que el año definido
    --Fecha       Usuario      Comentario
    --05/10/2009  JCORTEZ     Creacion
  FUNCTION FID_F_VALIDA_DNI_REC(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in     IN CHAR,
                               cDni_in          IN CHAR,
                               cFecha           IN CHAR)
  RETURN CHAR;

  --Descripcion: Se obtiene mensaje de
  --Fecha       Usuario      Comentario
  --05/10/2009  JCORTEZ     Creacion
  FUNCTION F_VAR2_GET_MENSAJE(cGrupoCia_in  IN CHAR,
                                cCodLocal_in  IN CHAR)
  RETURN VARCHAR2;

    FUNCTION FID_F_TIP_DOC  (cGrupoCia_in  IN CHAR)
  RETURN FarmaCursor;

    /***********************************************************/
  PROCEDURE ENVIA_CORREO_CONFIRMACION(cCodGrupoCia  IN CHAR,
                                      cCodLocal        IN CHAR,
                                      cUsuCrea         IN CHAR,
                                      cSecUsu          IN CHAR,
                                      cTipDoc          IN CHAR,
                                      cNumDoc          IN CHAR,
                                      cNomCli          IN CHAR,
                                      cFecNac          IN CHAR,
                                      pCodTarjeta      IN CHAR DEFAULT 'N');

   FUNCTION F_GET_ROL_CONFIRMACION(cGrupoCia_in  IN CHAR,
                                    cCodLocal_in  IN CHAR)
  RETURN VARCHAR2;

  /* ********************************************************* */
  FUNCTION F_VAR2_GET_IND_VALIDA_RENIEC
  RETURN VARCHAR2;
  /* ************************************************** */
  FUNCTION F_VAR2_GET_IND_CLAVE_CONF
  RETURN VARCHAR2;


  /* ************************************************** */
  PROCEDURE P_GENERA_TARJETA_DNI(
                                 cCodGrupoCia  IN CHAR,
                                 cCodLocal_in  IN CHAR,
                                 cDni_in       IN CHAR
                                );
 /* ***************************************************** */
  FUNCTION F_GENERA_EAN13(vCodigo_in IN VARCHAR2)
  RETURN CHAR;
 /* ***************************************************** */
  FUNCTION F_VALIDACION_FINAL_DNI(cCodGrupoCia_in        IN CHAR,
                                  cCodLocal_in           IN CHAR,
                                  cUsuLogin_in           IN CHAR,
                                  cFrmDni_in             IN CHAR,
                                  cFrmNombre_in          IN CHAR,
                                  cFrmFechaNacimiento_in IN CHAR,
                                  cValidaDni_in          IN CHAR,
                                  cTercerDni_in          IN CHAR)
    RETURN varchar2;


END PTOVENTA_FID_RENIEC;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_FID_RENIEC" AS

  /* ***************************************************************** */
  FUNCTION FID_F_VALIDA_DNI_REC(cCodGrupoCia_in IN CHAR,
                               cCodLocal_in     IN CHAR,
                               cDni_in          IN CHAR,
                               cFecha           IN CHAR)
  RETURN CHAR IS

  vRes char(1) := 'N';

  nCant number;
  v_vCodParamAnho char(4);
  v_dFecNac CHAR(8);
  CANT NUMBER;

    -- dubilluz 16.05.2012
    vDatosDNI varchar2(30000);
  VAL_DNI varchar2(20);
  VAL_NOMBRE varchar2(10000);
  VAL_APE_PAT varchar2(10000);
  VAL_APE_MAT varchar2(10000);
  VAL_SEXO varchar2(20);
  VAL_FECHA_NAC DATE;
    -- dubilluz  16.05.2012


  BEGIN
       SELECT TRIM(A.LLAVE_TAB_GRAL) INTO v_vCodParamAnho  FROM PBL_TAB_GRAL A WHERE A.ID_TAB_GRAL='305';


       IF( TO_NUMBER(SUBSTR(cFecha,7,10)) <=  TO_NUMBER(v_vCodParamAnho))THEN

          -- dubilluz 16.05.2012
          /*
          SELECT COUNT(1)
          INTO CANT
          FROM @PBL_DNI_RED@ A
          WHERE A.LE=cDNI_in;
          */
          -- vDatosDNI := utility_dni_reniec.aux_datos_existe_dni('001','000',cDNI_in);
          -- KMONCADA 24.09.2014 VERIFICA LONGITUD DE CADENA DNI
          IF LENGTH(TRIM(cDni_in))=8 THEN
            vDatosDNI := utility_dni_reniec.aux_datos_existe_dni('001','000',cDni_in);
          ELSE
            vDatosDNI := 'N';
          END IF;
          if vDatosDNI = 'N' then
             CANT := 0;
          else
            CANT := 1;
            VAL_DNI     := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_DNI,'@'));
            VAL_NOMBRE  := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_NOMBRE,'@'));
            VAL_APE_PAT := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_APE_PAT,'@'));
            VAL_APE_MAT := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_APE_MAT,'@'));
            VAL_SEXO    := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_SEXO,'@'));
            VAL_FECHA_NAC := TO_DATE(TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_FN,'@')),'yyyymmdd');
          end if;

          -- dubilluz 16.05.2012


          IF(CANT>0)THEN

              --SELECT T.FEC_NAC INTO v_dFecNac FROM @PBL_DNI_RED@ T WHERE T.LE like cDni_in || '%';

              IF /*TO_DATE(v_dFecNac,'YYYYMMDD')*/VAL_FECHA_NAC = TO_DATE(cFecha,'DD/MM/YYYY')  THEN

/*
              SELECT COUNT(1) INTO   nCant
              FROM   @PBL_DNI_RED@ T
              where  t.le = cDNI_in
              AND    TO_NUMBER(SUBSTR(T.FEC_NAC,1,4)) < TO_NUMBER(v_vCodParamAnho);
*/
              -- DUBILLUZ 16.05.2012
              IF TO_CHAR(VAL_FECHA_NAC,'YYYY')*1 < TO_NUMBER(v_vCodParamAnho)  THEN
                nCant := 1;
              ELSE
                nCant := 0;
              END IF;
              -- DUBILLUZ 16.05.2012

              IF nCant = 0 THEN
                 vRes := 'S';
              END IF;
            END IF;
          ELSE
            vRes := 'S';
          END IF;



       END IF;

       RETURN vRes;



  END;

  /******************************************************************************************/

    FUNCTION F_VAR2_GET_MENSAJE(cGrupoCia_in  IN CHAR,
                                cCodLocal_in  IN CHAR)
  RETURN VARCHAR2
  IS
  vIndParam varchar2(32000);

   n_IdColorLetra NUMBER;
    vDesColor varchar2(100);
    vDesMensaje varchar2(100);

  BEGIN
      BEGIN

       SELECT A.LLAVE_TAB_GRAL INTO vDesColor
        FROM PBL_TAB_GRAL A
        WHERE A.ID_TAB_GRAL=304;

       SELECT A.LLAVE_TAB_GRAL INTO vDesMensaje
        FROM PBL_TAB_GRAL A
        WHERE A.ID_TAB_GRAL=303;

          SELECT '<HTML> '||
                 '<table width="370" height="740" border="0">  '||
                 '<tr> '||
                 --'<td width="200" bgcolor="' ||vDesColor|| '">'||
                 '<td bgcolor="' ||vDesColor|| '">'||
                 '<div align="center"><font color="#FFFFFF" '||
                  'size ="6" face="ARIAL">' ||
                 '<br>'||
                 '<b>'||vDesMensaje||'</b>'||
                 '<br>'||
                 '</font></div></td> '||
                 ' </tr>'||
                 ' </table> '||
                 ' </HTML> '
          INTO   vIndParam
          FROM DUAL;

      EXCEPTION
               WHEN NO_DATA_FOUND THEN
                     vIndParam := '';
      END;

      return vIndParam;
    END ;
  /***********************************************************/
  FUNCTION FID_F_TIP_DOC(cGrupoCia_in  IN CHAR)
  RETURN FarmaCursor
  IS
    curCamp FarmaCursor;
  BEGIN
    OPEN curCamp FOR
      SELECT A.DESC_TIP_DOCUMENTO
        FROM PBL_TIP_DOCUMENTOS A;

    RETURN curCamp;

  END;


  PROCEDURE ENVIA_CORREO_CONFIRMACION(cCodGrupoCia  IN CHAR,
                                      cCodLocal        IN CHAR,
                                      cUsuCrea         IN CHAR,
                                      cSecUsu          IN CHAR,
                                      cTipDoc          IN CHAR,
                                      cNumDoc          IN CHAR,
                                      cNomCli          IN CHAR,
                                      cFecNac          IN CHAR,
                                      pCodTarjeta      IN CHAR DEFAULT 'N')
                                         IS


    mesg_body VARCHAR2(4000);
    cliente VARCHAR2(100);
    NomUsu  VARCHAR2(100);
    DescDoc VARCHAR2(100);
    CANT NUMBER;
    envia VARCHAR2(100);
    BEGIN
/*
       SELECT A.LLAVE_TAB_GRAL INTO envia
        FROM PBL_TAB_GRAL A
        WHERE A.ID_TAB_GRAL=307;

    SELECT X.DESC_TIP_DOCUMENTO INTO DescDoc
    FROM PBL_TIP_DOCUMENTOS X
    WHERE X.COD_TIP_DOCUMENTO=cTipDoc;

         SELECT A.NOM_USU||' '||A.APE_PAT||' '||A.APE_MAT INTO NomUsu
         FROM PBL_USU_LOCAL A
         WHERE A.COD_GRUPO_CIA=cCodGrupoCia
         AND A.COD_LOCAL=cCodLocal
         AND A.SEC_USU_LOCAL=cSecUsu;

    cliente :='Nombre :'|| cNomCli||'<BR>'||'Numero Documento :'|| cNumDoc ||'-'||DescDoc;
         mesg_body :='El usuario  '|| NomUsu ||'  confirma el ingreso del cliente  '||'<BR>'||cliente||
                     '<br> Fecha Nacimiento:' || cFecNac;
                    FARMA_UTILITY.envia_correo(cCodGrupoCia,cCodLocal,
                                         envia,
                                         'CONFIRMACION DE INGRESO_',
                                         'CONFIRMACION',mesg_body,'');
*/

      /* SELECT COUNT(*) INTO CANT
       FROM PBL_CLIENTE A WHERE TRIM(A.DNI_CLI)= cNumDoc
        AND A.NOM_CLI LIKE cNomCli
        AND ROWNUM<2;

        IF(CANT>0)THEN
            UPDATE PBL_CLIENTE X
            SET X.ID_USU_CONFIR=cUsuCrea                                                                                             ,
                X.USU_MOD_CLIENTE=cUsuCrea,
                X.FEC_MOD_CLIENTE=SYSDATE
            WHERE TRIM(X.DNI_CLI)= cNumDoc
            AND X.NOM_CLI LIKE cNomCli
            AND ROWNUM<2;
       ELSE
        --inserta nuevo cliente
           INSERT INTO PBL_CLIENTE(DNI_CLI,NOM_CLI,APE_PAT_CLI,APE_MAT_CLI,FONO_CLI,SEXO_CLI,DIR_CLI,FEC_NAC_CLI,USU_CREA_CLIENTE
           ,FEC_MOD_CLIENTE,USU_MOD_CLIENTE,EMAIL,COD_LOCAL_ORIGEN,CELL_CLI,COD_TIP_DOCUMENTO,ID_USU_CONFIR)
           VALUES(cNumDoc,cNomCli,NULL,NULL,NULL,NULL,NULL,cFecNac,cUsuCrea,NULL,NULL,NULL,cCodLocal,NULL,cTipDoc,cUsuCrea);

             UPDATE FID_TARJETA
               SET DNI_CLI = cNumDoc,
                   USU_MOD_TARJETA = cUsuCrea,
                   FEC_MOD_TARJETA = SYSDATE,
                   cod_local = cCodLocal
             WHERE COD_TARJETA = pCodTarjeta;

       END IF;   */
       null;
    END;

  /* ******************************************************************** */
  FUNCTION F_GET_ROL_CONFIRMACION(cGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR) RETURN VARCHAR2 IS
    vIndParam varchar2(10);
  BEGIN
    BEGIN

      SELECT TRIM(A.LLAVE_TAB_GRAL)
        INTO vIndParam
        FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL = 306;

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vIndParam := '';
    END;

    return vIndParam;
  END;
  /* ******************************************************************* */
  FUNCTION F_VAR2_GET_IND_VALIDA_RENIEC
  RETURN VARCHAR2
  IS
  vIndParam varchar2(10);
  BEGIN
      BEGIN

      SELECT TRIM(A.LLAVE_TAB_GRAL)
        INTO vIndParam
        FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL = 308;

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vIndParam := '';
    END;

    return vIndParam;

  END;

  /* ************************************************** */
  FUNCTION F_VAR2_GET_IND_CLAVE_CONF
  RETURN VARCHAR2
  IS
  vIndParam varchar2(10);
  BEGIN

      BEGIN

      SELECT TRIM(A.LLAVE_TAB_GRAL)
        INTO vIndParam
        FROM PBL_TAB_GRAL A
       WHERE A.ID_TAB_GRAL = 309;

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vIndParam := '';
    END;

    return vIndParam;



  END;
  /* *************************************************** */
  PROCEDURE P_GENERA_TARJETA_DNI(
                                 cCodGrupoCia  IN CHAR,
                                 cCodLocal_in  IN CHAR,
                                 cDni_in       IN CHAR
                                )
  AS
     PRAGMA AUTONOMOUS_TRANSACTION;

  nCantReniec  number;
  nCantCliente number;

  vPrefijo_tarjeta varchar2(20):='999';
  vCodTarjeta varchar2(30);

  vSecuencialNumera CHAR(6);
  vConcatenado      CHAR(12);
  vNuevaTarjetaFidelizacion CHAR(13);
  CC_COD_NUMERA  CHAR(3):='070';

  vUsu varchar(20) := 'SYS_AUTO';

    -- dubilluz 16.05.2012
    vDatosDNI varchar2(30000);
  VAL_DNI varchar2(20);
  VAL_NOMBRE varchar2(10000);
  VAL_APE_PAT varchar2(10000);
  VAL_APE_MAT varchar2(10000);
  VAL_SEXO varchar2(20);
  VAL_FECHA_NAC DATE;
    -- dubilluz  16.05.2012

    nCantTarjCliente number;

  BEGIN
    -- dubilluz 16.05.2012
    /*
    SELECT COUNT(1)
    INTO nCantReniec
    FROM @PBL_DNI_RED@ A
    WHERE A.LE=cDni_in;
    */
    -- vDatosDNI := utility_dni_reniec.aux_datos_existe_dni('001','000',cDni_in);
    -- KMONCADA 24.09.2014 VERIFICA LONGITUD DE CADENA DNI
    IF LENGTH(TRIM(cDni_in))=8 THEN
      vDatosDNI := utility_dni_reniec.aux_datos_existe_dni('001','000',cDni_in);
    ELSE
      vDatosDNI := 'N';
    END IF;
    if vDatosDNI = 'N' then
       nCantReniec := 0;
    else
      nCantReniec := 1;
      VAL_DNI     := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_DNI,'@'));
      VAL_NOMBRE  := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_NOMBRE,'@'));
      VAL_APE_PAT := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_APE_PAT,'@'));
      VAL_APE_MAT := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_APE_MAT,'@'));
      VAL_SEXO    := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_SEXO,'@'));
      VAL_FECHA_NAC := TO_DATE(TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_FN,'@')),'yyyymmdd');
    end if;

    -- dubilluz 16.05.2012



      SELECT  COUNT(*)
      INTO    nCantCliente
      FROM    PBL_CLIENTE C
      where   C.DNI_CLI = cDni_in;
       
    --  dbms_output.put_line('nCantReniec: ' ||nCantReniec);
    -- dbms_output.put_line('nCantCliente: '|| nCantCliente);
      if nCantCliente = 0 then
    --   dbms_output.put_line('paso 1:'||nCantReniec);
         if nCantReniec > 0 then
    --        dbms_output.put_line('paso 2:'||nCantReniec);
        INSERT INTO PBL_CLIENTE
          (DNI_CLI,
           NOM_CLI,
           FEC_NAC_CLI,
           FEC_CREA_CLIENTE,
           USU_CREA_CLIENTE,
           IND_ESTADO,
           SEXO_CLI,
           APE_PAT_CLI,
           APE_MAT_CLI
           ) --ASOSA, 06.04.2010
/*        select d.le,d.nombre_completo,
               to_date(to_char(to_date(d.fec_nac,'yyyymmdd'),'dd/mm/yyyy'),'dd/mm/yyyy'),
               sysdate,
               vUsu,
               'A',
               d.sexo --ASOSA, 06.04.2010
        from   @pbl_dni_red@ d
        where  d.le = cDni_in;
*/
        SELECT VAL_DNI dni,VAL_NOMBRE nombre,VAL_FECHA_NAC fecha,
               sysdate,
               vUsu,
               'A',
               TRIM(VAL_SEXO),
               VAL_APE_PAT,
               VAL_APE_MAT
        FROM   DUAL;



    vSecuencialNumera := TRIM(Farma_Utility.COMPLETAR_CON_SIMBOLO
                            (Farma_Utility.OBTENER_NUMERACION
                             (cCodGrupoCia,cCodLocal_in,CC_COD_NUMERA),6,'0','I'));
    vConcatenado := vPrefijo_tarjeta||cCodLocal_in||vSecuencialNumera;
    vNuevaTarjetaFidelizacion:= F_GENERA_EAN13(vConcatenado);
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia,cCodLocal_in,CC_COD_NUMERA,vUsu);

    begin
    INSERT INTO FID_TARJETA
    (COD_TARJETA, DNI_CLI, COD_LOCAL, USU_CREA_TARJETA, FEC_CREA_TARJETA)
    VALUES
    (vNuevaTarjetaFidelizacion, NULL, cCodLocal_in, vUsu, SYSDATE);
    exception 
      when others then
        null;
    end;
    
    vCodTarjeta :=  vNuevaTarjetaFidelizacion;

        update FID_TARJETA
        set    DNI_CLI = cDni_in
        where  COD_TARJETA = vCodTarjeta;
       -- dbms_output.put_line('vCodTarjeta: ' || vCodTarjeta || ' - '||cDni_in);

        commit;
         end if;
      ELSE --ini ASOSA, 06.04.2010
          if nCantReniec > 0 THEN
             UPDATE pbl_cliente cli
                  SET cli.sexo_cli = TRIM(VAL_SEXO),
                      cli.nom_cli = VAL_NOMBRE,
                      cli.ape_pat_cli = VAL_APE_PAT,
                      cli.ape_mat_cli = VAL_APE_MAT
                  WHERE cli.dni_cli=cDNI_in;
                  COMMIT;
          END IF; --fin ASOSA, 06.04.2010

      SELECT  COUNT(*)
      INTO    nCantTarjCliente
      FROM    FID_TARJETA T
      where   t.Dni_Cli = cDni_in;          
     
      if nCantTarjCliente = 0 then
            vSecuencialNumera := TRIM(Farma_Utility.COMPLETAR_CON_SIMBOLO
                                    (Farma_Utility.OBTENER_NUMERACION
                                     (cCodGrupoCia,cCodLocal_in,CC_COD_NUMERA),6,'0','I'));
            vConcatenado := vPrefijo_tarjeta||cCodLocal_in||vSecuencialNumera;
            vNuevaTarjetaFidelizacion:= F_GENERA_EAN13(vConcatenado);
            Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia,cCodLocal_in,CC_COD_NUMERA,vUsu);
        
            begin
            INSERT INTO FID_TARJETA
            (COD_TARJETA, DNI_CLI, COD_LOCAL, USU_CREA_TARJETA, FEC_CREA_TARJETA)
            VALUES
            (vNuevaTarjetaFidelizacion, NULL, cCodLocal_in, vUsu, SYSDATE);
            exception 
              when others then
                null;
            end;
            
            vCodTarjeta :=  vNuevaTarjetaFidelizacion;
        
                update FID_TARJETA
                set    DNI_CLI = cDni_in
                where  COD_TARJETA = vCodTarjeta;  
                
            commit;        
      end if;
          
      end if;
  exception
  when others then
       rollback;

  END;
  /* *************************************************** */
 FUNCTION F_GENERA_EAN13(vCodigo_in IN VARCHAR2)
  RETURN CHAR
  IS
    cCodEan13 CHAR(13);
    vDig1 NUMBER(1);
    vDig2 NUMBER(1);
    vDig3 NUMBER(1);
    vDig4 NUMBER(1);
    vDig5 NUMBER(1);
    vDig6 NUMBER(1);
    vDig7 NUMBER(1);
    vDig8 NUMBER(1);
    vDig9 NUMBER(1);
    vDig10 NUMBER(1);
    vDig11 NUMBER(1);
    vDig12 NUMBER(1);
    vDig13 NUMBER(1);

    vSumImp NUMBER;
    vSumPar NUMBER;
    vAux NUMBER;
    vAux2 NUMBER;

  BEGIN

    vDig1 := TO_NUMBER(SUBSTR(vCodigo_in,1,1));
    vDig2 := TO_NUMBER(SUBSTR(vCodigo_in,2,1));
    vDig3 := TO_NUMBER(SUBSTR(vCodigo_in,3,1));
    vDig4 := TO_NUMBER(SUBSTR(vCodigo_in,4,1));
    vDig5 := TO_NUMBER(SUBSTR(vCodigo_in,5,1));
    vDig6 := TO_NUMBER(SUBSTR(vCodigo_in,6,1));
    vDig7 := TO_NUMBER(SUBSTR(vCodigo_in,7,1));
    vDig8 := TO_NUMBER(SUBSTR(vCodigo_in,8,1));
    vDig9 := TO_NUMBER(SUBSTR(vCodigo_in,9,1));
    vDig10 := TO_NUMBER(SUBSTR(vCodigo_in,10,1));
    vDig11 := TO_NUMBER(SUBSTR(vCodigo_in,11,1));
    vDig12 := TO_NUMBER(SUBSTR(vCodigo_in,12,1));

    vSumImp := (vDig12+vDig10+vDig8+vDig6+vDig4+vDig2)*3;
    vSumPar := vDig11+vDig9+vDig7+vDig5+vDig3+vDig1;

    vAux := vSumImp+vSumPar;
    vAux2 := MOD(vAux,10);
    --RAISE_APPLICATION_ERROR(-20005,vCodigo_in||'*'||vAux||'/'||vAux2);
    IF vAux2 = 0 THEN
      vDig13 := 0;
    ELSE
      vDig13 := 10 - vAux2;
    END IF;

    cCodEan13 := vCodigo_in||vDig13;

    RETURN cCodEan13;
  END;

  /* ******************************************************** */
  FUNCTION F_VALIDACION_FINAL_DNI(cCodGrupoCia_in        IN CHAR,
                                  cCodLocal_in           IN CHAR,
                                  cUsuLogin_in           IN CHAR,
                                  cFrmDni_in             IN CHAR,
                                  cFrmNombre_in          IN CHAR,
                                  cFrmFechaNacimiento_in IN CHAR,
                                  cValidaDni_in          IN CHAR,
                                  cTercerDni_in          IN CHAR)
   RETURN varchar2
   is
   nCantReniec  number;
   nCantCliente number;
   vResultado varchar2(3000):= 'N';
   CC_MOD_TAR_FID char(2):='TF';--TARJETAS DE FIFELIZACION

   cCantidadCamposCliente number;
   cCantidadCamposNecesarios number;

   vFinalDni_in             varchar2(20);
   vFinalNombre_in          varchar2(20);
   vFinalFechaNacimiento_in varchar2(20);

   vIndPideClave char(1);

    -- dubilluz 16.05.2012
    vDatosDNI varchar2(30000);
  VAL_DNI varchar2(20);
  VAL_NOMBRE varchar2(10000);
  VAL_APE_PAT varchar2(10000);
  VAL_APE_MAT varchar2(10000);
  VAL_SEXO varchar2(20);
  VAL_FECHA_NAC DATE;
    -- dubilluz  16.05.2012

   begin


    -- dubilluz 16.05.2012
    /*
    SELECT COUNT(1)
    INTO nCantReniec
    FROM @PBL_DNI_RED@ A
    WHERE A.LE=cTercerDni_in;
    */
    -- vDatosDNI := utility_dni_reniec.aux_datos_existe_dni('001','000',cTercerDni_in);
    -- KMONCADA 24.09.2014 VERIFICA LONGITUD DE CADENA DNI
    IF LENGTH(TRIM(cTercerDni_in))=8 THEN
      vDatosDNI := utility_dni_reniec.aux_datos_existe_dni('001','000',cTercerDni_in);
    ELSE
      vDatosDNI := 'N';
    END IF;
    if vDatosDNI = 'N' then
       nCantReniec := 0;
    else
      nCantReniec := 1;
      VAL_DNI     := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_DNI,'@'));
      VAL_NOMBRE  := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_NOMBRE,'@'));
      VAL_APE_PAT := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_APE_PAT,'@'));
      VAL_APE_MAT := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_APE_MAT,'@'));
      VAL_SEXO    := TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_SEXO,'@'));
      VAL_FECHA_NAC := TO_DATE(TRIM(FARMA_UTILITY.split(vDatosDNI,COL_ET_RENIEC_FN,'@')),'yyyymmdd');
    end if;

    -- dubilluz 16.05.2012



      SELECT  COUNT(*)
      INTO    nCantCliente
      FROM    PBL_CLIENTE C
      where   C.DNI_CLI = cTercerDni_in;

     dbms_output.put_line('nCantReniec: ' ||nCantReniec);
     dbms_output.put_line('nCantCliente: '|| nCantCliente);

    if nCantCliente > 0 then
        -- se obtiene el numero de campos ingresados por el cliente
        select decode(DNI_CLI, null, 0, 1) +
               decode(NOM_CLI, null, 0, 1) +
               decode(APE_PAT_CLI, null, 0, 1) +
               decode(APE_MAT_CLI, null, 0, 1) +
               decode(FONO_CLI, null, 0, 1)    +
               decode(SEXO_CLI, null,0, 1)     +
               decode(DIR_CLI, null, 0, 1)     +
               decode(FEC_NAC_CLI, null, 0, 1) +
               decode(l.email, null, 0, 1)
          into cCantidadCamposCliente
          from pbl_cliente l
         where l.dni_cli = trim(cTercerDni_in);


        SELECT COUNT(*)
        INTO   cCantidadCamposNecesarios
        FROM   FID_CAMPOS_FIDELIZACION
        WHERE IND_MOD = CC_MOD_TAR_FID;

        dbms_output.put_line('cCantidadCamposCliente    :' || cCantidadCamposCliente);
        dbms_output.put_line('cCantidadCamposNecesarios :' || cCantidadCamposNecesarios);
        IF cCantidadCamposCliente >= cCantidadCamposNecesarios THEN
           dbms_output.put_line('1. datos completos Bienvenida al cliente : '||cTercerDni_in );

           select e.dni_cli,e.nom_cli,to_char(e.fec_nac_cli,'dd/mm/yyyy')
           into   vFinalDni_in,vFinalNombre_in,vFinalFechaNacimiento_in
           from   pbl_cliente e
           where  dni_cli = trim(cTercerDni_in);

        ELSe--IF cCantidadCamposCliente < cCantidadCamposNecesarios then

              IF  nCantReniec > 0 THEN
                  dbms_output.put_line('actualiza los datos de cliente segun reniec: '||cTercerDni_in );
                  /*
                  update pbl_cliente e
                  set    (e.fec_nac_cli,e.nom_cli) =
                                                     (
                                                     select to_date(d.fec_nac,'yyyymmdd'),d.nombre_completo
                                                     from   @pbl_dni_red@ d
                                                     where  d.le = e.dni_cli
                                                     )
                  where  e.dni_cli     = cTercerDni_in
                  and    exists
                                (
                                 select 1
                                 from   @pbl_dni_red@ q
                                 where  q.le = e.dni_cli
                                );
                    */
                   vFinalDni_in := cTercerDni_in;
                   /*
                   select to_char(to_date(d.fec_nac,'yyyymmdd'),'dd/mm/yyyy'),d.nombre_completo
                   into   vFinalFechaNacimiento_in,vFinalNombre_in
                   from   @pbl_dni_red@ d
                   where  d.le = cTercerDni_in;
                   */
                   vFinalFechaNacimiento_in := TO_CHAR(VAL_FECHA_NAC,'DD/MM/YYYY');
                   vFinalNombre_in := VAL_NOMBRE;

                  dbms_output.put_line('2.Bienvenida al cliente : '||cTercerDni_in );
              ELSE
                  /*
                  update pbl_cliente e
                  set    e.nom_cli     = cFrmNombre_in,
                         e.fec_nac_cli = to_date(cFrmFechaNacimiento_in,'yyyymmdd')
                  where  e.dni_cli     = cTercerDni_in;
                  */
                 vFinalDni_in := cTercerDni_in;
                 vFinalNombre_in := cFrmNombre_in;
                 vFinalFechaNacimiento_in := cFrmFechaNacimiento_in;

                  dbms_output.put_line('3.Bienvenida al cliente : '||cTercerDni_in );
              END IF;
        END IF;
    else
       dbms_output.put_line('no esta en cliente');
        if nCantReniec > 0 then
           dbms_output.put_line('datos de RENIEC');
           --se registra el cliente con los datos de RENIEC.
           --SE GRABA CON LOS VALORES DE RENIEC
/*           select w.le,w.nombre_completo,to_char(to_date(w.fec_nac,'yyyymmdd'),'dd/mm/yyyy')
           into   vFinalDni_in,vFinalNombre_in,vFinalFechaNacimiento_in
           from   @pbl_dni_red@ w
           where  w.le = cTercerDni_in;*/

           vFinalDni_in := VAL_DNI;
           vFinalFechaNacimiento_in := TO_CHAR(VAL_FECHA_NAC,'DD/MM/YYYY');
           vFinalNombre_in := VAL_NOMBRE;
           -- DUBILLUZ 16.05.2012
        else
           --SE GRABA CON LOS SIGUIENTES VALORES.
           --
           vFinalDni_in := cTercerDni_in;
           vFinalNombre_in := cFrmNombre_in;
           vFinalFechaNacimiento_in := cFrmFechaNacimiento_in;
        end if;

    end if;

    vResultado:= vFinalDni_in    ||'@'||
                 vFinalNombre_in ||'@'||
                 vFinalFechaNacimiento_in;

    if F_VAR2_GET_IND_CLAVE_CONF = 'S' then
       vIndPideClave := FID_F_VALIDA_DNI_REC(cCodGrupoCia_in,cCodLocal_in,vFinalDni_in,vFinalFechaNacimiento_in);
    else
       vIndPideClave := 'N';
    end if;



    return vResultado||'@'||vIndPideClave;
   end;

END PTOVENTA_FID_RENIEC;
/

