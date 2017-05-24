copy src\main\resources\configC.properties build\libs
cd build\libs
set logProgram=logCellocator
set configProgram=configC.properties
java -Dconfig.program=%configProgram% -Dlog.program=%logProgram% -Dlevel.program=INFO -jar matrix-dcs-1.1.0-SNAPSHOT.jar 30400
