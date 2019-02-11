package edu.ccsu.interfaces;

import ccsu.edu.grovepicomponents.Sensor;
import edu.ccsu.error.IncompatibleSensorError;

/**
 * Interface to be implemented by GrovePi devices that
 * have lights
 * @author Adrian
 *
 */
public interface LightEnabledDevice extends Device{
	/**
	 * Given a brightness level between 0-1023 will adjust brightness of 
	 * light enabled devices
	 * @param brightness
	 * @return
	 */
	public void adjustBrightness(int brightness);
	
	/**
	 * Will blink for specified number of blinks
	 * @param numberOfBlinks
	 */
	public void blink(int numberOfBlinks);
	
	/**
	 * Given a sensor will run appropriate run scripts to run automated processes.
	 * For LEDs, will run script that uses given light sensor to adjust the brightness.
	 * For LCDs, will run script to display current temperature.
	 * For Fans, will run script to use current weather temperature to determine fan speed.
	 * @param sensor
	 */
	public void automate(Sensor sensor) throws IncompatibleSensorError;
}