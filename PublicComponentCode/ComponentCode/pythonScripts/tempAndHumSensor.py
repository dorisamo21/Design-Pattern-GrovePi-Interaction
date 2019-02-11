import grovepi
import sys
import math
import time

# temp_humidity_sensor_type

# Grove Base Kit comes with the blue sensor.

blue = 0    # The Blue colored sensor.

white = 1   # The White colored sensor.


def tempAndHumData(portNumber, duration):
    
    sensor = portNumber
    
    if(duration <= 0):
        duration = 1
    if(duration > 2):
        duration = duration -1
    t_end = time.time() + duration
    
    while time.time() < t_end:

        try:

            # This example uses the blue colored sensor.

            # The first parameter is the port, the second parameter is the type of sensor.

            [celcius,humidity] = grovepi.dht(sensor,blue)

            if math.isnan(celcius) == False and math.isnan(humidity) == False:
                fahrenheit = (1.8*celcius) + 32
                print("%.02f %.02f %.02f,"%(fahrenheit, celcius, humidity))
            time.sleep(.5)


        except IOError:

            print ("Error")
            
tempAndHumData(int(sys.argv[1]), int(sys.argv[2]))