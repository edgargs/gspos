#!/bin/bash
cd /opt/matrix/dcs_cellocator 
java -Dconfig.program=configC.properties -Dlevel.program=INFO -jar matrix-dcs-1.2.2-SNAPSHOT.jar 30400 >/dev/null 2>&1 &
