#!/bin/sh

cd /home/dsp_deploy

echo "PID Check..."

CURRENT_PID=$(ps -ef | grep java | grep deploy | awk '{print $2}')

echo "Running PID: {$CURRENT_PID}"

kill -9 $CURRENT_PID
echo "kill: {$CURRENT_PID}"
sleep 10

echo "Done"
