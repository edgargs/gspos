CREATE OR REPLACE PACKAGE PTOVENTA_FTP IS

PROCEDURE SP_ENVIA_ARCHIVO(nIdArchivo_in   PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE,
                           vNomArchivo_in VARCHAR2,
                           cCodLocal_in in char);

MARCA_MIFARMA      PTOVENTA.PBL_LOCAL.COD_CIA%TYPE :='001';
MARCA_FASA         PTOVENTA.PBL_LOCAL.COD_CIA%TYPE :='002';
MARCA_BTL          PTOVENTA.PBL_LOCAL.COD_CIA%TYPE :='003';
MARCA_BTL_AMAZ     PTOVENTA.PBL_LOCAL.COD_CIA%TYPE :='004';
A_ID_ARCHIVO_CJE   PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE :=1;
A_ID_ARCHIVO_RMT   PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE :=2;
A_ID_ARCHIVO_GCONF PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE :=3;
A_ID_ARCHIVO_VTA   PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE :=4;
A_ID_ARCHIVO_MC    PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE :=5;
A_ID_ARCHIVO_CC    PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE :=6;
A_ID_ARCHIVO_CI    PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE :=7;
A_ID_ARCHIVO_OC    PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE :=8;
A_ID_ARCHIVO_DP    PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE :=9;
  /* ****************************************************************** */
  --Descripcion: Envia los archivos fisicos de solicitudes a matriz
  --Fecha       Usuario        Comentario
  --23/09/2015  EMAQUERA       Creacion 
  /* ****************************************************************** */ 
   PROCEDURE FTP_P_ENVIA_FILE_SOL(nIdArchivo_in   PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE,
                                  vNomArchivo_in VARCHAR2,
                                  cCodLocal_in in char);
--*****************************************************************************************
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA_FTP IS
--********************************************************************************************
PROCEDURE SP_ENVIA_ARCHIVO(nIdArchivo_in   PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE,
                           vNomArchivo_in VARCHAR2,
                           cCodLocal_in in char)
AS
  v_CONS_DIR_INTERFACES VARCHAR2(50) := 'DIR_INTERFACES';  --ORIGEN
  CONS_DIR_INTERFACES_OLD VARCHAR2(50) := 'DIR_INTERFACES_OLD';  --DESTINO
  v_REG_TRAMA                              PTOVENTA.AUX_ARCHIVO_FTP%ROWTYPE;
  --vNomArchivo_in                        VARCHAR2(1000):='CJE117733520150607145216.TXT';--PTOVENTA.AUX_ARCHIVO_FTP.NOMBRE_ARCHIVO%TYPE:= FN_DEV_NOM_CONVERT(nIdArchivo_in);
  v_DESTINO VARCHAR2(200);
  C_CIA PTOVENTA.PBL_LOCAL.COD_CIA%TYPE;
BEGIN

  --COMIENZO  A CREAR LA ESTRUCTURA DE LAS TABLAS
     SELECT *
     INTO v_REG_TRAMA
     FROM PTOVENTA.AUX_ARCHIVO_FTP
     WHERE ID_ARCHIVO = nIdArchivo_in;
  --COMIENZO A CREAR EL DETALLE CON LOS DATOS DE LA TABLAS

  --//////////////////////////////////////////////////////////////////////////////////////////////////
  declare
    v_CONN UTL_TCP.CONNECTION;
  BEGIN
    BEGIN
      v_CONN := PTOVENTA.PKG_FTP.LOGIN(v_REG_TRAMA.SERVIDOR,
                                             v_REG_TRAMA.PUERTO,
                                             v_REG_TRAMA.USUARIO,
                                             v_REG_TRAMA.PASSWORD);
    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20000,
                                ' Conectandose por FTP al servidor' ||
                                CHR(13) || SQLERRM);
       PTOVENTA.PKG_FTP.LOGOUT(v_CONN);
        UTL_TCP.CLOSE_ALL_CONNECTIONS;
    END;

    --PKG_FTP.ascii(p_conn => v_CONN);

    BEGIN

      /*
      PKG_FTP.put_direct (p_conn      => v_CONN,
                p_from_dir  => CONS_SERVER_CARPETA,
                p_from_file => A_NOM_ARCHIVO,
                p_to_file   => A_NOM_ARCHIVO);

      */
      --ptoventa.PKG_FTP.BINARY(P_CONN => v_CONN);

      IF nIdArchivo_in=A_ID_ARCHIVO_CJE THEN   --PARA CAJAS ELECTRONICAS (CJE)
         v_DESTINO:=v_REG_TRAMA.CARPETA||cCodLocal_in;
      END IF;

      IF nIdArchivo_in=A_ID_ARCHIVO_RMT THEN   --PARA REMITOS (RMT)
         SELECT COD_CIA INTO C_CIA FROM PTOVENTA.PBL_LOCAL WHERE COD_LOCAL= cCodLocal_in;
         IF C_CIA= MARCA_MIFARMA THEN
            v_DESTINO:=v_REG_TRAMA.CARPETA||'rmt_total_mf';
         END IF;
         IF C_CIA=MARCA_FASA THEN
            v_DESTINO:=v_REG_TRAMA.CARPETA||'rmt_total_fasa';
         END IF;
         IF C_CIA=MARCA_BTL THEN
            v_DESTINO:=v_REG_TRAMA.CARPETA||'rmt_total_btl';
         END IF;
         IF C_CIA=MARCA_BTL_AMAZ THEN
            v_DESTINO:=v_REG_TRAMA.CARPETA||'rmt_total_amaz';
         END IF;
      END IF;

      IF nIdArchivo_in=A_ID_ARCHIVO_GCONF THEN   --PARA CONFIRMACIONES FASA Y BTL (GCONF)
         v_DESTINO:=v_REG_TRAMA.CARPETA||cCodLocal_in;
      END IF;

      IF nIdArchivo_in=A_ID_ARCHIVO_VTA THEN   --PARA VENTAS
         v_DESTINO:=v_REG_TRAMA.CARPETA||cCodLocal_in;
      END IF;

      IF nIdArchivo_in=A_ID_ARCHIVO_MC THEN   --PARA ARCHIVOS MC (RECARGAS, AJUSTES Y COTIZACIONES A LA COMPETENCIA)
         v_DESTINO:=v_REG_TRAMA.CARPETA||cCodLocal_in;
      END IF;

      IF nIdArchivo_in=A_ID_ARCHIVO_CC THEN   --PARA ARCHIVOS DEVOLUCIONES MF
         v_DESTINO:=v_REG_TRAMA.CARPETA||cCodLocal_in;
      END IF;

      IF nIdArchivo_in=A_ID_ARCHIVO_CI THEN   --PARA ARCHIVOS CONFIRMACIONES MF
         v_DESTINO:=v_REG_TRAMA.CARPETA||cCodLocal_in;
      END IF;

      IF nIdArchivo_in=A_ID_ARCHIVO_OC THEN   --PARA ORDENES DE MERCADERIA DIRECTA
         v_DESTINO:=v_REG_TRAMA.CARPETA||cCodLocal_in;
      END IF;

      IF nIdArchivo_in=A_ID_ARCHIVO_DP THEN   --PARA DEVOLUCIONES DE ORDENES DE MERCADERIA DIRECTA
         v_DESTINO:=v_REG_TRAMA.CARPETA||cCodLocal_in;
      END IF;

      ptoventa.PKG_FTP.PUT(P_CONN      => v_CONN,
                           P_FROM_DIR  => v_CONS_DIR_INTERFACES,
                           P_FROM_FILE => vNomArchivo_in,
                           P_TO_DIR    => v_DESTINO,--v_REG_TRAMA.CARPETA||cCodLocal_in,
                           P_TO_FILE   => vNomArchivo_in);

      UTL_FILE.FCOPY(v_CONS_DIR_INTERFACES,vNomArchivo_in,CONS_DIR_INTERFACES_OLD,vNomArchivo_in);
      UTL_FILE.fremove(v_CONS_DIR_INTERFACES,vNomArchivo_in);

    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20000,
                                'Enviando de la carpeta ' || SQLERRM);
        PTOVENTA.PKG_FTP.LOGOUT(v_CONN);
        UTL_TCP.CLOSE_ALL_CONNECTIONS;
    END;

    PTOVENTA.PKG_FTP.LOGOUT(v_CONN);

    UTL_TCP.CLOSE_ALL_CONNECTIONS;
  END;
END;
--********************************************************************************************
PROCEDURE FTP_P_ENVIA_FILE_SOL(nIdArchivo_in   PTOVENTA.AUX_ARCHIVO_FTP.ID_ARCHIVO%TYPE,
                               vNomArchivo_in VARCHAR2,
                               cCodLocal_in in char)
AS
  v_CONS_DIR_INTERFACES VARCHAR2(50) := 'DIR_INTERFACES';  --ORIGEN
  v_REG_TRAMA         PTOVENTA.AUX_ARCHIVO_FTP%ROWTYPE;
  v_DESTINO           VARCHAR2(200);
BEGIN

  --COMIENZO  A CREAR LA ESTRUCTURA DE LAS TABLAS
     SELECT *
     INTO v_REG_TRAMA
     FROM PTOVENTA.AUX_ARCHIVO_FTP
     WHERE ID_ARCHIVO = nIdArchivo_in;
  --COMIENZO A CREAR EL DETALLE CON LOS DATOS DE LA TABLAS

  --//////////////////////////////////////////////////////////////////////////////////////////////////
  declare
    v_CONN UTL_TCP.CONNECTION;
  BEGIN
    BEGIN
      v_CONN := PTOVENTA.PKG_FTP.LOGIN(v_REG_TRAMA.SERVIDOR,
                                       v_REG_TRAMA.PUERTO,
                                       v_REG_TRAMA.USUARIO,
                                       v_REG_TRAMA.PASSWORD);
    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20000,
                                ' Conectandose por FTP al servidor' ||
                                CHR(13) || SQLERRM);
       PTOVENTA.PKG_FTP.LOGOUT(v_CONN);
        UTL_TCP.CLOSE_ALL_CONNECTIONS;
    END;

    BEGIN
      v_DESTINO:= v_REG_TRAMA.CARPETA;

      ptoventa.PKG_FTP.PUT(P_CONN      => v_CONN,
                           P_FROM_DIR  => v_CONS_DIR_INTERFACES,
                           P_FROM_FILE => vNomArchivo_in,
                           P_TO_DIR    => v_DESTINO,
                           P_TO_FILE   => vNomArchivo_in);

      UTL_FILE.fremove(v_CONS_DIR_INTERFACES,vNomArchivo_in);

    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20000,
                                'Enviando de la carpeta ' || SQLERRM);
        PTOVENTA.PKG_FTP.LOGOUT(v_CONN);
        UTL_TCP.CLOSE_ALL_CONNECTIONS;
    END;

    PTOVENTA.PKG_FTP.LOGOUT(v_CONN);

    UTL_TCP.CLOSE_ALL_CONNECTIONS;
  END;
END;
--********************************************************************************************
END;
/
