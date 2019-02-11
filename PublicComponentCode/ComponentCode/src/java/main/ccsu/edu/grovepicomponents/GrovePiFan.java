package ccsu.edu.grovepicomponents;

import edu.ccsu.error.IncompatibleDeviceError;
import edu.ccsu.error.PortInUseException;
import edu.ccsu.interfaces.Device;
import edu.ccsu.interfaces.Fan;
import edu.ccsu.utility.CommonConstants;
import edu.ccsu.utility.PortManagement;
/**
 * Class that has implementations to interact with Grove Minifan v1.1
 * @author Adrian
 *
 */
public class GrovePiFan implements Fan {

	private String portNumber;
	private String name;
	private boolean useNext;
	private Device nextDevice;
	private static PortManagement portManagement = PortManagement.getInstance();
	
	/**
	 * Constructs the grovepi fan
	 * @param name
	 * @param portNumber
	 */
	public GrovePiFan(String name, String portNumber) {
		this.portNumber = portNumber;
		this.name = name;
		this.useNext = true;
	}
	
	@Override
	public void setNextDevice(Device nextDevice) throws IncompatibleDeviceError {
		if(nextDevice instanceof GrovePiFan)
			this.nextDevice = nextDevice;
		else 
			throw new IncompatibleDeviceError(nextDevice.getName() + " is not compatible with the GrovePiFan");
	}
   
	@Override
	public Device getNextDevice() {
		return this.nextDevice;
	}
    
	@Override
	public void turnOn() {
		if(!this.getPortNumber().contains("D")) {
			System.out.println("Must use a digital port starting with D");
		}
		else if(GrovePiUtilities.checkOperatingSystem()) {
			GrovePiUtilities.callPython(CommonConstants.MINIFAN, this.portNumber.substring(1) + CommonConstants.BLANK + CommonConstants.ON);			
		}
		else {
			System.out.println("Cannot turn on Fan: " + this.name);
		}
	}
   
	@Override
	public void turnOff() {
		if(!this.getPortNumber().contains("D")) {
			System.out.println("Must use a digital port starting with D");
		}
		else if(GrovePiUtilities.checkOperatingSystem()) {
			GrovePiUtilities.callPython(CommonConstants.MINIFAN, this.portNumber.substring(1) + CommonConstants.BLANK + CommonConstants.OFF);			
		}
		else {
			System.out.println("Cannot turn off Fan: " + this.name);
		}
	}
	
	@Override
	public void adjustSpeed(int speed) {
		if(!this.getPortNumber().contains("D")) {
			System.out.println("Must use a digital port starting with D");
		}
		else if(GrovePiUtilities.checkOperatingSystem()) {
			GrovePiUtilities.callPython(CommonConstants.MINIFAN, this.portNumber.substring(1) + CommonConstants.BLANK + CommonConstants.ADJUST_FAN_SPEED
										+ CommonConstants.BLANK + Integer.toString(speed));			
		}
		else {
			System.out.println("Cannot adjust speed of Fan: " + this.name);
		}
	}
	
	@Override
	public String getName() {
		return this.name;
	}
   
	@Override
	public void setName(String name) {
		this.name = name;
	}
   
	@Override
	public String getPortNumber() {
		return this.portNumber;
	}
   
	@Override
	public void setPortNumber(String portNumber) throws PortInUseException {
		if(portManagement.add(portNumber) != false) {
			portManagement.remove(this.portNumber);
			this.portNumber = portNumber;
		}
		else
			throw new PortInUseException(portNumber + " already in use");
	}
    
	@Override
	public boolean isUseNext() {
		return this.useNext;
	}
   
	@Override
	public void setUseNext(boolean useNext) {
		this.useNext = useNext;
	}
}