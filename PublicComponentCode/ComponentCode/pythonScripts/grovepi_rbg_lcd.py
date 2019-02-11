#In this project, the Temperature and humidity from the sensor is printed on the grovepi_lc_dht
#
import grovepi
import math
from grove_rgb_lcd import *

sensor_port = 7         # connect the DHT sensor to port 7
#connect LCD into any of I2C port


# Grove Base Kit comes with the blue sensor.
blue = 0    # The Blue colored sensor.
white = 1   # The White colored sensor.


while True:
    try:
        setText("Hello, Team 2")
        setRGB(0,128,64)
        
        # Slowly change the brightness every 1 seconds.
        for c in range(0,255):
            setRGB(c,255-(c*50),0)
            time.sleep(2)
            setText("Temp     :"+t+"F Humidity :"+h+"%")
                
                except (IOError, TypeError) as e:
                    print ("Error")
