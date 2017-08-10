#!/bin/bash


echo making pipe
mkfifo /tmp/pipe

echo ky040
./KY040.py > /tmp/pipe &

KY_PID=$!

trap ctrl_c INT

function ctrl_c() {
kill $KY_PID
}

echo main $!
bin/main /tmp/pipe
