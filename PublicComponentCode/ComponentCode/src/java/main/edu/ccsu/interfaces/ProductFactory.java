package edu.ccsu.interfaces;

import ccsu.edu.grovepicomponents.Sensor;
import edu.ccsu.error.PortInUseException;

/**
 * Interface that specifies methods that can be used to create 
 * different devices and sensors
 * NOTE: Methods will return null if you try to use port that is already in use
 * @author Adrian
 * @author Kim
 * @author GaYoung
 */
public interface ProductFactory {

	/**
	 * Creates a device based on the name and returns it
	 * @param device
	 * @param name
	 * @param portNumber
	 * @return
	 * @throws PortInUseException
	 */
	public Device makeDevice(String device, String name, String portNumber) throws PortInUseException;
	
	/**
	 * Use method to create Fan device (currently supports the Grove MiniFan v1.1)
	 * @param device
	 * @param name
	 * @param portNumber
	 * @return
	 * @throws PortInUseException
	 */
	public Fan makeFan(String device, String name, String portNumber) throws PortInUseException;
	
	/**
	 * Generates a light enabled device (Currently LEDs are supported)
	 * @param device
	 * @param name
	 * @param portNumber
	 * @return
	 * @throws PortInUseException
	 */
	public LightEnabledDevice makeLightEnabledDevice(String device, String name, String portNumber) throws PortInUseException;
	
	/**
	 * Used to create a screen enabled device (currently supports the LCD display)
	 * @param device
	 * @param name
	 * @param portNumber
>>>>>>> master
	 * @return
	 * @throws PortInUseException
	 */
	public ScreenEnabledDevice makeScreenEnabledDevice(String device, String name, String portNumber) throws PortInUseException;
	
	/**
	 * Creates a sensor based on the name and returns it
	 * @param sensor
	 * @param name
	 * @param portNumber
	 * @return
	 * @throws PortInUseException
	 */
	public Sensor makeSensor(String sensor, String name, String portNumber) throws PortInUseException;
}