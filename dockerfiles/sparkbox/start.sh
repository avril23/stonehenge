#!/usr/bin/env bash

export SPARK_MASTER_HOST=0.0.0.0

echo Starting SSH 
/usr/sbin/sshd -D & 

echo Starting SPARK 
$SPARK_HOME/sbin/start-all.sh

echo Starting Dandelion
java -Dconfig.file=/mnt/datacanvas/sandbox/application.conf -cp /opt/jars/zetdata_dandelion-0.1.jar com.zetdata.zetinsight.dandelion.Dandelion

# block the script
#tail -f /dev/null