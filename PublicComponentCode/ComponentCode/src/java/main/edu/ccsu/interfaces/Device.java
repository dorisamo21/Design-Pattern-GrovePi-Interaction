package edu.ccsu.interfaces;

import edu.ccsu.error.IncompatibleDeviceError;
import edu.ccsu.error.PortInUseException;

/**
 * Interface that specifies operations on Devices
 */
public interface Device {
	
	/**
	 * Sets another devices to be used in Chain of Responsiblity.
	 * If accessing current device fails and there is another device in the chain,
	 * device will try to use that next device to perform operations.
	 * @param device
	 * @throws IncompatibleDeviceError
	 * @return 
	 */
	public void setNextDevice(Device nextDevice) throws IncompatibleDeviceError;

	/**
	 * Returns next device in CoR
	 * @return
	 */
	public Device getNextDevice();
	
	/**
	 * Can be used to turn on light capable devices
	 */
	public void turnOn();
	
	/**
	 * Can be used to turn off light capable devices
	 */
	public void turnOff();
	
	/**
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * Set the name
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * Return this devices port number
	 * @return
	 */
	public String getPortNumber();
	
	/**
	 * Set this devices port number
	 * @param portNumber
	 * @throws PortInUseException 
	 */
	public void setPortNumber(String portNumber) throws PortInUseException;
	
	/**
	 * Checks to see if device is allowed to use CoR
	 * @return
	 */
	public boolean isUseNext();
	
	/**
	 * Set use next to true if you want to use an object's CoR.
	 * Set to false if you don't want to use this object's CoR
	 * @param useNext
	 */
	public void setUseNext(boolean useNext);
	
}