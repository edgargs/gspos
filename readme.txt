
Use for generate executable:
gradlew clientBoot

Use for execute:
export levelProgram=INFO
export logProgram=logs
export configProgram=config.properties
java -Dconfig.program=$configProgram -Dlevel.program=$levelProgram -Dlog.program=$logProgram -jar matrix-dcs-XXX.jar 30100 >/dev/null 2>&1 &
