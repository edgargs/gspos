TITLE SrvMonitorFacturacionE v1.2.0

SET RUTA=D:\ERIOS\Desarrollo\others\SrvMonitorFacturacionE
SET JAVA_DIR=D:\ERIOS\Programas\jdk1.7.0_65\jre\bin
SET LOG=%RUTA%\logback\
SET CONF=%RUTA%\src
SET BIN=%RUTA%\deploy

%JAVA_DIR%\java -DUSER_HOME=%LOG% -jar %BIN%\srvmonitorfacturacione-v1.2.0.jar %CONF%\farmadaemon.properties %CONF%\servermail.properties
rem pause
