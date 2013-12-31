--------------------------------------------------------
--  DDL for Package Body PTOVENTA_MENSAJES
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_MENSAJES" IS


  /**
  * Copyright (c) 2006 MiFarma Peru S.A.
  *
  * Entorno de Desarrollo : Oracle9i
  * Nombre del Paquete    : FARMA_GRAL
  *
  * Histórico de Creación/Modificación
  * LMESIA       25.04.2006   Creación
  *
  * @author Luis Mesia Rivera
  * @version 1.0
  *
  */

  /************************************************************************************/

  FUNCTION F_VAR2_GET_MENSAJE(cGrupoCia_in  IN CHAR,
                             cCod_Rol_in   IN CHAR )
  RETURN VARCHAR2
  IS
  vIndParam varchar2(32000);
  vCodRol   char(3);
  nNumPedDel number;
   vFecha_Max varchar2(3000);

   n_CantMensajes NUMBER;

   n_IdMensaje    NUMBER;
   n_IdTipoLetra  NUMBER;
   n_IdColorLetra NUMBER;
      vDesColor varchar2(20);

   n_CodGrupo   NUMBER;

  BEGIN

      BEGIN

          vCodRol := trim(cCod_Rol_in);

-- 2009-09-18 JOLIVA: SE AGREGA CODIGO DE GRUPO DE MENSAJES PARA OBTENER EL GRUPO DE MENSAJES VIGENTES
          SELECT MAX(COD_GRUPO)
          INTO n_CodGrupo
          FROM PBL_MENSAJE_PERSONAL MM
          WHERE SYSDATE BETWEEN FECH_INI AND FECH_FIN;

          SELECT TRUNC(DBMS_RANDOM.VALUE(1,COUNT(*)))
          INTO n_IdMensaje
          FROM PBL_MENSAJE_PERSONAL
          WHERE COD_GRUPO = n_CodGrupo;

/*
          SELECT COUNT(*)
          INTO n_CantMensajes
          FROM PBL_MENSAJE_PERSONAL;

          -- Obtengo el ID del mensaje en base al día del mes y la cantidad de mensajes disponibles
          IF n_CantMensajes < TO_NUMBER(TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE,'MM'),+1)-1,'DD')) THEN
             n_IdMensaje := MOD(TO_NUMBER(TO_CHAR(SYSDATE,'DD')), n_CantMensajes) + 1;
          ELSE
             n_IdMensaje := MOD(n_CantMensajes, TO_NUMBER(TO_CHAR(SYSDATE,'DD'))) + 1;
          END IF;
*/


          DBMS_OUTPUT.put_line('n_IdMensaje=' || n_IdMensaje);

          -- Obtengo el ID del tipo de letra
          SELECT MOD(TO_NUMBER(TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE,'MM'),+1)-1,'DD')), COUNT(*)) + 1
          INTO n_IdTipoLetra
          FROM PBL_TIP_LETRA_MSJ;

          DBMS_OUTPUT.put_line('n_IdTipoLetra=' || n_IdTipoLetra);

          -- Obtengo el ID del color de letra
          SELECT MOD(TO_NUMBER(TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE,'MM'),+1)-1,'DD')), COUNT(*)) + 1
          INTO n_IdColorLetra
          FROM PBL_COLOR_MSJ;

          DBMS_OUTPUT.put_line('n_IdColorLetra=' || n_IdColorLetra);

             SELECT DESC_COLOR
             INTO vDesColor
          FROM PBL_COLOR_MSJ
          WHERE SEC_COLOR=n_IdColorLetra;

    --'<FONT FACE = "' || T.DESC_TIPO_LETRA || '" color="' || C.DESC_COLOR  || '">' || NVL(M.TEXTO,' ') || '</FONT></HTML>'
          SELECT '<HTML> '||
                 '<table width="300" height="84" border="0">  '||
                 '<tr> '||
                 --'<th width="350" bordercolor="#FFFFFF" bgcolor="' ||vDesColor|| '"><div align="center"><font color="#FFFFFF" '||
                 --'<td width="350" bgcolor="' ||vDesColor|| '">'||
                 '<td width="300" bgcolor="' ||vDesColor|| '">'||
                 '<div align="center"><font color="#FFFFFF" '||
                  'size ="5" face="' || T.DESC_TIPO_LETRA || '">' ||
                 '<br>'||
                 '<b>'||NVL(M.TEXTO,' ')||'</b>'||
                 '<br>'||
                 '</font></div></td> '||
                 ' </tr>'||
                 ' </table> '||
                 ' </HTML> '
          INTO   vIndParam
          FROM  PBL_MENSAJE_PERSONAL M,
                 PBL_ROL_X_MENSAJE RM,
                 PBL_HORAS_MENSAJES H,
                 PBL_TIP_LETRA_MSJ T,
                 PBL_COLOR_MSJ C
          WHERE  RM.COD_ROL = vCodRol
          AND    M.SECMENSAJE = n_IdMensaje
          AND    RM.SECMENSAJE = M.SECMENSAJE
          AND    TO_CHAR(SYSDATE,'HH24MISS') BETWEEN H.HORA_INI AND H.HORA_FIN
          AND    M.ESTADO = 'A'
          AND    T.SEC_TIP_LETRA = n_IdTipoLetra
          AND    C.SEC_COLOR = n_IdColorLetra
          AND    M.COD_GRUPO = n_CodGrupo;
          --and  rownum = 1;

              /*<HTML>
              <table width="292" height="61" border="1">
              <tr>
              <th width="282" bordercolor="#FFFFFF" bgcolor="#CC3300"><div align="center"><font color="#FFFFFF" face="Arial, Helvetica, sans-serif">FIDELIZA A TUS CLIENTES </font></div></th>
              </tr>
              </table>
              </HTML>*/

          /*
              SELECT 1
              FROM DUAL
              WHERE TO_CHAR(SYSDATE,'HH24MISS') BETWEEN '185834' AND '195900'
          */


      EXCEPTION
               WHEN NO_DATA_FOUND THEN
                     vIndParam := '';
      END;

      return vIndParam;
    END ;
  FUNCTION GET_MSG_FIDEICOMIZO(cCodGrupoCia in char,cCodLocal in char)
    RETURN VARCHAR2
  IS
    CADENA VARCHAR2(300);
  BEGIN
      --lunes = 1  & Domingo = 7
      BEGIN
      select NVL(t.desc_larga,' ')
      INTO  CADENA
      from  pbl_tab_gral t
      where t.id_tab_gral = 390
      AND   COD_TAB_GRAL = 'FIDEICOMIZO'
      and   sysdate between fech_ini_vig and nvl(fech_fin_vig,sysdate+1);
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
           CADENA := ' ';
      END;
	  RETURN CADENA;
  END;
END PTOVENTA_MENSAJES;

/
