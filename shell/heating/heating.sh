#!/bin/bash

cd `dirname $0`

./gpio mode 18 out
./gpio mode 4 in

while [[ 1 ]]
do
	BOOST=`find . -mmin -60 -name onehour -print`
	if [ -z "$BOOST" ]; then
		logger Central heating heartbeat
		./gpio read 4 | xargs ./gpio write 18 
	else
		logger Central heating in one hour boost period
		./gpio write 18 1
	fi

	sleep 30s
done
