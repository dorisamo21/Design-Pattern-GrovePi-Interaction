package ccsu.edu.grovepicomponents;

import java.util.Objects;

import edu.ccsu.error.IncompatibleDeviceError;
import edu.ccsu.error.IncompatibleSensorError;
import edu.ccsu.error.PortInUseException;
import edu.ccsu.interfaces.Device;
import edu.ccsu.interfaces.LightEnabledDevice;
import edu.ccsu.utility.CommonConstants;
import edu.ccsu.utility.PortManagement;

/**
 * Class allows for operations on GrovePi LEDs.
 */
public class Led implements LightEnabledDevice {

	private String name;
	private Device nextDevice;
	private String portNumber;
	private boolean useNext;
	private static PortManagement portManagement = PortManagement.getInstance();
	private Thread automatic;
	private volatile boolean running;		//TODO- do we need keyword volatile when only one instance of this class accesses this
	
	/**
	 * Default behavior is to use next device in chain.  If you wish to change this
	 * behavior use setUseNext() method to set to false.  CoR used for adjustBrightness
	 * given that only certain ports are capable of this
	 * @param name
	 * @param portNumber
	 */
	public Led(String name, String portNumber) {
		this.name = name;
		this.portNumber = portNumber;
		this.useNext = true;
	}
    
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getPortNumber() {
		return portNumber;
	}
	
	@Override
	public void setPortNumber(String portNumber) throws PortInUseException {
		interruptThread();
		if(portManagement.add(portNumber) != false) {
			portManagement.remove(this.portNumber);
			this.portNumber = portNumber;
		}
		else
			throw new PortInUseException(portNumber + " already in use");
	}
	
	@Override
	public void automate(Sensor sensor) throws IncompatibleSensorError {
		class AutomateTask implements Runnable {
	        Sensor sensor;
	        String portNumber;	//led's portNumber
	        AutomateTask(Sensor s, String p) { sensor = s; portNumber = p; }
	        public void run() {
	        	running = true;
	        	while(running) {
	        		GrovePiUtilities.callPython(CommonConstants.AUTOMATIC_LED, sensor.getPortNumber().substring(1) + CommonConstants.BLANK + portNumber.substring(1));	   
	        		try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        	}
	        }
	    }
		
		AutomateTask task = new AutomateTask(sensor, this.getPortNumber());
		if(sensor != null) {
			if(!(sensor instanceof LightSensor)) {
				throw new IncompatibleSensorError("Sensor " + sensor.getName() + " is not compatible with LEDs!");
			}
			else if(GrovePiUtilities.checkOperatingSystem()) {
				automatic = new Thread(task);
				automatic.start();
			}
			else {
				System.out.println("Cannot use automate mode for " + this.name);
			}
		}
	}
	
	/**
	 * Stops automatic thread execution
	 */
	private void interruptThread() {
		running = false;
		automatic = null;
	}
	
	@Override
	public void turnOn() {
		interruptThread();
		//port must be a digital port starting with D
		if(!this.getPortNumber().contains("D")) {
			System.out.println("Must use a digital port starting with D");
		}
		else if(GrovePiUtilities.checkOperatingSystem()) {
			GrovePiUtilities.callPython(CommonConstants.TOGGLE_LED, GrovePiUtilities.buildArgsString(this.getPortNumber(), CommonConstants.ON));			
		}
		else {
			System.out.println("Cannot turn on LED: " + this.name);
		}
	}
	
	@Override
	public void turnOff() {
		interruptThread();
		if(!this.getPortNumber().contains("D")) {
			System.out.println("Must use a digital port starting with D");
		}
		else if(GrovePiUtilities.checkOperatingSystem()) {
			GrovePiUtilities.callPython(CommonConstants.TOGGLE_LED, GrovePiUtilities.buildArgsString(this.getPortNumber(),CommonConstants.OFF));
		}
		else {
			System.out.println("Cannot turn off LED: " + this.name);
		}
	}
	
	@Override
	public void blink(int numberOfSeconds) {
		interruptThread();
		if(!this.getPortNumber().contains("D")) {
			System.out.println("Must use a digital port starting with D");
		}
		else if(GrovePiUtilities.checkOperatingSystem()) {
			GrovePiUtilities.callPython(CommonConstants.GROVE_LED_BLINK, GrovePiUtilities.buildArgsString(this.getPortNumber(), Integer.toString(numberOfSeconds)));
		}
		else {
			System.out.println("Cannot blink LED: " + this.name);
		}
	}
	
	@Override
	public Device getNextDevice() {
		if(this.nextDevice!= null)
			return this.nextDevice;
		return null;
	}

	@Override
	public boolean isUseNext() {
		return this.useNext;
	}

	@Override
	public void setUseNext(boolean useNext) {
		this.useNext = useNext;
	}
	
	@Override
	public void setNextDevice(Device nextDevice) throws IncompatibleDeviceError {
		if(nextDevice instanceof LightEnabledDevice) {
			this.nextDevice = nextDevice;
		}
		else {
			throw new IncompatibleDeviceError("LED not compatible with device.  LED chain can only be used by other LEDs");
		}
	}
    
	@Override
	public void adjustBrightness(int brightness) {
		interruptThread();
		if(GrovePiUtilities.checkOperatingSystem()) {
			String output = GrovePiUtilities.callPython(CommonConstants.ADJUST_BRIGHTNESS, GrovePiUtilities.buildArgsString(this.getPortNumber(), Integer.toString(brightness)));
			if(CommonConstants.TRY_NEXT_LED.equalsIgnoreCase(output)) {
				Led nextLED = (Led) this.getNextDevice();
				//use next LED in chain if useNext is true
				if(this.isUseNext() == true && nextLED!=null ) {
					System.out.println("Trying led: \n" + nextLED);
					nextLED.adjustBrightness(brightness);
				}
				else {
					System.out.println("No leds in chain are connected to port with PWM (pulse wave modulation)");
				}
			}
		}
		else {
			System.out.println("Cannot adjust brightness for LED: " + this.getName() + ". Must run on raspberry pi");
		}
		
	}
	
	@Override
	public String toString() {
		return "***********************************\n" +
				"Name: " + this.name + "\n" +
				"Port Number: " + this.portNumber + "\n" +
				(this.getNextDevice()!= null ? "Next Device: " + this.getNextDevice().getName() : "Next Device: None")+
				"\n***********************************\n";
	}
	
	
	@Override
	public boolean equals(Object o) {
		//check if references are equal
		if(this == o) {
			return true;
		}
		
		//check if null
		if(null == o) {
			return false;
		}
	
		//check if they are the same class
		if(getClass() != o.getClass()) {
			return false;
		}
		
		Led device = (Led) o;
		
		//significant field field comparison
		if(this.name.equals(device.getName())
				&& this.portNumber.equals(device.getPortNumber())) {
			//check if chain is equal after field comparison
			 if(chainComparison(device)) {
				 return true;
			 }
		}
		return false;
	}
	
	/**
	 * Logic to evaluate if the Leds share the 
	 * same CoR
	 * @param led
	 * @return
	 */
	private boolean chainComparison(Led led) {
		//check chain
		Led nextDevice1 = (Led) this.getNextDevice();
		Led nextDevice2 = (Led) led.getNextDevice();
		
		if(nextDevice1 != null && nextDevice2 != null) {
			if(nextDevice1.equals(nextDevice2)) {
				return true;
			}
		}
		/*
		 * after getting to end of chain and the devices are all equal,
		 * if next in chain is null for both devices then they are equal chains
		 * */
		if(nextDevice1 == null  && nextDevice2 == null) {
			return true;
		}
		return false;
	}
	
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + Objects.hashCode(this.getNextDevice());
		hash = 23 * hash + Objects.hashCode(this.name);
		hash = 23 * hash + Objects.hashCode(this.portNumber);
		return hash;
	}
}