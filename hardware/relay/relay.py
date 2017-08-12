#!/usr/bin/env python2.7

import RPi.GPIO as GPIO
import sys

if __name__ == "__main__":
    
    PIN = 23

    GPIO.setmode(GPIO.BCM)
    GPIO.setup(PIN, GPIO.OUT)
    if sys.argv[1]=="on":
        GPIO.write(PIN, GPIO.HIGH)
    else:
        GPIO.write(PIN, GPIO.LOW)

    GPIO.cleanup()

