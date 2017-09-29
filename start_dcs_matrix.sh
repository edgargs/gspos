#!/bin/bash
cd /opt/matrix/dcs
java -Dconfig.program=config.properties -Dlevel.program=INFO -jar matrix-dcs-1.2.2-SNAPSHOT.jar 30100 >/dev/null 2>&1 &
