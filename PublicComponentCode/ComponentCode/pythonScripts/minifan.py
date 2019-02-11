#!/usr/bin/env python
#
# GrovePi Example for using the Grove Light Sensor and the LED together to turn the LED On and OFF if the background light is greater than a threshold.
# Modules:
# 	http://www.seeedstudio.com/wiki/Grove_-_Light_Sensor
# 	http://www.seeedstudio.com/wiki/Grove_-_LED_Socket_Kit
#
# The GrovePi connects the Raspberry Pi and Grove sensors.  You can learn more about GrovePi here:  http://www.dexterindustries.com/GrovePi
#
# Have a question about this example?  Ask on the forums here:  http://forum.dexterindustries.com/c/grovepi
#
'''
## License

The MIT License (MIT)

GrovePi for the Raspberry Pi: an open source platform for connecting Grove Sensors to the Raspberry Pi.
Copyright (C) 2017  Dexter Industries

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
'''

import time
import sys
import grovepi
    
#can only use grovepi.analogWrite(led, val) on ports 3,5,6,9
def off(portNumber):
    minifan = portNumber

    grovepi.pinMode(minifan, "OUTPUT")
    
    try:
        grovepi.analogWrite(minifan, 0)

    except (IOError, TypeError):
        print ("Error")
        
def on(portNumber):
    minifan = portNumber
    
    grovepi.pinMode(minifan, "OUTPUT")
    
    try:
        grovepi.analogWrite(minifan, 255)

    except (IOError, TypeError):
        print ("Error")

def adjustSpeed(portNumber, speed):
    minifan = portNumber
    
    grovepi.pinMode(minifan, "OUTPUT")
    
    try:
        grovepi.analogWrite(minifan, speed)

    except (IOError, TypeError):
        print ("Error")
        
portNumber = int(sys.argv[1])

if(portNumber in [3, 5, 6, 9]):
    if sys.argv[2] == "ON":
        on(portNumber)
    if sys.argv[2] == "OFF":
        off(portNumber)
    if len(sys.argv) == 4 and sys.argv[2] == "adjustSpeed":
        speed = int(sys.argv[3])
        if(speed < 0):
            speed = 0
        if(speed > 255):
            speed = 255
        adjustSpeed(portNumber, speed)
else:
    print("Error: Please connect to D3, D4, D5, or D6")
