#!/bin/bash


echo making ky040 pipe
mkfifo /tmp/ky040_pipe

echo ky040
./KY040.py > /tmp/ky040_pipe &

KY_PID=$!

echo making external timer pipe
mkfifo /tmp/timer_pipe
./gpioInput.py > /tmp/timer_pipe &

TIMER_PID=$!

trap killKY040 INT

function killKY040() {
kill -9 $KY_PID
kill -9 $TIMER_PID
}

echo setup gpio 18 relay
sudo heating/gpio mode 18 out
sudo heating/gpio state 18

echo main
bin/main /tmp/ky040_pipe /sys/class/gpio/gpio4/value /tmp/timer

killKY040

