package edu.ccsu.factory;

import ccsu.edu.grovepicomponents.GrovePiFan;
import ccsu.edu.grovepicomponents.LcdScreen;
import ccsu.edu.grovepicomponents.Led;
import ccsu.edu.grovepicomponents.LightSensor;
import ccsu.edu.grovepicomponents.Sensor;
import ccsu.edu.grovepicomponents.TemperatureAndHumiditySensor;
import edu.ccsu.error.PortInUseException;
import edu.ccsu.interfaces.Device;
import edu.ccsu.interfaces.Fan;
import edu.ccsu.interfaces.LightEnabledDevice;
import edu.ccsu.interfaces.ProductFactory;
import edu.ccsu.interfaces.ScreenEnabledDevice;
import edu.ccsu.utility.CommonConstants;
import edu.ccsu.utility.PortManagement;
/**
 * Class used to dynamically create Device and Sensor objects
 * to be used by client code.
 * @author Adrian
 * @author Kim
 * @author Ga Young
 */
public class DeviceAndSensorFactory implements ProductFactory{

	PortManagement portManagement = PortManagement.getInstance();
	
	public DeviceAndSensorFactory() {
		//default constructor
	}
    /**
     * Makes devices, checking LED, LCD, or Minifan is being created
     * @param device
     * @param name
     * @param portNumber
     */
	@Override
	public Device makeDevice(String device, String name, String portNumber) throws PortInUseException{
		if(checkIfPortAvailable(portNumber)) {
			if(CommonConstants.LED.equalsIgnoreCase(device)) {
				 return new Led(name, portNumber);
			}
			else if(CommonConstants.LCD.equalsIgnoreCase(device)) {
				return new LcdScreen(name, portNumber);
			}
			else if(CommonConstants.MINIFAN.equals(device)) {
				return new GrovePiFan(name, portNumber);
			}
		}
		else
			throw new PortInUseException(portNumber + " already in use");
		return null;			
	}
    
	@Override
	public Sensor makeSensor(String sensor, String name, String portNumber) throws PortInUseException{
		if(checkIfPortAvailable(portNumber)) {
			if(CommonConstants.LIGHT_SENSOR.equalsIgnoreCase(sensor)) {
				return new LightSensor(name, portNumber);
			}
			else if(CommonConstants.TEMP_HUMIDITY_SENSOR.equalsIgnoreCase(sensor)) {
				return new TemperatureAndHumiditySensor(name, portNumber);
			}
		}
		else
			throw new PortInUseException(portNumber + " already in use");
		return null;
	}
    
	@Override
	public LightEnabledDevice makeLightEnabledDevice(String device, String name, String portNumber) throws PortInUseException {
		if(checkIfPortAvailable(portNumber)) {
			if(CommonConstants.LED.equalsIgnoreCase(device)) {
				return new Led(name, portNumber);
			}
			else if(CommonConstants.LCD.equalsIgnoreCase(device)) {
				return new LcdScreen(name, portNumber);
			}
		}
		else
			throw new PortInUseException(portNumber + " already in use");
		return null;
	}
    
	@Override
	public ScreenEnabledDevice makeScreenEnabledDevice(String device, String name, String portNumber)  throws PortInUseException {
		if(checkIfPortAvailable(portNumber)) {
			if(CommonConstants.LCD.equalsIgnoreCase(device)) {
				return new LcdScreen(name, portNumber);
			}
		}
		else 
			throw new PortInUseException(portNumber + " already in use");
		return null;
	}
    
	@Override
	public Fan makeFan(String device, String name, String portNumber) throws PortInUseException {
		if(checkIfPortAvailable(portNumber)) {
			if(CommonConstants.GROVEMINIFAN.equalsIgnoreCase(device)) {
				return new GrovePiFan(name, portNumber);
			}
		}
		else 
			throw new PortInUseException(portNumber + " already in use");
		return null;
	}	
	
	/**
	 * Check if port is available...If so add to list
	 * @param portNumber
	 * @return
	 */
	private boolean checkIfPortAvailable(String portNumber) {
		if(portManagement.add(portNumber) == false)
			return false;
		return true;
	}
}