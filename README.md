# CS505 - GrovePi Interaction Simplified

Authors: Kim Niehaus, Ga Young Lee, Adrian Ward-Manthey

## What it Does

The interface provided will allow users to interact with the sensors without having to use GrovePi API, allowing the user to work strictly with Java code.

For example, methods would allow users to specify what data they want from the light sensor, such as light intensity, or specify certain messages for the LCD RGB Backlight to display to their users.

## Classes and Interfaces


### ProductFactory Interface
Provides methods for Device, LightEnabledDevice, ScreenEnabledDevice, and Fan device creation.

### DeviceAndSensorFactory Class
Realizes the ProductFactory Interface and provides implementation for the inherited methods.

### Device Interface
Provides methods for setNextDevice and getNextDevice for generic devices

### Fan Interface
Extends off of device interface and provides method for adjusting speed

### GrovePiFan Class
Realizes fan interface and provides implementation to create a Grove Pi fan and provide method implementation for turning the fan on, off, and adjusting the speed

### LightEnabledDevice Interface
Extends off of device interface and provides methods for adjusting brightness and blinking

### LED Class
Realizes LightEnabledDevice interface and provides implementation to create an LED and provides method implementation for turning the LED on, off, blinking, and adjusting brightness

### ScreenEnabledDevice Interface
Extends off of device interface and provides methods for printing a message, getting and setting color of the screen

### LCD Class
Realizes ScreenEnabledDevice interface and provides implementation to create an LCD screen and provides method implementation to print a message, adjust brightness, blink, turning on and off

### Sensor Abstract Class
Abstract class that provides methods to get data from the sensors, to check the port number of the sensor, and other operations of sensors

### LightSensor Class
Extends sensor abstract class and creates a light sensor type sensor. Provides implementation for checking port. Also creates an inner class that will store the data in a list object

### TempAndHumiditySensor
Extends sensor abstract class and creates a temperature and humidity sensor type sensor. Provides implementation for checking port. Also creates an inner class that will store the data in a list object.

### Iterator Interface
Provides methods to enable classes that realize the sensor interface to iterate through the data they are holding

### PortManagement
Interanlly maintains the ports that are already in use

# Notes
Please see demo provided for indepth code and examples
