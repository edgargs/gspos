CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_VALIDACION_CE" AS

	TYPE FarmaCursor IS REF CURSOR;

	/*************************************************************/
  --Descripcion: ENVIA INFORMACION POR CORREO
  --Fecha       Usuario		Comentario
  --16/01/2007  PAULO     Creación
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR);

	/*************************************************************/
  --Descripcion: OBTIENE LOS USUARIOS SIN CODIGO DE TRABAJADOR
  --Fecha       Usuario		Comentario
  --16/01/2007  PAULO     Creación
    PROCEDURE USU_OBTIENE_USUARIOS_MAIL;

	/*************************************************************/
  --Descripcion: ENVIA INFORMACION POR CORREO AL OPERADOR
  --Fecha       Usuario		Comentario
  --16/01/2007  PAULO     Creación
   PROCEDURE INT_ENVIA_CORREO_INFO_OPER(vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR);




END;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_VALIDACION_CE" AS

  PROCEDURE INT_ENVIA_CORREO_INFORMACION(vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_ADMIN_USU;
    CCReceiverAddress VARCHAR2(120) := NULL;
    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN
/*    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;
*/
    SELECT 'LOCAL MATRIZ' INTO v_vDescLocal FROM DUAL ;
    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             CCReceiverAddress,
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;

  /****************************************************************************/
  PROCEDURE INT_ENVIA_CORREO_INFO_OPER(vAsunto_in       IN CHAR,
                                       vTitulo_in       IN CHAR,
                                       vMensaje_in      IN CHAR)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_CAMBIOS;
    CCReceiverAddress VARCHAR2(120) := NULL;
    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN
/*    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;
*/
    SELECT 'LOCAL MATRIZ' INTO v_vDescLocal FROM DUAL ;
    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             CCReceiverAddress,
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;

  /****************************************************************************/

  PROCEDURE USU_OBTIENE_USUARIOS_MAIL
  AS
    mesg_body VARCHAR2(20000);
    v_count NUMBER ;
    CURSOR curUsuarios IS
           SELECT  LOCAL.COD_LOCAL || ' - ' || LOCAL.DESC_CORTA_LOCAL TIENDA,
                   USU.NOM_USU NOMBRE,
                   USU.APE_PAT APELLIDOP,
                   USU.APE_MAT APELLIDOM,
                   USU.DNI_USU DNI
            FROM   PBL_USU_LOCAL USU,
                   PBL_LOCAL LOCAL
            WHERE  USU.EST_USU = 'A'
            AND    USU.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
            AND    USU.COD_LOCAL = LOCAL.COD_LOCAL
            AND    USU.COD_CIA = LOCAL.COD_CIA
            AND    USU.COD_TRAB IS NULL;
      v_rCurUsuarios curUsuarios%ROWTYPE;
      BEGIN
        SELECT  COUNT(*)
                INTO v_count
        FROM    PBL_USU_LOCAL USU,
                PBL_LOCAL LOCAL
        WHERE   USU.EST_USU = 'A'
        AND     USU.COD_GRUPO_CIA = LOCAL.COD_GRUPO_CIA
        AND     USU.COD_LOCAL = LOCAL.COD_LOCAL
        AND     USU.COD_CIA = LOCAL.COD_CIA
        AND     USU.COD_TRAB IS NULL;

          IF v_count > 0 THEN
              mesg_body := mesg_body||'<h4>USUARIOS SIN CODIGO DE TRABAJADOR</h4>';
              mesg_body := mesg_body||'<table style="text-align: left; width: 95%;" border="1"';
              mesg_body := mesg_body||' cellpadding="2" cellspacing="1">';
              mesg_body := mesg_body||'  <tbody>';
              mesg_body := mesg_body||'    <tr>';
              mesg_body := mesg_body||'      <th><small>LOCAL</small></th>';
              mesg_body := mesg_body||'      <th><small>NOMBRES</small></th>';
              mesg_body := mesg_body||'      <th><small>APELLIDO PATERNO</small></th>';
              mesg_body := mesg_body||'      <th><small>APELLIDO MATERNO</small></th>';
              mesg_body := mesg_body||'      <th><small>DNI</small></th>';
              mesg_body := mesg_body||'    </tr>';

              --CREAR CUERPO MENSAJE;
              FOR v_rCurUsuarios IN curUsuarios
              LOOP
                mesg_body := mesg_body||'   <tr>'||
                                        '      <td><small>'||v_rCurUsuarios.TIENDA||'</small></td>'||
                                        '      <td><small>'||v_rCurUsuarios.NOMBRE||'</small></td>'||
                                        '      <td><small>'||v_rCurUsuarios.APELLIDOP||'</small></td>'||
                                        '      <td><small>'||v_rCurUsuarios.APELLIDOM||'</small></td>'||
                                        '      <td><small>'||v_rCurUsuarios.DNI||'</small></td>'||
                                        '   </tr>';
              END LOOP;
              --FIN HTML
              mesg_body := mesg_body||'  </tbody>';
              mesg_body := mesg_body||'</table>';
              mesg_body := mesg_body||'<br>';

              --MAIL DE PROCESO CON INFORMACION
              INT_ENVIA_CORREO_INFORMACION('USUARIOS SIN CODIGO DE TRABAJADOR: ',
                                           'ALERTA',
                                           'EXISTEN USUARIOS QUE NO POSEEN CODIGO DE TRABAJADOR' ||
                                           '</B>'|| mesg_body);
                                             dbms_output.put_line('Envia el mail vacio');
         ELSE
             INT_ENVIA_CORREO_INFO_OPER('NO EXISTEN USUARIOS SIN CODIGO DE TRABAJADOR: ',
                                        'EXITO',
                                        'TODOS LOS USUARIOS POSEEN CODIGO DE TRABAJADOR');

         END IF;
      END;

END;
/

