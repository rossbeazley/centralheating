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

echo setup gpio 4 relay
sudo heating/gpio mode 4 out
sudo heating/gpio state 4

echo main $!
bin/main /tmp/pipe /sys/class/gpio/gpio4/value

killKY040

