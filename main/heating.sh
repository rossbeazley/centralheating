#!/bin/bash


echo making pipe
mkfifo /tmp/pipe

echo ky040
./KY040.py > /tmp/pipe &

KY_PID=$!

trap killKY040 INT

function killKY040() {
kill -9 $KY_PID
}

echo main $!
bin/main /tmp/pipe

killKY040

