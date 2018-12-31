#!/usr/bin/env bash

echo Starting SSH 
/usr/sbin/sshd -D & 

echo Starting SPARK 
$SPARK_HOME/sbin/start-all.sh

# block the script
tail -f /dev/null