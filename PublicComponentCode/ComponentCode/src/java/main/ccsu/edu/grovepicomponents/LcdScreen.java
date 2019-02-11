package ccsu.edu.grovepicomponents;

import java.util.Objects;
import edu.ccsu.error.IncompatibleDeviceError;
import edu.ccsu.error.IncompatibleSensorError;
import edu.ccsu.error.PortInUseException;
import edu.ccsu.interfaces.Device;
import edu.ccsu.interfaces.ScreenEnabledDevice;
import edu.ccsu.utility.CommonConstants;
import edu.ccsu.utility.PortManagement;

/**
 * Class connects to LCD RGB Backlight and allows
 * calling classes to print messages to the screen using
 * a variety of methods.
 */
public class LcdScreen implements ScreenEnabledDevice {
	
	private String name;
	private Device nextDevice;
	private String portNumber;
	private String color;
	private boolean useNext;
	private volatile boolean running;		//TODO- do we need keyword volatile when only one instance of this class accesses this
	private Thread automatic;
	private static PortManagement portManagement = PortManagement.getInstance();
	
	public LcdScreen(String name, String portNumber) {
		this.color = "Blue";
		this.name = name;
		this.portNumber = portNumber;
		this.useNext = false;
	}
	
	@Override
	public void automate(Sensor sensor) throws IncompatibleSensorError {
		//note portNumber not needed to use LCD
		class AutomateTask implements Runnable {
	        Sensor sensor;
	        AutomateTask(Sensor s, String p) { sensor = s; portNumber = p; }
	        public void run() {
	        	running = true;
	        	while(running) {
	        		GrovePiUtilities.callPython(CommonConstants.AUTOMATIC_LCD, sensor.getPortNumber().substring(1));	   
	        		try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        	}
	        }
	    }
		
		AutomateTask task = new AutomateTask(sensor, this.getPortNumber());
		if(sensor != null) {
			if(!(sensor instanceof TemperatureAndHumiditySensor)) {
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
	
	/**
	 * Print a message to the 
	 * @param message
	 */
	public void printMessage(String message) {
		interruptThread();
		//port must be a digital port starting with I
		if(!this.getPortNumber().contains("I")) {
			System.out.println("Must use a digital port starting with I");
		}
		else if(GrovePiUtilities.checkOperatingSystem()) {
			
			GrovePiUtilities.callPython(CommonConstants.GROVE_LCD, buildMessage(message));			
		}
		else {
			System.out.println("Cannot turn on LCD: " + this.name);
		}			
	}
	

	/**
	 * Change space in msg to underscore "_" 
	 * @param msg
	 * @return String	returns msg with space changed to "_"
	 */
	private String buildMessage(String msg) {
		StringBuilder buildMsg = new StringBuilder();
		String[] concatMsg = msg.split("\\s+");
		for(String str: concatMsg) {
			buildMsg.append(str);
			buildMsg.append(CommonConstants.UNDERSCORE);
		}
		return buildMsg.toString();	
	}	


	/**
	 * Print a message to the LCD RGB Backlight for
	 * a certain number of seconds
	 * @param message
	 * @param duration
	 */
	public void printMessage(String message, int duration) {
		interruptThread();
		//port must be a digital port starting with I
		if(!this.getPortNumber().contains("I")) {
			System.out.println("Must use a digital port starting with I");
		}
		else if(GrovePiUtilities.checkOperatingSystem()) {
			
			GrovePiUtilities.callPython(CommonConstants.GROVE_LCD_TIME, buildMessage(message)+ CommonConstants.BLANK +duration);			
		}
		else {
			System.out.println("Cannot turn on LCD: " + this.name);
		}					
	}
   
	/**
	 * Print a message to the LCD RGB Backlight for
	 * a certain number of seconds
	 * @param message
	 * @param duration
	 */
	public void printMessageColor(String message, String color) {
		interruptThread();
		//port must be a digital port starting with I
		if(!this.getPortNumber().contains("I")) {
			System.out.println("Must use a digital port starting with I");
		}
		else if(GrovePiUtilities.checkOperatingSystem()) {
			
			GrovePiUtilities.callPython(CommonConstants.GROVE_LCD_COLOR, buildMessage(message)+ CommonConstants.BLANK +color);			
		}
		else {
			System.out.println("Cannot turn on LCD: " + this.name);
		}					
	}	
	
	@Override
	public void setNextDevice(Device nextDevice) throws IncompatibleDeviceError {
		interruptThread();
		if(nextDevice instanceof LcdScreen) {
			this.nextDevice = nextDevice;
			this.setUseNext(true);
		} else {
			throw new IncompatibleDeviceError("Sensor not compatible with sensor. Sensor chain can only be use other Sensors");
		}		
	}
	
	@Override
	public void adjustBrightness(int brightness) {
		interruptThread();
		//port must be a digital port starting with I
		if(!this.getPortNumber().contains("I")) {
			System.out.println("Must use a digital port starting with I");
		}
		else if(GrovePiUtilities.checkOperatingSystem()) {
			
            GrovePiUtilities.callPython(CommonConstants.GROVE_LCD_BRIGHTNESS,""+brightness);

		}
		else {
			System.out.println("Cannot turn on LCD: " + this.name);
		}									
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
		
		LcdScreen device = (LcdScreen) o;
		
		//significant field field comparison
		if(this.name.equals(device.getName())
				&& this.portNumber.equals(device.getPortNumber())) {
				 return true;
		}
		return false;
	}
    
	@Override
	public void blink(int numberOfSeconds) {
		interruptThread();
		//port must be a digital port starting with I
		if(!this.getPortNumber().contains("I")) {
			System.out.println("Must use a digital port starting with I");
		}
		else if(GrovePiUtilities.checkOperatingSystem()) {
			
            GrovePiUtilities.callPython(CommonConstants.GROVE_LCD_BLINK,""+numberOfSeconds);

		}
		else {
			System.out.println("Cannot turn on LCD: " + this.name);
		}							
	}
	
	@Override
	public String toString() {
		return "Name: " + this.name + "\n" +
				"Port Number: " + this.portNumber;	}
    
	@Override
	public void turnOn() {
		interruptThread();
        if(!this.getPortNumber().contains("I")) {
        	System.out.println(this.getPortNumber());
            System.out.println("Must use a digital port starting with I");
       }
        else if(GrovePiUtilities.checkOperatingSystem()) {
        	GrovePiUtilities.callPython(CommonConstants.GROVE_LCD_ONOFF, CommonConstants.ON);
        }
        else {
            System.out.println("Cannot turn on LcdScreen: " + this.name);
        }
		
	}
    
	@Override
	public void turnOff() {
		interruptThread();
        if(!this.getPortNumber().contains("I")) {
            System.out.println("Must use a digital port starting with I");
        }
        else if(GrovePiUtilities.checkOperatingSystem()) {
            GrovePiUtilities.callPython(CommonConstants.GROVE_LCD_ONOFF, CommonConstants.OFF);
        }
        else {
            System.out.println("Cannot turn on LcdScreen: " + this.name);
        }
		
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
	public String getColor() {
		return this.color;
	}
    
	@Override
	public void setColor(String color) {
		this.color = color;
	}
    
	@Override
	public Device getNextDevice() {
		return nextDevice;
	}
     
	@Override
	public boolean isUseNext() {
		return useNext;
	}
    
	@Override
	public void setUseNext(boolean useNext) {
		this.useNext = useNext;		
	}

	public int hashCode() {
        	int hash = 7;
	        hash = 23 * hash + Objects.hashCode(this.getNextDevice());
	        hash = 23 * hash + Objects.hashCode(this.name);
	        hash = 23 * hash + Objects.hashCode(this.portNumber);
	        return hash;
    }
}