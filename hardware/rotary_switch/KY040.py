#!/usr/bin/env python2.7

#KY040 Python Class
#Martin O'Hanlon
#http://www.stuffaboutcode.com/2015/05/raspberry-pi-and-ky040-rotary-encoder.html

import RPi.GPIO as GPIO
import sys
from time import sleep

class KY040:

    CLOCKWISE = 0
    ANTICLOCKWISE = 1
    
    def __init__(self, clockPin, dataPin, switchPin, rotaryCallback, switchCallback):
        #persist values
        self.clockPin = clockPin
        self.dataPin = dataPin
        self.switchPin = switchPin
        self.rotaryCallback = rotaryCallback
        self.switchCallback = switchCallback

        #setup pins
        GPIO.setup(clockPin, GPIO.IN)
        GPIO.setup(dataPin, GPIO.IN)
        GPIO.setup(switchPin, GPIO.IN, pull_up_down=GPIO.PUD_UP)

    def start(self):
        GPIO.add_event_detect(self.clockPin,  GPIO.FALLING, callback=self._clockCallback, bouncetime=50)
        GPIO.add_event_detect(self.switchPin, GPIO.FALLING, callback=self._switchCallback, bouncetime=300)

    def stop(self):
        GPIO.remove_event_detect(self.clockPin)
        GPIO.remove_event_detect(self.switchPin)
    
    def _clockCallback(self, pin):
        if GPIO.input(self.clockPin) == 0:
            data = GPIO.input(self.dataPin)
            if data == 1:
                self.rotaryCallback(self.ANTICLOCKWISE)
            else:
                self.rotaryCallback(self.CLOCKWISE)

    def _switchCallback(self, pin):
        if GPIO.input(self.switchPin) == 0:
            self.switchCallback()

#test
if __name__ == "__main__":
    
    CLOCKPIN = 28
    DATAPIN = 29
    SWITCHPIN = 31

    def rotaryChange(direction):
        if direction == 0:
            print "c"
        else:
            print "a"
        sys.stdout.flush()

    def switchPressed():
        print "b"
        sys.stdout.flush()

    GPIO.setmode(GPIO.BCM)
    
    ky040 = KY040(CLOCKPIN, DATAPIN, SWITCHPIN, rotaryChange, switchPressed)

    ky040.start()

    try:
        while True:
            sleep(0.1)
    finally:
        ky040.stop()
        GPIO.cleanup()

