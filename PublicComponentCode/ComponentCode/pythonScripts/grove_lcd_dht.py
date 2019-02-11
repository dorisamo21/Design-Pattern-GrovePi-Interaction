# grovepi_lc_dht.py
#
#This is an project for using the Grove LCD Display
#and the Grove Digital Humidity and Temperature Sensor from the GrovePi starter _LED_Socket_Kit
#
#In this project, the Temperature and humidity from the sensor is printed on the grovepi_lc_dht
#
import grovepi
import math
from grove_rgb_lcd import *

                        #connect LCD into any of I2C port


# Grove Base Kit comes with the blue sensor.
blue = 0    # The Blue colored sensor.
white = 1   # The White colored sensor.

def automaticLcdTemp(sensorPort):
    try:
        [temp,humidity] = grovepi.dht(sensor_port,white)
        if math.isnan(temp) == False and math.isnan(humidity) == False:
            print("temp = %.02f C humidity =%.02f%%"%(temp, humidity))

        #Converting Celcius to Fahrenheit
        temp = (temp * (9/5)) +32

        #Getting datas to write message on LCD screen
        t=str(temp)
        h=str(humidity)
        setText("Temp     :"+t+"F Humidity :"+h+"%")

    except (IOError, TypeError) as e:
        print ("Error")
        
automaticLcdTemp(int(sys.argv[1]))