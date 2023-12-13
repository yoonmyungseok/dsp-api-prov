#!/bin/sh

cd /home/dsp_if

echo "PID Check..."

CURRENT_PID=$(ps -ef | grep java | grep if | awk '{print $2}')

echo "Running PID: {$CURRENT_PID}"

if "$CURRENT_PID" [ -z CURRENT_PID ] ; then
    echo "Project is not running"
else
    kill -9 $CURRENT_PID
    echo "kill: {$CURRENT_PID}"
    sleep 10
fi

echo "Deploy Project...."

nohup java -jar agent-1.0.0.jar --spring.profiles.active=if &

sleep 10

if [ $? -eq 0 ]; then
    echo "Execution successful."
else
    echo "Execution failed."
    exit 1
fi
