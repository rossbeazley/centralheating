#!/bin/bash



while [[ 1 ]]
do
BOOST=`find . -mmin -3 -name onehour -print`
if [ -z "$BOOST" ]; then
 echo READ TIMER
else
 echo BOOST ONE HOUR
fi
sleep 10s
done
