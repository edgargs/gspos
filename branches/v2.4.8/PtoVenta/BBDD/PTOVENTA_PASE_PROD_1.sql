--------------------------------------------------------
--  DDL for Package Body PTOVENTA_PASE_PROD
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_PASE_PROD" is

  FUNCTION PROC_PRELIMINAR_PASE_PROD(vIdUserName_in   IN CHAR)
  RETURN CHAR
  IS
    cCodGrupoCia_in   CHAR(3);
    cCodLocal_in      CHAR(3);
    vIdUsuario_in    VARCHAR2(200):= 'P_PASE_PROD';
    V_RES           CHAR(1);
  BEGIN
    BEGIN
      SELECT DISTINCT(C.COD_GRUPO_CIA)
      INTO   cCodGrupoCia_in
      FROM   VTA_PEDIDO_VTA_CAB C;

      SELECT DISTINCT(C.COD_LOCAL)
      INTO   cCodLocal_in
      FROM   VTA_PEDIDO_VTA_CAB C;

      V_RES := PROC_EJECUTA_RESPALDO(cCodGrupoCia_in,cCodLocal_in,vIdUsuario_in,vIdUserName_in);
    EXCEPTION
      WHEN OTHERS THEN
       V_RES := 'N';
   END;

  RETURN V_RES;
  END;
/*******************************************************************************/

  FUNCTION PROC_EJECUTA_RESPALDO(cCodGrupoCia_in  IN CHAR,
                                 cCodLocal_in     IN CHAR,
                                 vIdUsuario_in    IN CHAR,
                                 vIdUserName_in   IN CHAR)
  RETURN CHAR
  IS
    v_resultado CHAR ;
    v_alter_session VARCHAR2(2000);

    CURSOR curSession IS
          SELECT D.MACHINE,
                 D.SID,
                 D.SERIAL#
          FROM SYS.V_$SESSION D
          WHERE username= vIdUserName_in AND PROGRAM='JDBC Thin Client';

    CURSOR pedidos_pendientes IS
  		 		SELECT VTA_CAB.NUM_PED_VTA NUMERO
		  		FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
			  	WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
				  AND	   VTA_CAB.COD_LOCAL     = cCodLocal_in
				  AND	   VTA_CAB.EST_PED_VTA   = 'P';

  BEGIN
           DBMS_OUTPUT.put_line('cCodGrupoCia_in=' || cCodGrupoCia_in || ', cCodLocal_in=' || cCodLocal_in || ', vIdUsuario_in=' || vIdUsuario_in || ', vIdUserName_in=' || vIdUserName_in );

     BEGIN
       /** 1) ********BORRANDO SESSIONES**************/
        FOR sessiones_act IN curSession LOOP
          BEGIN
           v_alter_session :=
               'ALTER SYSTEM KILL SESSION '''||sessiones_act.sid||','||sessiones_act.serial#||''' IMMEDIATE ';
           EXECUTE IMMEDIATE
             v_alter_session;

           DBMS_OUTPUT.put_line('ELIMINÓ SESIÓN:' || sessiones_act.sid);

          EXCEPTION
          WHEN OTHERS THEN
           ROLLBACK;
           v_resultado := 'N';
           DBMS_OUTPUT.put_line('EXCEPTION SESIONES');
          END;
        END LOOP;

        DBMS_OUTPUT.put_line('TERMINÓ DE BORRAR SESIONES');



        DBMS_OUTPUT.put_line('TERMINÓ DE BORRAR RESPALDO STOCK Y LGT_PROD_LOCAL');

       /*** 3) *******ELMINANDO PEDIDOS PENDIENTES******/
     	  FOR pedidos_rec IN pedidos_pendientes
    	  LOOP
    	  	  PTOVENTA_CAJ_ANUL.CAJ_ANULAR_PEDIDO_PENDIENTE(cCodGrupoCia_in,
                  		  		   						   	  cCodLocal_in,
    							                    				  pedidos_rec.NUMERO,
                        											  vIdUsuario_in);
    	  END LOOP;

        DBMS_OUTPUT.put_line('TERMINÓ DE ELIMINAR PEDIDOS PENDIENTES');

       /*** 4) *******ELMINANDO INFORMACION Y
                      ACTUALIZANDO EL STOCK-COMPROMETDIDO******/

        DBMS_OUTPUT.put_line('ACTUALIZA STOCK COMPROMETIDOS');

        COMMIT;
        v_resultado := 'S';
   EXCEPTION
    WHEN OTHERS THEN
     ROLLBACK;
     v_resultado := 'N';
      DBMS_OUTPUT.put_line('EXCEPTION PEDIDO');
   END;
	  RETURN v_resultado;
  END;


end PTOVENTA_PASE_PROD;

/
