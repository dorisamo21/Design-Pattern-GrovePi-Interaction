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
import sys
import time
import grovepi

def LightSensor(portNumber, duration):
    # Connect the Grove Light Sensor to analog port A0
    # SIG,NC,VCC,GND
    if portNumber not in [0, 1, 2]:
       print("Error")
    else:
       
        light_sensor = portNumber

        grovepi.pinMode(light_sensor,"INPUT")
       
        if(duration <= 0):
            duration = 1
        if(duration > 2):
            duration = duration -1
        t_end = time.time() + duration
        
        while time.time() < t_end:
            try:
                # Get sensor value
                sensor_value = grovepi.analogRead(light_sensor)
                # Calculate resistance of sensor in K
                resistance = (float)(1023 - sensor_value) * 10 / sensor_value
                brightness = 1023-sensor_value

                #Calculate wattage
                voltage = brightness * (5.0/1023.0)
                watts   = voltage * 1.1                 #1 being 1 amp
                #gradually adjust brightness
                print("%d %.2f %f," %(sensor_value,  voltage, watts))
                time.sleep(.5)
                
            except (IOError, KeyboardInterrupt):
                print ("Error")
                
LightSensor(int(sys.argv[1]), int(sys.argv[2]))


