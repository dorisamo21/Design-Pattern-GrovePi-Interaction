package edu.ccsu.sample;

import java.util.List;

import ccsu.edu.grovepicomponents.Sensor;
import edu.ccsu.error.IncompatibleDeviceError;
import edu.ccsu.error.IncompatibleSensorError;
import edu.ccsu.error.PortInUseException;
import edu.ccsu.factory.DeviceAndSensorFactory;
import edu.ccsu.interfaces.Fan;
import edu.ccsu.interfaces.Iterator;
import edu.ccsu.interfaces.LightEnabledDevice;
import edu.ccsu.interfaces.ProductFactory;
import edu.ccsu.interfaces.ScreenEnabledDevice;
import edu.ccsu.utility.PortManagement;

/**
 * This main class is simply here to demonstrate how to use our code.
 * @author Adrian
 * @author Kim
 * @author Ga Young
 *
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		//instantiate factory to create objects 
		ProductFactory productFactory = new DeviceAndSensorFactory();
		Fan fan = null;
		Sensor lightSensor = null;
		Sensor tempAndHumid = null;
		try {
			 fan = productFactory.makeFan("minifan", "myFan", "D6");
			 lightSensor = productFactory.makeSensor("LightSensor", "test", "A0");
			 tempAndHumid = productFactory.makeSensor("TempAndHumiditySensor", "test", "D7");			
		}
		catch(PortInUseException ex) {
			ex.printStackTrace();
			//handle in use port exception here
		}
		
		System.out.println("************************");
		System.out.println("Testing Fan");
		fan.turnOn();
		Thread.sleep(1800l);
		fan.adjustSpeed(40);
		Thread.sleep(1800l);
		fan.turnOff();
		System.out.println("************************");
		
		/*
		 * Example of how to use iterator for sensors
		 * */
		
		System.out.println("************************");
		System.out.println("Testing LightSensor Iterator");
		//NOTE: this call will print the current data and also store data internally in light sensor class...data is cached and can be retrieved again 
		System.out.println(lightSensor.getData(2));
		
		Iterator itr = lightSensor.getIterator();
		while(itr.hasNext())
			System.out.println(itr.next());
		System.out.println("************************");
		System.out.println("Testing Temp and Humidity Iterator");
		
		//NOTE: this call will print the current data and also store data internally in temp sensor class...data is cached and can be retrieved again 
		System.out.println(tempAndHumid.getData(3));

		Iterator itrTemp = tempAndHumid.getIterator();
		while(itrTemp.hasNext()) {
			System.out.println("TempAndHumidity Iterator: " + itrTemp.next());
		}
		
		//sample devices
		/*
		 * NOTE that we have a hierarchy of interfaces extending Device interface.  If you just 
		 * need the basic functionality of Device interface use productFactory.makeDevice(...)
		 * If you need specific methods associated with different devices, use the specified methods in DeviceAndSensorFactory
		 *  */
		ScreenEnabledDevice display = null;
		LightEnabledDevice ledOne = null;
		LightEnabledDevice ledTwo = null;
		LightEnabledDevice ledThree = null;
		try {
			 display = productFactory.makeScreenEnabledDevice("LCD", "MYLCD", "I2C-1");
			 ledOne =  productFactory.makeLightEnabledDevice("LED", "LED", "D3");
			 ledTwo =  productFactory.makeLightEnabledDevice("LED", "LED2", "D4");
			 ledThree = productFactory.makeLightEnabledDevice("LED", "LED3", "D5");			
		}
		catch(PortInUseException ex) {
			ex.printStackTrace();
			//port in use exception here
		}
		
		System.out.println("Turning on Lcd Screen");
		display.turnOn();
		Thread.sleep(1800l);
		System.out.println("Turning off Lcd Screen");
		display.turnOff();
		
		display.printMessage("Hello World");
		System.out.println("Setting color to Cyan");
		display.printMessageColor("Hello, Team","Cyan");
		Thread.sleep(1800l);
		System.out.println("Setting color to Blue");
		display.printMessageColor("Hello, Team","Blue");
		Thread.sleep(1800l);
		System.out.println("Setting color to Red");
		display.printMessageColor("Hello, Team","Red");		
		Thread.sleep(1800l);
		System.out.println("Adjust brightness of " + display.getName()+"to 3");
		display.adjustBrightness(3);
		Thread.sleep(1800l);
		System.out.println("Adjust brightness of " + display.getName()+"to 10");
		display.adjustBrightness(10);
		Thread.sleep(1800l);
		System.out.println("Adjust brightness of " + display.getName()+"to 1");
		display.adjustBrightness(1);
		Thread.sleep(1800l);
		System.out.println("Blink 3 times");
		display.blink(3);
		display.turnOff();
		
		
		try {
			lightSensor.setPortNumber("D3");
		}
		catch(PortInUseException p) {
			//p.printStackTrace();
			List<String> pInUse = PortManagement.getPortsInUse();
			for(String port: pInUse)
				System.out.println(port);
			//NOTE: logic to handle situation goes here
			//if doing UI you may offer user list of used ports and ask them to choose an open one
		}
	
		/*
		 * Sample of building CoR with LEDs
		 * */
		try {
			//set ledOne chain
			ledOne.setNextDevice(ledTwo);
			ledTwo.setNextDevice(ledThree);
			
			/*
			 * Setting LED equal to device not of type LED will throw an error
			 * */
			//ledThree.setNextDevice(display);
		} catch (IncompatibleDeviceError e1) {
			//e1.printStackTrace();
			System.out.println("Incompatible Device Error!!!");
		}

		/*
		 * Test Automate Modes
		 * */
		System.out.println("Testing automated mode for devices");
		
		try {
			ledOne.automate(lightSensor);
			ledThree.automate(lightSensor);
		}
		catch(IncompatibleSensorError ex) {
			ex.printStackTrace();
			//handle your exceptions here
		}
		Thread.sleep(1800);
		ledThree.turnOff();
		ledOne.turnOff();
		
		System.out.println("LCD testing");
		display.turnOn();
		try {
			display.automate(tempAndHumid);
		} catch (IncompatibleSensorError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread.sleep(7000);
		display.turnOff();
		/*
		 * Simple demo of how to use turnOn and turnOff
		 * Note that Thread.sleep is simply here to allow you so see the LEDs being
		 * toggled and off otherwise the program would run so fast you'd miss it!
		 * If you are not running this code on Rapsberry Pi it methods will print message
		 * telling you to run it on Raspberry Pi
		 * */
		System.out.println("Toggle " + ledOne.getName() + " On and Off");
		ledOne.turnOn();
		Thread.sleep(1800);
		ledOne.turnOff();
		Thread.sleep(1800);
		//at the moment blink speed cannot be adjusted
		System.out.println("Blinking led " + ledOne.getName());
		ledOne.blink(2);
		Thread.sleep(1800);
		
		/*
		 * Demo of adjusting brightness.  Note that by default LEDs will attempt to use next led in chain
		 * Use the setUseNext() method to adjust this behavior
		 * */
		System.out.println("Adjusting brightness of " + ledOne.getName());
		ledOne.adjustBrightness(500);
		Thread.sleep(1800);
		ledOne.adjustBrightness(1023);
		Thread.sleep(1800);
		ledOne.adjustBrightness(0);
		/*
		 * Try using adjustBrightness on ledTwo it's not set to port that can handle 
		 * pulse wave modulation. ledTwo is set to use ledThree in this situation...run it to 
		 * confirm that it works.  Using CoR it will use the next LED in the chain
		 * */
		System.out.println("Adjust brightness of " + ledTwo.getName());
		ledTwo.adjustBrightness(1023);
		Thread.sleep(1800);
		System.out.println("Again, Adjust brightness of " + ledTwo.getName());
		ledTwo.adjustBrightness(0);	
		System.out.println("Deactivate CoR");
		ledTwo.setUseNext(false);
		ledTwo.adjustBrightness(0);
	}
}