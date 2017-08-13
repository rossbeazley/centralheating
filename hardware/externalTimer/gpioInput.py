#!/usr/bin/env python2.7

#KY040 Python Class
#Martin O'Hanlon
#http://www.stuffaboutcode.com/2015/05/raspberry-pi-and-ky040-rotary-encoder.html

import RPi.GPIO as GPIO
import sys
from time import sleep

class GpioInteruptInput:

    CLOCKWISE = 0
    ANTICLOCKWISE = 1

    def __init__(self, switchPin, switchOn, switchOff):
        self.switchPin = switchPin
        self.switchOn = switchOn
        self.switchOff = switchOff
        GPIO.setup(switchPin, GPIO.IN, pull_up_down=GPIO.PUD_UP)

    def start(self):
        GPIO.add_event_detect(self.switchPin, GPIO.FALLING, callback=self._switchCallback, bouncetime=300)
        self.announceState()

    def stop(self):
        GPIO.remove_event_detect(self.switchPin)

    def _switchCallback(self, pin):
        self.announceState()

    def announceState(self):
        if GPIO.input(self.switchPin) == 0:
            self.switchOn()
        else:
            self.switchOff()


if __name__ == "__main__":

    SWITCHPIN = 23

    def switchOn():
        print "n"
        sys.stdout.flush()

    def switchOff():
        print "f"
        sys.stdout.flush()

    GPIO.setmode(GPIO.BCM)

    gpioInteruptInput = GpioInteruptInput(SWITCHPIN, switchOn, switchOff)

    gpioInteruptInput.start()

    try:
        while True:
            sleep(0.1)
    finally:
        gpioInteruptInput.stop()
        GPIO.cleanup()

